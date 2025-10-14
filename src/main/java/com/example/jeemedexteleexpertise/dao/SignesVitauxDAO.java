package com.example.jeemedexteleexpertise.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.example.jeemedexteleexpertise.model.SignesVitaux;


@Stateless
public class SignesVitauxDAO {

    @PersistenceContext

    private EntityManager entityManager;

    @Transactional
    public void save(SignesVitaux signesVitaux) {
        entityManager.persist(signesVitaux);
    }

    @Transactional
    public void update(SignesVitaux signesVitaux) {
        entityManager.merge(signesVitaux);
    }

    @Transactional
    public void delete(Long id) {
        SignesVitaux signesVitaux = entityManager.find(SignesVitaux.class, id);
        if (signesVitaux != null) {
            entityManager.remove(signesVitaux);}}


    public SignesVitaux findById(Long id) {
        return entityManager.find(SignesVitaux.class, id);}



}
