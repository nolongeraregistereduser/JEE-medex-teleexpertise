package com.example.jeemedexteleexpertise.service;

import com.example.jeemedexteleexpertise.dao.FileAttenteDAO;
import com.example.jeemedexteleexpertise.model.FileAttente;
import jakarta.inject.Inject;

public class FileAttenteService {

    @Inject
    private FileAttenteDAO fileAttenteDAO;



    public void saveFileAttente(FileAttente fileAttente) {
        fileAttenteDAO.save(fileAttente);
    }

    public void updateFileAttente(FileAttente fileAttente) {
        fileAttenteDAO.update(fileAttente);
    }

    public void deleteFileAttente(Long id) {
        fileAttenteDAO.delete(id);
    }

    public FileAttente getFileAttenteById(Long id) {
        return fileAttenteDAO.findById(id);
    }
}
