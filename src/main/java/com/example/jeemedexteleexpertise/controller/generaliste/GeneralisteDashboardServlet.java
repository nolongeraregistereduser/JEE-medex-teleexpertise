package com.example.jeemedexteleexpertise.controller.generaliste;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "GeneralisteDashboardServlet", urlPatterns = {"/generaliste/dashboard"})
public class GeneralisteDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/generaliste/dashboard.jsp").forward(request, response);
    }
}

