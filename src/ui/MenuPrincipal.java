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


    public MenuPrincipal( MenuTransaction menuTransaction, MenuClient menuClient, MenuCompte menuCompte, MenuRapport menuRapport) {
        this.menuTransaction = menuTransaction;
        this.menuClient = menuClient;
        this.menuCompte = menuCompte;
        this.menuRapport = menuRapport;
    }

    public void menuPrincipal() {
        boolean back = false;
        Scanner sc = new Scanner(System.in);

        while (!back) {
            System.out.println("\n--- GESTION BANCAIRE ---");
            System.out.println("1. Gestion Client");
            System.out.println("2. Gestion Compte");
            System.out.println("3. Gestion Transaction");
            System.out.println("4. Gestion Rapport");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");

            int choix;
            try {
                choix = sc.nextInt();


                switch (choix) {
                    case 1:
                        menuClient.afficherMenu();
                        break;
                    case 2:
                        menuCompte.afficherMenu();
                        break;
                    case 3:
                        menuTransaction.afficherMenu();
                        break;
                    case 4:
                        menuRapport.afficherMenu();
                    case 0:
                        back = true;
                        System.out.println("Au revoir !");
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                sc.nextLine();
                continue;
            }
        }
    }

}

