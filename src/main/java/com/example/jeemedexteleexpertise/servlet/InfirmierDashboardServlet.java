package com.example.jeemedexteleexpertise.servlet;

import com.example.jeemedexteleexpertise.model.*;
import com.example.jeemedexteleexpertise.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/infirmier/dashboard")
public class InfirmierDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null || user.getRole() != Role.INFIRMIER) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            SignesVitauxDAO signesVitauxDAO = new SignesVitauxDAO();
            FileAttenteDAO fileAttenteDAO = new FileAttenteDAO();

            List<SignesVitaux> todaysVitals = signesVitauxDAO.findTodaysSignesVitaux();
            List<FileAttente> currentQueue = fileAttenteDAO.getCurrentQueue();
            long waitingCount = fileAttenteDAO.countPatientsWaiting();

            List<PatientDashboardInfo> todaysPatients = new ArrayList<>();

            for (SignesVitaux vitals : todaysVitals) {
                Patient patient = vitals.getPatient();
                if (patient != null) {
                    PatientDashboardInfo info = new PatientDashboardInfo();
                    info.setPatient(patient);
                    info.setSignesVitaux(vitals);
                    todaysPatients.add(info);
                }
            }

            request.setAttribute("todaysPatients", todaysPatients);
            request.setAttribute("currentQueue", currentQueue);
            request.setAttribute("waitingCount", waitingCount);
            request.setAttribute("totalToday", todaysPatients.size());

        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors du chargement des données: " + e.getMessage());
            request.setAttribute("todaysPatients", new ArrayList<>());
            request.setAttribute("currentQueue", new ArrayList<>());
            request.setAttribute("waitingCount", 0L);
            request.setAttribute("totalToday", 0);
        }

        request.getRequestDispatcher("/jsp/infirmier/dashboard.jsp").forward(request, response);
    }

    public static class PatientDashboardInfo {
        private Patient patient;
        private SignesVitaux signesVitaux;

        public Patient getPatient() { return patient; }
        public void setPatient(Patient patient) { this.patient = patient; }

        public SignesVitaux getSignesVitaux() { return signesVitaux; }
        public void setSignesVitaux(SignesVitaux signesVitaux) { this.signesVitaux = signesVitaux; }
    }


}
