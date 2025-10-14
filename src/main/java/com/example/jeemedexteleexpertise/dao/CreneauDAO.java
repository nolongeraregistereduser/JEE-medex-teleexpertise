package com.example.jeemedexteleexpertise.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.example.jeemedexteleexpertise.model.Creneau;

@Stateless
public class CreneauDAO {

    @PersistenceContext

    private EntityManager entityManager;

    @Transactional
    public void save(Creneau creneau) {
        entityManager.persist(creneau);
    }

    @Transactional
    public void update(Creneau creneau) {
        entityManager.merge(creneau);
    }

    @Transactional
    public void delete(Long id) {
        Creneau creneau = entityManager.find(Creneau.class, id);
        if (creneau != null) {
            entityManager.remove(creneau);
        }
    }

    public Creneau findById(Long id) {
        return entityManager.find(Creneau.class, id);
    }



}