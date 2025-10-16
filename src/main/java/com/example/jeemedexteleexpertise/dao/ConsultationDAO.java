package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Consultation;
import com.example.jeemedexteleexpertise.model.StatusConsultation;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class ConsultationDAO extends BaseDAO<Consultation, Long> {

    public ConsultationDAO() {
        super(Consultation.class);
    }

    public List<Consultation> findByStatus(StatusConsultation status) {
        String jpql = "SELECT c FROM Consultation c WHERE c.status = :status";
        return executeNamedQuery(jpql, "status", status);
    }


    public List<Consultation> findByPatientId(Long patientId) {
        String jpql = "SELECT c FROM Consultation c WHERE c.patient.id = :patientId";
        return executeNamedQuery(jpql, "patientId", patientId);
    }


    public List<Consultation> findByGeneralisteId(Long generalisteId) {
        String jpql = "SELECT c FROM Consultation c WHERE c.medecinGeneraliste.id = :generalisteId";
        return executeNamedQuery(jpql, "generalisteId", generalisteId);
    }


    public List<Consultation> findConsultationsAwaitingSpecialistAdvice() {
        String jpql = "SELECT c FROM Consultation c WHERE c.status = :status";
        return executeNamedQuery(jpql, "status", StatusConsultation.EN_ATTENTE_AVIS_SPECIALISTE);
    }


    public List<Consultation> findTodaysConsultations() {
        String jpql = "SELECT c FROM Consultation c WHERE DATE(c.dateConsultation) = CURRENT_DATE";
        return getEntityManager().createQuery(jpql, Consultation.class).getResultList();
    }
}
