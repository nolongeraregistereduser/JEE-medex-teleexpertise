package com.example.jeemedexteleexpertise.controller.generaliste;

import com.example.jeemedexteleexpertise.model.*;
import com.example.jeemedexteleexpertise.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/generaliste/request-specialist")
public class RequestSpecialistServlet extends HttpServlet {

    private PatientService patientService;
    private SpecialisteService specialisteService;
    private ConsultationService consultationService;
    private DemandeExpertiseService demandeExpertiseService;
    private FileAttenteService fileAttenteService;

    @Override
    public void init() throws ServletException {
        this.patientService = new PatientService();
        this.specialisteService = new SpecialisteService();
        this.consultationService = new ConsultationService();
        this.demandeExpertiseService = new DemandeExpertiseService();
        this.fileAttenteService = new FileAttenteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String patientIdStr = request.getParameter("patientId");
        String specialty = request.getParameter("specialty");

        if (patientIdStr == null || patientIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=missing");
            return;
        }

        try {
            Long patientId = Long.parseLong(patientIdStr);

            // Get patient
            Optional<Patient> patientOpt = patientService.findById(patientId);
            if (patientOpt.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=notfound");
                return;
            }

            Patient patient = patientOpt.get();
            request.setAttribute("patient", patient);

            // If no specialty selected yet, show specialty selection
            if (specialty == null || specialty.trim().isEmpty()) {
                request.setAttribute("specialties", Specialite.values());
                request.getRequestDispatcher("/jsp/generaliste/request-specialist-form.jsp").forward(request, response);
                return;
            }

            // Filter specialists by specialty using Stream API
            List<Specialiste> allSpecialists = specialisteService.findAll();
            List<Specialiste> filteredSpecialists = allSpecialists.stream()
                    .filter(s -> s.getSpecialite() != null && s.getSpecialite().equals(specialty))
                    .sorted((s1, s2) -> Double.compare(s1.getTarif(), s2.getTarif()))
                    .collect(Collectors.toList());

            request.setAttribute("specialists", filteredSpecialists);
            request.setAttribute("selectedSpecialty", specialty);

            // Forward to form with specialists list
            request.getRequestDispatcher("/jsp/generaliste/request-specialist-form.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=invalid");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=true");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get form data
            String patientIdStr = request.getParameter("patientId");
            String specialistIdStr = request.getParameter("specialistId");
            String question = request.getParameter("question");
            String prioriteStr = request.getParameter("priorite");
            String motif = request.getParameter("motif");

            // Validate
            if (patientIdStr == null || specialistIdStr == null) {
                response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=missing");
                return;
            }

            // Parse IDs
            Long patientId = Long.parseLong(patientIdStr);
            Long specialistId = Long.parseLong(specialistIdStr);

            // Get current generaliste from session
            HttpSession session = request.getSession();
            Utilisateur user = (Utilisateur) session.getAttribute("user");

            if (!(user instanceof Generaliste)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            Generaliste generaliste = (Generaliste) user;

            // Load entities
            Optional<Patient> patientOpt = patientService.findById(patientId);
            Optional<Specialiste> specialistOpt = specialisteService.findById(specialistId);

            if (patientOpt.isEmpty() || specialistOpt.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=notfound");
                return;
            }

            Patient patient = patientOpt.get();
            Specialiste specialist = specialistOpt.get();

            // Create consultation first
            Consultation consultation = new Consultation();
            consultation.setPatient(patient);
            consultation.setMedecinGeneraliste(generaliste);
            consultation.setMotif(motif != null && !motif.trim().isEmpty() ? motif : "Demande d'avis spécialiste");
            consultation.setObservations("Patient en attente - Demande d'expertise spécialisée");
            consultation.setStatus(StatusConsultation.EN_ATTENTE_AVIS_SPECIALISTE);
            consultation.setCout(150.0);

            consultation = consultationService.save(consultation);

            // Create expertise request (without creneau for now)
            PrioriteExpertise priorite = PrioriteExpertise.NORMALE;
            if (prioriteStr != null && !prioriteStr.trim().isEmpty()) {
                try {
                    priorite = PrioriteExpertise.valueOf(prioriteStr);
                } catch (IllegalArgumentException e) {
                    priorite = PrioriteExpertise.NORMALE;
                }
            }

            DemandeExpertise demandeExpertise = new DemandeExpertise();
            demandeExpertise.setConsultation(consultation);
            demandeExpertise.setMedecinSpecialiste(specialist);
            demandeExpertise.setQuestion(question != null && !question.trim().isEmpty() ? question : "Demande d'avis");
            demandeExpertise.setPriorite(priorite);
            demandeExpertise.setStatus(StatusExpertise.EN_ATTENTE);
            // creneau is null for now (static implementation)

            demandeExpertiseService.save(demandeExpertise);

            // Update file attente status to PRIS_EN_CHARGE
            List<FileAttente> patientQueue = fileAttenteService.findByPatientId(patientId);
            patientQueue.stream()
                    .filter(f -> f.getStatus() == StatusFileAttente.EN_ATTENTE)
                    .forEach(f -> fileAttenteService.markAsPrisEnCharge(f.getId()));

            // Success - redirect to file attente
            response.sendRedirect(request.getContextPath() +
                "/generaliste/file-attente?success=specialist_requested");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=true");
        }
    }
}

