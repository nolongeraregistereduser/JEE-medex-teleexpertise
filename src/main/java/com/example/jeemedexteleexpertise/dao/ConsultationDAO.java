package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.Consultation;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import java.util.List;
import com.example.jeemedexteleexpertise.model.StatusConsultation;

@Stateless
public class ConsultationDAO {

    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public void save(Consultation consultation) {
        entityManager.persist(consultation);
    }

    @Transactional
    public void update(Consultation consultation) {
        entityManager.merge(consultation);
    }

    @Transactional
    public void delete(Long id) {
        Consultation consultation = entityManager.find(Consultation.class, id);
        if (consultation != null) {
            entityManager.remove(consultation);
        }
    }

    public Consultation findById(Long id) {
        return entityManager.find(Consultation.class, id);
    }

    public List<Consultation> findAll() {
        return entityManager.createQuery("SELECT c FROM Consultation c", Consultation.class).getResultList();
    }

    public List<Consultation> findByPatientId(Long patientId) {
        return entityManager.createQuery("SELECT c FROM Consultation c WHERE c.patient.id = :patientId", Consultation.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }

    public List<Consultation> findByMedecinId(Long medecinId) {
        return entityManager.createQuery("SELECT c FROM Consultation c WHERE c.medecin.id = :medecinId", Consultation.class)
                .setParameter("medecinId", medecinId)
                .getResultList();
    }

    public List<Consultation> findByStatus(StatusConsultation status) {
        return entityManager.createQuery("SELECT c FROM Consultation c WHERE c.status = :status", Consultation.class)
                .setParameter("status", status)
                .getResultList();
    }



}
