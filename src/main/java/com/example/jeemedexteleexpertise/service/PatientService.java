package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.PatientDAO;
import com.example.jeemedexteleexpertise.model.Patient;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class PatientService extends BaseService<Patient, Long> {

    @Inject
    private PatientDAO patientDAO;

    @Override
    protected BaseDAO<Patient, Long> getDAO() {
        return patientDAO;
    }

    @Override
    protected void performAdditionalValidation(Patient patient) {
        // Custom validation for Patient
        if (patient.getNom() == null || patient.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name is required");
        }
        if (patient.getPrenom() == null || patient.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient first name is required");
        }
        if (patient.getDateNaissance() == null) {
            throw new IllegalArgumentException("Birth date is required");
        }
    }

    public List<Patient> findByNomAndPrenom(String nom, String prenom) {
        return patientDAO.findByNomAndPrenom(nom, prenom);
    }

    public List<Patient> findPatientsRegisteredToday() {
        return patientDAO.findPatientsRegisteredToday();
    }

    public List<Patient> searchByName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return List.of(); // Return empty list
        }
        return patientDAO.searchByName(searchTerm);
    }

    public Patient registerNewPatient(Patient patient) {
        performAdditionalValidation(patient);
        save(patient);
        return patient;
    }
}
