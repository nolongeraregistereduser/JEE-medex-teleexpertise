package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.ActeTechniqueDAO;
import com.example.jeemedexteleexpertise.model.ActeTechnique;
import com.example.jeemedexteleexpertise.model.TypeActe;

import java.util.List;

public class ActeTechniqueService extends BaseService<ActeTechnique, Long> {

    private final ActeTechniqueDAO acteTechniqueDAO;

    public ActeTechniqueService() {
        this.acteTechniqueDAO = new ActeTechniqueDAO();
    }

    @Override
    protected BaseDAO<ActeTechnique, Long> getDAO() {
        return acteTechniqueDAO;
    }

    public List<ActeTechnique> findByConsultationId(Long consultationId) {
        if (consultationId == null) {
            return List.of();
        }
        return acteTechniqueDAO.findByConsultationId(consultationId);
    }

    public List<ActeTechnique> findByType(TypeActe typeActe) {
        if (typeActe == null) {
            return List.of();
        }
        return acteTechniqueDAO.findByType(typeActe);
    }

    public List<ActeTechnique> findAllWithConsultations() {
        return acteTechniqueDAO.findAllWithConsultations();
    }

    @Override
    public ActeTechnique save(ActeTechnique entity) {
        validateEntity(entity);

        if (entity.getConsultation() == null) {
            throw new IllegalArgumentException("Consultation is required");
        }

        if (entity.getTypeActe() == null) {
            throw new IllegalArgumentException("Type acte is required");
        }

        return super.save(entity);
    }
}

