package com.kosmostecnologia.facturador.domain.service;

import bo.gob.impuestos.siat.*;
import com.kosmostecnologia.facturador.domain.repository.IProductoServicioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class ProductoServicioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoServicioService.class);
    private final ServicioFacturacionSincronizacion servicioFacturacionSincronizacion;
    private final IProductoServicioRepository productoServicioRepository;

    public ProductoServicioService(ServicioFacturacionSincronizacion servicioFacturacionSincronizacion, IProductoServicioRepository productoServicioRepository) {
        this.servicioFacturacionSincronizacion = servicioFacturacionSincronizacion;
        this.productoServicioRepository = productoServicioRepository;
    }

    public void guardarCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException{
        RespuestaListaProductos respuestaListaProductos = obtenerCatalogos(solicitudSincronizacion);
        this.productoServicioRepository.deleteAll();
        for(ProductosDto productosDto : respuestaListaProductos.getListaCodigos()){
            this.productoServicioRepository.save(productosDto);
        }
    }

    private RespuestaListaProductos obtenerCatalogos(SolicitudSincronizacion solicitudSincronizacion) throws RemoteException {
        RespuestaListaProductos respuestaListaProductos = servicioFacturacionSincronizacion
                .sincronizarListaProductosServicios(solicitudSincronizacion);
        if(!respuestaListaProductos.getTransaccion()){
            String mensajeError =  obtnenerMensajeServicio(respuestaListaProductos.getMensajesList());
            LOGGER.error(mensajeError);
            throw new IllegalStateException("Error en sincronización de actividades");
        }
        return respuestaListaProductos;
    }

    private String obtnenerMensajeServicio (MensajeServicio [] mensajeServiciosList){
        StringBuilder mensaje =  new StringBuilder();
        if(mensajeServiciosList != null ){
            for(MensajeServicio mensajeServicio : mensajeServiciosList){
                mensaje.append(mensajeServicio.getDescripcion()).append(". ");
            }
        }
        return mensaje.toString();
    }


}
