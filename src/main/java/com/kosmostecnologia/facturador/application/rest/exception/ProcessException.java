package com.kosmostecnologia.facturador.application.rest.exception;

import java.util.List;


public class ProcessException extends RuntimeException{

    private final List<String> errors;

    public ProcessException(String message) {
        super(message);
        this.errors = null;
    }

    public ProcessException(String message, List<String> errors) {
        super(message);
        this.errors=errors;
    }

    public List<String> getErrors() {
        return errors;
    }

}
