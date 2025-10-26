package com.example.jeemedexteleexpertise.controller.generaliste;

import com.example.jeemedexteleexpertise.model.Consultation;
import com.example.jeemedexteleexpertise.model.Generaliste;
import com.example.jeemedexteleexpertise.model.Patient;
import com.example.jeemedexteleexpertise.model.StatusConsultation;
import com.example.jeemedexteleexpertise.model.Utilisateur;
import com.example.jeemedexteleexpertise.service.ConsultationService;
import com.example.jeemedexteleexpertise.service.PatientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/generaliste/consultation")
public class ConsultationServlet extends HttpServlet {

    private ConsultationService consultationService;
    private PatientService patientService;

    @Override
    public void init() throws ServletException {
        this.consultationService = new ConsultationService();
        this.patientService = new PatientService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("new".equals(action)) {
            // Show form to create new consultation
            showNewConsultationForm(request, response);
        } else if ("view".equals(action)) {
            // View specific consultation details
            viewConsultationDetails(request, response);
        } else {
            // Default: show list of consultations for current generaliste
            showConsultationList(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // Handle closing consultation
        if ("close".equals(action)) {
            closeConsultation(request, response);
            return;
        }

        // Existing code for creating consultation
        try {
            // Get form parameters
            String patientIdStr = request.getParameter("patientId");
            String motif = request.getParameter("motif");
            String observations = request.getParameter("observations");

            // Validate input
            if (patientIdStr == null || patientIdStr.trim().isEmpty()) {
                request.setAttribute("error", "Veuillez sélectionner un patient");
                showNewConsultationForm(request, response);
                return;
            }

            if (motif == null || motif.trim().isEmpty()) {
                request.setAttribute("error", "Le motif de consultation est requis");
                showNewConsultationForm(request, response);
                return;
            }

            Long patientId = Long.parseLong(patientIdStr);

            // Get current generaliste from session
            HttpSession session = request.getSession();
            Utilisateur user = (Utilisateur) session.getAttribute("user");

            if (!(user instanceof Generaliste)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            Generaliste generaliste = (Generaliste) user;

            // Get patient
            Optional<Patient> patientOpt = patientService.findById(patientId);
            if (patientOpt.isEmpty()) {
                request.setAttribute("error", "Patient non trouvé");
                showNewConsultationForm(request, response);
                return;
            }

            Patient patient = patientOpt.get();

            // Create new consultation
            Consultation consultation = new Consultation();
            consultation.setPatient(patient);
            consultation.setMedecinGeneraliste(generaliste);
            consultation.setMotif(motif.trim());
            consultation.setObservations(observations != null ? observations.trim() : null);
            consultation.setCout(150.0); // Fixed cost
            consultation.setStatus(StatusConsultation.TERMINEE);

            // Save consultation
            consultation = consultationService.save(consultation);

            // Redirect to consultation list with success message
            response.sendRedirect(request.getContextPath() +
                "/generaliste/consultation?success=true&consultationId=" + consultation.getId());

        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID patient invalide");
            showNewConsultationForm(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur est survenue: " + e.getMessage());
            showNewConsultationForm(request, response);
        }
    }

    private void showNewConsultationForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Load all patients for dropdown
        List<Patient> patients = patientService.findAll();


        request.setAttribute("patients", patients);

        // Forward to form
        request.getRequestDispatcher("/jsp/generaliste/consultation-form.jsp").forward(request, response);
    }

    private void showConsultationList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get current generaliste from session
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (!(user instanceof Generaliste)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        Generaliste generaliste = (Generaliste) user;

        // Load consultations for this generaliste
        List<Consultation> consultations = consultationService.findByGeneralisteId(generaliste.getId());

        // Calculate statistics
        long totalConsultations = consultations.size();
        long enAttente = consultations.stream()
            .filter(c -> c.getStatus() == StatusConsultation.EN_ATTENTE)
            .count();
        long enAttenteAvis = consultations.stream()
            .filter(c -> c.getStatus() == StatusConsultation.EN_ATTENTE_AVIS_SPECIALISTE)
            .count();
        long terminees = consultations.stream()
            .filter(c -> c.getStatus() == StatusConsultation.TERMINEE)
            .count();

        request.setAttribute("consultations", consultations);
        request.setAttribute("totalConsultations", totalConsultations);
        request.setAttribute("enAttente", enAttente);
        request.setAttribute("enAttenteAvis", enAttenteAvis);
        request.setAttribute("terminees", terminees);

        // Forward to list page
        request.getRequestDispatcher("/jsp/generaliste/consultation-list.jsp").forward(request, response);
    }

    private void viewConsultationDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String consultationIdStr = request.getParameter("id");

        if (consultationIdStr == null || consultationIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/generaliste/consultation");
            return;
        }

        try {
            Long consultationId = Long.parseLong(consultationIdStr);

            // Load consultation with details
            Optional<Consultation> consultationOpt = consultationService.findByIdWithDetails(consultationId);

            if (consultationOpt.isEmpty()) {
                request.setAttribute("error", "Consultation non trouvée");
                showConsultationList(request, response);
                return;
            }

            Consultation consultation = consultationOpt.get();

            // Verify access (only the generaliste who created it can view)
            HttpSession session = request.getSession();
            Utilisateur user = (Utilisateur) session.getAttribute("user");

            if (!consultation.getMedecinGeneraliste().getId().equals(user.getId())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            request.setAttribute("consultation", consultation);
            request.getRequestDispatcher("/jsp/generaliste/consultation-details.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/generaliste/consultation");
        }
    }

    private void closeConsultation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String consultationIdStr = request.getParameter("consultationId");
            String diagnostic = request.getParameter("diagnostic");
            String traitement = request.getParameter("traitement");

            if (consultationIdStr == null || diagnostic == null || traitement == null) {
                response.sendRedirect(request.getContextPath() + "/generaliste/consultation?error=missing");
                return;
            }

            Long consultationId = Long.parseLong(consultationIdStr);

            // Load consultation
            Optional<Consultation> consultationOpt = consultationService.findById(consultationId);
            if (consultationOpt.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/generaliste/consultation?error=notfound");
                return;
            }

            Consultation consultation = consultationOpt.get();

            // Verify access
            HttpSession session = request.getSession();
            Utilisateur user = (Utilisateur) session.getAttribute("user");

            if (!consultation.getMedecinGeneraliste().getId().equals(user.getId())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            // Update consultation
            consultation.setDiagnostic(diagnostic.trim());
            consultation.setTraitement(traitement.trim());
            consultation.setStatus(StatusConsultation.TERMINEE);

            consultationService.update(consultation);

            // Redirect to details page
            response.sendRedirect(request.getContextPath() +
                "/generaliste/consultation?action=view&id=" + consultationId + "&success=closed");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/generaliste/consultation?error=true");
        }
    }
}
