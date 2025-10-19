package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Patient;
import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PatientDAO extends BaseDAO<Patient, Long> {

    public PatientDAO() {
        super(Patient.class);
    }

    public Optional<Patient> findByNumSecu(String numSecu) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Patient> query = em.createQuery(
                "SELECT p FROM Patient p WHERE p.numSecu = :numSecu", Patient.class);
            query.setParameter("numSecu", numSecu);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Patient> searchByNomOrPrenom(String searchTerm) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Patient> query = em.createQuery(
                "SELECT p FROM Patient p WHERE LOWER(p.nom) LIKE LOWER(:term) OR LOWER(p.prenom) LIKE LOWER(:term)",
                Patient.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Patient> findByDateCreation(LocalDate date) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Patient> query = em.createQuery(
                "SELECT p FROM Patient p WHERE DATE(p.dateCreation) = :date ORDER BY p.dateCreation ASC",
                Patient.class);
            query.setParameter("date", date);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public Optional<Patient> findByIdWithDossierMedical(Long id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Patient> query = em.createQuery(
                "SELECT p FROM Patient p LEFT JOIN FETCH p.dossierMedical WHERE p.id = :id",
                Patient.class);
            query.setParameter("id", id);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Patient> findTodayPatientsWithSignesVitaux() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Patient> query = em.createQuery(
                "SELECT DISTINCT p FROM Patient p " +
                "LEFT JOIN FETCH p.dossierMedical dm " +
                "LEFT JOIN FETCH dm.signesVitaux " +
                "WHERE DATE(p.dateCreation) = CURRENT_DATE " +
                "ORDER BY p.dateCreation ASC",
                Patient.class);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}

