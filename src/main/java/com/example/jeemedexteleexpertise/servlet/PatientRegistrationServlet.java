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
import java.time.LocalDate;
import java.util.List;

@WebServlet("/infirmier/patient-registration")
public class PatientRegistrationServlet extends HttpServlet {

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

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");

        if ((nom != null && !nom.trim().isEmpty()) || (prenom != null && !prenom.trim().isEmpty())) {
            PatientDAO patientDAO = new PatientDAO();
            List<Patient> patients;

            if (nom != null && !nom.trim().isEmpty() && prenom != null && !prenom.trim().isEmpty()) {
                patients = patientDAO.findByNomAndPrenom(nom.trim(), prenom.trim());
            } else {
                String searchTerm = nom != null && !nom.trim().isEmpty() ? nom.trim() : prenom.trim();
                patients = patientDAO.searchByName(searchTerm);
            }

            if (!patients.isEmpty()) {
                request.setAttribute("patients", patients);
                request.setAttribute("searchResult", "found");

                if (patients.size() == 1) {
                    Patient patient = patients.get(0);
                    SignesVitauxDAO signesVitauxDAO = new SignesVitauxDAO();
                    SignesVitaux latestVitals = signesVitauxDAO.findLatestByPatientId(patient.getId());
                    request.setAttribute("latestVitals", latestVitals);
                }
            } else {
                request.setAttribute("searchResult", "notFound");
                request.setAttribute("searchNom", nom);
                request.setAttribute("searchPrenom", prenom);
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
            String telephone = request.getParameter("telephone");
            String adresse = request.getParameter("adresse");
            String mutuelle = request.getParameter("mutuelle");

            LocalDate dateNaissance = LocalDate.parse(dateNaissanceStr);

            Patient patient = new Patient(nom, prenom, dateNaissance);
            patient.setTelephone(telephone);
            patient.setAdresse(adresse);
            patient.setMutuelle(mutuelle);

            PatientDAO patientDAO = new PatientDAO();
            patientDAO.save(patient);

            DossierMedical dossier = new DossierMedical(patient);
            DossierMedicalDAO dossierDAO = new DossierMedicalDAO();
            dossierDAO.save(dossier);

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

            PatientDAO patientDAO = new PatientDAO();
            Patient patient = patientDAO.findById(patientId);

            DossierMedicalDAO dossierDAO = new DossierMedicalDAO();
            DossierMedical dossier = dossierDAO.findByPatientId(patientId);

            SignesVitaux signesVitaux = new SignesVitaux(dossier);
            signesVitaux.setTensionArterielle(tension);
            signesVitaux.setFrequenceCardiaque(frequenceCardiaque);
            signesVitaux.setTemperature(temperature);
            signesVitaux.setFrequenceRespiratoire(frequenceRespiratoire);
            signesVitaux.setPoids(poids);
            signesVitaux.setTaille(taille);

            SignesVitauxDAO signesVitauxDAO = new SignesVitauxDAO();
            signesVitauxDAO.save(signesVitaux);

            FileAttente fileAttente = new FileAttente(patient);
            FileAttenteDAO fileAttenteDAO = new FileAttenteDAO();
            fileAttenteDAO.save(fileAttente);

            request.setAttribute("success", "Signes vitaux enregistrés et patient ajouté à la file d'attente");
            response.sendRedirect(request.getContextPath() + "/infirmier/dashboard");
            return;

        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'enregistrement des signes vitaux: " + e.getMessage());
            request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
        }
    }
}
