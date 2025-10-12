package com.example.jeemedexteleexpertise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "creneau")
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medecin_specialiste_id", nullable = false)
    private Specialiste medecinSpecialiste;

    @NotNull(message = "La date et heure de début ne peut pas être vide")
    @Column(name = "date_heure_debut", nullable = false)
    private LocalDateTime dateHeureDebut;

    @NotNull(message = "La date et heure de fin ne peut pas être vide")
    @Column(name = "date_heure_fin", nullable = false)
    private LocalDateTime dateHeureFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusCreneau status;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = StatusCreneau.DISPONIBLE;
        }
    }

    // Constructors
    public Creneau() {}

    public Creneau(Specialiste medecinSpecialiste, LocalDateTime dateHeureDebut, LocalDateTime dateHeureFin) {
        this.medecinSpecialiste = medecinSpecialiste;
        this.dateHeureDebut = dateHeureDebut;
        this.dateHeureFin = dateHeureFin;
        this.status = StatusCreneau.DISPONIBLE;
    }

    // Business methods
    public boolean isDisponible() {
        return status == StatusCreneau.DISPONIBLE && dateHeureDebut.isAfter(LocalDateTime.now());
    }

    public void reserver() {
        this.status = StatusCreneau.RESERVE;
    }

    public void liberer() {
        if (dateHeureDebut.isAfter(LocalDateTime.now())) {
            this.status = StatusCreneau.DISPONIBLE;
        }
    }

    public void archiver() {
        this.status = StatusCreneau.ARCHIVE;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Specialiste getMedecinSpecialiste() { return medecinSpecialiste; }
    public void setMedecinSpecialiste(Specialiste medecinSpecialiste) { this.medecinSpecialiste = medecinSpecialiste; }

    public LocalDateTime getDateHeureDebut() { return dateHeureDebut; }
    public void setDateHeureDebut(LocalDateTime dateHeureDebut) { this.dateHeureDebut = dateHeureDebut; }

    public LocalDateTime getDateHeureFin() { return dateHeureFin; }
    public void setDateHeureFin(LocalDateTime dateHeureFin) { this.dateHeureFin = dateHeureFin; }

    public StatusCreneau getStatus() { return status; }
    public void setStatus(StatusCreneau status) { this.status = status; }

    @Override
    public String toString() {
        return "Creneau{" +
                "id=" + id +
                ", medecinSpecialiste=" + (medecinSpecialiste != null ? medecinSpecialiste.getNomComplet() : null) +
                ", dateHeureDebut=" + dateHeureDebut +
                ", dateHeureFin=" + dateHeureFin +
                ", status=" + status +
                '}';
    }
}
