package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.UtilisateurDAO;
import com.example.jeemedexteleexpertise.model.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;


@Stateless
public class UtilisateurService {

    @Inject
    private UtilisateurDAO utilisateurDAO;


    // crud

    public void saveUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.save(utilisateur);
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.update(utilisateur);
    }

    public void deleteUtilisateur(Long id) {
        utilisateurDAO.delete(id);
    }

    public Utilisateur findUtilisateurById(Long id) {
        return utilisateurDAO.findById(id);
    }

    public Utilisateur authentifier(String email, String motDePasse) {
        Utilisateur utilisateur = utilisateurDAO.findByEmail(email);
        if (utilisateur != null && utilisateur.getMotDePasse().equals(motDePasse)) {
            return utilisateur;
        }
        return null;
    }

    public boolean emailExists(String email) {
        return utilisateurDAO.findByEmail(email) != null;
    }

}
