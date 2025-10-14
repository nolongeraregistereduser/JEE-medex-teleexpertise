package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


@Stateless
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


    public Utilisateur findByEmail(String email) {
        TypedQuery<Utilisateur> query = entityManager.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class);
        query.setParameter("email", email);
        List<Utilisateur> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
