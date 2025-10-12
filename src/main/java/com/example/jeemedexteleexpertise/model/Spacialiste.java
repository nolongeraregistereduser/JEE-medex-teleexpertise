package com.example.jeemedexteleexpertise.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SPACIALISTE")
public class Spacialiste extends Utilisateur {

    private double tarif;
    private String specialite;
    private int dureeConsultation; // en minutes

    public Spacialiste() {
        super();
    }

    public Spacialiste(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
        this.tarif = 0.0;
        this.specialite = specialite;
        this.dureeConsultation = 30; // Durée par défaut de 30 minutes
    }

    public Role getRole() {
        return Role.SPECIALISTE;
    }

}
