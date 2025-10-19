package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Specialiste;
import com.example.jeemedexteleexpertise.model.Specialite;
import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class SpecialisteDAO extends BaseDAO<Specialiste, Long> {

    public SpecialisteDAO() {
        super(Specialiste.class);
    }

    public List<Specialiste> findBySpecialite(Specialite specialite) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Specialiste> query = em.createQuery(
                "SELECT s FROM Specialiste s WHERE s.specialite = :specialite AND s.actif = true ORDER BY s.tarif ASC",
                Specialiste.class);
            query.setParameter("specialite", specialite.name());
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Specialiste> findAllActive() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Specialiste> query = em.createQuery(
                "SELECT s FROM Specialiste s WHERE s.actif = true ORDER BY s.nom, s.prenom",
                Specialiste.class);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public Optional<Specialiste> findByIdWithCreneaux(Long id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Specialiste> query = em.createQuery(
                "SELECT s FROM Specialiste s LEFT JOIN FETCH s.creneaux WHERE s.id = :id",
                Specialiste.class);
            query.setParameter("id", id);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public Optional<Specialiste> findByIdWithDemandesExpertise(Long id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Specialiste> query = em.createQuery(
                "SELECT s FROM Specialiste s LEFT JOIN FETCH s.demandesExpertise WHERE s.id = :id",
                Specialiste.class);
            query.setParameter("id", id);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<Specialiste> findAllOrderBySpecialiteAndTarif() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Specialiste> query = em.createQuery(
                "SELECT s FROM Specialiste s WHERE s.actif = true ORDER BY s.specialite, s.tarif",
                Specialiste.class);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}

