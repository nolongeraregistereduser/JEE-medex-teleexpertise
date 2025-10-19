package com.example.jeemedexteleexpertise.controller.infirmier;

import com.example.jeemedexteleexpertise.model.DossierMedical;
import com.example.jeemedexteleexpertise.model.FileAttente;
import com.example.jeemedexteleexpertise.model.Patient;
import com.example.jeemedexteleexpertise.model.SignesVitaux;
import com.example.jeemedexteleexpertise.service.DossierMedicalService;
import com.example.jeemedexteleexpertise.service.FileAttenteService;
import com.example.jeemedexteleexpertise.service.PatientService;
import com.example.jeemedexteleexpertise.service.SignesVitauxService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet(name = "PatientRegistrationServlet", urlPatterns = {"/infirmier/patient-registration"})
public class PatientRegistrationServlet extends HttpServlet {

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
        request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String action = request.getParameter("action");

            if ("search".equals(action)) {
                handleSearch(request, response);
            } else if ("register".equals(action)) {
                handleRegistration(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur est survenue: " + e.getMessage());
            request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
        }
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String numSecu = request.getParameter("numeroSecuriteSociale");

        if (numSecu == null || numSecu.trim().isEmpty()) {
            request.setAttribute("error", "Veuillez saisir un numéro de sécurité sociale");
            request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
            return;
        }

        Optional<Patient> patientOpt = patientService.findByNumSecu(numSecu.trim());

        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();

            // Load dossier medical with signes vitaux
            Optional<DossierMedical> dossierOpt = dossierMedicalService.findByPatientIdWithSignesVitaux(patient.getId());

            request.setAttribute("patient", patient);
            request.setAttribute("dossierMedical", dossierOpt.orElse(null));
            request.setAttribute("patientFound", true);
        } else {
            request.setAttribute("patientNotFound", true);
            request.setAttribute("numeroSecuriteSociale", numSecu);
        }

        request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String patientIdStr = request.getParameter("patientId");
        Patient patient;
        DossierMedical dossierMedical;

        if (patientIdStr != null && !patientIdStr.trim().isEmpty()) {
            // Existing patient - update vital signs only
            Long patientId = Long.parseLong(patientIdStr);
            Optional<Patient> patientOpt = patientService.findById(patientId);

            if (patientOpt.isEmpty()) {
                request.setAttribute("error", "Patient non trouvé");
                request.getRequestDispatcher("/jsp/infirmier/patient-registration.jsp").forward(request, response);
                return;
            }

            patient = patientOpt.get();

            // Get existing dossier medical
            Optional<DossierMedical> dossierOpt = dossierMedicalService.findByPatientId(patientId);
            dossierMedical = dossierOpt.orElse(null);

        } else {
            // New patient - create patient and dossier medical
            patient = new Patient();
            patient.setNom(request.getParameter("nom"));
            patient.setPrenom(request.getParameter("prenom"));

            String dateNaissanceStr = request.getParameter("dateNaissance");
            if (dateNaissanceStr != null && !dateNaissanceStr.isEmpty()) {
                patient.setDateNaissance(LocalDate.parse(dateNaissanceStr));
            }

            patient.setNumSecu(request.getParameter("numeroSecuriteSociale"));
            patient.setTelephone(request.getParameter("telephone"));
            patient.setAdresse(request.getParameter("adresse"));
            patient.setMutuelle(request.getParameter("mutuelle"));

            // Create dossier medical
            dossierMedical = new DossierMedical();
            dossierMedical.setAntecedents(request.getParameter("antecedents"));
            dossierMedical.setAllergies(request.getParameter("allergies"));
            dossierMedical.setTraitementEnCours(request.getParameter("traitementEnCours"));

            // Save patient with dossier medical
            patient = patientService.createPatientWithDossierMedical(patient, dossierMedical);

            // Reload dossier medical to get the ID
            Optional<DossierMedical> dossierOpt = dossierMedicalService.findByPatientId(patient.getId());
            dossierMedical = dossierOpt.orElse(null);
        }

        // Add new vital signs
        if (dossierMedical != null) {
            SignesVitaux signesVitaux = new SignesVitaux(dossierMedical);

            String tension = request.getParameter("tension");
            String freqCardiaqueStr = request.getParameter("frequenceCardiaque");
            String temperatureStr = request.getParameter("temperature");
            String freqRespiratoireStr = request.getParameter("frequenceRespiratoire");
            String poidsStr = request.getParameter("poids");
            String tailleStr = request.getParameter("taille");

            if (tension != null && !tension.isEmpty()) {
                signesVitaux.setTension(tension);
            }
            if (freqCardiaqueStr != null && !freqCardiaqueStr.isEmpty()) {
                signesVitaux.setFrequenceCardiaque(Integer.parseInt(freqCardiaqueStr));
            }
            if (temperatureStr != null && !temperatureStr.isEmpty()) {
                signesVitaux.setTemperature(Double.parseDouble(temperatureStr));
            }
            if (freqRespiratoireStr != null && !freqRespiratoireStr.isEmpty()) {
                signesVitaux.setFrequenceRespiratoire(Integer.parseInt(freqRespiratoireStr));
            }
            if (poidsStr != null && !poidsStr.isEmpty()) {
                signesVitaux.setPoids(Double.parseDouble(poidsStr));
            }
            if (tailleStr != null && !tailleStr.isEmpty()) {
                signesVitaux.setTaille(Double.parseDouble(tailleStr));
            }

            signesVitauxService.save(signesVitaux);
        }

        // Add patient to queue
        FileAttente fileAttente = new FileAttente(patient);
        fileAttenteService.save(fileAttente);

        // Redirect to patient list with success message
        response.sendRedirect(request.getContextPath() + "/infirmier/patients?success=true");
    }
}

