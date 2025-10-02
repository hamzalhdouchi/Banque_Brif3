package service;

import enums.TypeTransaction;
import entity.Client;
import entity.Compte;
import entity.Transaction;
import service.InterfaceService.RapportServiceInterface;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class RapportService implements RapportServiceInterface {

    private final CompteService compteService;
    private final ClientService clientService;
    private final TransactionService transactionService;
    public RapportService(CompteService compteService, ClientService clientService, TransactionService transactionService) {
        this.compteService = compteService;
        this.clientService = clientService;
        this.transactionService = transactionService;
    }

    public List<Client> genererTop5ClientsParSolde() {
        try{
            List<Client> clients = clientService.trouverTousLesClients();
            return clients.stream()
                    .sorted(Comparator.comparingDouble(this::getTotalBalanceForClient).reversed()).limit(5).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    private double getTotalBalanceForClient(Client c){
        try{
            List<Compte> accounts = compteService.trouverParClient(c.id());
            return accounts.stream().mapToDouble(Compte::getSolde).sum();
        }catch(Exception e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }

    }
}
