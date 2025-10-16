package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.UtilisateurDAO;
import com.example.jeemedexteleexpertise.model.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class UtilisateurService extends BaseService<Utilisateur, Long> {

    @Inject
    private UtilisateurDAO utilisateurDAO;

    @Override
    protected BaseDAO<Utilisateur, Long> getDAO() {
        return utilisateurDAO;
    }

    @Override
    protected void performAdditionalValidation(Utilisateur utilisateur) {
        // Custom validation for Utilisateur
        if (utilisateur.getEmail() == null || utilisateur.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (utilisateur.getMotDePasse() == null || utilisateur.getMotDePasse().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        // Check if email already exists (for new users)
        if (utilisateur.getId() == null) {
            Utilisateur existingUser = utilisateurDAO.findByEmail(utilisateur.getEmail());
            if (existingUser != null) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
    }

    /**
     * Business method: Find user by email
     */
    public Utilisateur findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return utilisateurDAO.findByEmail(email);
    }

    /**
     * Business method: Find active users
     */
    public List<Utilisateur> findActiveUsers() {
        return utilisateurDAO.findActiveUsers();
    }

    /**
     * Business method: Find users by role
     */
    public List<Utilisateur> findByRole(String role) {
        return utilisateurDAO.findByRole(role);
    }

    /**
     * Business method: Authenticate user
     */
    public Utilisateur authenticate(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        Utilisateur user = findByEmail(email);
        if (user != null && user.isActif()) {
            // In a real application, you would verify the hashed password here
            // For now, this is a placeholder
            return user;
        }
        return null;
    }

    /**
     * Business method: Deactivate user
     */
    public void deactivateUser(Long userId) {
        Utilisateur user = findById(userId);
        if (user != null) {
            user.setActif(false);
            update(user);
        }
    }

    /**
     * Business method: Activate user
     */
    public void activateUser(Long userId) {
        Utilisateur user = findById(userId);
        if (user != null) {
            user.setActif(true);
            update(user);
        }
    }
}
