package com.example.jeemedexteleexpertise.model;

public enum StatusConsultation {
    EN_ATTENTE("En attente"),
    TERMINEE("Terminée"),
    EN_ATTENTE_AVIS_SPECIALISTE("En attente avis spécialiste");

    private final String displayName;

    StatusConsultation(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
