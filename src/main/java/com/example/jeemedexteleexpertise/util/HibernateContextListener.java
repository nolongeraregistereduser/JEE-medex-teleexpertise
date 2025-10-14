package com.example.jeemedexteleexpertise.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class HibernateContextListener implements ServletContextListener {

    private static EntityManagerFactory entityManagerFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("🚀 Initializing Hibernate on application startup...");
        try {
            // Initialize Hibernate - this will create the tables
            entityManagerFactory = Persistence.createEntityManagerFactory("medex-pu");
            System.out.println("✅ Hibernate initialized successfully - Tables created!");

            // Store in servlet context for application use
            sce.getServletContext().setAttribute("entityManagerFactory", entityManagerFactory);

        } catch (Exception e) {
            System.err.println("❌ Failed to initialize Hibernate: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Hibernate", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("🔄 Shutting down Hibernate...");
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            System.out.println("✅ Hibernate shutdown complete - Tables dropped (create-drop mode)");
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
