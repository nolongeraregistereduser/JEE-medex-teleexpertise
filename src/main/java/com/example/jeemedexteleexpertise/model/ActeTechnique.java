package com.example.jeemedexteleexpertise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "acte_technique")
public class ActeTechnique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_acte", nullable = false, length = 50)
    private TypeActe typeActe;

    @NotNull(message = "La date ne peut pas être vide")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Size(max = 255, message = "Le résultat ne peut pas dépasser 255 caractères")
    @Column(name = "resultat", length = 255)
    private String resultat;

    @ManyToOne
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }

    // Constructors
    public ActeTechnique() {}

    public ActeTechnique(TypeActe typeActe, Consultation consultation) {
        this.typeActe = typeActe;
        this.consultation = consultation;
        this.date = LocalDateTime.now();
    }

    public ActeTechnique(TypeActe typeActe, String resultat, Consultation consultation) {
        this.typeActe = typeActe;
        this.resultat = resultat;
        this.consultation = consultation;
        this.date = LocalDateTime.now();
    }

    // Business methods
    public double getCoutActe() {
        return typeActe != null ? typeActe.getCout() : 0.0;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TypeActe getTypeActe() { return typeActe; }
    public void setTypeActe(TypeActe typeActe) { this.typeActe = typeActe; }

    // Add getType method (alias for getTypeActe)
    public TypeActe getType() { return typeActe; }
    public void setType(TypeActe type) { this.typeActe = type; }

    // Add getCout method (delegating to TypeActe)
    public double getCout() { return getCoutActe(); }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getResultat() { return resultat; }
    public void setResultat(String resultat) { this.resultat = resultat; }

    public Consultation getConsultation() { return consultation; }
    public void setConsultation(Consultation consultation) { this.consultation = consultation; }

    @Override
    public String toString() {
        return "ActeTechnique{" +
                "id=" + id +
                ", typeActe=" + typeActe +
                ", date=" + date +
                ", resultat='" + resultat + '\'' +
                ", consultation=" + (consultation != null ? consultation.getId() : null) +
                '}';
    }
}
