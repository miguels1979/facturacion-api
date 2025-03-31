package com.kosmostecnologia.facturador.domain.service.emision;

import bo.gob.impuestos.siat.*;
import com.kosmostecnologia.facturador.application.rest.exception.ProcessException;
import com.kosmostecnologia.facturador.commons.CodigoDocumentoSectorEnum;
import com.kosmostecnologia.facturador.commons.CodigoModalidadEmisionEnum;
import com.kosmostecnologia.facturador.commons.CodigoTipoDocumentoFiscalEnum;
import com.kosmostecnologia.facturador.commons.CodigoTipoEmisionEnum;
import com.kosmostecnologia.facturador.config.AppConfig;
import com.kosmostecnologia.facturador.domain.helpers.Utils;
import com.kosmostecnologia.facturador.domain.repository.ICuisRepository;
import com.kosmostecnologia.facturador.persistence.entity.CufdEntity;
import com.kosmostecnologia.facturador.persistence.entity.CuisEntity;
import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EnvioFacturaService {

    private final AppConfig appConfig;
    private final ServicioFacturacion clientSiatFacturacionCompraVenta;
    private final ICuisRepository cuisRepository;

    public EnvioFacturaService(AppConfig appConfig, ServicioFacturacion clientSiatFacturacionCompraVenta, ICuisRepository cuisRepository) {
        this.appConfig = appConfig;
        this.clientSiatFacturacionCompraVenta = clientSiatFacturacionCompraVenta;
        this.cuisRepository = cuisRepository;
    }

    public RespuestaRecepcion enviar (PuntoVentaEntity puntoVenta, CufdEntity cufd, byte[] comprimidoByte) throws RemoteException {
        RespuestaComunicacion respuestaComunicacion =  clientSiatFacturacionCompraVenta.verificarComunicacion();
        if(!respuestaComunicacion.getTransaccion()){
            throw new ProcessException("No se pudo conectar con los servidores del S.I.N");
        }

        CuisEntity cuis = this.cuisRepository.findActual(puntoVenta).orElseThrow(()-> new ProcessException("Cuis vigente no encontrado."));
        String sha2 = Utils.obtenerHash(comprimidoByte);

        SolicitudRecepcionFactura solicitudRecepcionFactura = new SolicitudRecepcionFactura();
        solicitudRecepcionFactura.setCodigoAmbiente(appConfig.getCodigoAmbiente());
        solicitudRecepcionFactura.setCodigoPuntoVenta(puntoVenta.getCodigo());
        solicitudRecepcionFactura.setCodigoSistema(appConfig.getCodigoSistema());
        solicitudRecepcionFactura.setCodigoSucursal(puntoVenta.getSucursal().getCodigo());
        solicitudRecepcionFactura.setNit(puntoVenta.getSucursal().getEmpresa().getNit());
        solicitudRecepcionFactura.setCodigoDocumentoSector(CodigoDocumentoSectorEnum.COMPRA_VENTA.getValue());
        solicitudRecepcionFactura.setCodigoEmision(CodigoTipoEmisionEnum.ONLINE.getValue());
        solicitudRecepcionFactura.setCodigoModalidad(CodigoModalidadEmisionEnum.ELECTRONICA_EN_LINEA.getValue());
        solicitudRecepcionFactura.setCufd(cufd.getCodigo());
        solicitudRecepcionFactura.setCuis(cuis.getCodigo());
        solicitudRecepcionFactura.setTipoFacturaDocumento(CodigoTipoDocumentoFiscalEnum.FACTURA_CON_DERECHO_CREDITO_FISCAL.getValue());
        solicitudRecepcionFactura.setArchivo(comprimidoByte);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        solicitudRecepcionFactura.setFechaEnvio((LocalDateTime.now()).format(formatter));
        solicitudRecepcionFactura.setHashArchivo(sha2);

        RespuestaRecepcion respuestaRecepcion = this.clientSiatFacturacionCompraVenta.recepcionFactura(solicitudRecepcionFactura);

        if (respuestaRecepcion != null && respuestaRecepcion.getCodigoEstado() != 908) {
            StringBuilder mensajes = new StringBuilder();
            for (MensajeRecepcion mensajeRecepcion : respuestaRecepcion.getMensajesList()) {
                mensajes.append(mensajeRecepcion.getDescripcion()).append(". ");
            }
            throw new ProcessException(mensajes.toString());
        }
        return respuestaRecepcion;
    }
}
