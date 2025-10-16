package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Patient;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class PatientDAO extends BaseDAO<Patient, Long> {

    public PatientDAO() {
        super(Patient.class);
    }

    public List<Patient> findByNomAndPrenom(String nom, String prenom) {
        String jpql = "SELECT p FROM Patient p WHERE p.nom = :nom AND p.prenom = :prenom";
        return getEntityManager().createQuery(jpql, Patient.class)
                .setParameter("nom", nom)
                .setParameter("prenom", prenom)
                .getResultList();
    }

    public List<Patient> findPatientsRegisteredToday() {
        String jpql = "SELECT p FROM Patient p WHERE DATE(p.dateCreation) = CURRENT_DATE";
        return getEntityManager().createQuery(jpql, Patient.class).getResultList();
    }

    public List<Patient> searchByName(String searchTerm) {
        String jpql = "SELECT p FROM Patient p WHERE LOWER(p.nom) LIKE LOWER(:term) OR LOWER(p.prenom) LIKE LOWER(:term)";
        return executeNamedQuery(jpql, "term", "%" + searchTerm + "%");
    }
}
