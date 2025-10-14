package com.example.jeemedexteleexpertise.service;

public class DemandeExpertiseDAO {

    private DemandeExpertiseDAO demandeExpertiseDAO;

    public DemandeExpertiseDAO() {
        this.demandeExpertiseDAO = new DemandeExpertiseDAO();
    }

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
