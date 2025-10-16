package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.DemandeExpertiseDAO;
import com.example.jeemedexteleexpertise.model.DemandeExpertise;
import com.example.jeemedexteleexpertise.model.StatusExpertise;
import com.example.jeemedexteleexpertise.model.PrioriteExpertise;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class DemandeExpertiseService extends BaseService<DemandeExpertise, Long> {

    @Inject
    private DemandeExpertiseDAO demandeExpertiseDAO;

    @Override
    protected BaseDAO<DemandeExpertise, Long> getDAO() {
        return demandeExpertiseDAO;
    }

    @Override
    protected void performAdditionalValidation(DemandeExpertise demande) {
        // Custom validation for DemandeExpertise
        if (demande.getConsultation() == null) {
            throw new IllegalArgumentException("Consultation is required for expertise request");
        }
        if (demande.getMedecinSpecialiste() == null) {
            throw new IllegalArgumentException("Specialist is required for expertise request");
        }
        if (demande.getQuestion() == null || demande.getQuestion().trim().isEmpty()) {
            throw new IllegalArgumentException("Question for specialist is required");
        }
    }


    public List<DemandeExpertise> findByStatusSorted(StatusExpertise status) {
        return demandeExpertiseDAO.findByStatus(status)
                .stream()
                .sorted((d1, d2) -> d1.getDateCreation().compareTo(d2.getDateCreation()))
                .collect(Collectors.toList());
    }


     // Business method: Find demandes by specialist with Stream API filtering

    public List<DemandeExpertise> findPendingDemandesForSpecialiste(Long specialisteId) {
        return demandeExpertiseDAO.findPendingDemandesForSpecialiste(specialisteId);
    }


     // Business method: Find urgent demandes using Stream API

    public List<DemandeExpertise> findUrgentDemandes() {
        return demandeExpertiseDAO.findUrgentDemandes();
    }


     // Business method: Filter demandes by priority using Stream API

    public List<DemandeExpertise> filterByPriority(PrioriteExpertise priorite) {
        return findAll().stream()
                .filter(demande -> demande.getPriorite() == priorite)
                .sorted((d1, d2) -> d1.getDateCreation().compareTo(d2.getDateCreation()))
                .collect(Collectors.toList());
    }


     // Business method: Respond to expertise request

    public void respondToExpertise(Long demandeId, String avisMedical, String recommandations) {
        DemandeExpertise demande = findById(demandeId);
        if (demande != null) {
            demande.setAvisMedical(avisMedical);
            demande.setRecommandations(recommandations);
            demande.setStatus(StatusExpertise.TERMINEE);
            update(demande);
        }
    }


     //Business method: Get demandes statistics using Stream API

    public long countDemandesByStatus(StatusExpertise status) {
        return findAll().stream()
                .filter(demande -> demande.getStatus() == status)
                .count();
    }
}
