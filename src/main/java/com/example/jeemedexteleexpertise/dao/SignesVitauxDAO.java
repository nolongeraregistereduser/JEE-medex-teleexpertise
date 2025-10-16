package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.SignesVitaux;
import jakarta.ejb.Stateless;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class SignesVitauxDAO extends BaseDAO<SignesVitaux, Long> {

    public SignesVitauxDAO() {
        super(SignesVitaux.class);
    }


    public List<SignesVitaux> findByPatientId(Long patientId) {
        String jpql = "SELECT s FROM SignesVitaux s WHERE s.dossierMedical.patient.id = :patientId ORDER BY s.dateSaisie DESC";
        return executeNamedQuery(jpql, "patientId", patientId);
    }


    public SignesVitaux findLatestByPatientId(Long patientId) {
        String jpql = "SELECT s FROM SignesVitaux s WHERE s.dossierMedical.patient.id = :patientId ORDER BY s.dateSaisie DESC";
        List<SignesVitaux> results = executeNamedQuery(jpql, "patientId", patientId);
        return results.isEmpty() ? null : results.get(0);
    }


    public List<SignesVitaux> findTodaysSignesVitaux() {
        String jpql = "SELECT s FROM SignesVitaux s WHERE DATE(s.dateSaisie) = CURRENT_DATE ORDER BY s.dateSaisie DESC";
        return getEntityManager().createQuery(jpql, SignesVitaux.class).getResultList();
    }
}
