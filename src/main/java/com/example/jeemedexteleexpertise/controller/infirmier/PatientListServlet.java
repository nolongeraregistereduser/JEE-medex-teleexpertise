package com.example.jeemedexteleexpertise.controller.infirmier;

import com.example.jeemedexteleexpertise.model.FileAttente;
import com.example.jeemedexteleexpertise.service.FileAttenteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PatientListServlet", urlPatterns = {"/infirmier/patients"})
public class PatientListServlet extends HttpServlet {

    private FileAttenteService fileAttenteService;

    @Override
    public void init() throws ServletException {
        this.fileAttenteService = new FileAttenteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get today's queue sorted by arrival time
        List<FileAttente> todayQueue = fileAttenteService.findTodayQueueSortedByArrival();

        // Get count of waiting patients
        long waitingCount = fileAttenteService.countWaiting();

        request.setAttribute("queue", todayQueue);
        request.setAttribute("waitingCount", waitingCount);
        request.setAttribute("totalToday", todayQueue.size());

        // Forward to the patient list JSP
        request.getRequestDispatcher("/jsp/infirmier/patient-list.jsp").forward(request, response);
    }
}

