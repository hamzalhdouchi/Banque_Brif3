package service;

import dao.Interfase.CompteDAO;
import dao.Interfase.TransactionDAO;
import enums.TypeTransaction;
import entity.Compte;
import entity.Transaction;
import service.InterfaceService.TransactionServiceInterface;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionService implements TransactionServiceInterface {

    private TransactionDAO transactionDAO;
    private CompteDAO compteDAO;

    public TransactionService(TransactionDAO transactionDAO, CompteDAO compteDAO) {
        this.transactionDAO = transactionDAO;
        this.compteDAO = compteDAO;
    }

    public boolean enregistrerTransaction(Transaction transaction) {

        boolean ajouter = transactionDAO.ajouter(transaction);
        if (ajouter) {
            return true;
        }else {
            return false;
        }
    }


    public List<Transaction> trouveAll() {

        List<Transaction> transactions = transactionDAO.trouverToutes();
        if(transactions.isEmpty()) {
            return List.of();
        }
        return transactions;

    }

    public List<Transaction> trouverParClient(String idClient) {

        List<Transaction> transactions = trouveAll();
        List<Transaction> transactionsClient = new ArrayList<>();
        List<Compte> comptes = compteDAO.trouverTous();

        List<Compte> comptesClient = comptes.stream()
                .filter(c -> c.getIdClient().equals(idClient))
                .collect(Collectors.toList());

        for (Compte compte : comptesClient) {
            List<Transaction> transPourCompte = transactions.stream()
                    .filter(t -> t.idCompte().equals(compte.getId()))
                    .collect(Collectors.toList());

            transactionsClient.addAll(transPourCompte);
        }

        return transactionsClient;
    }

    public int CalculerCount(String idCompte, String idClient) {

        if(idClient.isEmpty()){
            List<Transaction> transactions = trouveAll();
            long transactionsClient ;
            transactionsClient = transactions.stream().filter(c -> c.idCompte().equals(idCompte)).count();

            return (int) transactionsClient;
        } else if (idCompte.isEmpty()) {
            List<Transaction> transactions = trouverParClient(idClient);
            long transactionsClient ;
            transactionsClient = transactions.stream().filter(c -> c.idCompte().equals(idCompte)).count();
            return (int) transactionsClient;

        }
        return 0;

    }

    public double CalculerMoyane(String idCompte, String idClient) {

        if (idClient.isEmpty()) {
            List<Transaction> transactions = trouveAll();
            double transactionsClient;
            transactionsClient = transactions.stream().filter(c -> c.idCompte().equals(idCompte)).mapToDouble(Transaction::montant).average().getAsDouble();

            return transactionsClient;
        } else if (idCompte.isEmpty()) {
            List<Transaction> transactions = trouverParClient(idClient);
            double transactionsClient;
            transactionsClient = transactions.stream().filter(c -> c.idCompte().equals(idCompte)).mapToDouble(Transaction::montant).average().getAsDouble();
            return transactionsClient;

        }
        return 0;

    }
}
