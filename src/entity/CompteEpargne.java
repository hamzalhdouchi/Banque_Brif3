package entity;

import dao.enums.TypeCompte;

public final class CompteEpargne extends Compte {
    private double tauxInteret;

    public CompteEpargne(String id, String numero, double solde, String idClient, double tauxInteret, TypeCompte type) {
        super(id, numero, solde, idClient, type);
        this.tauxInteret = tauxInteret;
    }

    public double getTauxInteret() { return tauxInteret; }
    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    @Override
    public TypeCompte getType() { return TypeCompte.EPARGNE; }
}