package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.DossierMedicalDAO;
import com.example.jeemedexteleexpertise.model.DossierMedical;

import java.util.List;
import java.util.Optional;

public class DossierMedicalService extends BaseService<DossierMedical, Long> {

    private final DossierMedicalDAO dossierMedicalDAO;

    public DossierMedicalService() {
        this.dossierMedicalDAO = new DossierMedicalDAO();
    }

    @Override
    protected BaseDAO<DossierMedical, Long> getDAO() {
        return dossierMedicalDAO;
    }

    public Optional<DossierMedical> findByPatientId(Long patientId) {
        if (patientId == null) {
            return Optional.empty();
        }
        return dossierMedicalDAO.findByPatientId(patientId);
    }

    public Optional<DossierMedical> findByPatientIdWithSignesVitaux(Long patientId) {
        if (patientId == null) {
            return Optional.empty();
        }
        return dossierMedicalDAO.findByPatientIdWithSignesVitaux(patientId);
    }

    public List<DossierMedical> findAllWithPatients() {
        return dossierMedicalDAO.findAllWithPatients();
    }

    public DossierMedical getOrCreateDossierForPatient(Long patientId) {
        if (patientId == null) {
            throw new IllegalArgumentException("Patient ID cannot be null");
        }

        Optional<DossierMedical> existing = findByPatientId(patientId);
        if (existing.isPresent()) {
            return existing.get();
        }

        throw new IllegalStateException("Dossier medical not found for patient: " + patientId);
    }
}

