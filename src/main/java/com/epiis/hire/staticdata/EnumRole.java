package com.epiis.hire.staticdata;

public enum EnumRole {
    CANDIDATE("Candidato"),
    COMPANY("Empresa"),
    ADMIN("Administrador");

    private final String value;

    EnumRole(String value) {
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
