package com.example.jeemedexteleexpertise.service;
import jakarta.inject.Inject;

public class DemandeExpertiseDAO {

    @Inject
    private DemandeExpertiseDAO demandeExpertiseDAO;


    public void saveDemandeExpertise() {
        demandeExpertiseDAO.saveDemandeExpertise();
    }

    public void updateDemandeExpertise() {
        demandeExpertiseDAO.updateDemandeExpertise();
    }

    public void deleteDemandeExpertise(Long id) {
        demandeExpertiseDAO.deleteDemandeExpertise(id);
    }

    public DemandeExpertiseDAO getDemandeExpertiseById(Long id) {
        return demandeExpertiseDAO.getDemandeExpertiseById(id);
    }



}
