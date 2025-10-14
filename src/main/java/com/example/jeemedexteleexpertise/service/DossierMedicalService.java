package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.DossierMedicalDAO;
import com.example.jeemedexteleexpertise.model.DossierMedical;
import jakarta.inject.Inject;


public class DossierMedicalService {


    @Inject
    private DossierMedicalDAO dossierMedicalDAO;



    public void saveDossierMedical(DossierMedical dossierMedical) {
        dossierMedicalDAO.save(dossierMedical);
    }

    public void updateDossierMedical(DossierMedical dossierMedical) {
        dossierMedicalDAO.update(dossierMedical);
    }

    public void deleteDossierMedical(Long id) {
        dossierMedicalDAO.delete(id);
    }

    public DossierMedical getDossierMedicalById(Long id) {
        return dossierMedicalDAO.findById(id);
    }


}
