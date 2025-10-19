package com.example.jeemedexteleexpertise.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        String password = "password";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        System.out.println("=== BCrypt Password Hash Generator ===");
        System.out.println("Plain password: " + password);
        System.out.println("BCrypt hash: " + hashedPassword);
        System.out.println("\nSQL Script:");
        System.out.println("-- Password for all test accounts: \"password\"");
        System.out.println("-- BCrypt hash: " + hashedPassword);
        System.out.println("\nINSERT INTO utilisateur (nom, prenom, email, mot_de_passe, role, actif, date_creation) VALUES");
        System.out.println("('Alami', 'Hassan', 'infirmier@medex.com', '" + hashedPassword + "', 'INFIRMIER', true, CURRENT_TIMESTAMP),");
        System.out.println("('Bennani', 'Fatima', 'generaliste@medex.com', '" + hashedPassword + "', 'GENERALISTE', true, CURRENT_TIMESTAMP),");
        System.out.println("('Tazi', 'Mohammed', 'specialiste@medex.com', '" + hashedPassword + "', 'SPECIALISTE', true, CURRENT_TIMESTAMP);");
    }
}

