package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.SignesVitauxDAO;
import com.example.jeemedexteleexpertise.model.SignesVitaux;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SignesVitauxService extends BaseService<SignesVitaux, Long> {

    @Inject
    private SignesVitauxDAO signesVitauxDAO;

    @Override
    protected BaseDAO<SignesVitaux, Long> getDAO() {
        return signesVitauxDAO;
    }

    @Override
    protected void performAdditionalValidation(SignesVitaux signes) {
        if (signes.getPatient() == null) {
            throw new IllegalArgumentException("Patient is required for vital signs");
        }
        if (signes.getTensionArterielle() == null || signes.getTensionArterielle().trim().isEmpty()) {
            throw new IllegalArgumentException("Blood pressure is required");
        }
        if (signes.getFrequenceCardiaque() == null || signes.getFrequenceCardiaque() <= 0) {
            throw new IllegalArgumentException("Heart rate must be positive");
        }
        if (signes.getTemperature() == null || signes.getTemperature() <= 0) {
            throw new IllegalArgumentException("Temperature must be positive");
        }
    }

    public List<SignesVitaux> findByPatientId(Long patientId) {
        return signesVitauxDAO.findByPatientId(patientId);
    }

    public SignesVitaux findLatestByPatientId(Long patientId) {
        return signesVitauxDAO.findLatestByPatientId(patientId);
    }

    public List<SignesVitaux> findTodaysSignesVitaux() {
        return signesVitauxDAO.findTodaysSignesVitaux()
                .stream()
                .sorted((s1, s2) -> s2.getDateMesure().compareTo(s1.getDateMesure()))
                .collect(Collectors.toList());
    }

    public List<SignesVitaux> findAbnormalVitalSigns() {
        return findAll().stream()
                .filter(signes -> signes.getFrequenceCardiaque() > 100 ||
                               signes.getFrequenceCardiaque() < 60 ||
                               signes.getTemperature() > 38.0 ||
                               signes.getTemperature() < 36.0)
                .sorted((s1, s2) -> s2.getDateMesure().compareTo(s1.getDateMesure()))
                .collect(Collectors.toList());
    }

    public Double calculateAverageTemperature(Long patientId) {
        return findByPatientId(patientId).stream()
                .mapToDouble(SignesVitaux::getTemperature)
                .average()
                .orElse(0.0);
    }
}
