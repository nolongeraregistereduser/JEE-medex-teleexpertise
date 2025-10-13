package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Role;
import com.example.jeemedexteleexpertise.model.Utilisateur;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import java.util.List;
public class UtilisateurDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Utilisateur utilisateur) {
        entityManager.persist(utilisateur);
    }

    @Transactional
    public void update(Utilisateur utilisateur) {
        entityManager.merge(utilisateur);
    }

    @Transactional
    public void delete(Long id) {
        Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
        if (utilisateur != null) {
            entityManager.remove(utilisateur);
        }
    }


    public Utilisateur findById(Long id) {
        return entityManager.find(Utilisateur.class, id);
    }

    public List<Utilisateur> findAll() {
        return entityManager.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    public Utilisateur findByEmail(String email) {
        List<Utilisateur> results = entityManager.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                .setParameter("email", email)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public List<Utilisateur> findByRole(String role) {
        return entityManager.createQuery("SELECT u FROM Utilisateur u WHERE TYPE(u) = :role", Utilisateur.class)
                .setParameter("role", role)
                .getResultList();
    }

    public List<Utilisateur> findSpecialistesBySpecialite(String specialite) {
        return entityManager.createQuery("SELECT s FROM utilisateur s WHERE s.specialite = :specialite", Utilisateur.class)
                .setParameter("specialite", specialite)
                .getResultList();
    }


    // get all specialists

    public List<Utilisateur> findAllSpecialistes() {
        return findByRole(String.valueOf(Role.SPECIALISTE));
    }

    // return all generalists

    public List<Utilisateur> findAllGeneralistes() {
        return findByRole(String.valueOf(Role.GENERALISTE));
    }

    // return all generalists

    public List<Utilisateur> findAllPatients() {
        return findByRole(String.valueOf(Role.INFERMIER));
    }

    public List<Utilisateur> findAvailableSpecialistes() {
        return entityManager.createQuery(
                "SELECT DISTINCT u FROM Utilisateur u JOIN u.creneaux c WHERE u.role = :role AND c.statut = 'LIBRE'",
                Utilisateur.class)
        .setParameter("role", Role.SPECIALISTE)
        .getResultList();
    }
}
