package com.example.jeemedexteleexpertise.controller.infirmier;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "InfirmierDashboardServlet", urlPatterns = {"/infirmier/dashboard"})
public class InfirmierDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/infirmier/dashboard.jsp").forward(request, response);
    }
}

