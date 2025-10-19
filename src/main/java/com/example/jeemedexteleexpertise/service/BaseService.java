package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID> {

    protected abstract BaseDAO<T, ID> getDAO();

    protected void validateEntity(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
    }

    protected void validateId(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
    }

    public T save(T entity) {
        validateEntity(entity);
        return getDAO().save(entity);
    }

    public T update(T entity) {
        validateEntity(entity);
        return getDAO().update(entity);
    }

    public void delete(T entity) {
        validateEntity(entity);
        getDAO().delete(entity);
    }

    public void deleteById(ID id) {
        validateId(id);
        getDAO().deleteById(id);
    }

    public Optional<T> findById(ID id) {
        if (id == null) {
            return Optional.empty();
        }
        return getDAO().findById(id);
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
}

