package com.example.jeemedexteleexpertise.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SPECIALISTE")
public class Specialiste extends Utilisateur {

    @Column(name = "tarif")
    private double tarif;

    @Column(name = "specialite")
    private String specialite;

    @Column(name = "duree_consultation")
    private int dureeConsultation = 30;

    public Specialiste() {
        super();
    }

    public Specialiste(String nom, String prenom, String email, String motDePasse, double tarif, String specialite, int dureeConsultation) {
        super(nom, prenom, email, motDePasse);
        this.tarif = tarif;
        this.specialite = specialite;
        this.dureeConsultation = dureeConsultation;
    }

    @Override
    public Role getRole() {
        return Role.SPECIALISTE;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public int getDureeConsultation() {
        return dureeConsultation;
    }

    public void setDureeConsultation(int dureeConsultation) {
        this.dureeConsultation = dureeConsultation;
    }
}
