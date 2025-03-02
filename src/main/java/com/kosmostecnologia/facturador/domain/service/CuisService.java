package com.kosmostecnologia.facturador.domain.service;

import bo.gob.impuestos.siat.MensajeServicio;
import bo.gob.impuestos.siat.RespuestaCuis;
import bo.gob.impuestos.siat.ServicioFacturacionCodigos;
import bo.gob.impuestos.siat.SolicitudCuis;
import com.kosmostecnologia.facturador.application.rest.exception.ProcessException;
import com.kosmostecnologia.facturador.config.AppConfig;
import com.kosmostecnologia.facturador.domain.repository.ICuisRepository;
import com.kosmostecnologia.facturador.domain.repository.IPuntoVentaRepository;
import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;


@Service
public class CuisService {
    /*
    En la capa de dominio no interesa como se obtienen los datos de la capa de persistencia
     */

    private final AppConfig appConfig;

    private final IPuntoVentaRepository puntoVentaRepository;

    private final ServicioFacturacionCodigos servicioFacturacionCodigos;

    private final ICuisRepository cuisRepository;

    public CuisService(IPuntoVentaRepository puntoVentaRepository, AppConfig appConfig,
                       ServicioFacturacionCodigos servicioFacturacionCodigos, ICuisRepository cuisRepository){
        this.puntoVentaRepository = puntoVentaRepository;
        this.appConfig = appConfig;
        this.servicioFacturacionCodigos = servicioFacturacionCodigos;
        this.cuisRepository =  cuisRepository;
    }

    public void obtenerCuis(Integer idPuntoVenta) throws RemoteException {
        PuntoVentaEntity puntoVenta = this.puntoVentaRepository.findById(idPuntoVenta)
                .orElseThrow(()-> new ProcessException("Punto de venta no encontrado"));

        SolicitudCuis solicitudCuis =  new SolicitudCuis();
        solicitudCuis.setCodigoAmbiente(this.appConfig.getCodigoAmbiente());
        solicitudCuis.setCodigoModalidad(this.appConfig.getCodigoModalidad());
        solicitudCuis.setCodigoSistema(this.appConfig.getCodigoSistema());
        solicitudCuis.setNit(puntoVenta.getSucursal().getEmpresa().getNit());
        solicitudCuis.setCodigoSucursal(puntoVenta.getSucursal().getCodigo());
        solicitudCuis.setCodigoPuntoVenta(puntoVenta.getCodigo());

        RespuestaCuis respuestaCuis = this.servicioFacturacionCodigos.cuis(solicitudCuis);

        if(!respuestaCuis.getTransaccion() && respuestaCuis.getMensajesList() !=null){
            StringBuilder mensajes = new StringBuilder();
            for(MensajeServicio mensajeServicio : respuestaCuis.getMensajesList()){
                    mensajes.append(mensajeServicio.getDescripcion()).append(" ");
            }
                throw new ProcessException(mensajes.toString().trim());
        }
        this.cuisRepository.save(respuestaCuis,puntoVenta);
    }
}
