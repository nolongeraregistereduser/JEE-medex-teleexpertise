package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.PatientDAO;
import com.example.jeemedexteleexpertise.model.Patient;
import jakarta.inject.Inject;

public class PatientService {

    @Inject
    private PatientDAO patientDAO;


    // crud

    public void savePatient(Patient patient) {
        patientDAO.save(patient);
    }

    public void updatePatient(Patient patient) {
        patientDAO.update(patient);
    }

    public void deletePatient(Long id) {
        patientDAO.delete(id);
    }

    public Patient findPatientById(Long id) {
        return patientDAO.findById(id);
    }
}
