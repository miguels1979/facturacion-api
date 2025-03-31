package com.kosmostecnologia.facturador.commons;

public enum CodigoAmbienteEnum {

    PRODUCCION(1),
    PRUEBAS_PILOTO(2);

    private final int value;


    CodigoAmbienteEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
