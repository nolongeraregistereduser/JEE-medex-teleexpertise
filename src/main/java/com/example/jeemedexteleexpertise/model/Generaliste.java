package com.example.jeemedexteleexpertise.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GENERALISTE")
public class Generaliste extends Utilisateur {

    @Column(name = "tarif")
    private double tarif = 150.0;

    public Generaliste() {
        super();
    }

    public Generaliste(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
        this.tarif = 150.0; // Default
    }

    public Generaliste(String nom, String prenom, String email, String motDePasse, double tarif) {
        super(nom, prenom, email, motDePasse);
        this.tarif = tarif;
    }

    @Override
    public Role getRole() {
        return Role.GENERALISTE;
    }

    // Getters and Setters
    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }
}
