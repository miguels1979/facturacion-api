package com.kosmostecnologia.facturador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.siat.token}")
    private String token;

    @Value("${app.siat.url}")
    private String siatUrl;

    @Value("${app.codigo-sistema}")
    private String codigoSistema;

    @Value("${app.codigo-modalidad}")
    private Integer codigoModalidad;

    @Value("${app.codigo-ambiente}")
    private Integer codigoAmbiente;

    @Value("${app.path.files}")
    private String pathFiles;

    public String getToken() {
        return token;
    }

    public String getSiatUrl() {
        return siatUrl;
    }

    public String getCodigoSistema() {
        return codigoSistema;
    }

    public Integer getCodigoModalidad() {
        return codigoModalidad;
    }

    public Integer getCodigoAmbiente() {
        return codigoAmbiente;
    }

    public String getPathFiles() {
        return pathFiles;
    }
}
