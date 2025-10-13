package com.example.jeemedexteleexpertise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@DiscriminatorValue("SPECIALISTE")
public class Specialiste extends Utilisateur {

    @Column(name = "tarif")
    private double tarif;

    @NotBlank(message = "La spécialité ne peut pas être vide")
    @Column(name = "specialite")
    private String specialite;

    @Column(name = "duree_consultation")
    private int dureeConsultation = 30;

    @OneToMany(mappedBy = "medecinSpecialiste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Creneau> creneaux;

    @OneToMany(mappedBy = "medecinSpecialiste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DemandeExpertise> demandesExpertise;


    public Specialiste() {
        super();
    }

    public Specialiste(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
        this.dureeConsultation = 30;
    }

    public Specialiste(String nom, String prenom, String email, String motDePasse, double tarif, String specialite) {
        super(nom, prenom, email, motDePasse);
        this.tarif = tarif;
        this.specialite = specialite;
        this.dureeConsultation = 30;
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

    // Getters and Setters
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
