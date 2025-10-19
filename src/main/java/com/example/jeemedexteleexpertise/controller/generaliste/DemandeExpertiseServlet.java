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

@WebServlet("/generaliste/demande-expertise")
public class DemandeExpertiseServlet extends HttpServlet {

    private ConsultationService consultationService;
    private SpecialisteService specialisteService;
    private CreneauService creneauService;
    private DemandeExpertiseService demandeExpertiseService;

    @Override
    public void init() throws ServletException {
        this.consultationService = new ConsultationService();
        this.specialisteService = new SpecialisteService();
        this.creneauService = new CreneauService();
        this.demandeExpertiseService = new DemandeExpertiseService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get consultation ID from URL
        String consultationIdStr = request.getParameter("consultationId");
        if (consultationIdStr == null || consultationIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/generaliste/consultation");
            return;
        }

        try {
            Long consultationId = Long.parseLong(consultationIdStr);

            // Load consultation
            Optional<Consultation> consultationOpt = consultationService.findById(consultationId);
            if (consultationOpt.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/generaliste/consultation?error=notfound");
                return;
            }

            Consultation consultation = consultationOpt.get();
            request.setAttribute("consultation", consultation);

            // Check if specialty is selected
            String specialtyParam = request.getParameter("specialty");

            // STEP 1: Show specialty selection page
            if (specialtyParam == null || specialtyParam.trim().isEmpty()) {
                request.setAttribute("specialties", Specialite.values());
                request.getRequestDispatcher("/jsp/generaliste/select-specialty.jsp").forward(request, response);
                return;
            }

            // STEP 2: Filter specialists by specialty using STREAM API
            Specialite specialty = Specialite.valueOf(specialtyParam);

            // Get all specialists
            List<Specialiste> allSpecialists = specialisteService.findAll();

            // ⭐ STREAM API #1: Filter by specialty
            List<Specialiste> filteredSpecialists = allSpecialists.stream()
                .filter(s -> s.getSpecialite() != null && s.getSpecialite().equals(specialty.name()))
                .collect(Collectors.toList());

            // ⭐ STREAM API #2: Sort by tarif (cheapest first)
            List<Specialiste> sortedSpecialists = filteredSpecialists.stream()
                .sorted((s1, s2) -> Double.compare(s1.getTarif(), s2.getTarif()))
                .collect(Collectors.toList());

            request.setAttribute("specialists", sortedSpecialists);
            request.setAttribute("specialty", specialty);

            // STEP 3: Load time slots if specialist selected
            String specialistIdParam = request.getParameter("specialistId");
            if (specialistIdParam != null && !specialistIdParam.trim().isEmpty()) {
                Long specialistId = Long.parseLong(specialistIdParam);

                Optional<Specialiste> specialistOpt = specialisteService.findById(specialistId);
                if (specialistOpt.isPresent()) {
                    Specialiste selectedSpecialist = specialistOpt.get();
                    List<Creneau> availableSlots = creneauService.findAvailableBySpecialisteId(specialistId);

                    request.setAttribute("selectedSpecialist", selectedSpecialist);
                    request.setAttribute("availableSlots", availableSlots);
                }
            }

            // Forward to expertise request form
            request.getRequestDispatcher("/jsp/generaliste/demande-expertise-form.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/generaliste/consultation?error=true");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get form data
            String consultationIdStr = request.getParameter("consultationId");
            String specialistIdStr = request.getParameter("specialistId");
            String creneauIdStr = request.getParameter("creneauId");
            String question = request.getParameter("question");
            String prioriteStr = request.getParameter("priorite");

            // Validate
            if (consultationIdStr == null || specialistIdStr == null ||
                creneauIdStr == null || question == null || prioriteStr == null) {
                response.sendRedirect(request.getContextPath() +
                    "/generaliste/demande-expertise?consultationId=" + consultationIdStr + "&error=missing");
                return;
            }

            // Parse IDs
            Long consultationId = Long.parseLong(consultationIdStr);
            Long specialistId = Long.parseLong(specialistIdStr);
            Long creneauId = Long.parseLong(creneauIdStr);
            PrioriteExpertise priorite = PrioriteExpertise.valueOf(prioriteStr);

            // Load entities
            Optional<Consultation> consultationOpt = consultationService.findById(consultationId);
            Optional<Specialiste> specialistOpt = specialisteService.findById(specialistId);
            Optional<Creneau> creneauOpt = creneauService.findById(creneauId);

            if (consultationOpt.isEmpty() || specialistOpt.isEmpty() || creneauOpt.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/generaliste/consultation?error=notfound");
                return;
            }

            Consultation consultation = consultationOpt.get();
            Specialiste specialist = specialistOpt.get();
            Creneau creneau = creneauOpt.get();

            // Create expertise request
            DemandeExpertise demandeExpertise = new DemandeExpertise();
            demandeExpertise.setConsultation(consultation);
            demandeExpertise.setMedecinSpecialiste(specialist);
            demandeExpertise.setCreneau(creneau);
            demandeExpertise.setQuestion(question.trim());
            demandeExpertise.setPriorite(priorite);
            demandeExpertise.setStatus(StatusExpertise.EN_ATTENTE);

            // Save
            demandeExpertiseService.save(demandeExpertise);

            // Update consultation status
            consultation.setStatus(StatusConsultation.EN_ATTENTE_AVIS_SPECIALISTE);
            consultationService.update(consultation);

            // Reserve time slot
            creneauService.reserveCreneau(creneauId);

            // Success
            response.sendRedirect(request.getContextPath() +
                "/generaliste/consultation?success=expertise");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/generaliste/consultation?error=true");
        }
    }
}

