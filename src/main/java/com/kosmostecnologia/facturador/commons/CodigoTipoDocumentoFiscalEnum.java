package com.kosmostecnologia.facturador.commons;

public enum CodigoTipoDocumentoFiscalEnum {

    FACTURA_CON_DERECHO_CREDITO_FISCAL(1);

    private final int value;

    CodigoTipoDocumentoFiscalEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
