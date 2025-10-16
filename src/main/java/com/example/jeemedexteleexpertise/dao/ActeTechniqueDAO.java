package com.example.jeemedexteleexpertise.dao;

import com.example.jeemedexteleexpertise.model.ActeTechnique;
import com.example.jeemedexteleexpertise.model.TypeActe;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class ActeTechniqueDAO extends BaseDAO<ActeTechnique, Long> {

    public ActeTechniqueDAO() {
        super(ActeTechnique.class);
    }

    public List<ActeTechnique> findByType(TypeActe type) {
        String jpql = "SELECT a FROM ActeTechnique a WHERE a.type = :type";
        return executeNamedQuery(jpql, "type", type);
    }


    public List<ActeTechnique> findByConsultationId(Long consultationId) {
        String jpql = "SELECT a FROM ActeTechnique a WHERE a.consultation.id = :consultationId";
        return executeNamedQuery(jpql, "consultationId", consultationId);
    }


    public Double calculateTotalCostForConsultation(Long consultationId) {
        String jpql = "SELECT SUM(a.cout) FROM ActeTechnique a WHERE a.consultation.id = :consultationId";
        return getEntityManager().createQuery(jpql, Double.class)
                .setParameter("consultationId", consultationId)
                .getSingleResult();
    }
}
