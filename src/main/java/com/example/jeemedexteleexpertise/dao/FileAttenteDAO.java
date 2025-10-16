package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.FileAttente;
import com.example.jeemedexteleexpertise.model.StatusFileAttente;
import jakarta.ejb.Stateless;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class FileAttenteDAO extends BaseDAO<FileAttente, Long> {

    public FileAttenteDAO() {
        super(FileAttente.class);
    }


    public List<FileAttente> findByStatus(StatusFileAttente status) {
        String jpql = "SELECT f FROM FileAttente f WHERE f.status = :status ORDER BY f.dateArrivee ASC";
        return executeNamedQuery(jpql, "status", status);
    }


    public List<FileAttente> findCurrentQueue() {
        String jpql = "SELECT f FROM FileAttente f WHERE f.status = :status ORDER BY f.dateArrivee ASC";
        return executeNamedQuery(jpql, "status", StatusFileAttente.EN_ATTENTE);
    }


    public List<FileAttente> findTodaysQueue() {
        String jpql = "SELECT f FROM FileAttente f WHERE DATE(f.dateArrivee) = CURRENT_DATE ORDER BY f.dateArrivee ASC";
        return getEntityManager().createQuery(jpql, FileAttente.class).getResultList();
    }


    public FileAttente getNextPatientInQueue() {
        List<FileAttente> queue = findCurrentQueue();
        return queue.isEmpty() ? null : queue.get(0);
    }


    public long countPatientsWaiting() {
        String jpql = "SELECT COUNT(f) FROM FileAttente f WHERE f.status = :status";
        return getEntityManager().createQuery(jpql, Long.class)
                .setParameter("status", StatusFileAttente.EN_ATTENTE)
                .getSingleResult();
    }
}
