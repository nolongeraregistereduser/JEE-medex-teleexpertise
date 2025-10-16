package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.ConsultationDAO;
import com.example.jeemedexteleexpertise.model.Consultation;
import com.example.jeemedexteleexpertise.model.StatusConsultation;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class ConsultationService extends BaseService<Consultation, Long> {

    @Inject
    private ConsultationDAO consultationDAO;

    @Override
    protected BaseDAO<Consultation, Long> getDAO() {
        return consultationDAO;
    }

    @Override
    protected void performAdditionalValidation(Consultation consultation) {
        // Custom validation for Consultation
        if (consultation.getPatient() == null) {
            throw new IllegalArgumentException("Patient is required for consultation");
        }
        if (consultation.getMedecinGeneraliste() == null) {
            throw new IllegalArgumentException("General practitioner is required for consultation");
        }
        if (consultation.getMotif() == null || consultation.getMotif().trim().isEmpty()) {
            throw new IllegalArgumentException("Consultation reason is required");
        }
    }


    public List<Consultation> findByStatus(StatusConsultation status) {
        return consultationDAO.findByStatus(status);
    }


    public List<Consultation> findByPatientId(Long patientId) {
        return consultationDAO.findByPatientId(patientId);
    }


    public List<Consultation> findByGeneralisteId(Long generalisteId) {
        return consultationDAO.findByGeneralisteId(generalisteId);
    }


    public List<Consultation> findConsultationsAwaitingSpecialistAdvice() {
        return consultationDAO.findConsultationsAwaitingSpecialistAdvice();
    }

    public List<Consultation> findTodaysConsultations() {
        return consultationDAO.findTodaysConsultations();
    }


    public void closeConsultation(Long consultationId, String diagnostic, String traitement) {
        Consultation consultation = findById(consultationId);
        if (consultation != null) {
            consultation.setDiagnostic(diagnostic);
            consultation.setTraitement(traitement);
            consultation.setStatus(StatusConsultation.TERMINEE);
            update(consultation);
        }
    }


    public void requestSpecialistAdvice(Long consultationId) {
        Consultation consultation = findById(consultationId);
        if (consultation != null) {
            consultation.setStatus(StatusConsultation.EN_ATTENTE_AVIS_SPECIALISTE);
            update(consultation);
        }
    }
}
