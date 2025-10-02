package service.InterfaceService;

import entity.Client;
import entity.Compte;
import entity.Transaction;

import java.util.List;
import java.util.Map;

public interface RapportServiceInterface {

    List<Client> genererTop5ClientsParSolde();

    Map<String, Object> genererRapportMensuel(int annee, int mois);

    List<Transaction> TransactionsSuspectesParMontant(double montant);

    List<Transaction> TransactionsSuspectesParLeiu(String lieu);

    List<Compte> InactifCompte(int moisInactivite);

    List<Transaction> operationsMoinsMinute(int maxTransactionsParMinute);
}
