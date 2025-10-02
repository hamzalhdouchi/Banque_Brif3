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
}
