package com.kosmostecnologia.facturador.domain.service;

import bo.gob.impuestos.siat.SolicitudSincronizacion;
import com.kosmostecnologia.facturador.application.rest.exception.ProcessException;
import com.kosmostecnologia.facturador.config.AppConfig;
import com.kosmostecnologia.facturador.domain.repository.ICuisRepository;
import com.kosmostecnologia.facturador.domain.repository.IPuntoVentaRepository;
import com.kosmostecnologia.facturador.persistence.entity.CuisEntity;
import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.Optional;

@Service
public class SincronizacionCatalogosParametrosService {

    private final AppConfig appConfig;
    private final ActividadService actividadService;
    private final ActividadDocumentoSectorService actividadDocumentoSectorService;
    private final LeyendaService leyendaService;
    private final ProductoServicioService productoServicioService;
    private final ParametroService parametroService;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICuisRepository cuisRepository;

    public SincronizacionCatalogosParametrosService(AppConfig appConfig, ActividadService actividadService, ActividadDocumentoSectorService actividadDocumentoSectorService, LeyendaService leyendaService, ProductoServicioService productoServicioService, ParametroService parametroService, IPuntoVentaRepository puntoVentaRepository,
                                                    ICuisRepository cuisRepository) {
        this.appConfig = appConfig;
        this.actividadService = actividadService;
        this.actividadDocumentoSectorService = actividadDocumentoSectorService;
        this.leyendaService = leyendaService;
        this.productoServicioService = productoServicioService;
        this.parametroService = parametroService;
        this.puntoVentaRepository = puntoVentaRepository;
        this.cuisRepository = cuisRepository;
    }



    public void sincronizarCatalogos() throws RemoteException, RemoteException {

        Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findByCodigo(0);
        if (puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");

        Optional<CuisEntity> cuis = this.cuisRepository.findActual(puntoVenta.get());
        if (cuis.isEmpty()) throw new ProcessException("CUIS vigente no encontrado");

        SolicitudSincronizacion solicitudSincronizacion = this.obtenerSolicitud(puntoVenta.get(), cuis.get());

        this.actividadService.guardarCatalogos(solicitudSincronizacion);
        this.actividadDocumentoSectorService.guardarCatalogos(solicitudSincronizacion);
        this.leyendaService.guardarCatalogos(solicitudSincronizacion);
        this.productoServicioService.guardarCatalogos(solicitudSincronizacion);

    }

    public void sincronizarParametros() throws RemoteException {

        /*Optional<PuntoVentaEntity> puntoVenta = this.puntoVentaRepository.findById(0);
        if(puntoVenta.isEmpty()) throw new ProcessException("Punto venta no encontrado");*/

       PuntoVentaEntity puntoVenta = this.puntoVentaRepository.findById(0)
                .orElseThrow(() -> new ProcessException("Punto venta no encontrado"));

        /*Optional<CuisEntity>cuis = this.cuisRepository.findActual(puntoVenta.get());
        if(cuis.isEmpty()) throw new ProcessException("Punto venta no encontrado");*/

        CuisEntity cuis = this.cuisRepository.findActual(puntoVenta)
                .orElseThrow(() -> new ProcessException("Punto venta no encontrado"));

        SolicitudSincronizacion solicitudSincronizacion = this.obtenerSolicitud(puntoVenta,cuis);
        this.parametroService.guardarParametros(solicitudSincronizacion);
        //return true;
    }


    private SolicitudSincronizacion obtenerSolicitud(PuntoVentaEntity puntoVenta, CuisEntity cuis) {
        SolicitudSincronizacion solicitudSincronizacion = new SolicitudSincronizacion();
        solicitudSincronizacion.setCodigoAmbiente(this.appConfig.getCodigoAmbiente());
        solicitudSincronizacion.setCodigoPuntoVenta(puntoVenta.getCodigo());
        solicitudSincronizacion.setCodigoSistema(this.appConfig.getCodigoSistema());
        solicitudSincronizacion.setCodigoSucursal(puntoVenta.getSucursal().getCodigo());
        solicitudSincronizacion.setCuis(cuis.getCodigo());
        solicitudSincronizacion.setNit(puntoVenta.getSucursal().getEmpresa().getNit());
        return solicitudSincronizacion;
    }



}
