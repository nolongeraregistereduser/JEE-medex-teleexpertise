package com.example.jeemedexteleexpertise.model;

public enum Role {
    ADMIN("Administrateur"),
    GENERALISTE("Médecin Généraliste"),
    SPECIALISTE("Médecin Spécialiste"),
    INFIRMIER("Infirmier(ère)");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
