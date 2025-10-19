package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.SignesVitaux;
import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class SignesVitauxDAO extends BaseDAO<SignesVitaux, Long> {

    public SignesVitauxDAO() {
        super(SignesVitaux.class);
    }

    public List<SignesVitaux> findByDossierMedicalId(Long dossierId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<SignesVitaux> query = em.createQuery(
                "SELECT s FROM SignesVitaux s WHERE s.dossierMedical.id = :dossierId ORDER BY s.dateSaisie DESC",
                SignesVitaux.class);
            query.setParameter("dossierId", dossierId);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<SignesVitaux> findLatestByDossierMedicalId(Long dossierId, int limit) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<SignesVitaux> query = em.createQuery(
                "SELECT s FROM SignesVitaux s WHERE s.dossierMedical.id = :dossierId ORDER BY s.dateSaisie DESC",
                SignesVitaux.class);
            query.setParameter("dossierId", dossierId);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<SignesVitaux> findByDossierMedicalIdAndDateRange(Long dossierId, LocalDateTime startDate, LocalDateTime endDate) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<SignesVitaux> query = em.createQuery(
                "SELECT s FROM SignesVitaux s " +
                "WHERE s.dossierMedical.id = :dossierId " +
                "AND s.dateSaisie BETWEEN :startDate AND :endDate " +
                "ORDER BY s.dateSaisie DESC",
                SignesVitaux.class);
            query.setParameter("dossierId", dossierId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<SignesVitaux> findTodayByDossierMedicalId(Long dossierId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<SignesVitaux> query = em.createQuery(
                "SELECT s FROM SignesVitaux s " +
                "WHERE s.dossierMedical.id = :dossierId " +
                "AND DATE(s.dateSaisie) = CURRENT_DATE " +
                "ORDER BY s.dateSaisie DESC",
                SignesVitaux.class);
            query.setParameter("dossierId", dossierId);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}

