package com.example.jeemedexteleexpertise.controller;

import com.example.jeemedexteleexpertise.model.Utilisateur;
import com.example.jeemedexteleexpertise.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UtilisateurService utilisateurService;

    @Override
    public void init() throws ServletException {
        utilisateurService = new UtilisateurService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            Utilisateur user = (Utilisateur) session.getAttribute("user");
            redirectToDashboard(request, response, user);
            return;
        }

        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email et mot de passe sont requis");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }

        try {
            Optional<Utilisateur> userOpt = utilisateurService.authenticate(email.trim(), password);

            if (userOpt.isEmpty()) {
                request.setAttribute("error", "Email ou mot de passe incorrect");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                return;
            }

            Utilisateur user = userOpt.get();

            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userName", user.getNomComplet());
            session.setMaxInactiveInterval(30 * 60);

            redirectToDashboard(request, response, user);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur est survenue lors de la connexion");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }

    private void redirectToDashboard(HttpServletRequest request, HttpServletResponse response,
                                     Utilisateur user) throws IOException {
        switch (user.getRole()) {
            case INFIRMIER:
                response.sendRedirect(request.getContextPath() + "/infirmier/dashboard");
                break;
            case GENERALISTE:
                response.sendRedirect(request.getContextPath() + "/generaliste/dashboard");
                break;
            case SPECIALISTE:
                response.sendRedirect(request.getContextPath() + "/specialiste/dashboard");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }
}

