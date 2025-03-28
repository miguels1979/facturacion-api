package com.kosmostecnologia.facturador.domain.service;

import bo.gob.impuestos.siat.*;
import com.kosmostecnologia.facturador.domain.repository.ILeyendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class LeyendaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeyendaService.class);
    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private final ILeyendaRepository leyendaRepository;

    public LeyendaService(ServicioFacturacionSincronizacion servicioFacturacionSincronizacion, ILeyendaRepository leyendaRepository) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
        this.leyendaRepository = leyendaRepository;
    }


    public void guardarCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaParametricasLeyendas listaParametricasLeyendas = obtenerCatalogos(solicitudSincronizacion);
        this.leyendaRepository.deleteAll();
        for(ParametricaLeyendasDto parametricaLeyendasDto : listaParametricasLeyendas.getListaLeyendas()){
            this.leyendaRepository.save(parametricaLeyendasDto);
        }
    }


    private RespuestaListaParametricasLeyendas obtenerCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaParametricasLeyendas respuestaListaParametricasLeyendas = servicioFacturacionSincronizacion
                .sincronizarListaLeyendasFactura(solicitudSincronizacion);
        if(!respuestaListaParametricasLeyendas.getTransaccion()){
            String mensajeError = obtenerMensajeServicio(respuestaListaParametricasLeyendas.getMensajesList());
            LOGGER.error(mensajeError);
            throw new IllegalStateException("Error en sincronización de actividades");
        }
        return respuestaListaParametricasLeyendas;
    }


    private String obtenerMensajeServicio(MensajeServicio[] mensajeServiciosList) {
        StringBuilder mensaje = new StringBuilder();
        if (mensajeServiciosList != null) {
            for (MensajeServicio mensajeServicio : mensajeServiciosList) {
                mensaje.append(mensajeServicio.getDescripcion()).append(". ");
            }
        }
        return mensaje.toString();
    }
}