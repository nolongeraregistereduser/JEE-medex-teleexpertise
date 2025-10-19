package com.example.jeemedexteleexpertise.util;

import com.example.jeemedexteleexpertise.model.Generaliste;
import com.example.jeemedexteleexpertise.model.Infermier;
import com.example.jeemedexteleexpertise.model.Specialiste;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Listener to automatically initialize test users when the application starts
 */
@WebListener
public class DataInitializationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=".repeat(60));
        System.out.println("Starting application initialization...");
        System.out.println("=".repeat(60));

        initializeTestUsers();

        System.out.println("=".repeat(60));
        System.out.println("Application initialization complete!");
        System.out.println("=".repeat(60));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application shutting down...");
    }

    private void initializeTestUsers() {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("medex-pu");
            em = emf.createEntityManager();

            em.getTransaction().begin();

            // Check if users already exist
            Long count = em.createQuery("SELECT COUNT(u) FROM Utilisateur u", Long.class).getSingleResult();
            if (count > 0) {
                System.out.println("ℹ Users already exist in database (" + count + " users found)");
                System.out.println("  Skipping user initialization.");
                em.getTransaction().rollback();
                return;
            }

            System.out.println("✓ Creating test users...");

            // Create test Généraliste
            Generaliste generaliste = new Generaliste();
            generaliste.setNom("Simo");
            generaliste.setPrenom("Generaliste");
            generaliste.setEmail("generaliste@test.com");
            generaliste.setMotDePasse(BCrypt.hashpw("password123", BCrypt.gensalt()));
            em.persist(generaliste);
            System.out.println("  ✓ Généraliste created: Jean Dupont");

            // Create test Spécialiste - Cardiologue
            Specialiste specialiste1 = new Specialiste();
            specialiste1.setNom("Simo");
            specialiste1.setPrenom("Specialiste");
            specialiste1.setEmail("specialiste@test.com");
            specialiste1.setMotDePasse(BCrypt.hashpw("password123", BCrypt.gensalt()));
            specialiste1.setTarif(300.0);
            specialiste1.setSpecialite("CARDIOLOGIE");
            em.persist(specialiste1);
            System.out.println("  ✓ Spécialiste created: Marie Martin (Cardiologie)");

            // Create additional Spécialiste - Dermatologue
            Specialiste specialiste2 = new Specialiste();
            specialiste2.setNom("Bernard");
            specialiste2.setPrenom("Sophie");
            specialiste2.setEmail("dermato@test.com");
            specialiste2.setMotDePasse(BCrypt.hashpw("password123", BCrypt.gensalt()));
            specialiste2.setTarif(250.0);
            specialiste2.setSpecialite("DERMATOLOGIE");
            em.persist(specialiste2);
            System.out.println("  ✓ Spécialiste created: Sophie Bernard (Dermatologie)");

            // Create additional Spécialiste - Neurologue
            Specialiste specialiste3 = new Specialiste();
            specialiste3.setNom("Petit");
            specialiste3.setPrenom("Thomas");
            specialiste3.setEmail("neuro@test.com");
            specialiste3.setMotDePasse(BCrypt.hashpw("password123", BCrypt.gensalt()));
            specialiste3.setTarif(350.0);
            specialiste3.setSpecialite("NEUROLOGIE");
            em.persist(specialiste3);
            System.out.println("  ✓ Spécialiste created: Thomas Petit (Neurologie)");

            // Create test Infirmier
            Infermier infirmier = new Infermier();
            infirmier.setNom("Simo");
            infirmier.setPrenom("Infirmier");
            infirmier.setEmail("infirmier@test.com");
            infirmier.setMotDePasse(BCrypt.hashpw("password123", BCrypt.gensalt()));
            em.persist(infirmier);
            System.out.println("  ✓ Infirmier created: Claire Durand");

            em.getTransaction().commit();

            System.out.println();
            System.out.println("✓ Test users created successfully!");
            System.out.println();
            System.out.println("Login credentials (all use password: password123):");
            System.out.println("  • Généraliste: generaliste@test.com");
            System.out.println("  • Spécialiste (Cardio): specialiste@test.com");
            System.out.println("  • Spécialiste (Dermato): dermato@test.com");
            System.out.println("  • Spécialiste (Neuro): neuro@test.com");
            System.out.println("  • Infirmier: infirmier@test.com");

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("✗ Error creating test users: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}

