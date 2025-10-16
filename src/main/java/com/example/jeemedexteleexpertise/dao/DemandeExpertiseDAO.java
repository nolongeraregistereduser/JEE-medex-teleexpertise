package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.DemandeExpertise;
import com.example.jeemedexteleexpertise.model.StatusExpertise;
import com.example.jeemedexteleexpertise.model.PrioriteExpertise;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class DemandeExpertiseDAO extends BaseDAO<DemandeExpertise, Long> {

    public DemandeExpertiseDAO() {
        super(DemandeExpertise.class);
    }


    public List<DemandeExpertise> findByStatus(StatusExpertise status) {
        String jpql = "SELECT d FROM DemandeExpertise d WHERE d.status = :status";
        return executeNamedQuery(jpql, "status", status);
    }


    public List<DemandeExpertise> findBySpecialisteId(Long specialisteId) {
        String jpql = "SELECT d FROM DemandeExpertise d WHERE d.medecinSpecialiste.id = :specialisteId";
        return executeNamedQuery(jpql, "specialisteId", specialisteId);
    }


    public List<DemandeExpertise> findByPriorite(PrioriteExpertise priorite) {
        String jpql = "SELECT d FROM DemandeExpertise d WHERE d.priorite = :priorite ORDER BY d.dateCreation ASC";
        return executeNamedQuery(jpql, "priorite", priorite);
    }


    public List<DemandeExpertise> findPendingDemandesForSpecialiste(Long specialisteId) {
        String jpql = "SELECT d FROM DemandeExpertise d WHERE d.medecinSpecialiste.id = :specialisteId AND d.status = :status ORDER BY d.priorite DESC, d.dateCreation ASC";
        return getEntityManager().createQuery(jpql, DemandeExpertise.class)
                .setParameter("specialisteId", specialisteId)
                .setParameter("status", StatusExpertise.EN_ATTENTE)
                .getResultList();
    }


    public List<DemandeExpertise> findUrgentDemandes() {
        String jpql = "SELECT d FROM DemandeExpertise d WHERE d.priorite = :priorite AND d.status = :status ORDER BY d.dateCreation ASC";
        return getEntityManager().createQuery(jpql, DemandeExpertise.class)
                .setParameter("priorite", PrioriteExpertise.URGENTE)
                .setParameter("status", StatusExpertise.EN_ATTENTE)
                .getResultList();
    }
}
