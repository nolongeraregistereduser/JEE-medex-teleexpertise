package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Utilisateur;
import com.example.jeemedexteleexpertise.model.Role;
import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class UtilisateurDAO extends BaseDAO<Utilisateur, Long> {

    public UtilisateurDAO() {
        super(Utilisateur.class);
    }

    public Optional<Utilisateur> findByEmail(String email) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Utilisateur> query = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class);
            query.setParameter("email", email);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Utilisateur> findAllActive() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Utilisateur> query = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.actif = true", Utilisateur.class);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public boolean existsByEmail(String email) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM Utilisateur u WHERE u.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public <T extends Utilisateur> List<T> findByType(Class<T> type) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<T> query = em.createQuery(
                "SELECT u FROM " + type.getSimpleName() + " u WHERE u.actif = true", type);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}

