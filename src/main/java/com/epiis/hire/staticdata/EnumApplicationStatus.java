package com.epiis.hire.staticdata;

public enum EnumApplicationStatus {
    PENDING("Pendiente"),
    INTERVIEW("Entrevista"),
    HIRED("Contratado"),
    REJECTED("Rechazado");

    private final String value;

    EnumApplicationStatus(String value) {
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
