package com.example.jeemedexteleexpertise.model;

public enum StatusCreneau {
    DISPONIBLE("Disponible"),
    RESERVE("Réservé"),
    ARCHIVE("Archivé");

    private final String displayName;

    StatusCreneau(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
