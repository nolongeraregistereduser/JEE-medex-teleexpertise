package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.ConsultationDAO;
import com.example.jeemedexteleexpertise.model.Consultation;

public class ConsultationService {

    private ConsultationDAO consultationDAO;

    public ConsultationService() {
        this.consultationDAO = new ConsultationDAO();
    }

    public void saveConsultation(Consultation consultation) {
        consultationDAO.save(consultation);
    }

    public void updateConsultation(Consultation consultation) {
        consultationDAO.update(consultation);
    }

    public void deleteConsultation(Long id) {
        consultationDAO.delete(id);
    }

    public Consultation getConsultationById(Long id) {
        return consultationDAO.findById(id);
    }



}
