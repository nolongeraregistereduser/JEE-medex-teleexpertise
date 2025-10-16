package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.CreneauDAO;
import com.example.jeemedexteleexpertise.model.Creneau;
import com.example.jeemedexteleexpertise.model.StatusCreneau;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CreneauService extends BaseService<Creneau, Long> {

    @Inject
    private CreneauDAO creneauDAO;

    @Override
    protected BaseDAO<Creneau, Long> getDAO() {
        return creneauDAO;
    }

    @Override
    protected void performAdditionalValidation(Creneau creneau) {
        // Custom validation for Creneau
        if (creneau.getMedecinSpecialiste() == null) {
            throw new IllegalArgumentException("Specialist is required for slot");
        }
        if (creneau.getDateHeure() == null) {
            throw new IllegalArgumentException("Date and time are required for slot");
        }
        if (creneau.getDateHeure().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot create slot in the past");
        }
    }


    public List<Creneau> findAvailableSlotsBySpecialiste(Long specialisteId) {
        return creneauDAO.findAvailableSlotsBySpecialiste(specialisteId)
                .stream()
                .filter(creneau -> creneau.getDateHeure().isAfter(LocalDateTime.now()))
                .sorted((c1, c2) -> c1.getDateHeure().compareTo(c2.getDateHeure()))
                .collect(Collectors.toList());
    }


    public List<Creneau> findBySpecialisteId(Long specialisteId) {
        return creneauDAO.findBySpecialisteId(specialisteId);
    }


    public boolean reserveSlot(Long creneauId) {
        Creneau creneau = findById(creneauId);
        if (creneau != null && creneau.getStatus() == StatusCreneau.DISPONIBLE
            && creneau.getDateHeure().isAfter(LocalDateTime.now())) {
            creneau.setStatus(StatusCreneau.RESERVE);
            update(creneau);
            return true;
        }
        return false;
    }


    public boolean cancelReservation(Long creneauId) {
        Creneau creneau = findById(creneauId);
        if (creneau != null && creneau.getStatus() == StatusCreneau.RESERVE) {
            creneau.setStatus(StatusCreneau.DISPONIBLE);
            update(creneau);
            return true;
        }
        return false;
    }


    public long countAvailableSlots(Long specialisteId) {
        return findBySpecialisteId(specialisteId).stream()
                .filter(creneau -> creneau.getStatus() == StatusCreneau.DISPONIBLE)
                .filter(creneau -> creneau.getDateHeure().isAfter(LocalDateTime.now()))
                .count();
    }
}
