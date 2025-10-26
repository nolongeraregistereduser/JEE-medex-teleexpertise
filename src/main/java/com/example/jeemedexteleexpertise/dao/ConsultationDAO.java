package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Consultation;
import com.example.jeemedexteleexpertise.model.StatusConsultation;
import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ConsultationDAO extends BaseDAO<Consultation, Long> {

    public ConsultationDAO() {
        super(Consultation.class);
    }

    // Fetch Consultation with associated Patient, MedecinGeneraliste, and ActesTechniques

    public Optional<Consultation> findByIdWithDetails(Long id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            // First query: fetch consultation with patient, generaliste, and actesTechniques
            TypedQuery<Consultation> query1 = em.createQuery(
                "SELECT DISTINCT c FROM Consultation c " +
                "LEFT JOIN FETCH c.patient " +
                "LEFT JOIN FETCH c.medecinGeneraliste " +
                "LEFT JOIN FETCH c.actesTechniques " +
                "WHERE c.id = :id",
                Consultation.class);
            query1.setParameter("id", id);
            Consultation consultation = query1.getSingleResult();

            // Second query: fetch demandesExpertise with medecinSpecialiste
            if (consultation != null) {
                TypedQuery<Consultation> query2 = em.createQuery(
                    "SELECT DISTINCT c FROM Consultation c " +
                    "LEFT JOIN FETCH c.demandesExpertise de " +
                    "LEFT JOIN FETCH de.medecinSpecialiste " +
                    "WHERE c.id = :id",
                    Consultation.class);
                query2.setParameter("id", id);
                query2.getSingleResult(); // This loads demandesExpertise into the session
            }

            return Optional.ofNullable(consultation);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Consultation> findByPatientId(Long patientId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Consultation> query = em.createQuery(
                "SELECT c FROM Consultation c WHERE c.patient.id = :patientId ORDER BY c.dateConsultation DESC",
                Consultation.class);
            query.setParameter("patientId", patientId);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Consultation> findByGeneralisteId(Long generalisteId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Consultation> query = em.createQuery(
                "SELECT c FROM Consultation c WHERE c.medecinGeneraliste.id = :generalisteId ORDER BY c.dateConsultation DESC",
                Consultation.class);
            query.setParameter("generalisteId", generalisteId);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Consultation> findByStatus(StatusConsultation status) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Consultation> query = em.createQuery(
                "SELECT c FROM Consultation c WHERE c.status = :status ORDER BY c.dateConsultation DESC",
                Consultation.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Consultation> findAwaitingSpecialistOpinion() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Consultation> query = em.createQuery(
                "SELECT c FROM Consultation c WHERE c.status = :status ORDER BY c.dateConsultation ASC",
                Consultation.class);
            query.setParameter("status", StatusConsultation.EN_ATTENTE_AVIS_SPECIALISTE);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Consultation> findByDateRange(LocalDate startDate, LocalDate endDate) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Consultation> query = em.createQuery(
                "SELECT c FROM Consultation c WHERE DATE(c.dateConsultation) BETWEEN :startDate AND :endDate " +
                "ORDER BY c.dateConsultation DESC",
                Consultation.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Consultation> findTodayConsultations() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Consultation> query = em.createQuery(
                "SELECT c FROM Consultation c WHERE DATE(c.dateConsultation) = CURRENT_DATE " +
                "ORDER BY c.dateConsultation DESC",
                Consultation.class);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public long countByStatus(StatusConsultation status) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(c) FROM Consultation c WHERE c.status = :status",
                Long.class);
            query.setParameter("status", status);
            return query.getSingleResult();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}
