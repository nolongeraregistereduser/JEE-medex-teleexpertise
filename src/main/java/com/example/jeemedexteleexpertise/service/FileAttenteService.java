package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.BaseDAO;
import com.example.jeemedexteleexpertise.dao.FileAttenteDAO;
import com.example.jeemedexteleexpertise.model.FileAttente;
import com.example.jeemedexteleexpertise.model.StatusFileAttente;

import java.util.List;
import java.util.stream.Collectors;

public class FileAttenteService extends BaseService<FileAttente, Long> {

    private final FileAttenteDAO fileAttenteDAO;

    public FileAttenteService() {
        this.fileAttenteDAO = new FileAttenteDAO();
    }

    @Override
    protected BaseDAO<FileAttente, Long> getDAO() {
        return fileAttenteDAO;
    }

    public List<FileAttente> findAllWaiting() {
        return fileAttenteDAO.findAllWaiting();
    }

    public List<FileAttente> findByStatus(StatusFileAttente status) {
        if (status == null) {
            return List.of();
        }
        return fileAttenteDAO.findByStatus(status);
    }

    public List<FileAttente> findTodayQueue() {
        return fileAttenteDAO.findTodayQueue();
    }

    public List<FileAttente> findTodayQueueSortedByArrival() {
        return findTodayQueue().stream()
                .sorted((f1, f2) -> f1.getDateArrivee().compareTo(f2.getDateArrivee()))
                .collect(Collectors.toList());
    }

    public List<FileAttente> findByPatientId(Long patientId) {
        if (patientId == null) {
            return List.of();
        }
        return fileAttenteDAO.findByPatientId(patientId);
    }

    public int getQueuePosition(Long fileAttenteId) {
        if (fileAttenteId == null) {
            return -1;
        }
        return fileAttenteDAO.getQueuePosition(fileAttenteId);
    }

    public long countWaiting() {
        return fileAttenteDAO.countWaiting();
    }

    public void markAsPrisEnCharge(Long fileAttenteId) {
        if (fileAttenteId == null) {
            throw new IllegalArgumentException("File attente ID cannot be null");
        }

        fileAttenteDAO.findById(fileAttenteId).ifPresent(fileAttente -> {
            fileAttente.priseEnCharge();
            fileAttenteDAO.update(fileAttente);
        });
    }

    @Override
    public FileAttente save(FileAttente entity) {
        validateEntity(entity);

        if (entity.getPatient() == null) {
            throw new IllegalArgumentException("Patient is required");
        }

        return super.save(entity);
    }
}

