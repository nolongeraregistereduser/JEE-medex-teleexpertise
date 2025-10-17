package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;


public abstract class BaseDAO<T, ID> {

    protected EntityManager entityManager;

    private final Class<T> entityClass;

    public BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        // Ensure we have an EntityManager even when not running in a Jakarta container
        if (this.entityManager == null) {
            this.entityManager = JpaUtil.createEntityManager();
        }
    }


    @Transactional
    public void save(T entity) {
        boolean createdTx = false;
        try {
            if (!entityManager.getTransaction().isActive()) { entityManager.getTransaction().begin(); createdTx = true; }
            entityManager.persist(entity);
            if (createdTx) { entityManager.getTransaction().commit(); }
        } catch (RuntimeException ex) {
            if (createdTx && entityManager.getTransaction().isActive()) { entityManager.getTransaction().rollback(); }
            throw ex;
        }
    }


    @Transactional
    public T update(T entity) {
        boolean createdTx = false;
        try {
            if (!entityManager.getTransaction().isActive()) { entityManager.getTransaction().begin(); createdTx = true; }
            T merged = entityManager.merge(entity);
            if (createdTx) { entityManager.getTransaction().commit(); }
            return merged;
        } catch (RuntimeException ex) {
            if (createdTx && entityManager.getTransaction().isActive()) { entityManager.getTransaction().rollback(); }
            throw ex;
        }
    }


    @Transactional
    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            boolean createdTx = false;
            try {
                if (!entityManager.getTransaction().isActive()) { entityManager.getTransaction().begin(); createdTx = true; }
                entityManager.remove(entity);
                if (createdTx) { entityManager.getTransaction().commit(); }
            } catch (RuntimeException ex) {
                if (createdTx && entityManager.getTransaction().isActive()) { entityManager.getTransaction().rollback(); }
                throw ex;
            }
        }
    }


    @Transactional
    public void deleteEntity(T entity) {
        if (entity != null) {
            boolean createdTx = false;
            try {
                if (!entityManager.getTransaction().isActive()) { entityManager.getTransaction().begin(); createdTx = true; }
                entityManager.remove(entity);
                if (createdTx) { entityManager.getTransaction().commit(); }
            } catch (RuntimeException ex) {
                if (createdTx && entityManager.getTransaction().isActive()) { entityManager.getTransaction().rollback(); }
                throw ex;
            }
        }
    }


    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }


    public Optional<T> findByIdOptional(ID id) {
        return Optional.ofNullable(findById(id));
    }


    public List<T> findAll() {
        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return entityManager.createQuery(queryString, entityClass).getResultList();
    }


    public long count() {
        String queryString = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
        return entityManager.createQuery(queryString, Long.class).getSingleResult();
    }


    public boolean existsById(ID id) {
        return findById(id) != null;
    }


    public List<T> findWithPagination(int offset, int limit) {
        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return entityManager.createQuery(queryString, entityClass)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }


    protected List<T> executeQuery(String jpql, Object... parameters) {
        TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i + 1, parameters[i]);
        }
        return query.getResultList();
    }


    protected List<T> executeNamedQuery(String jpql, String paramName, Object paramValue) {
        TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
        query.setParameter(paramName, paramValue);
        return query.getResultList();
    }


    protected T executeSingleResultQuery(String jpql, String paramName, Object paramValue) {
        List<T> results = executeNamedQuery(jpql, paramName, paramValue);
        return results.isEmpty() ? null : results.get(0);
    }


    protected Class<T> getEntityClass() {
        return entityClass;
    }


    protected EntityManager getEntityManager() {
        if (this.entityManager == null) {
            this.entityManager = JpaUtil.createEntityManager();
        }
        return this.entityManager;
    }
}
