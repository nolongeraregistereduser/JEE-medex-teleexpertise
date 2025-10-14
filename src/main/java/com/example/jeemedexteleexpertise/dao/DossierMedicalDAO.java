package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.DossierMedical;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Stateless
public class DossierMedicalDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(DossierMedical dossierMedical) {
        entityManager.persist(dossierMedical);
    }

    @Transactional
    public void update(DossierMedical dossierMedical) {
        entityManager.merge(dossierMedical);}

    @Transactional
    public void delete(Long id) {
        DossierMedical dossierMedical = entityManager.find(DossierMedical.class, id);
        if (dossierMedical != null) {
            entityManager.remove(dossierMedical);
        }
    }

    public DossierMedical findById(Long id) {
        return entityManager.find(DossierMedical.class, id);
    }

}
