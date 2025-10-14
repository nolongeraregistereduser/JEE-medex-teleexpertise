package com.example.jeemedexteleexpertise.dao;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import com.example.jeemedexteleexpertise.model.ActeTechnique;

public class ActeTechniqueDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(ActeTechnique acteTechnique) {
        entityManager.persist(acteTechnique);
    }

    @Transactional
    public void update(ActeTechnique acteTechnique) {
        entityManager.merge(acteTechnique);}

    @Transactional
    public void delete(Long id) {
        ActeTechnique acteTechnique = entityManager.find(ActeTechnique.class, id);
        if (acteTechnique != null) {
            entityManager.remove(acteTechnique);}}

    public ActeTechnique findById(Long id) {
        return entityManager.find(ActeTechnique.class, id);}



}
