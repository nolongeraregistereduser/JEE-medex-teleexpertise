package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.DossierMedicalDAO;
import com.example.jeemedexteleexpertise.model.DossierMedical;
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

    /**
     * Business method: Find dossier by patient
     */
    public DossierMedical findByPatientId(Long patientId) {
        return dossierMedicalDAO.findByPatientId(patientId);
    }

    /**
     * Business method: Find dossiers with allergies
     */
    public List<DossierMedical> findByAllergies(String allergie) {
        if (allergie == null || allergie.trim().isEmpty()) {
            return List.of();
        }
        return dossierMedicalDAO.findByAllergies(allergie);
    }

    /**
     * Business method: Find dossiers with antecedents
     */
    public List<DossierMedical> findByAntecedents(String antecedent) {
        if (antecedent == null || antecedent.trim().isEmpty()) {
            return List.of();
        }
        return dossierMedicalDAO.findByAntecedents(antecedent);
    }

    /**
     * Business method: Create or update patient's medical record
     */
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
}
