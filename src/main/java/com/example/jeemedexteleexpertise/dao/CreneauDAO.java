package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Creneau;
import com.example.jeemedexteleexpertise.model.StatusCreneau;
import jakarta.ejb.Stateless;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class CreneauDAO extends BaseDAO<Creneau, Long> {

    public CreneauDAO() {
        super(Creneau.class);
    }


    public List<Creneau> findAvailableSlotsBySpecialiste(Long specialisteId) {
        String jpql = "SELECT c FROM Creneau c WHERE c.medecinSpecialiste.id = :specialisteId AND c.status = :status AND c.dateHeure > :now";
        return getEntityManager().createQuery(jpql, Creneau.class)
                .setParameter("specialisteId", specialisteId)
                .setParameter("status", StatusCreneau.DISPONIBLE)
                .setParameter("now", LocalDateTime.now())
                .getResultList();
    }


    public List<Creneau> findByStatus(StatusCreneau status) {
        String jpql = "SELECT c FROM Creneau c WHERE c.status = :status";
        return executeNamedQuery(jpql, "status", status);
    }


    public List<Creneau> findBySpecialisteId(Long specialisteId) {
        String jpql = "SELECT c FROM Creneau c WHERE c.medecinSpecialiste.id = :specialisteId ORDER BY c.dateHeure ASC";
        return executeNamedQuery(jpql, "specialisteId", specialisteId);
    }


    public List<Creneau> findFutureSlots() {
        String jpql = "SELECT c FROM Creneau c WHERE c.dateHeure > :now ORDER BY c.dateHeure ASC";
        return executeNamedQuery(jpql, "now", LocalDateTime.now());
    }
}