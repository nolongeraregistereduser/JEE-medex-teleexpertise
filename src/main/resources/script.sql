-- CREATE DATABASE medex;
-- \c medex;

CREATE TABLE utilisateur (
                             id BIGSERIAL PRIMARY KEY,
                             nom VARCHAR(100) NOT NULL,
                             prenom VARCHAR(100) NOT NULL,
                             email VARCHAR(150) UNIQUE NOT NULL,
                             mot_de_passe VARCHAR(255) NOT NULL,
                             CREATE TYPE role AS ENUM ('INFIRMIER', 'GENERALISTE', 'SPECIALISTE', 'ADMIN');
                             specialite VARCHAR(20) CHECK (specialite IN ('CARDIOLOGIE', 'PNEUMOLOGIE')),
                             tarif DOUBLE PRECISION,
                             duree_consultation INTEGER default 30,
                             actif BOOLEAN DEFAULT TRUE,
                             date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE patient (
                         id BIGSERIAL PRIMARY KEY,
                         nom VARCHAR(100) NOT NULL,
                         prenom VARCHAR(100) NOT NULL,
                         date_naissance DATE NOT NULL,
                         adresse VARCHAR(255),
                         telephone VARCHAR(50),
                         mutuelle VARCHAR(100),
                         date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dossier_medical (
                                 id BIGSERIAL PRIMARY KEY,
                                 patient_id BIGINT UNIQUE NOT NULL,
                                 antecedents VARCHAR(255),
                                 allergies VARCHAR(255),
                                 traitement_en_cours VARCHAR(255),
                                 FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE TABLE consultation (
                              id BIGSERIAL PRIMARY KEY,
                              patient_id BIGINT NOT NULL,
                              medecin_generaliste_id BIGINT NOT NULL,
                              date_consultation TIMESTAMP NOT NULL,
                              motif VARCHAR(255),
                              observations VARCHAR(255),
                              status VARCHAR(50) NOT NULL CHECK (status IN ('EN_ATTENTE', 'TERMINEE', 'EN_ATTENTE_AVIS_SPECIALISTE')),
                              diagnostic VARCHAR(255),
                              traitement VARCHAR(255),
                              cout DOUBLE PRECISION,
                              FOREIGN KEY (patient_id) REFERENCES patient(id),
                              FOREIGN KEY (medecin_generaliste_id) REFERENCES utilisateur(id)
);

CREATE TABLE acte_technique (
                                id BIGSERIAL PRIMARY KEY,
                                type_acte VARCHAR(50) NOT NULL CHECK (type_acte IN ('RADIOGRAPHIE','ECHOGRAPHIE','IRM','ELECTROCARDIOGRAMME','LASER_DERMATO','FOND_OEIL','ANALYSE_SANG','ANALYSE_URINE')),
                                date TIMESTAMP NOT NULL,
                                resultat VARCHAR(255),
                                consultation_id BIGINT NOT NULL,
                                FOREIGN KEY (consultation_id) REFERENCES consultation(id)
);

CREATE TABLE signes_vitaux (
                               id BIGSERIAL PRIMARY KEY,
                               dossier_medical_id BIGINT NOT NULL,
                               date_saisie TIMESTAMP NOT NULL,
                               tension VARCHAR(20),
                               frequence_cardiaque INTEGER,
                               temperature DOUBLE PRECISION,
                               frequence_respiratoire INTEGER,
                               poids DOUBLE PRECISION,
                               taille DOUBLE PRECISION,
                               FOREIGN KEY (dossier_medical_id) REFERENCES dossier_medical(id)
);

CREATE TABLE creneau (
                         id BIGSERIAL PRIMARY KEY,
                         medecin_specialiste_id BIGINT NOT NULL,
                         date_heure_debut TIMESTAMP NOT NULL,
                         date_heure_fin TIMESTAMP NOT NULL,
                         status VARCHAR(20) NOT NULL CHECK (status IN ('DISPONIBLE', 'RESERVE', 'ARCHIVE')),
                         FOREIGN KEY (medecin_specialiste_id) REFERENCES utilisateur(id)
);

CREATE TABLE demande_expertise (
                                   id BIGSERIAL PRIMARY KEY,
                                   consultation_id BIGINT NOT NULL,
                                   medecin_specialiste_id BIGINT NOT NULL,
                                   creneau_id BIGINT,
                                   question VARCHAR(255),
                                   priorite VARCHAR(20) NOT NULL CHECK (priorite IN ('URGENTE', 'NORMALE', 'NON_URGENTE')),
                                   status VARCHAR(20) NOT NULL CHECK (status IN ('EN_ATTENTE', 'TERMINEE')),
                                   date_demande TIMESTAMP NOT NULL,
                                   date_reponse TIMESTAMP,
                                   avis_medecin VARCHAR(255),
                                   recommandations VARCHAR(255),
                                   FOREIGN KEY (consultation_id) REFERENCES consultation(id),
                                   FOREIGN KEY (medecin_specialiste_id) REFERENCES utilisateur(id),
                                   FOREIGN KEY (creneau_id) REFERENCES creneau(id)
);

CREATE TABLE file_attente (
                              id BIGSERIAL PRIMARY KEY,
                              patient_id BIGINT NOT NULL,
                              date_arrivee TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              priorite INTEGER DEFAULT 0,
                              status VARCHAR(20) DEFAULT 'EN_ATTENTE' CHECK (status IN ('EN_ATTENTE', 'PRIS_EN_CHARGE')),
                              FOREIGN KEY (patient_id) REFERENCES patient(id)
);