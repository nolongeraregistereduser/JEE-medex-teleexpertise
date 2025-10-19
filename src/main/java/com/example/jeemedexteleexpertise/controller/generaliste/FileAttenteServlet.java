package com.example.jeemedexteleexpertise.controller.generaliste;

import com.example.jeemedexteleexpertise.model.FileAttente;
import com.example.jeemedexteleexpertise.service.FileAttenteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/generaliste/file-attente")
public class FileAttenteServlet extends HttpServlet {

    private FileAttenteService fileAttenteService;

    @Override
    public void init() throws ServletException {
        this.fileAttenteService = new FileAttenteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get today's queue - already sorted by arrival time using Stream API
        List<FileAttente> queue = fileAttenteService.findTodayQueueSortedByArrival();

        // list only the patients still waiting
        queue = queue.stream()
                .filter(f -> f.getStatus() != null && f.getStatus().name().equals("EN_ATTENTE"))
                .toList();

        // Count waiting patients
        long waitingCount = fileAttenteService.countWaiting();
        long totalToday = queue.size();

        // Set attributes for JSP
        request.setAttribute("queue", queue);
        request.setAttribute("waitingCount", waitingCount);
        request.setAttribute("totalToday", totalToday);

        // Forward to JSP page
        request.getRequestDispatcher("/jsp/generaliste/file-attente.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("take-charge".equals(action)) {
            // Handle "Prendre en charge" button
            handleTakeCharge(request, response);
        } else {
            // Default: redirect to queue page
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente");
        }
    }

    private void handleTakeCharge(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fileAttenteIdStr = request.getParameter("fileAttenteId");

        if (fileAttenteIdStr == null || fileAttenteIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=invalid");
            return;
        }

        try {
            Long fileAttenteId = Long.parseLong(fileAttenteIdStr);

            // Update status to PRISE_EN_CHARGE
            fileAttenteService.markAsPrisEnCharge(fileAttenteId);

            // Redirect with success message
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?success=taken");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=invalid");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=true");
        }
    }
}

