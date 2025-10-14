package com.example.jeemedexteleexpertise.servlet;

import com.example.jeemedexteleexpertise.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test-db")
public class DatabaseTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Database Test</title></head><body>");
        out.println("<h1>Database Connection Test</h1>");

        EntityManager em = null;
        try {
            // Test Hibernate connection
            em = HibernateUtil.getEntityManager();

            // Test query to count users
            Long userCount = (Long) em.createNativeQuery("SELECT COUNT(*) FROM utilisateur", Long.class)
                    .getSingleResult();

            out.println("<p style='color: green;'>✅ Database connection successful!</p>");
            out.println("<p>📊 Number of users in database: " + userCount + "</p>");

            // Test query to show all tables
            out.println("<h2>Database Tables Created:</h2>");
            out.println("<ul>");

            try {
                em.createNativeQuery("SELECT 1 FROM utilisateur LIMIT 1").getResultList();
                out.println("<li>✅ utilisateur</li>");
            } catch (Exception e) {
                out.println("<li>❌ utilisateur</li>");
            }

            try {
                em.createNativeQuery("SELECT 1 FROM patient LIMIT 1").getResultList();
                out.println("<li>✅ patient</li>");
            } catch (Exception e) {
                out.println("<li>❌ patient</li>");
            }

            try {
                em.createNativeQuery("SELECT 1 FROM consultation LIMIT 1").getResultList();
                out.println("<li>✅ consultation</li>");
            } catch (Exception e) {
                out.println("<li>❌ consultation</li>");
            }

            try {
                em.createNativeQuery("SELECT 1 FROM dossier_medical LIMIT 1").getResultList();
                out.println("<li>✅ dossier_medical</li>");
            } catch (Exception e) {
                out.println("<li>❌ dossier_medical</li>");
            }

            out.println("</ul>");

        } catch (Exception e) {
            out.println("<p style='color: red;'>❌ Database connection failed: " + e.getMessage() + "</p>");
            e.printStackTrace();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }

        out.println("</body></html>");
    }
}
