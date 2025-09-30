package entity;

public record Client(String id, String nom, String email) {
    public Client {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalide");
        }
    }
}