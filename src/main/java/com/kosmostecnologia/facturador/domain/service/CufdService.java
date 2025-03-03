package com.kosmostecnologia.facturador.domain.service;

import bo.gob.impuestos.siat.MensajeServicio;
import bo.gob.impuestos.siat.RespuestaCufd;
import bo.gob.impuestos.siat.ServicioFacturacionCodigos;
import bo.gob.impuestos.siat.SolicitudCufd;
import com.kosmostecnologia.facturador.application.rest.exception.ProcessException;
import com.kosmostecnologia.facturador.config.AppConfig;
import com.kosmostecnologia.facturador.domain.repository.ICufdRepository;
import com.kosmostecnologia.facturador.domain.repository.ICuisRepository;
import com.kosmostecnologia.facturador.domain.repository.IPuntoVentaRepository;
import com.kosmostecnologia.facturador.persistence.entity.CuisEntity;
import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class CufdService {

    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICuisRepository cuisRepository;
    private final AppConfig appConfig;
    private final ServicioFacturacionCodigos servicioFacturacionCodigos;
    private final ICufdRepository cufdRepository;

    public CufdService(IPuntoVentaRepository puntoVentaRepository, ICuisRepository cuisRepository,
                       AppConfig appConfig, ServicioFacturacionCodigos servicioFacturacionCodigos,
                       ICufdRepository cufdRepository) {
        this.puntoVentaRepository = puntoVentaRepository;
        this.cuisRepository = cuisRepository;
        this.appConfig = appConfig;
        this.servicioFacturacionCodigos = servicioFacturacionCodigos;
        this.cufdRepository =  cufdRepository;
    }

    public void obtenerCufd(Integer idPuntoVenta) throws RemoteException {
        PuntoVentaEntity puntoVenta = this.puntoVentaRepository.findById(idPuntoVenta)
                .orElseThrow(()-> new ProcessException("Punto de venta no encontrado"));
        CuisEntity cuis = this.cuisRepository.findActual(puntoVenta)
                .orElseThrow(()-> new ProcessException("CUIS vigente no encontrado"));

        SolicitudCufd solicitudCufd =  new SolicitudCufd();
        solicitudCufd.setCodigoAmbiente(this.appConfig.getCodigoAmbiente());
        solicitudCufd.setCodigoModalidad(this.appConfig.getCodigoModalidad());
        solicitudCufd.setCodigoSistema(this.appConfig.getCodigoSistema());
        solicitudCufd.setNit(puntoVenta.getSucursal().getEmpresa().getNit());
        solicitudCufd.setCodigoSucursal(puntoVenta.getSucursal().getCodigo());
        solicitudCufd.setCodigoPuntoVenta(puntoVenta.getCodigo());
        solicitudCufd.setCuis(cuis.getCodigo());

        RespuestaCufd respuestaCufd =  this.servicioFacturacionCodigos.cufd(solicitudCufd);

        if(!respuestaCufd.getTransaccion() && respuestaCufd.getMensajesList() != null){
            StringBuilder mensajes = new StringBuilder();
            for(MensajeServicio mensajeServicio : respuestaCufd.getMensajesList()){
                mensajes.append(mensajeServicio.getDescripcion()).append(" ");
            }
            throw new ProcessException(mensajes.toString().trim());
        }

        this.cufdRepository.save(respuestaCufd,puntoVenta);
    }

}
