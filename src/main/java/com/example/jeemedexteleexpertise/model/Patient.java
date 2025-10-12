package com.example.jeemedexteleexpertise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @NotBlank(message = "Le prénom ne peut pas être vide")
    @Size(max = 100, message = "Le prénom ne peut pas dépasser 100 caractères")
    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @NotNull(message = "La date de naissance ne peut pas être vide")
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @NotBlank(message = "Le numéro de sécurité sociale ne peut pas être vide")
    @Size(max = 50, message = "Le numéro de sécurité sociale ne peut pas dépasser 50 caractères")
    @Column(name = "num_secu", nullable = false, unique = true, length = 50)
    private String numSecu;

    @Size(max = 255, message = "L'adresse ne peut pas dépasser 255 caractères")
    @Column(name = "adresse", length = 255)
    private String adresse;

    @Size(max = 50, message = "Le téléphone ne peut pas dépasser 50 caractères")
    @Column(name = "telephone", length = 50)
    private String telephone;

    @Size(max = 100, message = "La mutuelle ne peut pas dépasser 100 caractères")
    @Column(name = "mutuelle", length = 100)
    private String mutuelle;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    // Relationships
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DossierMedical dossierMedical;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FileAttente> fileAttentes;

    @PrePersist
    protected void onCreate() {
        if (dateCreation == null) {
            dateCreation = LocalDateTime.now();
        }
    }

    // Constructors
    public Patient() {}

    public Patient(String nom, String prenom, LocalDate dateNaissance, String numSecu) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numSecu = numSecu;
    }

    public Patient(String nom, String prenom, LocalDate dateNaissance, String numSecu, String adresse, String telephone, String mutuelle) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numSecu = numSecu;
        this.adresse = adresse;
        this.telephone = telephone;
        this.mutuelle = mutuelle;
    }

    // Business methods
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getNumSecu() { return numSecu; }
    public void setNumSecu(String numSecu) { this.numSecu = numSecu; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getMutuelle() { return mutuelle; }
    public void setMutuelle(String mutuelle) { this.mutuelle = mutuelle; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public DossierMedical getDossierMedical() { return dossierMedical; }
    public void setDossierMedical(DossierMedical dossierMedical) { this.dossierMedical = dossierMedical; }

    public List<Consultation> getConsultations() { return consultations; }
    public void setConsultations(List<Consultation> consultations) { this.consultations = consultations; }

    public List<FileAttente> getFileAttentes() { return fileAttentes; }
    public void setFileAttentes(List<FileAttente> fileAttentes) { this.fileAttentes = fileAttentes; }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", numSecu='" + numSecu + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
