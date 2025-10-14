package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.DemandeExpertise;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Stateless
public class DemandeExpertiseDAO {

    @PersistenceContext

    private EntityManager entityManager;
    @Transactional
    public void save(DemandeExpertise demandeExpertise) {
        entityManager.persist(demandeExpertise);}

    @Transactional
    public void update(DemandeExpertise demandeExpertise) {
        entityManager.merge(demandeExpertise);}

    @Transactional
    public void delete(Long id) {
        DemandeExpertise demandeExpertise = entityManager.find(DemandeExpertise.class, id);
        if (demandeExpertise != null) {
            entityManager.remove(demandeExpertise);}}


    public DemandeExpertise findById(Long id) {
        return entityManager.find(DemandeExpertise.class, id);}


}
