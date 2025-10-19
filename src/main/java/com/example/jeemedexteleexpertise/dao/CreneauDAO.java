package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Creneau;
import com.example.jeemedexteleexpertise.model.StatusCreneau;
import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class CreneauDAO extends BaseDAO<Creneau, Long> {

    public CreneauDAO() {
        super(Creneau.class);
    }

    public List<Creneau> findBySpecialisteId(Long specialisteId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Creneau> query = em.createQuery(
                "SELECT c FROM Creneau c WHERE c.medecinSpecialiste.id = :specialisteId ORDER BY c.dateHeureDebut ASC",
                Creneau.class);
            query.setParameter("specialisteId", specialisteId);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Creneau> findAvailableBySpecialisteId(Long specialisteId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Creneau> query = em.createQuery(
                "SELECT c FROM Creneau c " +
                "WHERE c.medecinSpecialiste.id = :specialisteId " +
                "AND c.status = :status " +
                "AND c.dateHeureDebut > :now " +
                "ORDER BY c.dateHeureDebut ASC",
                Creneau.class);
            query.setParameter("specialisteId", specialisteId);
            query.setParameter("status", StatusCreneau.DISPONIBLE);
            query.setParameter("now", LocalDateTime.now());
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Creneau> findBySpecialisteIdAndStatus(Long specialisteId, StatusCreneau status) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Creneau> query = em.createQuery(
                "SELECT c FROM Creneau c " +
                "WHERE c.medecinSpecialiste.id = :specialisteId AND c.status = :status " +
                "ORDER BY c.dateHeureDebut ASC",
                Creneau.class);
            query.setParameter("specialisteId", specialisteId);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Creneau> findPastCreneaux() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Creneau> query = em.createQuery(
                "SELECT c FROM Creneau c WHERE c.dateHeureFin < :now",
                Creneau.class);
            query.setParameter("now", LocalDateTime.now());
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Creneau> findBySpecialisteIdAndDateRange(Long specialisteId, LocalDateTime startDate, LocalDateTime endDate) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Creneau> query = em.createQuery(
                "SELECT c FROM Creneau c " +
                "WHERE c.medecinSpecialiste.id = :specialisteId " +
                "AND c.dateHeureDebut >= :startDate " +
                "AND c.dateHeureFin <= :endDate " +
                "ORDER BY c.dateHeureDebut ASC",
                Creneau.class);
            query.setParameter("specialisteId", specialisteId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public long countAvailableBySpecialisteId(Long specialisteId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(c) FROM Creneau c " +
                "WHERE c.medecinSpecialiste.id = :specialisteId " +
                "AND c.status = :status " +
                "AND c.dateHeureDebut > :now",
                Long.class);
            query.setParameter("specialisteId", specialisteId);
            query.setParameter("status", StatusCreneau.DISPONIBLE);
            query.setParameter("now", LocalDateTime.now());
            return query.getSingleResult();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}

