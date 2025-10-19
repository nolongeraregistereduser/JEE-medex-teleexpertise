package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.PatientDAO;
import com.example.jeemedexteleexpertise.dao.DossierMedicalDAO;
import com.example.jeemedexteleexpertise.model.Patient;
import com.example.jeemedexteleexpertise.model.DossierMedical;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PatientService extends BaseService<Patient, Long> {

    private final PatientDAO patientDAO;
    private final DossierMedicalDAO dossierMedicalDAO;

    public PatientService() {
        this.patientDAO = new PatientDAO();
        this.dossierMedicalDAO = new DossierMedicalDAO();
    }

    @Override
    protected BaseDAO<Patient, Long> getDAO() {
        return patientDAO;
    }

    public Optional<Patient> findByNumSecu(String numSecu) {
        if (numSecu == null || numSecu.trim().isEmpty()) {
            return Optional.empty();
        }
        return patientDAO.findByNumSecu(numSecu);
    }

    public boolean existsByNumSecu(String numSecu) {
        return findByNumSecu(numSecu).isPresent();
    }

    public List<Patient> searchByName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return List.of();
        }
        return patientDAO.searchByNomOrPrenom(searchTerm);
    }

    public List<Patient> findTodayPatients() {
        return patientDAO.findByDateCreation(LocalDate.now());
    }

    public List<Patient> findPatientsByDate(LocalDate date) {
        if (date == null) {
            return List.of();
        }
        return patientDAO.findByDateCreation(date);
    }

    public List<Patient> findTodayPatientsSortedByArrival() {
        return findTodayPatients().stream()
                .sorted((p1, p2) -> p1.getDateCreation().compareTo(p2.getDateCreation()))
                .collect(Collectors.toList());
    }

    public Optional<Patient> findByIdWithDossierMedical(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return patientDAO.findByIdWithDossierMedical(id);
    }

    public List<Patient> findTodayPatientsWithSignesVitaux() {
        return patientDAO.findTodayPatientsWithSignesVitaux();
    }

    public Patient createPatientWithDossierMedical(Patient patient, DossierMedical dossierMedical) {
        validateEntity(patient);

        if (patient.getNumSecu() == null || patient.getNumSecu().trim().isEmpty()) {
            throw new IllegalArgumentException("Numéro de sécurité sociale is required");
        }

        if (existsByNumSecu(patient.getNumSecu())) {
            throw new IllegalArgumentException("A patient with this numéro de sécurité sociale already exists");
        }

        Patient savedPatient = patientDAO.save(patient);

        if (dossierMedical != null) {
            dossierMedical.setPatient(savedPatient);
            dossierMedicalDAO.save(dossierMedical);
        }

        return savedPatient;
    }

    @Override
    public Patient save(Patient entity) {
        validateEntity(entity);

        if (entity.getNumSecu() == null || entity.getNumSecu().trim().isEmpty()) {
            throw new IllegalArgumentException("Numéro de sécurité sociale is required");
        }

        if (entity.getId() == null && existsByNumSecu(entity.getNumSecu())) {
            throw new IllegalArgumentException("A patient with this numéro de sécurité sociale already exists");
        }

        return super.save(entity);
    }
}

