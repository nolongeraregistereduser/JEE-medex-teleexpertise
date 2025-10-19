package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.FileAttente;
import com.example.jeemedexteleexpertise.model.StatusFileAttente;
import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class FileAttenteDAO extends BaseDAO<FileAttente, Long> {

    public FileAttenteDAO() {
        super(FileAttente.class);
    }

    public List<FileAttente> findAllWaiting() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<FileAttente> query = em.createQuery(
                "SELECT f FROM FileAttente f " +
                "LEFT JOIN FETCH f.patient " +
                "WHERE f.status = :status " +
                "ORDER BY f.dateArrivee ASC",
                FileAttente.class);
            query.setParameter("status", StatusFileAttente.EN_ATTENTE);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<FileAttente> findByStatus(StatusFileAttente status) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<FileAttente> query = em.createQuery(
                "SELECT f FROM FileAttente f " +
                "LEFT JOIN FETCH f.patient " +
                "WHERE f.status = :status " +
                "ORDER BY f.dateArrivee ASC",
                FileAttente.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<FileAttente> findTodayQueue() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<FileAttente> query = em.createQuery(
                "SELECT f FROM FileAttente f " +
                "LEFT JOIN FETCH f.patient " +
                "WHERE DATE(f.dateArrivee) = CURRENT_DATE " +
                "ORDER BY f.dateArrivee ASC",
                FileAttente.class);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<FileAttente> findByPatientId(Long patientId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<FileAttente> query = em.createQuery(
                "SELECT f FROM FileAttente f WHERE f.patient.id = :patientId ORDER BY f.dateArrivee DESC",
                FileAttente.class);
            query.setParameter("patientId", patientId);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public int getQueuePosition(Long fileAttenteId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            FileAttente entry = em.find(FileAttente.class, fileAttenteId);
            if (entry == null || entry.getStatus() != StatusFileAttente.EN_ATTENTE) {
                return -1;
            }
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(f) FROM FileAttente f " +
                "WHERE f.status = :status AND f.dateArrivee < :dateArrivee",
                Long.class);
            query.setParameter("status", StatusFileAttente.EN_ATTENTE);
            query.setParameter("dateArrivee", entry.getDateArrivee());
            return query.getSingleResult().intValue() + 1;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public long countWaiting() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(f) FROM FileAttente f WHERE f.status = :status",
                Long.class);
            query.setParameter("status", StatusFileAttente.EN_ATTENTE);
            return query.getSingleResult();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}

