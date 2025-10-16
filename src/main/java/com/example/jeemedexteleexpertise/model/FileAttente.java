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

    @NotNull
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

    public FileAttente() {}

    public FileAttente(Patient patient) {
        this.patient = patient;
        this.dateArrivee = LocalDateTime.now();
        this.status = StatusFileAttente.EN_ATTENTE;
        this.priorite = 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public LocalDateTime getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(LocalDateTime dateArrivee) { this.dateArrivee = dateArrivee; }

    public LocalDateTime getHeureArrivee() { return dateArrivee; }
    public void setHeureArrivee(LocalDateTime heureArrivee) { this.dateArrivee = heureArrivee; }

    public Integer getPriorite() { return priorite; }
    public void setPriorite(Integer priorite) { this.priorite = priorite; }

    public StatusFileAttente getStatus() { return status; }
    public void setStatus(StatusFileAttente status) { this.status = status; }
}
