package com.example.jeemedexteleexpertise.service;
import com.example.jeemedexteleexpertise.dao.SignesVitauxDAO;
import com.example.jeemedexteleexpertise.model.SignesVitaux;

public class SignesVitauxService {

    private SignesVitauxDAO signesVitauxDAO;

    public SignesVitauxService() {
        this.signesVitauxDAO = new SignesVitauxDAO();
    }

    public void saveSignesVitaux(SignesVitaux signesVitaux) {
        signesVitauxDAO.save(signesVitaux);
    }

    public void updateSignesVitaux(SignesVitaux signesVitaux) {
        signesVitauxDAO.update(signesVitaux);
    }

    public void deleteSignesVitaux(Long id) {
        signesVitauxDAO.delete(id);
    }

    public SignesVitaux getSignesVitauxById(Long id) {
        return signesVitauxDAO.findById(id);}

}

