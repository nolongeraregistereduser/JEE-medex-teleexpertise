package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.model.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class AuthService {


    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }


    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }


    public Utilisateur authenticate(String email, String password) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("medex-pu");
            em = emf.createEntityManager();

            // Find user by email
            TypedQuery<Utilisateur> query = em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class);
            query.setParameter("email", email);
            List<Utilisateur> results = query.getResultList();

            if (results.isEmpty()) {
                return null; // User not found
            }

            Utilisateur user = results.get(0);

            if (!user.isActif()) {
                return null; // User is inactive
            }

            // Verify password
            if (verifyPassword(password, user.getMotDePasse())) {
                return user; // Authentication successful
            }

            return null; // Wrong password
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Error during authentication
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }

    public String getDashboardUrl(Utilisateur user) {
        switch (user.getRole()) {
            case GENERALISTE:
                return "generaliste/dashboard";
            case SPECIALISTE:
                return "specialiste/dashboard";
            case INFIRMIER:
                return "infirmier/dashboard";
            case ADMIN:
                return "admin/dashboard";
            default:
                return "login";
        }
    }
}
