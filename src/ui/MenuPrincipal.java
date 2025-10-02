package ui;

import entity.Client;
import service.ClientService;
import service.CompteService;
import service.RapportService;
import service.TransactionService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal{

    private  final MenuTransaction menuTransaction;
    private  final MenuClient menuClient;
    private  final MenuCompte menuCompte;
    private  final MenuRapport menuRapport;
}
