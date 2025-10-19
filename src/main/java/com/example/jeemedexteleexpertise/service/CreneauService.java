package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.CreneauDAO;
import com.example.jeemedexteleexpertise.model.Creneau;
import com.example.jeemedexteleexpertise.model.StatusCreneau;

import java.time.LocalDateTime;
import java.util.List;

public class CreneauService extends BaseService<Creneau, Long> {

    private final CreneauDAO creneauDAO;

    public CreneauService() {
        this.creneauDAO = new CreneauDAO();
    }

    @Override
    protected BaseDAO<Creneau, Long> getDAO() {
        return creneauDAO;
    }

    public List<Creneau> findBySpecialisteId(Long specialisteId) {
        if (specialisteId == null) {
            return List.of();
        }
        return creneauDAO.findBySpecialisteId(specialisteId);
    }

    public List<Creneau> findAvailableBySpecialisteId(Long specialisteId) {
        if (specialisteId == null) {
            return List.of();
        }
        return creneauDAO.findAvailableBySpecialisteId(specialisteId);
    }

    public List<Creneau> findBySpecialisteIdAndStatus(Long specialisteId, StatusCreneau status) {
        if (specialisteId == null || status == null) {
            return List.of();
        }
        return creneauDAO.findBySpecialisteIdAndStatus(specialisteId, status);
    }

    public List<Creneau> findPastCreneaux() {
        return creneauDAO.findPastCreneaux();
    }

    public List<Creneau> findBySpecialisteIdAndDateRange(Long specialisteId, LocalDateTime startDate, LocalDateTime endDate) {
        if (specialisteId == null || startDate == null || endDate == null) {
            return List.of();
        }
        return creneauDAO.findBySpecialisteIdAndDateRange(specialisteId, startDate, endDate);
    }

    public long countAvailableBySpecialisteId(Long specialisteId) {
        if (specialisteId == null) {
            return 0;
        }
        return creneauDAO.countAvailableBySpecialisteId(specialisteId);
    }

    public void reserveCreneau(Long creneauId) {
        if (creneauId == null) {
            throw new IllegalArgumentException("Creneau ID cannot be null");
        }

        creneauDAO.findById(creneauId).ifPresent(creneau -> {
            if (!creneau.isDisponible()) {
                throw new IllegalStateException("Creneau is not available");
            }
            creneau.reserver();
            creneauDAO.update(creneau);
        });
    }

    public void liberCreneau(Long creneauId) {
        if (creneauId == null) {
            throw new IllegalArgumentException("Creneau ID cannot be null");
        }

        creneauDAO.findById(creneauId).ifPresent(creneau -> {
            creneau.liberer();
            creneauDAO.update(creneau);
        });
    }

    public void archivePastCreneaux() {
        List<Creneau> pastCreneaux = findPastCreneaux();
        pastCreneaux.forEach(creneau -> {
            if (creneau.getStatus() != StatusCreneau.ARCHIVE) {
                creneau.archiver();
                creneauDAO.update(creneau);
            }
        });
    }

    @Override
    public Creneau save(Creneau entity) {
        validateEntity(entity);

        if (entity.getMedecinSpecialiste() == null) {
            throw new IllegalArgumentException("Medecin specialiste is required");
        }

        if (entity.getDateHeureDebut() == null || entity.getDateHeureFin() == null) {
            throw new IllegalArgumentException("Date heure debut and fin are required");
        }

        if (entity.getDateHeureDebut().isAfter(entity.getDateHeureFin())) {
            throw new IllegalArgumentException("Date heure debut must be before date heure fin");
        }

        return super.save(entity);
    }
}

