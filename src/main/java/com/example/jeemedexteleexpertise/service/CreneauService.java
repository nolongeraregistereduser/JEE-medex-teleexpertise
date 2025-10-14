package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.CreneauDAO;
import com.example.jeemedexteleexpertise.model.Creneau;

public class CreneauService {

    private CreneauDAO creneauDAO;

    public CreneauService() {
        this.creneauDAO = new CreneauDAO();
    }

    public void saveCreneau(Creneau creneau) {
        creneauDAO.save(creneau);
    }

    public void updateCreneau(Creneau creneau) {
        creneauDAO.update(creneau);
    }

    public void deleteCreneau(Long id) {
        creneauDAO.delete(id);
    }

    public Creneau getCreneauById(Long id) {
        return creneauDAO.findById(id);
    }
}
