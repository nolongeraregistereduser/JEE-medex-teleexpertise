package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.UtilisateurDAO;
import com.example.jeemedexteleexpertise.model.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UtilisateurService extends BaseService<Utilisateur, Long> {

    private final UtilisateurDAO utilisateurDAO;

    public UtilisateurService() {
        this.utilisateurDAO = new UtilisateurDAO();
    }

    @Override
    protected BaseDAO<Utilisateur, Long> getDAO() {
        return utilisateurDAO;
    }

    public Optional<Utilisateur> authenticate(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        if (password == null || password.trim().isEmpty()) {
            return Optional.empty();
        }

        Optional<Utilisateur> userOpt = utilisateurDAO.findByEmail(email);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        Utilisateur user = userOpt.get();

        if (!user.isActif()) {
            return Optional.empty();
        }

        if (BCrypt.checkpw(password, user.getMotDePasse())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    public Optional<Utilisateur> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        return utilisateurDAO.findByEmail(email);
    }

    public List<Utilisateur> findAllActive() {
        return utilisateurDAO.findAllActive();
    }

    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return utilisateurDAO.existsByEmail(email);
    }

    public <T extends Utilisateur> List<T> findByType(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        return utilisateurDAO.findByType(type);
    }

    public String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    @Override
    public Utilisateur save(Utilisateur entity) {
        validateEntity(entity);

        if (entity.getEmail() == null || entity.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (entity.getId() == null && existsByEmail(entity.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (entity.getMotDePasse() != null && !entity.getMotDePasse().startsWith("$2a$")) {
            entity.setMotDePasse(hashPassword(entity.getMotDePasse()));
        }

        return super.save(entity);
    }
}

