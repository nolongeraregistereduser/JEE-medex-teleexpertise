package com.example.jeemedexteleexpertise.servlet;

import com.example.jeemedexteleexpertise.model.Generaliste;
import com.example.jeemedexteleexpertise.model.Infermier;
import com.example.jeemedexteleexpertise.model.Specialiste;
import com.example.jeemedexteleexpertise.service.AuthService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CreateTestUsersServlet", urlPatterns = {"/create-test-users"})
public class CreateTestUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Creating Test Users...</title>");
        out.println("<style>body{font-family: Arial, sans-serif; padding: 20px; background: #f5f5f5;} .log{background: #eee; padding: 10px; margin: 10px 0; border-radius: 5px;}</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>🔄 Creating Test Users...</h2>");
        out.flush();

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            out.println("<div class='log'>📁 Creating EntityManagerFactory...</div>");
            out.flush();

            emf = Persistence.createEntityManagerFactory("medex-pu");
            em = emf.createEntityManager();

            out.println("<div class='log'>✅ EntityManager created successfully</div>");
            out.flush();

            AuthService authService = new AuthService();

            out.println("<div class='log'>🔒 Starting transaction...</div>");
            out.flush();

            em.getTransaction().begin();

            // Create test Généraliste
            out.println("<div class='log'>👨‍⚕️ Creating Généraliste...</div>");
            out.flush();

            Generaliste generaliste = new Generaliste();
            generaliste.setNom("Dupont");
            generaliste.setPrenom("Jean");
            generaliste.setEmail("generaliste@test.com");
            generaliste.setMotDePasse(authService.hashPassword("password123"));
            generaliste.setActif(true);
            em.persist(generaliste);

            out.println("<div class='log'>✅ Généraliste created: " + generaliste.getNomComplet() + "</div>");
            out.flush();

            // Create test Spécialiste
            out.println("<div class='log'>🔬 Creating Spécialiste...</div>");
            out.flush();

            Specialiste specialiste = new Specialiste();
            specialiste.setNom("Martin");
            specialiste.setPrenom("Marie");
            specialiste.setEmail("specialiste@test.com");
            specialiste.setMotDePasse(authService.hashPassword("password123"));
            specialiste.setActif(true);
            specialiste.setTarif(300.0);
            specialiste.setSpecialite("CARDIOLOGIE"); // Fix: Set the required specialite field
            specialiste.setDureeConsultation(30);
            em.persist(specialiste);

            out.println("<div class='log'>✅ Spécialiste created: " + specialiste.getNomComplet() + "</div>");
            out.flush();

            // Create test Infirmier
            out.println("<div class='log'>👩‍⚕️ Creating Infirmier...</div>");
            out.flush();

            Infermier infirmier = new Infermier();
            infirmier.setNom("Durand");
            infirmier.setPrenom("Claire");
            infirmier.setEmail("infirmier@test.com");
            infirmier.setMotDePasse(authService.hashPassword("password123"));
            infirmier.setActif(true);
            em.persist(infirmier);

            out.println("<div class='log'>✅ Infirmier created: " + infirmier.getNomComplet() + "</div>");
            out.flush();

            out.println("<div class='log'>💾 Committing transaction...</div>");
            out.flush();

            em.getTransaction().commit();

            out.println("<div class='log'>✅ All users saved to database successfully!</div>");
            out.flush();

            out.println("<h2>✅ Test users created successfully!</h2>");
            out.println("<h3>Login credentials:</h3>");
            out.println("<ul>");
            out.println("<li><strong>Généraliste:</strong> generaliste@test.com / password123</li>");
            out.println("<li><strong>Spécialiste:</strong> specialiste@test.com / password123</li>");
            out.println("<li><strong>Infirmier:</strong> infirmier@test.com / password123</li>");
            out.println("</ul>");
            out.println("<a href='" + request.getContextPath() + "/login' style='background: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Go to Login Page</a>");

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                out.println("<div class='log'>❌ Rolling back transaction due to error...</div>");
                out.flush();
                em.getTransaction().rollback();
            }

            out.println("<h2>❌ Error creating test users:</h2>");
            out.println("<div class='log'><strong>Error:</strong> " + e.getMessage() + "</div>");

            // Print stack trace for debugging
            out.println("<h3>Stack Trace (for debugging):</h3>");
            out.println("<pre style='background: #ffeeee; padding: 10px; border-radius: 5px;'>");
            e.printStackTrace(out);
            out.println("</pre>");

            out.println("<a href='" + request.getContextPath() + "/login' style='background: #dc3545; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Back to Login</a>");

        } finally {
            out.println("<div class='log'>🧹 Cleaning up resources...</div>");
            out.flush();

            if (em != null) {
                em.close();
                out.println("<div class='log'>✅ EntityManager closed</div>");
                out.flush();
            }
            if (emf != null) {
                emf.close();
                out.println("<div class='log'>✅ EntityManagerFactory closed</div>");
                out.flush();
            }
        }

        out.println("</body>");
        out.println("</html>");
    }
}
