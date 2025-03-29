package com.kosmostecnologia.facturador.application.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter //Clase base de respuesta para excepciones.
public class ExceptionResponse {

    private final String message;
    private List<String> details;

    public ExceptionResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

    public ExceptionResponse(String message){
        this.message = message;
    }
}
