package com.example.jeemedexteleexpertise.servlet;

import com.example.jeemedexteleexpertise.model.Utilisateur;
import com.example.jeemedexteleexpertise.service.AuthService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {

    @Inject
    private AuthService authService;

    @Override
    public void init() throws ServletException {
        authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/logout".equals(path)) {
            handleLogout(request, response);
        } else {
            // Show login page
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        handleLogin(request, response);
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Simple validation
        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Veuillez saisir votre email et mot de passe");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }

        // Authenticate user
        Utilisateur user = authService.authenticate(email.trim(), password);

        if (user != null) {
            // Login successful - create session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userRole", user.getRole().name());
            session.setAttribute("userName", user.getNomComplet());

            // Redirect to appropriate dashboard
            String dashboardUrl = authService.getDashboardUrl(user);
            response.sendRedirect(request.getContextPath() + "/" + dashboardUrl);

        } else {
            // Login failed
            request.setAttribute("errorMessage", "Email ou mot de passe incorrect");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Invalidate session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Redirect to login page
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
