package com.example.jeemedexteleexpertise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "demande_expertise")
public class DemandeExpertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @ManyToOne
    @JoinColumn(name = "medecin_specialiste_id", nullable = false)
    private Specialiste medecinSpecialiste;

    @ManyToOne
    @JoinColumn(name = "creneau_id")
    private Creneau creneau;

    @Size(max = 255, message = "La question ne peut pas dépasser 255 caractères")
    @Column(name = "question", length = 255)
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "priorite", nullable = false, length = 20)
    private PrioriteExpertise priorite;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusExpertise status;

    @NotNull(message = "La date de demande ne peut pas être vide")
    @Column(name = "date_demande", nullable = false)
    private LocalDateTime dateDemande;

    @Column(name = "date_reponse")
    private LocalDateTime dateReponse;

    @Size(max = 255, message = "L'avis médical ne peut pas dépasser 255 caractères")
    @Column(name = "avis_medecin", length = 255)
    private String avisMedecin;

    @Size(max = 255, message = "Les recommandations ne peuvent pas dépasser 255 caractères")
    @Column(name = "recommandations", length = 255)
    private String recommandations;

    @PrePersist
    protected void onCreate() {
        if (dateDemande == null) {
            dateDemande = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusExpertise.EN_ATTENTE;
        }
    }

    // Constructors
    public DemandeExpertise() {}

    public DemandeExpertise(Consultation consultation, Specialiste medecinSpecialiste, String question, PrioriteExpertise priorite) {
        this.consultation = consultation;
        this.medecinSpecialiste = medecinSpecialiste;
        this.question = question;
        this.priorite = priorite;
        this.status = StatusExpertise.EN_ATTENTE;
        this.dateDemande = LocalDateTime.now();
    }

    // Business methods
    public void terminerExpertise(String avisMedecin, String recommandations) {
        this.avisMedecin = avisMedecin;
        this.recommandations = recommandations;
        this.status = StatusExpertise.TERMINEE;
        this.dateReponse = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Consultation getConsultation() { return consultation; }
    public void setConsultation(Consultation consultation) { this.consultation = consultation; }

    public Specialiste getMedecinSpecialiste() { return medecinSpecialiste; }
    public void setMedecinSpecialiste(Specialiste medecinSpecialiste) { this.medecinSpecialiste = medecinSpecialiste; }

    public Creneau getCreneau() { return creneau; }
    public void setCreneau(Creneau creneau) { this.creneau = creneau; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public PrioriteExpertise getPriorite() { return priorite; }
    public void setPriorite(PrioriteExpertise priorite) { this.priorite = priorite; }

    public StatusExpertise getStatus() { return status; }
    public void setStatus(StatusExpertise status) { this.status = status; }

    public LocalDateTime getDateDemande() { return dateDemande; }
    public void setDateDemande(LocalDateTime dateDemande) { this.dateDemande = dateDemande; }

    public LocalDateTime getDateReponse() { return dateReponse; }
    public void setDateReponse(LocalDateTime dateReponse) { this.dateReponse = dateReponse; }

    public String getAvisMedecin() { return avisMedecin; }
    public void setAvisMedecin(String avisMedecin) { this.avisMedecin = avisMedecin; }

    public String getRecommandations() { return recommandations; }
    public void setRecommandations(String recommandations) { this.recommandations = recommandations; }

    @Override
    public String toString() {
        return "DemandeExpertise{" +
                "id=" + id +
                ", consultation=" + (consultation != null ? consultation.getId() : null) +
                ", medecinSpecialiste=" + (medecinSpecialiste != null ? medecinSpecialiste.getNomComplet() : null) +
                ", question='" + question + '\'' +
                ", priorite=" + priorite +
                ", status=" + status +
                ", dateDemande=" + dateDemande +
                ", dateReponse=" + dateReponse +
                '}';
    }
}
