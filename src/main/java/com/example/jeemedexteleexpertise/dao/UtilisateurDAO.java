package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Utilisateur;
import com.example.jeemedexteleexpertise.model.Role;
import com.example.jeemedexteleexpertise.model.Specialite;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

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
        return entityManager.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class)
                .getResultList();
    }


}
