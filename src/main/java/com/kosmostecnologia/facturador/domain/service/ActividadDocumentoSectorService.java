package com.kosmostecnologia.facturador.domain.service;

import bo.gob.impuestos.siat.*;
import bo.gob.impuestos.siat.RespuestaListaActividadesDocumentoSector;
import bo.gob.impuestos.siat.SolicitudSincronizacion;
import com.kosmostecnologia.facturador.domain.repository.IActividadDocumentoSectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class ActividadDocumentoSectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActividadDocumentoSectorService.class);

    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private final IActividadDocumentoSectorRepository actividadDocumentoSectorRepository;

    public ActividadDocumentoSectorService(ServicioFacturacionSincronizacion servicioFacturacionSincronizacion, IActividadDocumentoSectorRepository actividadDocumentoSectorRepository) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
        this.actividadDocumentoSectorRepository = actividadDocumentoSectorRepository;
    }

    public void guardarCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaActividadesDocumentoSector respuestaListaActividadesDocumentoSector = obtenerActividadesDocumentosSector(solicitudSincronizacion);
        this.actividadDocumentoSectorRepository.deleteAll();
        for(ActividadesDocumentoSectorDto actividadesDocumentoSectorDto : respuestaListaActividadesDocumentoSector.getListaActividadesDocumentoSector()){
            actividadDocumentoSectorRepository.save(actividadesDocumentoSectorDto);
        }
    }


    private RespuestaListaActividadesDocumentoSector obtenerActividadesDocumentosSector(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaActividadesDocumentoSector respuestaListaActividadesDocumentoSector = servicioFacturacionSincronizacion
                .sincronizarListaActividadesDocumentoSector(solicitudSincronizacion);
        if(!respuestaListaActividadesDocumentoSector.getTransaccion()){
            String mensajeError = obtenerMensajeServicio(respuestaListaActividadesDocumentoSector.getMensajesList());
            LOGGER.error(mensajeError);
            throw new IllegalStateException("Error en sincronización de actividades");
        }
        return respuestaListaActividadesDocumentoSector;
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
