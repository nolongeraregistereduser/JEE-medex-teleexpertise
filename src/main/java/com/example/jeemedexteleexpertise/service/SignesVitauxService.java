package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.SignesVitauxDAO;
import com.example.jeemedexteleexpertise.model.SignesVitaux;

import java.time.LocalDateTime;
import java.util.List;

public class SignesVitauxService extends BaseService<SignesVitaux, Long> {

    private final SignesVitauxDAO signesVitauxDAO;

    public SignesVitauxService() {
        this.signesVitauxDAO = new SignesVitauxDAO();
    }

    @Override
    protected BaseDAO<SignesVitaux, Long> getDAO() {
        return signesVitauxDAO;
    }

    public List<SignesVitaux> findByDossierMedicalId(Long dossierId) {
        if (dossierId == null) {
            return List.of();
        }
        return signesVitauxDAO.findByDossierMedicalId(dossierId);
    }

    public List<SignesVitaux> findLatestByDossierMedicalId(Long dossierId, int limit) {
        if (dossierId == null || limit <= 0) {
            return List.of();
        }
        return signesVitauxDAO.findLatestByDossierMedicalId(dossierId, limit);
    }

    public List<SignesVitaux> findByDossierMedicalIdAndDateRange(Long dossierId, LocalDateTime startDate, LocalDateTime endDate) {
        if (dossierId == null || startDate == null || endDate == null) {
            return List.of();
        }
        return signesVitauxDAO.findByDossierMedicalIdAndDateRange(dossierId, startDate, endDate);
    }

    public List<SignesVitaux> findTodayByDossierMedicalId(Long dossierId) {
        if (dossierId == null) {
            return List.of();
        }
        return signesVitauxDAO.findTodayByDossierMedicalId(dossierId);
    }

    @Override
    public SignesVitaux save(SignesVitaux entity) {
        validateEntity(entity);

        if (entity.getDossierMedical() == null) {
            throw new IllegalArgumentException("Dossier medical is required");
        }

        return super.save(entity);
    }
}

