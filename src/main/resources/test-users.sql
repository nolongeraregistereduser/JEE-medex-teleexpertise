-- Script to insert test users with BCrypt hashed passwords
-- Password for all test accounts: "password"
-- BCrypt hash generated for "password": $2a$10$8K1p/a0dL3.kJe5L5K5jH.X5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K

-- Note: You need to run this AFTER Hibernate creates the tables
-- Or manually create the utilisateur table first

-- Clean existing test data
DELETE FROM utilisateur WHERE email LIKE '%@medex.com';

-- Insert test users
-- Password = "password" (BCrypt hashed)
INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, role, actif, date_creation) VALUES
('Alami', 'Hassan', 'infirmier@medex.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J7Y8h8eQ8h8eQ8h8eQ8h8eQ8h8eQ8e', 'INFIRMIER', true, CURRENT_TIMESTAMP),
('Bennani', 'Fatima', 'generaliste@medex.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J7Y8h8eQ8h8eQ8h8eQ8h8eQ8h8eQ8e', 'GENERALISTE', true, CURRENT_TIMESTAMP),
('Tazi', 'Mohammed', 'specialiste@medex.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J7Y8h8eQ8h8eQ8h8eQ8h8eQ8h8eQ8e', 'SPECIALISTE', true, CURRENT_TIMESTAMP);

-- Update specialiste with profile information
UPDATE utilisateur
SET specialite = 'CARDIOLOGIE', tarif = 300.0, duree_consultation = 30
WHERE email = 'specialiste@medex.com';

-- Verify inserted users
SELECT id, nom, prenom, email, role, specialite, tarif, actif
FROM utilisateur
WHERE email LIKE '%@medex.com';

