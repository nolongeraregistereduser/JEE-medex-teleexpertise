package com.example.jeemedexteleexpertise.controller.specialiste;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "SpecialisteDashboardServlet", urlPatterns = {"/specialiste/dashboard"})
public class SpecialisteDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/specialiste/dashboard.jsp").forward(request, response);
    }
}

