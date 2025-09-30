package entity;

import dao.enums.TypeTransaction;

import java.time.LocalDateTime;

public record Transaction(String id, LocalDateTime date, double montant, TypeTransaction type, String lieu, String idCompte) {


    public Transaction {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }
        if (lieu == null || lieu.trim().isEmpty()) {
            lieu = "Non spécifié";
        }
    }

}