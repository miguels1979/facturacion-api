package com.kosmostecnologia.facturador.config;

import org.apache.axis.AxisFault;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration
public class ImpuestosClientConfig {

    @Value("{api.siat.token}")
    private String token;

    bo.gob.impuestos.siat.ServicioFacturacionCodigos servicioFacturacionCodigos() throws AxisFault, MalformedURLException{

        bo.gob.impuestos.siat.FacturacionCodigos.ServicioFacturacionCodigos service = new bo.gob.impuestos.siat.FacturacionCodigos.ServicioFacturacionCodigosLocator(){



        };

    }
}
