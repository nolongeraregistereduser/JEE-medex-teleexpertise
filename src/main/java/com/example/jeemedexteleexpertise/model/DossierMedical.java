package com.example.jeemedexteleexpertise.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "dossier_medical")
public class DossierMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
    private Patient patient;

    @Column(name = "antecedents", length = 255)
    private String antecedents;

    @Column(name = "allergies", length = 255)
    private String allergies;

    @Column(name = "traitement_en_cours", length = 255)
    private String traitementEnCours;

    // Relationship with SignesVitaux
    @OneToMany(mappedBy = "dossierMedical", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SignesVitaux> signesVitaux;

    // constructorrrrrrrrrrr
    public DossierMedical() {}

    public DossierMedical(Patient patient) {
        this.patient = patient;
    }

    public DossierMedical(Patient patient, String antecedents, String allergies, String traitementEnCours) {
        this.patient = patient;
        this.antecedents = antecedents;
        this.allergies = allergies;
        this.traitementEnCours = traitementEnCours;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public String getAntecedents() { return antecedents; }
    public void setAntecedents(String antecedents) { this.antecedents = antecedents; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }

    public String getTraitementEnCours() { return traitementEnCours; }
    public void setTraitementEnCours(String traitementEnCours) { this.traitementEnCours = traitementEnCours; }

    public List<SignesVitaux> getSignesVitaux() { return signesVitaux; }
    public void setSignesVitaux(List<SignesVitaux> signesVitaux) { this.signesVitaux = signesVitaux; }

    @Override
    public String toString() {
        return "DossierMedical{" +
                "id=" + id +
                ", patient=" + (patient != null ? patient.getNomComplet() : null) +
                ", antecedents='" + antecedents + '\'' +
                ", allergies='" + allergies + '\'' +
                ", traitementEnCours='" + traitementEnCours + '\'' +
                '}';
    }
}
