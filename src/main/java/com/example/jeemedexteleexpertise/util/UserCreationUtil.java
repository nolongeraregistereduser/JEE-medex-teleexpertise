package com.example.jeemedexteleexpertise.util;

import com.example.jeemedexteleexpertise.model.Generaliste;
import com.example.jeemedexteleexpertise.model.Infermier;
import com.example.jeemedexteleexpertise.model.Specialiste;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class to create test users with hashed passwords
 */
public class UserCreationUtil {

    public static void main(String[] args) {
        createTestUsers();
    }

    public static void createTestUsers() {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("medex-pu");
            em = emf.createEntityManager();

            em.getTransaction().begin();

            // Check if users already exist
            Long count = em.createQuery("SELECT COUNT(u) FROM Utilisateur u", Long.class).getSingleResult();
            if (count > 0) {
                System.out.println("Users already exist. Skipping initialization.");
                em.getTransaction().rollback();
                return;
            }

            // Create test Généraliste
            Generaliste generaliste = new Generaliste();
            generaliste.setNom("Dupont");
            generaliste.setPrenom("Jean");
            generaliste.setEmail("generaliste@test.com");
            generaliste.setMotDePasse(BCrypt.hashpw("password123", BCrypt.gensalt()));
            em.persist(generaliste);

            // Create test Spécialiste
            Specialiste specialiste = new Specialiste();
            specialiste.setNom("Martin");
            specialiste.setPrenom("Marie");
            specialiste.setEmail("specialiste@test.com");
            specialiste.setMotDePasse(BCrypt.hashpw("password123", BCrypt.gensalt()));
            specialiste.setTarif(300.0);
            specialiste.setSpecialite("CARDIOLOGIE");
            em.persist(specialiste);

            // Create test Infirmier
            Infermier infirmier = new Infermier();
            infirmier.setNom("Durand");
            infirmier.setPrenom("Claire");
            infirmier.setEmail("infirmier@test.com");
            infirmier.setMotDePasse(BCrypt.hashpw("password123", BCrypt.gensalt()));
            em.persist(infirmier);

            em.getTransaction().commit();

            System.out.println("✓ Test users created successfully!");
            System.out.println("=".repeat(50));
            System.out.println("Login credentials:");
            System.out.println("  Généraliste: generaliste@test.com / password123");
            System.out.println("  Spécialiste: specialiste@test.com / password123");
            System.out.println("  Infirmier: infirmier@test.com / password123");
            System.out.println("=".repeat(50));

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error creating test users: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}
