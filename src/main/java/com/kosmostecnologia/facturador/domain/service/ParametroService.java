package com.kosmostecnologia.facturador.domain.service;

import bo.gob.impuestos.siat.*;
import com.kosmostecnologia.facturador.commons.ParametricaEnum;
import com.kosmostecnologia.facturador.domain.repository.IParametroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class ParametroService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParametroService.class);

    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private final IParametroRepository parametroRepository;

    public ParametroService(ServicioFacturacionSincronizacion servicioFacturacionSincronizacion, IParametroRepository iParametroRepository) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
        this.parametroRepository = iParametroRepository;
    }

    public void guardarParametros(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        this.parametroRepository.deleteAll();
        for(ParametricaEnum parametricaEnum : ParametricaEnum.values()){
            obtenerParametros(solicitudSincronizacion,parametricaEnum);
        }
    }

    private void obtenerParametros(SolicitudSincronizacion solicitudSincronizacion,
                                   ParametricaEnum parametricaEnum) throws RemoteException {
        RespuestaListaParametricas respuestaListaParametricas = switch (parametricaEnum) {
            case EVENTOS_SIGNIFICATIVOS ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaEventosSignificativos(solicitudSincronizacion);
            case MOTIVO_ANULACION ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaMotivoAnulacion(solicitudSincronizacion);
            case PAIS_ORIGEN ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaPaisOrigen(solicitudSincronizacion);
            case TIPO_DOCUMENTO_IDENTIDAD ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaTipoDocumentoIdentidad(solicitudSincronizacion);
            case TIPO_DOCUMENTO_SECTOR ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaTipoDocumentoSector(solicitudSincronizacion);
            case TIPO_EMISION ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaTipoEmision(solicitudSincronizacion);
            case TIPO_HABITACION ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaTipoHabitacion(solicitudSincronizacion);
            case TIPO_METODO_PAGO ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaTipoMetodoPago(solicitudSincronizacion);
            case TIPO_MONEDA ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaTipoMoneda(solicitudSincronizacion);
            case TIPOS_FACTURA ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaTiposFactura(solicitudSincronizacion);
            case UNIDAD_MEDIDA ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaUnidadMedida(solicitudSincronizacion);
            case TIPO_PUNTO_VENTA ->
                    this.servicioFacturacionSincronizacion.sincronizarParametricaTipoPuntoVenta(solicitudSincronizacion);
            default -> throw new IllegalStateException("Tipo de parámetro no soportado");
        };
        if(!respuestaListaParametricas.getTransaccion()){
            LOGGER.error(obtenerMensajeServicio(respuestaListaParametricas.getMensajesList()));
        }
        for(ParametricasDto parametricasDto : respuestaListaParametricas.getListaCodigos()){
            this.parametroRepository.save(parametricasDto, parametricaEnum);
        }
    }

    private String obtenerMensajeServicio(MensajeServicio [] mensajeServicioList){
        StringBuilder mensaje =  new StringBuilder();
        if(mensajeServicioList != null ){
            for(MensajeServicio mensajeServicio :  mensajeServicioList){
                mensaje.append(mensajeServicio.getDescripcion()).append(". ");
            }
        }
        return mensaje.toString();
    }
}
