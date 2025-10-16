package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.ActeTechniqueDAO;
import com.example.jeemedexteleexpertise.model.ActeTechnique;
import com.example.jeemedexteleexpertise.model.TypeActe;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ActTechniqueService extends BaseService<ActeTechnique, Long> {

    @Inject
    private ActeTechniqueDAO acteTechniqueDAO;

    @Override
    protected BaseDAO<ActeTechnique, Long> getDAO() {
        return acteTechniqueDAO;
    }

    @Override
    protected void performAdditionalValidation(ActeTechnique acte) {
        // Custom validation for ActeTechnique
        if (acte.getConsultation() == null) {
            throw new IllegalArgumentException("Consultation is required for medical act");
        }
        if (acte.getType() == null) {
            throw new IllegalArgumentException("Medical act type is required");
        }
        if (acte.getCout() < 0) {
            throw new IllegalArgumentException("Cost must be a positive value");
        }
    }


    public List<ActeTechnique> findByType(TypeActe type) {
        return acteTechniqueDAO.findByType(type);
    }


    public List<ActeTechnique> findByConsultationId(Long consultationId) {
        return acteTechniqueDAO.findByConsultationId(consultationId);
    }


    public Double calculateTotalCostForConsultation(Long consultationId) {
        List<ActeTechnique> actes = findByConsultationId(consultationId);
        return actes.stream()
                .mapToDouble(ActeTechnique::getCout)
                .sum();
    }


    public Double getAverageCostByType(TypeActe type) {
        return findByType(type).stream()
                .mapToDouble(ActeTechnique::getCout)
                .average()
                .orElse(0.0);
    }


    public List<ActeTechnique> findExpensiveActes(Double minCost) {
        return findAll().stream()
                .filter(acte -> acte.getCout() >= minCost)
                .sorted((a1, a2) -> Double.compare(a2.getCout(), a1.getCout())) // Descending order
                .collect(Collectors.toList());
    }
}
