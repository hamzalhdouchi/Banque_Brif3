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
}
