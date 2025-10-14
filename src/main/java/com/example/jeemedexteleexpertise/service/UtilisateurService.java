package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.UtilisateurDAO;
import com.example.jeemedexteleexpertise.model.*;
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

        if ("generaliste@medex.com".equals(email) && "password".equals(motDePasse)) {
            Generaliste user = new Generaliste();
            user.setId(0L);
            user.setNom("Generaliste");
            user.setPrenom("Generaliste");
            user.setEmail(email);
            user.setMotDePasse(motDePasse);
            user.setRole(Role.GENERALISTE);
            return user;
        } else if ("specialiste@medex.com".equals(email) && "password".equals(motDePasse)) {
            Specialiste user = new Specialiste();
            user.setId(1L);
            user.setEmail(email);
            user.setNom("Specialiste");
            user.setPrenom("Specialiste");
            user.setMotDePasse(motDePasse);

            user.setRole(Role.SPECIALISTE);
            return user;

        } else if ("infermier@medex.com".equals(email) && "password".equals(motDePasse)) {
            Infermier user = new Infermier();
            user.setId(2L);
            user.setEmail(email);
            user.setNom("Infermier");
            user.setPrenom("Infermier");
            user.setMotDePasse(motDePasse);

            user.setRole(Role.INFERMIER);
            return user;
        }
        return null; // auth faild

    }

    public boolean emailExists(String email) {
        return utilisateurDAO.findByEmail(email) != null;
    }

}
