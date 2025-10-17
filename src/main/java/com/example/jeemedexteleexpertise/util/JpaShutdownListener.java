package com.example.jeemedexteleexpertise.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class JpaShutdownListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // no-op: JpaUtil will initialize lazily when first used
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Close the EntityManagerFactory to release resources when the webapp stops
        try {
            JpaUtil.close();
        } catch (Throwable t) {
            // log to stdout as a fallback (no logger configured)
            System.err.println("Error closing JPA EntityManagerFactory: " + t.getMessage());
        }
    }
}

