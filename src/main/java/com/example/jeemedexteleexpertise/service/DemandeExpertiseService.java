package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.DemandeExpertiseDAO;
import com.example.jeemedexteleexpertise.dao.ConsultationDAO;
import com.example.jeemedexteleexpertise.dao.CreneauDAO;
import com.example.jeemedexteleexpertise.model.DemandeExpertise;
import com.example.jeemedexteleexpertise.model.StatusExpertise;
import com.example.jeemedexteleexpertise.model.PrioriteExpertise;
import com.example.jeemedexteleexpertise.model.Consultation;
import com.example.jeemedexteleexpertise.model.StatusConsultation;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DemandeExpertiseService extends BaseService<DemandeExpertise, Long> {

    private final DemandeExpertiseDAO demandeExpertiseDAO;
    private final ConsultationDAO consultationDAO;
    private final CreneauDAO creneauDAO;

    public DemandeExpertiseService() {
        this.demandeExpertiseDAO = new DemandeExpertiseDAO();
        this.consultationDAO = new ConsultationDAO();
        this.creneauDAO = new CreneauDAO();
    }

    @Override
    protected BaseDAO<DemandeExpertise, Long> getDAO() {
        return demandeExpertiseDAO;
    }

    public Optional<DemandeExpertise> findByIdWithDetails(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return demandeExpertiseDAO.findByIdWithDetails(id);
    }

    public List<DemandeExpertise> findBySpecialisteId(Long specialisteId) {
        if (specialisteId == null) {
            return List.of();
        }
        return demandeExpertiseDAO.findBySpecialisteId(specialisteId);
    }

    public List<DemandeExpertise> findBySpecialisteIdAndStatus(Long specialisteId, StatusExpertise status) {
        if (specialisteId == null || status == null) {
            return List.of();
        }
        return demandeExpertiseDAO.findBySpecialisteIdAndStatus(specialisteId, status);
    }

    public List<DemandeExpertise> findBySpecialisteIdAndPriorite(Long specialisteId, PrioriteExpertise priorite) {
        if (specialisteId == null || priorite == null) {
            return List.of();
        }
        return demandeExpertiseDAO.findBySpecialisteIdAndPriorite(specialisteId, priorite);
    }

    public List<DemandeExpertise> findPendingBySpecialisteOrderedByPriority(Long specialisteId) {
        if (specialisteId == null) {
            return List.of();
        }
        return demandeExpertiseDAO.findPendingBySpecialisteOrderedByPriority(specialisteId);
    }

    public List<DemandeExpertise> findBySpecialisteFilteredByStatusAndPriority(
            Long specialisteId, StatusExpertise status, PrioriteExpertise priorite) {

        if (specialisteId == null) {
            return List.of();
        }

        List<DemandeExpertise> demandes = findBySpecialisteId(specialisteId);

        return demandes.stream()
                .filter(d -> status == null || d.getStatus() == status)
                .filter(d -> priorite == null || d.getPriorite() == priorite)
                .sorted(Comparator
                    .comparing((DemandeExpertise d) -> {
                        if (d.getPriorite() == PrioriteExpertise.URGENTE) return 1;
                        if (d.getPriorite() == PrioriteExpertise.NORMALE) return 2;
                        return 3;
                    })
                    .thenComparing(DemandeExpertise::getDateDemande))
                .collect(Collectors.toList());
    }

    public List<DemandeExpertise> findByConsultationId(Long consultationId) {
        if (consultationId == null) {
            return List.of();
        }
        return demandeExpertiseDAO.findByConsultationId(consultationId);
    }

    public List<DemandeExpertise> findAllPending() {
        return demandeExpertiseDAO.findAllPending();
    }

    public long countBySpecialisteIdAndStatus(Long specialisteId, StatusExpertise status) {
        if (specialisteId == null || status == null) {
            return 0;
        }
        return demandeExpertiseDAO.countBySpecialisteIdAndStatus(specialisteId, status);
    }

    public void repondreExpertise(Long demandeId, String avisMedecin, String recommandations) {
        if (demandeId == null) {
            throw new IllegalArgumentException("Demande expertise ID cannot be null");
        }

        if (avisMedecin == null || avisMedecin.trim().isEmpty()) {
            throw new IllegalArgumentException("Avis medecin is required");
        }

        Optional<DemandeExpertise> demandeOpt = findById(demandeId);
        if (demandeOpt.isEmpty()) {
            throw new IllegalArgumentException("Demande expertise not found");
        }

        DemandeExpertise demande = demandeOpt.get();
        demande.terminerExpertise(avisMedecin, recommandations);

        demandeExpertiseDAO.update(demande);

        if (demande.getConsultation() != null) {
            Consultation consultation = demande.getConsultation();
            consultation.setStatus(StatusConsultation.TERMINEE);
            consultationDAO.update(consultation);
        }
    }

    @Override
    public DemandeExpertise save(DemandeExpertise entity) {
        validateEntity(entity);

        if (entity.getConsultation() == null) {
            throw new IllegalArgumentException("Consultation is required");
        }

        if (entity.getMedecinSpecialiste() == null) {
            throw new IllegalArgumentException("Medecin specialiste is required");
        }

        if (entity.getCreneau() != null) {
            creneauDAO.findById(entity.getCreneau().getId()).ifPresent(creneau -> {
                if (!creneau.isDisponible()) {
                    throw new IllegalStateException("Creneau is not available");
                }
                creneau.reserver();
                creneauDAO.update(creneau);
            });
        }

        return super.save(entity);
    }
}

