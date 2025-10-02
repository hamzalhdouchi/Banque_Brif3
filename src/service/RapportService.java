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
}
