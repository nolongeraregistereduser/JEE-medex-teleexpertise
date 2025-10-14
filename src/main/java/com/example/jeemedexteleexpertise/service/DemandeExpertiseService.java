package com.example.jeemedexteleexpertise.service;
import com.example.jeemedexteleexpertise.model.DemandeExpertise;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.example.jeemedexteleexpertise.dao.DemandeExpertiseDAO;

@Stateless
public class DemandeExpertiseService {

    @Inject
    private DemandeExpertiseDAO demandeExpertiseDAO;


    public void saveDemandeExpertise( DemandeExpertise demandeExpertise) {
        demandeExpertiseDAO.save(demandeExpertise);
    }

    public void updateDemandeExpertise(DemandeExpertise demandeExpertise) {
        demandeExpertiseDAO.update(demandeExpertise);
    }

    public void deleteDemandeExpertise(Long id) {
        demandeExpertiseDAO.delete(id);
    }



}
