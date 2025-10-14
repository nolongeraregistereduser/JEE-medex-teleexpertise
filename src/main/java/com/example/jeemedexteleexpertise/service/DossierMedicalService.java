package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.DossierMedicalDAO;
import com.example.jeemedexteleexpertise.model.DossierMedical;

public class DossierMedicalService {


    private DossierMedicalDAO dossierMedicalDAO;

    public DossierMedicalService() {
        this.dossierMedicalDAO = new DossierMedicalDAO();
    }

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
