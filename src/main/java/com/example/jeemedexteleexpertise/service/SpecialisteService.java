package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.SpecialisteDAO;
import com.example.jeemedexteleexpertise.model.Specialiste;
import com.example.jeemedexteleexpertise.model.Specialite;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpecialisteService extends BaseService<Specialiste, Long> {

    private final SpecialisteDAO specialisteDAO;

    public SpecialisteService() {
        this.specialisteDAO = new SpecialisteDAO();
    }

    @Override
    protected BaseDAO<Specialiste, Long> getDAO() {
        return specialisteDAO;
    }

    public List<Specialiste> findBySpecialite(Specialite specialite) {
        if (specialite == null) {
            return List.of();
        }
        return specialisteDAO.findBySpecialite(specialite);
    }

    public List<Specialiste> findBySpecialiteSortedByTarif(Specialite specialite) {
        if (specialite == null) {
            return List.of();
        }

        return specialisteDAO.findBySpecialite(specialite).stream()
                .sorted(Comparator.comparingDouble(Specialiste::getTarif))
                .collect(Collectors.toList());
    }

    public List<Specialiste> findAllActive() {
        return specialisteDAO.findAllActive();
    }

    public Optional<Specialiste> findByIdWithCreneaux(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return specialisteDAO.findByIdWithCreneaux(id);
    }

    public Optional<Specialiste> findByIdWithDemandesExpertise(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return specialisteDAO.findByIdWithDemandesExpertise(id);
    }

    public List<Specialiste> findAllOrderBySpecialiteAndTarif() {
        return specialisteDAO.findAllOrderBySpecialiteAndTarif();
    }

    public void updateProfile(Long specialisteId, Double tarif, String specialite) {
        if (specialisteId == null) {
            throw new IllegalArgumentException("Specialiste ID cannot be null");
        }

        Optional<Specialiste> specialisteOpt = findById(specialisteId);
        if (specialisteOpt.isEmpty()) {
            throw new IllegalArgumentException("Specialiste not found");
        }

        Specialiste specialisteEntity = specialisteOpt.get();

        if (tarif != null && tarif > 0) {
            specialisteEntity.setTarif(tarif);
        }

        if (specialite != null && !specialite.trim().isEmpty()) {
            specialisteEntity.setSpecialite(specialite);
        }

        specialisteDAO.update(specialisteEntity);
    }

    @Override
    public Specialiste save(Specialiste entity) {
        validateEntity(entity);

        if (entity.getSpecialite() == null || entity.getSpecialite().trim().isEmpty()) {
            throw new IllegalArgumentException("Specialite is required");
        }

        return super.save(entity);
    }
}

