package com.example.jeemedexteleexpertise.model;

public enum PrioriteExpertise {
    URGENTE("Urgente"),
    NORMALE("Normale"),
    NON_URGENTE("Non urgente");

    private final String displayName;

    PrioriteExpertise(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
