package com.example.jeemedexteleexpertise.model;

public enum StatusFileAttente {
    EN_ATTENTE("En attente"),
    PRIS_EN_CHARGE("Pris en charge");

    private final String displayName;

    StatusFileAttente(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
