package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.FileAttenteDAO;
import com.example.jeemedexteleexpertise.model.FileAttente;
import com.example.jeemedexteleexpertise.model.StatusFileAttente;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class FileAttenteService extends BaseService<FileAttente, Long> {

    @Inject
    private FileAttenteDAO fileAttenteDAO;

    @Override
    protected BaseDAO<FileAttente, Long> getDAO() {
        return fileAttenteDAO;
    }

    @Override
    protected void performAdditionalValidation(FileAttente fileAttente) {
        // Custom validation for FileAttente
        if (fileAttente.getPatient() == null) {
            throw new IllegalArgumentException("Patient is required for queue entry");
        }
        if (fileAttente.getHeureArrivee() == null) {
            throw new IllegalArgumentException("Arrival time is required");
        }
    }


    public FileAttente addPatientToQueue(FileAttente fileAttente) {
        fileAttente.setHeureArrivee(LocalDateTime.now());
        fileAttente.setStatus(StatusFileAttente.EN_ATTENTE);
        save(fileAttente);
        return fileAttente;
    }


    public List<FileAttente> getCurrentQueue() {
        return fileAttenteDAO.getCurrentQueue()
                .stream()
                .sorted((f1, f2) -> f1.getDateArrivee().compareTo(f2.getDateArrivee()))
                .collect(Collectors.toList());
    }


    public FileAttente getNextPatientInQueue() {
        return fileAttenteDAO.getNextPatientInQueue();
    }


    public FileAttente callNextPatient() {
        FileAttente nextPatient = getNextPatientInQueue();
        if (nextPatient != null) {
            nextPatient.setStatus(StatusFileAttente.PRIS_EN_CHARGE);
            update(nextPatient);
        }
        return nextPatient;
    }


    public void completePatientConsultation(Long fileAttenteId) {
        FileAttente fileAttente = findById(fileAttenteId);
        if (fileAttente != null) {
            fileAttente.setStatus(StatusFileAttente.PRIS_EN_CHARGE);
            update(fileAttente);
        }
    }


    public List<FileAttente> getTodaysQueue() {
        return fileAttenteDAO.findTodaysQueue()
                .stream()
                .sorted((f1, f2) -> f1.getDateArrivee().compareTo(f2.getDateArrivee()))
                .collect(Collectors.toList());
    }


    //  Business method: Count waiting patients using Stream API

    public long countPatientsWaiting() {
        return fileAttenteDAO.countPatientsWaiting();
    }


    //  Business method: Get queue statistics using Lambda expressions

    public double getAverageWaitingTime() {
        List<FileAttente> completedToday = getTodaysQueue().stream()
                .filter(f -> f.getStatus() == StatusFileAttente.PRIS_EN_CHARGE)
                .collect(Collectors.toList());

        if (completedToday.isEmpty()) {
            return 0.0;
        }

        return completedToday.stream()
                .mapToDouble(f -> java.time.Duration.between(f.getDateArrivee(), LocalDateTime.now()).toMinutes())
                .average()
                .orElse(0.0);
    }
}
