package service.InterfaceService;

import entity.Compte;

import java.util.List;
import java.util.Optional;

public interface CompteServiceInterface {

    boolean ajouterCompte(Compte compte);

    void modifierCompte(Compte compte);

    boolean supprimerCompte(String numero);

    List<Compte> trouverTo();

    Optional<Compte> trouverParId(String id);

    List<Compte> trouverParClient(String idClient);

    Optional<Compte> trouverParNumero(String numero);

    boolean mettreAJourSolde(String idCompte, double nouveauSolde);
}
