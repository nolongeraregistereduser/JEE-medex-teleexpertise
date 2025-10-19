package com.example.jeemedexteleexpertise.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final String PERSISTENCE_UNIT_NAME = "medex-pu";
    private static volatile EntityManagerFactory emf = null;

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            // don't fail at class load time; propagate later when trying to create EM
            throw new RuntimeException("Failed to create EntityManagerFactory: " + e.getMessage(), e);
        }
    }

    public static EntityManager createEntityManager() {
        if (emf == null) {
            synchronized (JpaUtil.class) {
                if (emf == null) {
                    emf = buildEntityManagerFactory();
                }
            }
        }
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
