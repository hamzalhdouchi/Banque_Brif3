package entity;

import dao.enums.TypeCompte;

public sealed abstract class Compte permits CompteCourant, CompteEpargne {
    private final String id;
    private final String numero;
    private double solde;
    private final String idClient;
    private final TypeCompte type;

    public Compte(String id, String numero, double solde, String idClient, TypeCompte type) {
        this.id = id;
        this.numero = numero;
        this.solde = solde;
        this.idClient = idClient;
        this.type = type;
    }

    // Getters
    public String getId() { return id; }
    public String getNumero() { return numero; }
    public double getSolde() { return solde; }
    public String getIdClient() { return idClient; }

    // Setters
    public void setSolde(double solde) { this.solde = solde; }

    public  TypeCompte getType(){ return type; };
}