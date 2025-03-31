package com.kosmostecnologia.facturador.commons;

public enum CodigoTipoEmisionEnum {
    ONLINE(1);

    private final int value;

    CodigoTipoEmisionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
