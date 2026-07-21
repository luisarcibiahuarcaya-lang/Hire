package com.epiis.hire.staticdata;

public enum EnumJobOfferStatus {
    DRAFT("Borrador"),
    PUBLISHED("Activa"),
    PAUSED("Pausada"),
    CLOSED("Cerrada");

    private final String value;

    EnumJobOfferStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public String getLabel() {
        return this.value;
    }
}
