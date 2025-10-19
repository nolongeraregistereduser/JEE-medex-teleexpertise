package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.DossierMedical;
import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class DossierMedicalDAO extends BaseDAO<DossierMedical, Long> {

    public DossierMedicalDAO() {
        super(DossierMedical.class);
    }

    public Optional<DossierMedical> findByPatientId(Long patientId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DossierMedical> query = em.createQuery(
                "SELECT d FROM DossierMedical d WHERE d.patient.id = :patientId",
                DossierMedical.class);
            query.setParameter("patientId", patientId);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public Optional<DossierMedical> findByPatientIdWithSignesVitaux(Long patientId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DossierMedical> query = em.createQuery(
                "SELECT d FROM DossierMedical d " +
                "LEFT JOIN FETCH d.signesVitaux " +
                "WHERE d.patient.id = :patientId",
                DossierMedical.class);
            query.setParameter("patientId", patientId);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<DossierMedical> findAllWithPatients() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DossierMedical> query = em.createQuery(
                "SELECT d FROM DossierMedical d LEFT JOIN FETCH d.patient",
                DossierMedical.class);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}

