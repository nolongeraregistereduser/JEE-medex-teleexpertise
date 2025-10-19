package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.ActeTechnique;
import com.example.jeemedexteleexpertise.model.TypeActe;
import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ActeTechniqueDAO extends BaseDAO<ActeTechnique, Long> {

    public ActeTechniqueDAO() {
        super(ActeTechnique.class);
    }

    public List<ActeTechnique> findByConsultationId(Long consultationId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<ActeTechnique> query = em.createQuery(
                "SELECT a FROM ActeTechnique a WHERE a.consultation.id = :consultationId ORDER BY a.date DESC",
                ActeTechnique.class);
            query.setParameter("consultationId", consultationId);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<ActeTechnique> findByType(TypeActe typeActe) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<ActeTechnique> query = em.createQuery(
                "SELECT a FROM ActeTechnique a WHERE a.typeActe = :typeActe ORDER BY a.date DESC",
                ActeTechnique.class);
            query.setParameter("typeActe", typeActe);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<ActeTechnique> findAllWithConsultations() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<ActeTechnique> query = em.createQuery(
                "SELECT a FROM ActeTechnique a LEFT JOIN FETCH a.consultation ORDER BY a.date DESC",
                ActeTechnique.class);
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}

