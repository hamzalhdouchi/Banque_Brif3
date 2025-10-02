package service.InterfaceService;

import entity.Compte;
import entity.Transaction;
import enums.TypeTransaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TransactionServiceInterface {

    boolean enregistrerTransaction(Transaction transaction);

    List<Transaction> trouveAll();

    List<Transaction> trouverParCompte(String idCompte);

    List<Transaction> trouverParClient(String idClient);

    int CalculerCount(String idCompte, String idClient);

    double CalculerMoyane(String idCompte, String idClient);

    List<Transaction> filtreParMontant(double montant);

    List<Transaction> filtreParType(TypeTransaction type);

    List<Transaction> filtreParLeiu(String leiu);

    List<Transaction> filtreParDate(LocalDateTime date);

    Map<TypeTransaction, List<Transaction>> regrouperParType();

    List<Compte> compteClient(String idClient);
    boolean mettreAJourSolde(String idCompte, double nouveauSolde);

    List<Compte> troveToutCompte();
    List<Transaction> ParMois(int annee,int mois);
}
