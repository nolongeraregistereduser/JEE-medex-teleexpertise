package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Utilisateur;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class UtilisateurDAO extends BaseDAO<Utilisateur, Long> {

    public UtilisateurDAO() {
        super(Utilisateur.class);
    }


    public Utilisateur findByEmail(String email) {
        String jpql = "SELECT u FROM Utilisateur u WHERE u.email = :email";
        return executeSingleResultQuery(jpql, "email", email);
    }


    public List<Utilisateur> findActiveUsers() {
        String jpql = "SELECT u FROM Utilisateur u WHERE u.actif = true";
        return executeNamedQuery(jpql, "actif", true);
    }


    public List<Utilisateur> findByRole(String role) {
        String jpql = "SELECT u FROM Utilisateur u WHERE TYPE(u) = :role";
        return executeNamedQuery(jpql, "role", role);
    }
}
