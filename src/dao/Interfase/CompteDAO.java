package dao.Interfase;

import entity.Compte;
import java.util.List;
import java.util.Optional;

public interface CompteDAO {
    boolean ajouter(Compte compte);
    void modifier(Compte compte);
    boolean supprimer(String id);
    public List<Compte>  trouverTous();
    public boolean mettreAJourSolde(String idCompte, double nouveauSolde);
}