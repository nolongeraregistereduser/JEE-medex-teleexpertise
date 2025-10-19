package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;

import java.util.List;
import java.util.Optional;

/**
 * Base Service class providing common business operations for all entities
 * @param <T> Entity type
 * @param <ID> Primary key type
 */
public abstract class BaseService<T, ID> {

    protected abstract BaseDAO<T, ID> getDAO();


    public void save(T entity) {
        validateEntity(entity);
        getDAO().save(entity);
    }

    public T update(T entity) {
        validateEntity(entity);
        return getDAO().update(entity);
    }


    public void deleteById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        getDAO().deleteById(id);
    }


     // Delete entity directly

    public void delete(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        getDAO().deleteEntity(entity);
    }


    public T findById(ID id) {
        if (id == null) {
            return null;
        }
        return getDAO().findById(id);
    }


    public Optional<T> findByIdOptional(ID id) {
        return getDAO().findByIdOptional(id);
    }


    public List<T> findAll() {
        return getDAO().findAll();
    }


    public long count() {
        return getDAO().count();
    }


    public boolean existsById(ID id) {
        if (id == null) {
            return false;
        }
        return getDAO().existsById(id);
    }

    public List<T> findWithPagination(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be >= 0 and size must be > 0");
        }
        int offset = page * size;
        return getDAO().findWithPagination(offset, size);
    }


    protected void validateEntity(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        // Additional validation can be added in subclasses
    }


    protected abstract void performAdditionalValidation(T entity);


    public void saveWithValidation(T entity) {
        validateEntity(entity);
        performAdditionalValidation(entity);
        getDAO().save(entity);
    }


    public T updateWithValidation(T entity) {
        validateEntity(entity);
        performAdditionalValidation(entity);
        return getDAO().update(entity);
    }
}
