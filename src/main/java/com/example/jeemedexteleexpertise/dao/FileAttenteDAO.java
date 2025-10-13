package com.example.jeemedexteleexpertise.dao;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import com.example.jeemedexteleexpertise.model.FileAttente;

public class FileAttenteDAO {


    @PersistenceContext

    private EntityManager entityManager;

    @Transactional
    public void save(FileAttente fileAttente) {
        entityManager.persist(fileAttente);
    }

    @Transactional
    public void update(FileAttente fileAttente) {
        entityManager.merge(fileAttente);}

    @Transactional
    public void delete(Long id) {
        FileAttente fileAttente = entityManager.find(FileAttente.class, id);
        if (fileAttente != null) {
            entityManager.remove(fileAttente);
        }
    }



    public FileAttente findById(Long id) {
        return entityManager.find(FileAttente.class, id);
    }
}
