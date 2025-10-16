package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.DossierMedical;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class DossierMedicalDAO extends BaseDAO<DossierMedical, Long> {

    public DossierMedicalDAO() {
        super(DossierMedical.class);
    }


    public DossierMedical findByPatientId(Long patientId) {
        String jpql = "SELECT d FROM DossierMedical d WHERE d.patient.id = :patientId";
        return executeSingleResultQuery(jpql, "patientId", patientId);
    }


    public List<DossierMedical> findByAllergies(String allergie) {
        String jpql = "SELECT d FROM DossierMedical d WHERE d.allergies LIKE :allergie";
        return executeNamedQuery(jpql, "allergie", "%" + allergie + "%");
    }


    public List<DossierMedical> findByAntecedents(String antecedent) {
        String jpql = "SELECT d FROM DossierMedical d WHERE d.antecedents LIKE :antecedent";
        return executeNamedQuery(jpql, "antecedent", "%" + antecedent + "%");
    }
}
