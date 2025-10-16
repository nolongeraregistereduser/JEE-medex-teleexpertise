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
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/infirmier/patient-registration")
public class PatientRegistrationServlet extends HttpServlet {

    @Inject
    private PatientService patientService;

    @Inject
    private DossierMedicalService dossierMedicalService;

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

        String action = request.getParameter("action");

        if ("search".equals(action)) {
            handlePatientSearch(request, response);
        } else {
            request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null || user.getRole() != Role.INFIRMIER) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        if ("search".equals(action)) {
            handlePatientSearch(request, response);
        } else if ("register".equals(action)) {
            handlePatientRegistration(request, response);
        } else if ("addVitals".equals(action)) {
            handleAddVitalSigns(request, response);
        }
    }

    private void handlePatientSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String numSecu = request.getParameter("numSecu");

        if (numSecu != null && !numSecu.trim().isEmpty()) {
            Patient patient = patientService.findByNumeroSecuriteSociale(numSecu.trim());

            if (patient != null) {
                request.setAttribute("patient", patient);
                request.setAttribute("searchResult", "found");

                SignesVitaux latestVitals = signesVitauxService.findLatestByPatientId(patient.getId());
                request.setAttribute("latestVitals", latestVitals);
            } else {
                request.setAttribute("searchResult", "notFound");
                request.setAttribute("numSecu", numSecu);
            }
        }

        request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
    }

    private void handlePatientRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String dateNaissanceStr = request.getParameter("dateNaissance");
            String numSecu = request.getParameter("numSecu");
            String telephone = request.getParameter("telephone");
            String adresse = request.getParameter("adresse");
            String mutuelle = request.getParameter("mutuelle");

            LocalDate dateNaissance = LocalDate.parse(dateNaissanceStr);

            Patient patient = new Patient(nom, prenom, dateNaissance, numSecu);
            patient.setTelephone(telephone);
            patient.setAdresse(adresse);
            patient.setMutuelle(mutuelle);

            patientService.save(patient);

            DossierMedical dossier = new DossierMedical(patient);
            dossierMedicalService.save(dossier);

            request.setAttribute("patient", patient);
            request.setAttribute("success", "Patient enregistré avec succès");
            request.setAttribute("showVitalsForm", true);

        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'enregistrement: " + e.getMessage());
        }

        request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
    }

    private void handleAddVitalSigns(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long patientId = Long.parseLong(request.getParameter("patientId"));
            String tension = request.getParameter("tension");
            Integer frequenceCardiaque = Integer.parseInt(request.getParameter("frequenceCardiaque"));
            Double temperature = Double.parseDouble(request.getParameter("temperature"));
            Integer frequenceRespiratoire = Integer.parseInt(request.getParameter("frequenceRespiratoire"));
            Double poids = request.getParameter("poids").isEmpty() ? null : Double.parseDouble(request.getParameter("poids"));
            Double taille = request.getParameter("taille").isEmpty() ? null : Double.parseDouble(request.getParameter("taille"));

            Patient patient = patientService.findById(patientId);
            DossierMedical dossier = dossierMedicalService.findByPatientId(patientId);

            SignesVitaux signesVitaux = new SignesVitaux(dossier);
            signesVitaux.setTensionArterielle(tension);
            signesVitaux.setFrequenceCardiaque(frequenceCardiaque);
            signesVitaux.setTemperature(temperature);
            signesVitaux.setFrequenceRespiratoire(frequenceRespiratoire);
            signesVitaux.setPoids(poids);
            signesVitaux.setTaille(taille);

            signesVitauxService.save(signesVitaux);

            FileAttente fileAttente = new FileAttente(patient);
            fileAttenteService.addPatientToQueue(fileAttente);

            request.setAttribute("success", "Signes vitaux enregistrés et patient ajouté à la file d'attente");
            response.sendRedirect(request.getContextPath() + "/infirmier/dashboard");
            return;

        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'enregistrement des signes vitaux: " + e.getMessage());
            request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
        }
    }
}
