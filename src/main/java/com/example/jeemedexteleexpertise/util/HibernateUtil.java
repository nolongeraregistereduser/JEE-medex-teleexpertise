package com.example.jeemedexteleexpertise.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class HibernateUtil {

    public static EntityManager getEntityManager() {
        EntityManagerFactory emf = HibernateContextListener.getEntityManagerFactory();
        if (emf == null) {
            throw new RuntimeException("EntityManagerFactory not initialized. Make sure the web application started properly.");
        }
        return emf.createEntityManager();
    }

    public static void closeEntityManager(EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
