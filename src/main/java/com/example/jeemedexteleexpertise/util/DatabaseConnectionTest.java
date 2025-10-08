package com.example.jeemedexteleexpertise.util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseConnectionTest {

    public static void main(String[] args) {
        testDatabaseConnection();
    }

    public static void testDatabaseConnection() {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            System.out.println("Testing database connection...");

            // Create EntityManagerFactory
            emf = Persistence.createEntityManagerFactory("medexPU");
            System.out.println("✅ EntityManagerFactory created successfully");

            // Create EntityManager
            em = emf.createEntityManager();
            System.out.println("✅ EntityManager created successfully");

            // Test a simple query
            Long count = (Long) em.createNativeQuery("SELECT COUNT(*) FROM utilisateur", Long.class)
                    .getSingleResult();
            System.out.println("✅ Database query executed successfully");
            System.out.println("📊 Number of users in database: " + count);

        } catch (Exception e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
