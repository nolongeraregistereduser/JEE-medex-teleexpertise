package com.example.jeemedexteleexpertise.model;

public enum TypeActe {
    RADIOGRAPHIE("Radiographie"),
    ECHOGRAPHIE("Échographie"),
    IRM("IRM"),
    ELECTROCARDIOGRAMME("Électrocardiogramme"),
    LASER_DERMATO("Laser dermatologique"),
    FOND_OEIL("Fond d'œil"),
    ANALYSE_SANG("Analyse de sang"),
    ANALYSE_URINE("Analyse d'urine");

    private final String displayName;

    TypeActe(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
