package com.example.jeemedexteleexpertise.servlet;

import com.example.jeemedexteleexpertise.model.*;
import com.example.jeemedexteleexpertise.service.*;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/infirmier/dashboard")
public class InfirmierDashboardServlet extends HttpServlet {

    @Inject
    private PatientService patientService;

    @Inject
    private SignesVitauxService signesVitauxService;

    @Inject
    private FileAttenteService fileAttenteService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null || user.getRole() != Role.INFIRMIER) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<SignesVitaux> todaysVitals = signesVitauxService.findTodaysSignesVitaux();

        List<PatientDashboardInfo> todaysPatients = todaysVitals.stream()
                .filter(vitals -> vitals.getDateSaisie().toLocalDate().equals(LocalDate.now()))
                .map(vitals -> {
                    Patient patient = vitals.getPatient();
                    PatientDashboardInfo info = new PatientDashboardInfo();
                    info.setPatient(patient);
                    info.setSignesVitaux(vitals);
                    return info;
                })
                .sorted((p1, p2) -> p1.getSignesVitaux().getDateSaisie()
                        .compareTo(p2.getSignesVitaux().getDateSaisie()))
                .collect(Collectors.toList());

        List<FileAttente> currentQueue = fileAttenteService.getCurrentQueue();
        long waitingCount = fileAttenteService.countPatientsWaiting();

        request.setAttribute("todaysPatients", todaysPatients);
        request.setAttribute("currentQueue", currentQueue);
        request.setAttribute("waitingCount", waitingCount);
        request.setAttribute("totalToday", todaysPatients.size());

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
