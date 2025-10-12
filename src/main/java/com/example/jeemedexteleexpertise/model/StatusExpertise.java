package com.example.jeemedexteleexpertise.model;

public enum StatusExpertise {
    EN_ATTENTE("En attente"),
    TERMINEE("Terminée");

    private final String displayName;

    StatusExpertise(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
