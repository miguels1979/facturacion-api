package com.kosmostecnologia.facturador.config;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.client.Call;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bo.gob.impuestos.siat.ServicioFacturacionCodigos;
import bo.gob.impuestos.siat.FacturacionCodigos.ServicioFacturacionCodigosSoapBindingStub;
import bo.gob.impuestos.siat.FacturacionCodigos.ServicioFacturacionCodigosLocator;
import bo.gob.impuestos.siat.FacturacionSincronizacion.ServicioFacturacionSincronizacion;
import bo.gob.impuestos.siat.FacturacionSincronizacion.ServicioFacturacionSincronizacionLocator;
import bo.gob.impuestos.siat.FacturacionSincronizacion.ServicioFacturacionSincronizacionSoapBindingStub;
import bo.gob.impuestos.siat.ServicioFacturacionCompraVenta.ServicioFacturacion;
import bo.gob.impuestos.siat.ServicioFacturacionCompraVenta.ServicioFacturacionLocator;
import bo.gob.impuestos.siat.ServicioFacturacionCompraVenta.ServicioFacturacionSoapBindingStub;


import javax.xml.soap.MimeHeaders;
import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class ImpuestosClientConfig {

    @Value("{api.siat.token}")
    private String token;

    @Bean
    ServicioFacturacionCodigos servicioFacturacionCodigos() throws AxisFault, MalformedURLException{
        bo.gob.impuestos.siat.FacturacionCodigos.ServicioFacturacionCodigos service = new ServicioFacturacionCodigosLocator(){
            @Override
            public Call createCall(){
                _call =  new Call(this){
                    @Override
                    public void setRequestMessage(Message msg){
                        super.setRequestMessage(msg);
                        MimeHeaders mimeHeaders = msg.getMimeHeaders();
                        mimeHeaders.addHeader("apiKey", "TokenApi " + token);
                    }
                };
                return _call;
            }
        };
        return new ServicioFacturacionCodigosSoapBindingStub(new URL(service.getServicioFacturacionCodigosPortAddress()),service);
    }


    @Bean
    bo.gob.impuestos.siat.ServicioFacturacionSincronizacion servicioFacturacionSincronizacion() throws AxisFault, MalformedURLException{
        ServicioFacturacionSincronizacion service = new ServicioFacturacionSincronizacionLocator(){

            @Override
            public Call createCall() {
                _call = new Call(this) {
                    @Override
                    public void setRequestMessage(Message msg) {
                        super.setRequestMessage(msg);

                        MimeHeaders mimeHeaders = msg.getMimeHeaders();
                        mimeHeaders.addHeader("apiKey", "TokenApi " + token);
                    }
                };
                return _call;
            }

        };
        return new ServicioFacturacionSincronizacionSoapBindingStub(new URL(service.getServicioFacturacionSincronizacionPortAddress()), service);
    }
    @Bean
    public bo.gob.impuestos.siat.ServicioFacturacion servicioFacturacion() throws AxisFault, MalformedURLException {
        ServicioFacturacion service = new ServicioFacturacionLocator() {
            @Override
            public Call createCall() {
                _call = new Call(this) {
                    @Override
                    public void setRequestMessage(Message msg) {
                        super.setRequestMessage(msg);

                        MimeHeaders mimeHeaders = msg.getMimeHeaders();
                        mimeHeaders.addHeader("apiKey", "TokenApi " + token);
                    }
                };
                return _call;
            }
        };
        return new ServicioFacturacionSoapBindingStub(new URL(service.getServicioFacturacionPortAddress()), service);
    }



}
