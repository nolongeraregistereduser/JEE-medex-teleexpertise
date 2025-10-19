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
        } else if ("addDossier".equals(action)) {
            handleAddDossier(request, response);
        }
    }

    private void handlePatientSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ssn = request.getParameter("ssn");

        PatientDAO patientDAO = new PatientDAO();
        if (ssn != null && !ssn.trim().isEmpty()) {
            // search by unique SSN
            Patient patient = patientDAO.findBySSN(ssn.trim());
            if (patient != null) {
                request.setAttribute("searchResult", "found");
                SignesVitauxDAO signesVitauxDAO = new SignesVitauxDAO();
                SignesVitaux latestVitals = signesVitauxDAO.findLatestByPatientId(patient.getId());
                request.setAttribute("latestVitals", latestVitals);
                request.setAttribute("patient", patient);
                // fetch dossier if exists
                DossierMedicalDAO dossierDAO = new DossierMedicalDAO();
                DossierMedical dossier = dossierDAO.findByPatientId(patient.getId());
                request.setAttribute("dossier", dossier);
            } else {
                request.setAttribute("searchResult", "notFound");
                request.setAttribute("searchSSN", ssn);
            }
            request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
            return;
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
            String numeroSecuriteSociale = request.getParameter("numeroSecuriteSociale");

            // validate SSN presence (DB requires non-null)
            if (numeroSecuriteSociale == null || numeroSecuriteSociale.trim().isEmpty()) {
                request.setAttribute("error", "Numéro de sécurité sociale requis.");
                request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
                return;
            }

            PatientDAO patientDAO = new PatientDAO();
            Patient existing = patientDAO.findBySSN(numeroSecuriteSociale.trim());
            if (existing != null) {
                // If patient already exists with SSN, redirect to search result showing existing patient
                request.setAttribute("patient", existing);
                request.setAttribute("searchResult", "found");
                SignesVitauxDAO signesVitauxDAO = new SignesVitauxDAO();
                SignesVitaux latestVitals = signesVitauxDAO.findLatestByPatientId(existing.getId());
                request.setAttribute("latestVitals", latestVitals);
                DossierMedicalDAO dossierDAO = new DossierMedicalDAO();
                DossierMedical dossier = dossierDAO.findByPatientId(existing.getId());
                request.setAttribute("dossier", dossier);
                request.setAttribute("error", "Un patient avec ce numéro de sécurité sociale existe déjà.");
                request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
                return;
            }

            LocalDate dateNaissance = LocalDate.parse(dateNaissanceStr);

            Patient patient = new Patient(nom, prenom, dateNaissance);
            patient.setTelephone(telephone);
            patient.setAdresse(adresse);
            patient.setMutuelle(mutuelle);
            patient.setNumeroSecuriteSociale(numeroSecuriteSociale.trim());

            patientDAO.save(patient);

            DossierMedical dossier = new DossierMedical(patient);
            DossierMedicalDAO dossierDAO = new DossierMedicalDAO();
            dossierDAO.save(dossier);

            // Check if vitals were submitted as part of the registration form
            String tension = request.getParameter("tension");
            String frequenceCardiaqueStr = request.getParameter("frequenceCardiaque");
            String temperatureStr = request.getParameter("temperature");
            String frequenceRespiratoireStr = request.getParameter("frequenceRespiratoire");
            String poidsStr = request.getParameter("poids");
            String tailleStr = request.getParameter("taille");

            boolean vitalsProvided = (tension != null && !tension.isBlank()) ||
                    (frequenceCardiaqueStr != null && !frequenceCardiaqueStr.isBlank()) ||
                    (temperatureStr != null && !temperatureStr.isBlank()) ||
                    (frequenceRespiratoireStr != null && !frequenceRespiratoireStr.isBlank()) ||
                    (poidsStr != null && !poidsStr.isBlank()) ||
                    (tailleStr != null && !tailleStr.isBlank());

            if (vitalsProvided) {
                // parse numeric vitals defensively
                Integer frequenceCardiaque = null;
                Double temperature = null;
                Integer frequenceRespiratoire = null;
                Double poids = null;
                Double taille = null;

                try { if (frequenceCardiaqueStr != null && !frequenceCardiaqueStr.isBlank()) frequenceCardiaque = Integer.parseInt(frequenceCardiaqueStr); } catch (NumberFormatException ignored) {}
                try { if (temperatureStr != null && !temperatureStr.isBlank()) temperature = Double.parseDouble(temperatureStr); } catch (NumberFormatException ignored) {}
                try { if (frequenceRespiratoireStr != null && !frequenceRespiratoireStr.isBlank()) frequenceRespiratoire = Integer.parseInt(frequenceRespiratoireStr); } catch (NumberFormatException ignored) {}
                try { if (poidsStr != null && !poidsStr.isBlank()) poids = Double.parseDouble(poidsStr); } catch (NumberFormatException ignored) {}
                try { if (tailleStr != null && !tailleStr.isBlank()) taille = Double.parseDouble(tailleStr); } catch (NumberFormatException ignored) {}

                SignesVitaux signesVitaux = new SignesVitaux(dossier);
                signesVitaux.setTensionArterielle(tension);
                signesVitaux.setFrequenceCardiaque(frequenceCardiaque);
                signesVitaux.setTemperature(temperature);
                signesVitaux.setFrequenceRespiratoire(frequenceRespiratoire);
                signesVitaux.setPoids(poids);
                signesVitaux.setTaille(taille);

                SignesVitauxDAO signesVitauxDAO = new SignesVitauxDAO();
                signesVitauxDAO.save(signesVitaux);

                // Automatically add to queue when vitals provided during registration
                FileAttente fileAttente = new FileAttente(patient);
                FileAttenteDAO fileAttenteDAO = new FileAttenteDAO();
                fileAttenteDAO.save(fileAttente);

                // Redirect to dashboard after successful registration + queue
                response.sendRedirect(request.getContextPath() + "/infirmier/dashboard");
                return;
            }

            // If no vitals provided, show success and keep patient/dossier for vitals entry
            request.setAttribute("patient", patient);
            request.setAttribute("dossier", dossier);
            request.setAttribute("success", "Patient enregistré avec succès. Veuillez saisir les signes vitaux.");

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

        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'enregistrement des signes vitaux: " + e.getMessage());
            request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
        }
    }

    private void handleAddDossier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long patientId = Long.parseLong(request.getParameter("patientId"));
            String antecedents = request.getParameter("antecedents");
            String allergies = request.getParameter("allergies");
            String traitement = request.getParameter("traitementEnCours");

            DossierMedicalDAO dossierDAO = new DossierMedicalDAO();
            DossierMedical dossier = dossierDAO.findByPatientId(patientId);
            if (dossier == null) {
                PatientDAO patientDAO = new PatientDAO();
                Patient patient = patientDAO.findById(patientId);
                dossier = new DossierMedical(patient);
                dossier.setAntecedents(antecedents);
                dossier.setAllergies(allergies);
                dossier.setTraitementEnCours(traitement);
                dossierDAO.save(dossier);
            } else {
                dossier.setAntecedents(antecedents);
                dossier.setAllergies(allergies);
                dossier.setTraitementEnCours(traitement);
                dossierDAO.update(dossier);
            }

            request.setAttribute("success", "Dossier médical mis à jour avec succès");
            // reload patient view
            PatientDAO patientDAO = new PatientDAO();
            Patient patient = patientDAO.findById(patientId);
            request.setAttribute("patient", patient);
            request.setAttribute("dossier", dossier);
            SignesVitauxDAO signesVitauxDAO = new SignesVitauxDAO();
            SignesVitaux latestVitals = signesVitauxDAO.findLatestByPatientId(patientId);
            request.setAttribute("latestVitals", latestVitals);

        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la mise à jour du dossier: " + e.getMessage());
        }

        request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
    }
}
