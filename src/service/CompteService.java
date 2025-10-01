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
}
