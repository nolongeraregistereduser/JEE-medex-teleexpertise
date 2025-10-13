package com.example.jeemedexteleexpertise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_generaliste_id", nullable = false)
    private Generaliste medecinGeneraliste;


    @NotNull(message = "La date de consultation ne peut pas être vide")
    @Column(name = "date_consultation", nullable = false)
    private LocalDateTime dateConsultation;

    @Size(max = 255, message = "Le motif ne peut pas dépasser 255 caractères")
    @Column(name = "motif", length = 255)
    private String motif;

    @Size(max = 255, message = "Les observations ne peuvent pas dépasser 255 caractères")
    @Column(name = "observations", length = 255)
    private String observations;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private StatusConsultation status;

    @Size(max = 255, message = "Le diagnostic ne peut pas dépasser 255 caractères")
    @Column(name = "diagnostic", length = 255)
    private String diagnostic;

    @Size(max = 255, message = "Le traitement ne peut pas dépasser 255 caractères")
    @Column(name = "traitement", length = 255)
    private String traitement;

    @Column(name = "cout")
    private Double cout = 150.0; // Default cost as per requirements

    // Relationships
    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActeTechnique> actesTechniques;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DemandeExpertise> demandesExpertise;

    @PrePersist
    protected void onCreate() {
        if (dateConsultation == null) {
            dateConsultation = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusConsultation.EN_ATTENTE;
        }
        if (cout == null) {
            cout = 150.0;
        }
    }

    // Constructors
    public Consultation() {}

    public Consultation(Patient patient, Generaliste medecinGeneraliste, String motif) {
        this.patient = patient;
        this.medecinGeneraliste = medecinGeneraliste;
        this.motif = motif;
        this.status = StatusConsultation.EN_ATTENTE;
        this.cout = 150.0;
    }

    // Business methods
    public double calculerCoutTotal() {
        double coutTotal = this.cout != null ? this.cout : 150.0;

        if (actesTechniques != null) {
            // Add cost of technical acts if needed
        }

        if (demandesExpertise != null) {
            coutTotal += demandesExpertise.stream()
                .mapToDouble(demande -> demande.getMedecinSpecialiste().getTarif())
                .sum();
        }

        return coutTotal;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Generaliste getMedecinGeneraliste() { return medecinGeneraliste; }
    public void setMedecinGeneraliste(Generaliste medecinGeneraliste) { this.medecinGeneraliste = medecinGeneraliste; }

    public LocalDateTime getDateConsultation() { return dateConsultation; }
    public void setDateConsultation(LocalDateTime dateConsultation) { this.dateConsultation = dateConsultation; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    public StatusConsultation getStatus() { return status; }
    public void setStatus(StatusConsultation status) { this.status = status; }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public String getTraitement() { return traitement; }
    public void setTraitement(String traitement) { this.traitement = traitement; }

    public Double getCout() { return cout; }
    public void setCout(Double cout) { this.cout = cout; }

    public List<ActeTechnique> getActesTechniques() { return actesTechniques; }
    public void setActesTechniques(List<ActeTechnique> actesTechniques) { this.actesTechniques = actesTechniques; }

    public List<DemandeExpertise> getDemandesExpertise() { return demandesExpertise; }
    public void setDemandesExpertise(List<DemandeExpertise> demandesExpertise) { this.demandesExpertise = demandesExpertise; }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", patient=" + (patient != null ? patient.getNomComplet() : null) +
                ", medecinGeneraliste=" + (medecinGeneraliste != null ? medecinGeneraliste.getNomComplet() : null) +
                ", dateConsultation=" + dateConsultation +
                ", motif='" + motif + '\'' +
                ", status=" + status +
                ", cout=" + cout +
                '}';
    }
}
