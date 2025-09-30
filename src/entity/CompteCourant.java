package entity;

import dao.enums.TypeCompte;

public final class CompteCourant extends Compte {
    private double decouvertAutorise;

    public CompteCourant(String id, String numero, double solde, String idClient, double decouvertAutorise, TypeCompte type) {
        super(id, numero, solde, idClient,type);
        this.decouvertAutorise = decouvertAutorise;
    }

    public double getDecouvertAutorise() { return decouvertAutorise; }
    public void setDecouvertAutorise(double decouvertAutorise) {
        this.decouvertAutorise = decouvertAutorise;
    }

    @Override
    public TypeCompte getType() { return TypeCompte.COURANT; }
}