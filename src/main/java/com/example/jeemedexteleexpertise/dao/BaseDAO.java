package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public abstract class BaseDAO<T, ID> {

    private final Class<T> entityClass;

    protected BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T save(T entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error saving entity: " + e.getMessage(), e);
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public T update(T entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            T merged = em.merge(entity);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error updating entity: " + e.getMessage(), e);
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public void delete(T entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            T managedEntity = em.merge(entity);
            em.remove(managedEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error deleting entity: " + e.getMessage(), e);
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public void deleteById(ID id) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = HibernateUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error deleting entity: " + e.getMessage(), e);
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public Optional<T> findById(ID id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public List<T> findAll() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root);
            return em.createQuery(cq).getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public long count() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<T> root = cq.from(entityClass);
            cq.select(cb.count(root));
            return em.createQuery(cq).getSingleResult();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    protected List<T> executeQuery(String jpql, Object... params) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    protected Optional<T> executeSingleResultQuery(String jpql, Object... params) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            List<T> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }
}

