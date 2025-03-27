package com.kosmostecnologia.facturador.domain.service;

import bo.gob.impuestos.siat.*;
import com.kosmostecnologia.facturador.domain.repository.IActividadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class ActividadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActividadService.class);
    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private final IActividadRepository ActividadRepository;


    public ActividadService(ServicioFacturacionSincronizacion servicioFacturacionSincronizacion, IActividadRepository ActividadRepository) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
        this.ActividadRepository = ActividadRepository;
    }


    public void guardarCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaActividades respuestaListaActividades = obtenerCatalogos(solicitudSincronizacion);
        this.ActividadRepository.deleteAll();
        for(ActividadesDto actividadesDto : respuestaListaActividades.getListaActividades()){
            this.ActividadRepository.save(actividadesDto);
        }
    }

    private RespuestaListaActividades obtenerCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaActividades respuestaListaActividades = this.servicioFacturacionSincronizacion.sincronizarActividades(solicitudSincronizacion);
        if(!respuestaListaActividades.getTransaccion()){
            String mensajeError =  obtenerMensajeServicio(respuestaListaActividades.getMensajesList());
            LOGGER.error(mensajeError);
            throw new IllegalStateException("Error en sincronización de actividades");
        }
        return respuestaListaActividades;


    }

    private String obtenerMensajeServicio(MensajeServicio[] mensajeServicioList){
        StringBuilder mensaje = new StringBuilder();
        if(mensajeServicioList != null ){
            for (MensajeServicio mensajeServicio : mensajeServicioList){
                mensaje.append(mensajeServicio.getDescripcion()).append(". ");
            }
        }
        return mensaje.toString();
    }
}
