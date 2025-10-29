package com.example.jeemedexteleexpertise.controller.generaliste;

import com.example.jeemedexteleexpertise.model.*;
import com.example.jeemedexteleexpertise.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/generaliste/patient-waiting-details")
public class PatientWaitingDetailsServlet extends HttpServlet {

    private PatientService patientService;
    private DossierMedicalService dossierMedicalService;
    private SignesVitauxService signesVitauxService;
    private FileAttenteService fileAttenteService;

    @Override
    public void init() throws ServletException {
        this.patientService = new PatientService();
        this.dossierMedicalService = new DossierMedicalService();
        this.signesVitauxService = new SignesVitauxService();
        this.fileAttenteService = new FileAttenteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String patientIdStr = request.getParameter("patientId");
        String fileAttenteIdStr = request.getParameter("fileAttenteId");


        if (patientIdStr == null || patientIdStr.trim().isEmpty()) {
            System.out.println("ERROR: Missing patientId parameter");
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=missing");
            return;
        }

        try {
            Long patientId = Long.parseLong(patientIdStr);
            System.out.println("Parsed patientId: " + patientId);

            // Get patient
            System.out.println("Fetching patient...");
            Optional<Patient> patientOpt = patientService.findById(patientId);
            if (patientOpt.isEmpty()) {
                System.out.println("ERROR: Patient not found with ID: " + patientId);
                response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=notfound");
                return;
            }

            Patient patient = patientOpt.get();
            System.out.println("Patient found: " + patient.getNomComplet());

            // Get dossier medical with signes vitaux
            System.out.println("Fetching dossier medical...");
            Optional<DossierMedical> dossierOpt = dossierMedicalService.findByPatientIdWithSignesVitaux(patientId);
            DossierMedical dossierMedical = dossierOpt.orElse(null);
            System.out.println("Dossier medical found: " + (dossierMedical != null));

            // Get latest vital signs
            List<SignesVitaux> signesVitauxList = List.of();
            if (dossierMedical != null) {
                System.out.println("Fetching vital signs for dossier: " + dossierMedical.getId());
                try {
                    signesVitauxList = signesVitauxService.findLatestByDossierMedicalId(dossierMedical.getId(), 5);
                    System.out.println("Vital signs count: " + signesVitauxList.size());
                } catch (Exception e) {
                    System.out.println("WARNING: Error fetching vital signs: " + e.getMessage());
                    e.printStackTrace();
                    // Continue without vital signs
                }
            }

            // Get file attente info if provided
            FileAttente fileAttente = null;
            if (fileAttenteIdStr != null && !fileAttenteIdStr.trim().isEmpty()) {
                try {
                    Long fileAttenteId = Long.parseLong(fileAttenteIdStr);
                    Optional<FileAttente> fileOpt = fileAttenteService.findById(fileAttenteId);
                    fileAttente = fileOpt.orElse(null);
                    System.out.println("File attente found: " + (fileAttente != null));
                } catch (Exception e) {
                    System.out.println("WARNING: Error fetching file attente: " + e.getMessage());
                    // Continue without file attente info
                }
            }

            // Set attributes for JSP
            request.setAttribute("patient", patient);
            request.setAttribute("dossierMedical", dossierMedical);
            request.setAttribute("signesVitauxList", signesVitauxList);
            request.setAttribute("fileAttente", fileAttente);

            System.out.println("Forwarding to JSP...");
            // Forward to JSP
            request.getRequestDispatcher("/jsp/generaliste/patient-waiting-details.jsp").forward(request, response);
            System.out.println("=== END DEBUG ===");

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid number format");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=invalid");
        } catch (Exception e) {
            System.out.println("ERROR: Exception occurred");
            System.out.println("Exception type: " + e.getClass().getName());
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/generaliste/file-attente?error=true");
        }
    }
}
