package com.example.jeemedexteleexpertise.model;

public enum TypeActe {
    RADIOGRAPHIE("Radiographie", 150.0),
    ECHOGRAPHIE("Échographie", 200.0),
    IRM("IRM", 800.0),
    ELECTROCARDIOGRAMME("Électrocardiogramme", 100.0),
    LASER_DERMATO("Laser Dermato", 300.0),
    FOND_OEIL("Fond d'œil", 120.0),
    ANALYSE_SANG("Analyse de sang", 80.0),
    ANALYSE_URINE("Analyse d'urine", 60.0);

    private final String displayName;
    private final double cout;

    TypeActe(String displayName, double cout) {
        this.displayName = displayName;
        this.cout = cout;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getCout() {
        return cout;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
