package com.kosmostecnologia.facturador.domain.service.emision;

import com.kosmostecnologia.facturador.application.request.VentaDetalleRequest;
import com.kosmostecnologia.facturador.application.request.VentaRequest;
import com.kosmostecnologia.facturador.application.rest.exception.ProcessException;
import com.kosmostecnologia.facturador.commons.CodigoModalidadEmisionEnum;
import com.kosmostecnologia.facturador.commons.CodigoTipoDocumentoFiscalEnum;
import com.kosmostecnologia.facturador.commons.CodigoTipoEmisionEnum;
import com.kosmostecnologia.facturador.config.AppConfig;
import com.kosmostecnologia.facturador.domain.CabeceraCompraVenta;
import com.kosmostecnologia.facturador.domain.DatosCUF;
import com.kosmostecnologia.facturador.domain.DetalleCompraVenta;
import com.kosmostecnologia.facturador.domain.FacturaElectronicaCompraVenta;
import com.kosmostecnologia.facturador.domain.helpers.GZIPHelper;
import com.kosmostecnologia.facturador.domain.helpers.Utils;
import com.kosmostecnologia.facturador.domain.helpers.XmlHelper;
import com.kosmostecnologia.facturador.domain.repository.*;
import com.kosmostecnologia.facturador.persistence.entity.*;
import com.kosmostecnologia.facturador.persistence.view.LeyendaView;
import org.apache.xmlbeans.XmlObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class GeneraFacturaService {

    private final AppConfig appConfig;

    private final GZIPHelper gzipHelper;

    private final IProductoServicioRepository productoServicioRepository;
    private final IFacturaRepository facturaCabeceraRepository;
    private final IPuntoVentaRepository puntoVentaRepository;
    private final ILeyendaRepository leyendaRepository;
    private final IClienteRepository clienteRepository;
    private final IItemRepository itemRepository;


    public GeneraFacturaService(AppConfig appConfig, GZIPHelper gzipHelper, IProductoServicioRepository productoServicioRepository, IFacturaRepository facturaCabeceraRepository, IPuntoVentaRepository puntoVentaRepository, ILeyendaRepository leyendaRepository, IClienteRepository clienteRepository, IItemRepository itemRepository) {
        this.appConfig = appConfig;
        this.gzipHelper = gzipHelper;
        this.productoServicioRepository = productoServicioRepository;
        this.facturaCabeceraRepository = facturaCabeceraRepository;
        this.puntoVentaRepository = puntoVentaRepository;
        this.leyendaRepository = leyendaRepository;
        this.clienteRepository = clienteRepository;
        this.itemRepository = itemRepository;
    }


    public FacturaElectronicaCompraVenta llenarDatos(VentaRequest ventaRequest , CufdEntity cufd) throws DatatypeConfigurationException {

        PuntoVentaEntity puntoVenta = this.puntoVentaRepository.findById(ventaRequest.getIdPuntoVenta())
                .orElseThrow(()-> new ProcessException("Punto de venta no encontrado"));
        ClienteEntity cliente = this.clienteRepository.findById(ventaRequest.getIdCliente())
                .orElseThrow(()-> new ProcessException("Cliente no encontrado"));

        ItemEntity  item;
        ProductoServicioEntity productoServicio;

        DetalleCompraVenta detalleCompraVenta;
        List<DetalleCompraVenta> detalleCompraVentaList = new LinkedList<>();

        BigDecimal total = BigDecimal.ZERO;

        for(VentaDetalleRequest ventaDetalleRequest : ventaRequest.getDetalle()){

            item = this.itemRepository.findById(ventaDetalleRequest.getIdProducto())
                    .orElseThrow(()-> new ProcessException("Item no encontrado"));;
            /*if (item.isEmpty()) throw new ProcessException("Item no encontrado");*/

            productoServicio = this.productoServicioRepository.findByCodigoProducto(item.getCodigoProductoSin().longValue())
                    .orElseThrow(()-> new ProcessException("Homologación de item no encontrado"));

            detalleCompraVenta = new DetalleCompraVenta.Builder().buildItem(
                    item,
                    productoServicio.getCodigoActividad(),
                    ventaDetalleRequest.getCantidad(),
                    ventaDetalleRequest.getMontoDescuento()
            ).build();

            detalleCompraVentaList.add(detalleCompraVenta);
            total = total.add(detalleCompraVenta.getSubTotal());
        }
        if(detalleCompraVentaList.isEmpty()) throw new ProcessException("Detalle no puede ser vacío");

        long numeroFactura = this.facturaCabeceraRepository.obtenerNumeroFactura();
        LeyendaView leyendaView = this.leyendaRepository.obtenerLeyenda(detalleCompraVentaList.get(0).getActividadEconomica())
                .orElseThrow(()-> new ProcessException("Leyenda no encontrada"));

        CabeceraCompraVenta cabeceraCompraVenta = new CabeceraCompraVenta.Builder()
                .buildEmpresa(puntoVenta)
                .buildCliente(cliente)
                .buildPago(total)
                .setUsuario(ventaRequest.getUsuario())
                .setNumeroFactura(numeroFactura)
                .setFechaEmision(Utils.localDateToXmlGregorianCalendar(LocalDateTime.now()))
                .setLeyenda(leyendaView.getLeyenda())
                .setNitClienteExcepcion(ventaRequest.getNitInvalido())
                .build();

        FacturaElectronicaCompraVenta facturaElectronicaCompraVenta = new FacturaElectronicaCompraVenta();
        facturaElectronicaCompraVenta.setCabecera(cabeceraCompraVenta);
        facturaElectronicaCompraVenta.setDetalle(detalleCompraVentaList);

        String cuf = obtenerCUF(facturaElectronicaCompraVenta, cufd);
        facturaElectronicaCompraVenta.getCabecera().setCufd(cufd.getCodigo());
        facturaElectronicaCompraVenta.getCabecera().setCuf(cuf);

        return facturaElectronicaCompraVenta;
    }

    private String obtenerCUF(FacturaElectronicaCompraVenta facturaElectronicaCompraVenta, CufdEntity cufd) {

        DatosCUF datosFacturaCUF = new DatosCUF();
        datosFacturaCUF.setNitEmisor(facturaElectronicaCompraVenta.getCabecera().getNitEmisor());
        datosFacturaCUF.setFechaHora(Utils.xMLGregorianCalendarToLocalDateTime(facturaElectronicaCompraVenta.getCabecera().getFechaEmision()));
        datosFacturaCUF.setSucursal(facturaElectronicaCompraVenta.getCabecera().getCodigoSucursal());
        datosFacturaCUF.setModalidad(CodigoModalidadEmisionEnum.ELECTRONICA_EN_LINEA.getValue());
        datosFacturaCUF.setTipoEmision(CodigoTipoEmisionEnum.ONLINE.getValue());
        datosFacturaCUF.setTipoFacturaDocumentoAjuste(CodigoTipoDocumentoFiscalEnum.FACTURA_CON_DERECHO_CREDITO_FISCAL.getValue());
        datosFacturaCUF.setTipoDocumentoSector(facturaElectronicaCompraVenta.getCabecera().getCodigoDocumentoSector());
        datosFacturaCUF.setNumeroFactura(facturaElectronicaCompraVenta.getCabecera().getNumeroFactura());
        datosFacturaCUF.setPuntoVenta(facturaElectronicaCompraVenta.getCabecera().getCodigoPuntoVenta());

        return Utils.obtenerCUF(datosFacturaCUF, cufd.getCodigoControl());
    }

    public byte[] obtenerArchivo(FacturaElectronicaCompraVenta factura) throws Exception {
        //TODO: 1 - Generar XML
        byte[] xml = getXmlBytes(factura);
        //TODO: 2 - Firmar XML
        byte[] xmlFirmado = firmarArchivo(xml);
        //TODO: 3 - Validar XML
        XmlObject xmlFacturaObj = XmlObject.Factory.parse(new String(xmlFirmado));
        validarContraXSD(xmlFacturaObj);
        //TODO: 4 - Comprimir XML
        return comprimirXMLFactura(xmlFirmado, factura.getCabecera().getCuf());
    }

    private byte[] getXmlBytes(FacturaElectronicaCompraVenta facturaObject) throws JAXBException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(FacturaElectronicaCompraVenta.class);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter writer = new StringWriter();
        marshaller.marshal(facturaObject, writer);

        return writer.toString().getBytes();
    }


    private byte[] firmarArchivo(byte[] xmlBytes) throws Exception {
        PrivateKey privateKey = XmlHelper.getPrivateKey(appConfig.getPathFiles() + "/resources/cert/CERT-DEV.pem"); //privateKey.pem
        X509Certificate cert =  XmlHelper.getX509Certificate(appConfig.getPathFiles() + "/resources/cert/CERT-DEV.crt"); //ende.crt

        return XmlHelper.firmarDsig(xmlBytes, privateKey, cert);
    }

    private void validarContraXSD(XmlObject xmlFacturaObj) {
        String schemaFile = appConfig.getPathFiles() + "/resources/xsd/facturaElectronicaCompraVenta.xsd";

        if(!XmlHelper.validate(schemaFile, xmlFacturaObj)) {
            throw new ProcessException("El XML no cumple las especificaciones de impuestos.");
        }
    }

    private byte[] comprimirXMLFactura(byte[] xmlFacturaFirmada, String cuf) throws Exception {
        File tempFacturaXml = escribirArchivo(xmlFacturaFirmada, cuf);
        File archivoComprimido = obtenerArchivoComprimido(tempFacturaXml);
        return convertirArchivoABytes(archivoComprimido);
    }

    private File escribirArchivo(byte[] xmlFacturaFirmada, String cuf) throws Exception {
        String contextPath = appConfig.getPathFiles();
        String fullPath = contextPath + "/facturas/" + cuf + ".xml";

        File tempFacturaXml = new File(fullPath);

        Document sourceDoc = XmlHelper.leerXML(xmlFacturaFirmada);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File(tempFacturaXml.getPath()));
        Source input = new DOMSource(sourceDoc);
        transformer.transform(input, output);
        return tempFacturaXml;
    }

    private File obtenerArchivoComprimido(File tempFacturaXml) {
        if (!gzipHelper.compress(tempFacturaXml)) {
            throw new ProcessException("No se pudo comprimir la factura");
        }
        return new File(tempFacturaXml.getPath() + ".zip");
    }

    private byte[] convertirArchivoABytes(File archivoComprimido) throws Exception {
        return Files.readAllBytes(archivoComprimido.toPath());
    }
}
