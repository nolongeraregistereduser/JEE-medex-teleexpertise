package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.ActeTechniqueDAO;


public class ActTechniqueService {

    private ActeTechniqueDAO acteTechniqueDAO;

    public ActTechniqueService() {
        this.acteTechniqueDAO = new ActeTechniqueDAO();
    }

    public void saveActeTechnique(com.example.jeemedexteleexpertise.model.ActeTechnique acteTechnique) {
        acteTechniqueDAO.save(acteTechnique);
    }

    public void updateActeTechnique(com.example.jeemedexteleexpertise.model.ActeTechnique acteTechnique) {
        acteTechniqueDAO.update(acteTechnique);
    }

    public void deleteActeTechnique(Long id) {
        acteTechniqueDAO.delete(id);
    }

    public com.example.jeemedexteleexpertise.model.ActeTechnique getActeTechniqueById(Long id) {
        return acteTechniqueDAO.findById(id);
    }
}
