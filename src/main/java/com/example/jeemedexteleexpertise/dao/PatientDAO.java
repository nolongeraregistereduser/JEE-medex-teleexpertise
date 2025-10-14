package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Patient;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
public class PatientDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Patient patient) {
        entityManager.persist(patient);
    }

    @Transactional
    public void update(Patient patient) {
        entityManager.merge(patient);
    }

    @Transactional
    public void delete(Long id) {
        Patient patient = entityManager.find(Patient.class, id);
        if (patient != null) {
            entityManager.remove(patient);
        }
    }


    public Patient findById(Long id) {
        return entityManager.find(Patient.class, id);
    }

    public List<Patient> findAll() {
        return entityManager.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }


    public Patient findByEmail(String email) {
        List<Patient> results = entityManager.createQuery("SELECT p FROM Patient p WHERE p.email = :email", Patient.class)
                .setParameter("email", email)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }




}
