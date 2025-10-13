package com.example.jeemedexteleexpertise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "file_attente")
public class FileAttente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull(message = "La date d'arrivée ne peut pas être vide")
    @Column(name = "date_arrivee", nullable = false)
    private LocalDateTime dateArrivee;

    @Column(name = "priorite")
    private Integer priorite = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusFileAttente status;

    @PrePersist
    protected void onCreate() {
        if (dateArrivee == null) {
            dateArrivee = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusFileAttente.EN_ATTENTE;
        }
        if (priorite == null) {
            priorite = 0;
        }
    }

    // Constructors
    public FileAttente() {}

    public FileAttente(Patient patient) {
        this.patient = patient;
        this.dateArrivee = LocalDateTime.now();
        this.status = StatusFileAttente.EN_ATTENTE;
        this.priorite = 0;
    }

    public FileAttente(Patient patient, Integer priorite) {
        this.patient = patient;
        this.dateArrivee = LocalDateTime.now();
        this.status = StatusFileAttente.EN_ATTENTE;
        this.priorite = priorite;
    }

    // Business methods
    public void priseEnCharge() {
        this.status = StatusFileAttente.PRIS_EN_CHARGE;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public LocalDateTime getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(LocalDateTime dateArrivee) { this.dateArrivee = dateArrivee; }

    public Integer getPriorite() { return priorite; }
    public void setPriorite(Integer priorite) { this.priorite = priorite; }

    public StatusFileAttente getStatus() { return status; }
    public void setStatus(StatusFileAttente status) { this.status = status; }

    @Override
    public String toString() {
        return "FileAttente{" +
                "id=" + id +
                ", patient=" + (patient != null ? patient.getNomComplet() : null) +
                ", dateArrivee=" + dateArrivee +
                ", priorite=" + priorite +
                ", status=" + status +
                '}';
    }

    public void setPatientId(Long patientId) {
        if (this.patient == null) {
            this.patient = new Patient();
        }
        this.patient.setId(patientId);
    }
}
