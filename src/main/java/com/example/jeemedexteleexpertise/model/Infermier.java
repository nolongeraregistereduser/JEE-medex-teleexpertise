package com.example.jeemedexteleexpertise.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INFERMIER")

public class Infermier extends Utilisateur {


    public Infermier() {
        super();
    }

    public Infermier(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }

    public Role getRole() {
        return Role.INFERMIER;
    }



}
