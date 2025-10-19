package com.example.jeemedexteleexpertise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "signes_vitaux")
public class SignesVitaux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dossier_medical_id", nullable = false)
    private DossierMedical dossierMedical;

    @NotNull
    @Column(name = "date_saisie", nullable = false)
    private LocalDateTime dateSaisie;

    @Column(name = "tension", length = 20)
    private String tension;

    @Column(name = "frequence_cardiaque")
    private Integer frequenceCardiaque;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "frequence_respiratoire")
    private Integer frequenceRespiratoire;

    @Column(name = "poids")
    private Double poids;

    @Column(name = "taille")
    private Double taille;

    @PrePersist
    protected void onCreate() {
        if (dateSaisie == null) {
            dateSaisie = LocalDateTime.now();
        }
    }

    public SignesVitaux() {}

    public SignesVitaux(DossierMedical dossierMedical) {
        this.dossierMedical = dossierMedical;
        this.dateSaisie = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DossierMedical getDossierMedical() { return dossierMedical; }
    public void setDossierMedical(DossierMedical dossierMedical) { this.dossierMedical = dossierMedical; }

    public Patient getPatient() {
        return dossierMedical != null ? dossierMedical.getPatient() : null;
    }

    public LocalDateTime getDateSaisie() { return dateSaisie; }
    public void setDateSaisie(LocalDateTime dateSaisie) { this.dateSaisie = dateSaisie; }

    public LocalDateTime getDateMesure() { return dateSaisie; }
    public void setDateMesure(LocalDateTime dateMesure) { this.dateSaisie = dateMesure; }

    public String getTension() { return tension; }
    public void setTension(String tension) { this.tension = tension; }

    public String getTensionArterielle() { return tension; }
    public void setTensionArterielle(String tensionArterielle) { this.tension = tensionArterielle; }

    public Integer getFrequenceCardiaque() { return frequenceCardiaque; }
    public void setFrequenceCardiaque(Integer frequenceCardiaque) { this.frequenceCardiaque = frequenceCardiaque; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Integer getFrequenceRespiratoire() { return frequenceRespiratoire; }
    public void setFrequenceRespiratoire(Integer frequenceRespiratoire) { this.frequenceRespiratoire = frequenceRespiratoire; }

    public Double getPoids() { return poids; }
    public void setPoids(Double poids) { this.poids = poids; }

    public Double getTaille() { return taille; }
    public void setTaille(Double taille) { this.taille = taille; }
}
