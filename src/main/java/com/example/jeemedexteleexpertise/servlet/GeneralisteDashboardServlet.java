package com.example.jeemedexteleexpertise.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "GeneralisteDashboardServlet", urlPatterns = {"/generaliste/dashboard"})
public class GeneralisteDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Check if user has the right role
        String userRole = (String) session.getAttribute("userRole");
        if (!"GENERALISTE".equals(userRole)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Forward to dashboard JSP
        request.getRequestDispatcher("/jsp/generaliste/dashboard.jsp").forward(request, response);
    }
}
