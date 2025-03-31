package com.kosmostecnologia.facturador.domain.service;

import bo.gob.impuestos.siat.RespuestaRecepcion;
import com.kosmostecnologia.facturador.application.request.VentaRequest;
import com.kosmostecnologia.facturador.application.response.FacturaResponse;
import com.kosmostecnologia.facturador.application.rest.exception.ProcessException;
import com.kosmostecnologia.facturador.domain.FacturaElectronicaCompraVenta;
import com.kosmostecnologia.facturador.domain.repository.ICufdRepository;
import com.kosmostecnologia.facturador.domain.repository.IPuntoVentaRepository;
import com.kosmostecnologia.facturador.domain.service.emision.EnvioFacturaService;
import com.kosmostecnologia.facturador.domain.service.emision.GeneraFacturaService;
import com.kosmostecnologia.facturador.persistence.entity.CufdEntity;
import com.kosmostecnologia.facturador.persistence.entity.PuntoVentaEntity;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;

@Service
public class FacturacionService {

    private final GeneraFacturaService generaFacturaService;
    private final EnvioFacturaService envioFacturaService;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ICufdRepository cufdRepository;

    public FacturacionService(GeneraFacturaService generaFacturaService, EnvioFacturaService envioFacturaService, IPuntoVentaRepository puntoVentaRepository, ICufdRepository cufdRepository) {
        this.generaFacturaService = generaFacturaService;
        this.envioFacturaService = envioFacturaService;
        this.puntoVentaRepository = puntoVentaRepository;
        this.cufdRepository = cufdRepository;
    }

    public FacturaResponse emitirFactura (VentaRequest ventaRequest) throws Exception {
        PuntoVentaEntity puntoVenta = this.puntoVentaRepository.findById(ventaRequest.getIdPuntoVenta()).orElseThrow(()-> new ProcessException("Punto venta no encontrado"));
        CufdEntity cufd = this.cufdRepository.findActual(puntoVenta).orElseThrow(()-> new ProcessException("Cufd vigente no encontrado"));

        FacturaElectronicaCompraVenta factura = this.generaFacturaService.llenarDatos(ventaRequest,cufd);
        byte[] xmlComprimidoZip =  this.generaFacturaService.obtenerArchivo(factura);
        RespuestaRecepcion respuestaRecepcion = this.envioFacturaService.enviar(puntoVenta,cufd,xmlComprimidoZip);

        FacturaResponse facturaResponse = new FacturaResponse();
        facturaResponse.setCodigoEstado(respuestaRecepcion.getCodigoEstado());
        facturaResponse.setCuf(factura.getCabecera().getCuf());
        facturaResponse.setNumeroFactura(factura.getCabecera().getNumeroFactura());

        return facturaResponse;
    }
}
