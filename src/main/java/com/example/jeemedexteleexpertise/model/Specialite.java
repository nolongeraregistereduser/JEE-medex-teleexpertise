package com.example.jeemedexteleexpertise.model;

public enum Specialite {
    CARDIOLOGIE("Cardiologie"),
    DERMATOLOGIE("Dermatologie"),
    NEUROLOGIE("Neurologie"),
    PEDIATRIE("Pédiatrie"),
    RADIOLOGIE("Radiologie"),
    ORTHOPEDIE("Orthopédie"),
    GYNECOLOGIE("Gynécologie"),
    PSYCHIATRIE("Psychiatrie"),
    ONCOLOGIE("Oncologie"),
    UROLOGIE("Urologie");

    private final String displayName;

    Specialite(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
