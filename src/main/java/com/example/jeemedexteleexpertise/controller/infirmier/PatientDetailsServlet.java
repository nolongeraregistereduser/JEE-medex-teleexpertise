package com.example.jeemedexteleexpertise.controller.infirmier;

import com.example.jeemedexteleexpertise.model.DossierMedical;
import com.example.jeemedexteleexpertise.model.Patient;
import com.example.jeemedexteleexpertise.model.SignesVitaux;
import com.example.jeemedexteleexpertise.service.DossierMedicalService;
import com.example.jeemedexteleexpertise.service.PatientService;
import com.example.jeemedexteleexpertise.service.SignesVitauxService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "PatientDetailsServlet", urlPatterns = {"/infirmier/patient-details"})
public class PatientDetailsServlet extends HttpServlet {

    private PatientService patientService;
    private DossierMedicalService dossierMedicalService;
    private SignesVitauxService signesVitauxService;

    @Override
    public void init() throws ServletException {
        this.patientService = new PatientService();
        this.dossierMedicalService = new DossierMedicalService();
        this.signesVitauxService = new SignesVitauxService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String patientIdStr = request.getParameter("id");

        if (patientIdStr == null || patientIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/infirmier/patients");
            return;
        }

        try {
            Long patientId = Long.parseLong(patientIdStr);

            Optional<Patient> patientOpt = patientService.findById(patientId);

            if (patientOpt.isEmpty()) {
                request.setAttribute("error", "Patient non trouvé");
                request.getRequestDispatcher("/infirmier/patients").forward(request, response);
                return;
            }

            Patient patient = patientOpt.get();

            // Get dossier medical
            Optional<DossierMedical> dossierOpt = dossierMedicalService.findByPatientId(patientId);

            // Get all vital signs for this patient (using Stream API to sort)
            List<SignesVitaux> signesVitauxList = List.of();
            if (dossierOpt.isPresent()) {
                signesVitauxList = signesVitauxService.findByDossierMedicalId(dossierOpt.get().getId())
                    .stream()
                    .sorted((s1, s2) -> s2.getDateSaisie().compareTo(s1.getDateSaisie())) // Latest first
                    .toList();
            }

            request.setAttribute("patient", patient);
            request.setAttribute("dossierMedical", dossierOpt.orElse(null));
            request.setAttribute("signesVitauxList", signesVitauxList);

            request.getRequestDispatcher("/jsp/infirmier/patient-details.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/infirmier/patients");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur est survenue: " + e.getMessage());
            request.getRequestDispatcher("/infirmier/patients").forward(request, response);
        }
    }
}

