package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.DossierMedicalDAO;
import com.example.jeemedexteleexpertise.model.DossierMedical;
import com.example.jeemedexteleexpertise.model.Patient;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class DossierMedicalService extends BaseService<DossierMedical, Long> {

    @Inject
    private DossierMedicalDAO dossierMedicalDAO;

    @Override
    protected BaseDAO<DossierMedical, Long> getDAO() {
        return dossierMedicalDAO;
    }

    @Override
    protected void performAdditionalValidation(DossierMedical dossier) {
        // Custom validation for DossierMedical
        if (dossier.getPatient() == null) {
            throw new IllegalArgumentException("Patient is required for medical record");
        }
        // Additional validation can be added here
    }


    public DossierMedical findByPatientId(Long patientId) {
        return dossierMedicalDAO.findByPatientId(patientId);
    }


    public List<DossierMedical> findByAllergies(String allergie) {
        if (allergie == null || allergie.trim().isEmpty()) {
            return List.of();
        }
        return dossierMedicalDAO.findByAllergies(allergie);
    }


    public List<DossierMedical> findByAntecedents(String antecedent) {
        if (antecedent == null || antecedent.trim().isEmpty()) {
            return List.of();
        }
        return dossierMedicalDAO.findByAntecedents(antecedent);
    }


    public DossierMedical createOrUpdateDossier(DossierMedical dossier) {
        performAdditionalValidation(dossier);

        // Check if dossier already exists for this patient
        DossierMedical existingDossier = findByPatientId(dossier.getPatient().getId());

        if (existingDossier != null) {
            // Update existing dossier
            existingDossier.setAntecedents(dossier.getAntecedents());
            existingDossier.setAllergies(dossier.getAllergies());
            existingDossier.setTraitementsEnCours(dossier.getTraitementsEnCours());
            return update(existingDossier);
        } else {
            save(dossier);
            return dossier;
        }
    }

    public DossierMedical createDossierForPatient(Long patientId) {
        Patient patient = new Patient();
        patient.setId(patientId);

        DossierMedical dossier = new DossierMedical(patient);
        save(dossier);
        return dossier;
    }
}
