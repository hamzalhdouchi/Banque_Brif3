package service;

import dao.Interfase.CompteDAO;
import entity.Compte;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompteService {

    private CompteDAO compteDAO;

    public CompteService(CompteDAO compteDAO) {
        this.compteDAO = compteDAO;
    }

    public boolean ajouterCompte(Compte compte) {
        return compteDAO.ajouter(compte);
    }

    public void modifierCompte(Compte compte) {
        compteDAO.modifier(compte);
    }


    public boolean supprimerCompte(String numero) {
        return compteDAO.supprimer(numero);
    }



    public List<Compte> trouverTo() {
        return compteDAO.trouverTous();
    }


    public Optional<Compte> trouverParId(String id) {


        try {
            List<Compte> comptes = trouverTo();

            Compte compte = comptes.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);

            return Optional.of(compte);
        }catch (NullPointerException e){
            return Optional.empty();
        }

    }


    public List<Compte> trouverParClient(String idClient) {

        List<Compte> comptes = new ArrayList<>();
        List<Compte> compteClients = trouverTo();
        comptes = compteClients.stream().filter(c->c.getIdClient().equals(idClient)).collect(Collectors.toList());

        return comptes;
    }

}
