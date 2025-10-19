package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.ConsultationDAO;
import com.example.jeemedexteleexpertise.dao.ActeTechniqueDAO;
import com.example.jeemedexteleexpertise.dao.DemandeExpertiseDAO;
import com.example.jeemedexteleexpertise.model.Consultation;
import com.example.jeemedexteleexpertise.model.StatusConsultation;
import com.example.jeemedexteleexpertise.model.ActeTechnique;
import com.example.jeemedexteleexpertise.model.DemandeExpertise;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ConsultationService extends BaseService<Consultation, Long> {

    private final ConsultationDAO consultationDAO;
    private final ActeTechniqueDAO acteTechniqueDAO;
    private final DemandeExpertiseDAO demandeExpertiseDAO;

    public ConsultationService() {
        this.consultationDAO = new ConsultationDAO();
        this.acteTechniqueDAO = new ActeTechniqueDAO();
        this.demandeExpertiseDAO = new DemandeExpertiseDAO();
    }

    @Override
    protected BaseDAO<Consultation, Long> getDAO() {
        return consultationDAO;
    }

    public Optional<Consultation> findByIdWithDetails(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return consultationDAO.findByIdWithDetails(id);
    }

    public List<Consultation> findByPatientId(Long patientId) {
        if (patientId == null) {
            return List.of();
        }
        return consultationDAO.findByPatientId(patientId);
    }

    public List<Consultation> findByGeneralisteId(Long generalisteId) {
        if (generalisteId == null) {
            return List.of();
        }
        return consultationDAO.findByGeneralisteId(generalisteId);
    }

    public List<Consultation> findByStatus(StatusConsultation status) {
        if (status == null) {
            return List.of();
        }
        return consultationDAO.findByStatus(status);
    }

    public List<Consultation> findAwaitingSpecialistOpinion() {
        return consultationDAO.findAwaitingSpecialistOpinion();
    }

    public List<Consultation> findByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return List.of();
        }
        return consultationDAO.findByDateRange(startDate, endDate);
    }

    public List<Consultation> findTodayConsultations() {
        return consultationDAO.findTodayConsultations();
    }

    public long countByStatus(StatusConsultation status) {
        if (status == null) {
            return 0;
        }
        return consultationDAO.countByStatus(status);
    }

    public void cloturer(Long consultationId, String diagnostic, String traitement) {
        if (consultationId == null) {
            throw new IllegalArgumentException("Consultation ID cannot be null");
        }

        Optional<Consultation> consultationOpt = findById(consultationId);
        if (consultationOpt.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found");
        }

        Consultation consultation = consultationOpt.get();
        consultation.setDiagnostic(diagnostic);
        consultation.setTraitement(traitement);
        consultation.setStatus(StatusConsultation.TERMINEE);

        update(consultation); // Use inherited update method from BaseService
    }

    public void requestSpecialistOpinion(Long consultationId) {
        if (consultationId == null) {
            throw new IllegalArgumentException("Consultation ID cannot be null");
        }

        Optional<Consultation> consultationOpt = findById(consultationId);
        if (consultationOpt.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found");
        }

        Consultation consultation = consultationOpt.get();
        consultation.setStatus(StatusConsultation.EN_ATTENTE_AVIS_SPECIALISTE);

        update(consultation);
    }

    public double calculateTotalCost(Long consultationId) {
        if (consultationId == null) {
            return 0.0;
        }

        Optional<Consultation> consultationOpt = findByIdWithDetails(consultationId);
        if (consultationOpt.isEmpty()) {
            return 0.0;
        }

        Consultation consultation = consultationOpt.get();

        // Base consultation cost
        double total = consultation.getCout() != null ? consultation.getCout() : 150.0;

        // Add technical acts cost using Lambda
        List<ActeTechnique> actes = acteTechniqueDAO.findByConsultationId(consultationId);
        double actesCost = actes.stream()
                .mapToDouble(ActeTechnique::getCoutActe)
                .sum();

        // Add expertise cost using Lambda
        List<DemandeExpertise> demandes = demandeExpertiseDAO.findByConsultationId(consultationId);
        double expertiseCost = demandes.stream()
                .mapToDouble(d -> d.getMedecinSpecialiste().getTarif())
                .sum();

        return total + actesCost + expertiseCost;
    }

    @Override
    public Consultation save(Consultation entity) {
        validateEntity(entity);

        if (entity.getPatient() == null) {
            throw new IllegalArgumentException("Patient is required");
        }

        if (entity.getMedecinGeneraliste() == null) {
            throw new IllegalArgumentException("Medecin generaliste is required");
        }

        if (entity.getCout() == null) {
            entity.setCout(150.0);
        }

        return super.save(entity);
    }
}
