package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.DemandeExpertise;
import com.example.jeemedexteleexpertise.model.StatusExpertise;
import com.example.jeemedexteleexpertise.model.PrioriteExpertise;
import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class DemandeExpertiseDAO extends BaseDAO<DemandeExpertise, Long> {

    public DemandeExpertiseDAO() {
        super(DemandeExpertise.class);
    }

    public Optional<DemandeExpertise> findByIdWithDetails(Long id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DemandeExpertise> query = em.createQuery(
                "SELECT d FROM DemandeExpertise d " +
                "LEFT JOIN FETCH d.consultation c " +
                "LEFT JOIN FETCH c.patient " +
                "LEFT JOIN FETCH c.medecinGeneraliste " +
                "LEFT JOIN FETCH d.medecinSpecialiste " +
                "LEFT JOIN FETCH d.creneau " +
                "WHERE d.id = :id",
                DemandeExpertise.class);
            query.setParameter("id", id);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<DemandeExpertise> findBySpecialisteId(Long specialisteId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DemandeExpertise> query = em.createQuery(
                "SELECT d FROM DemandeExpertise d " +
                "LEFT JOIN FETCH d.consultation c " +
                "LEFT JOIN FETCH c.patient " +
                "WHERE d.medecinSpecialiste.id = :specialisteId " +
                "ORDER BY d.dateDemande DESC",
                DemandeExpertise.class);
            query.setParameter("specialisteId", specialisteId);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<DemandeExpertise> findBySpecialisteIdAndStatus(Long specialisteId, StatusExpertise status) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DemandeExpertise> query = em.createQuery(
                "SELECT d FROM DemandeExpertise d " +
                "LEFT JOIN FETCH d.consultation c " +
                "LEFT JOIN FETCH c.patient " +
                "WHERE d.medecinSpecialiste.id = :specialisteId AND d.status = :status " +
                "ORDER BY d.dateDemande DESC",
                DemandeExpertise.class);
            query.setParameter("specialisteId", specialisteId);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<DemandeExpertise> findBySpecialisteIdAndPriorite(Long specialisteId, PrioriteExpertise priorite) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DemandeExpertise> query = em.createQuery(
                "SELECT d FROM DemandeExpertise d " +
                "LEFT JOIN FETCH d.consultation c " +
                "LEFT JOIN FETCH c.patient " +
                "WHERE d.medecinSpecialiste.id = :specialisteId AND d.priorite = :priorite " +
                "ORDER BY d.dateDemande DESC",
                DemandeExpertise.class);
            query.setParameter("specialisteId", specialisteId);
            query.setParameter("priorite", priorite);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<DemandeExpertise> findPendingBySpecialisteOrderedByPriority(Long specialisteId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DemandeExpertise> query = em.createQuery(
                "SELECT d FROM DemandeExpertise d " +
                "LEFT JOIN FETCH d.consultation c " +
                "LEFT JOIN FETCH c.patient " +
                "WHERE d.medecinSpecialiste.id = :specialisteId AND d.status = :status " +
                "ORDER BY " +
                "CASE d.priorite " +
                "  WHEN com.example.jeemedexteleexpertise.model.PrioriteExpertise.URGENTE THEN 1 " +
                "  WHEN com.example.jeemedexteleexpertise.model.PrioriteExpertise.NORMALE THEN 2 " +
                "  WHEN com.example.jeemedexteleexpertise.model.PrioriteExpertise.NON_URGENTE THEN 3 " +
                "END, d.dateDemande ASC",
                DemandeExpertise.class);
            query.setParameter("specialisteId", specialisteId);
            query.setParameter("status", StatusExpertise.EN_ATTENTE);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<DemandeExpertise> findByConsultationId(Long consultationId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DemandeExpertise> query = em.createQuery(
                "SELECT d FROM DemandeExpertise d WHERE d.consultation.id = :consultationId ORDER BY d.dateDemande DESC",
                DemandeExpertise.class);
            query.setParameter("consultationId", consultationId);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<DemandeExpertise> findAllPending() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DemandeExpertise> query = em.createQuery(
                "SELECT d FROM DemandeExpertise d WHERE d.status = :status ORDER BY d.dateDemande ASC",
                DemandeExpertise.class);
            query.setParameter("status", StatusExpertise.EN_ATTENTE);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public long countBySpecialisteIdAndStatus(Long specialisteId, StatusExpertise status) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(d) FROM DemandeExpertise d WHERE d.medecinSpecialiste.id = :specialisteId AND d.status = :status",
                Long.class);
            query.setParameter("specialisteId", specialisteId);
            query.setParameter("status", status);
            return query.getSingleResult();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}

