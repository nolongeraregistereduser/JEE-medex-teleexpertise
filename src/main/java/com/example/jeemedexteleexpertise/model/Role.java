package com.example.jeemedexteleexpertise.model;

public enum Role {
    INFIRMIER("Infirmier"),
    GENERALISTE("Généraliste"),
    SPECIALISTE("Spécialiste"),
    ADMIN("Administrateur");

    private final String displayName;

    Role(String displayName) {
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
