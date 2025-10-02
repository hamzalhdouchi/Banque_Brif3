package ui;

import dao.ClientDAOImpl;
import entity.Client;
import service.ClientService;
import service.InterfaceService.ClientServiceInterface;
import util.ValidationUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuClient{

    private final ClientServiceInterface clientService;
    private final Scanner scanner = new Scanner(System.in);

    public MenuClient(ClientService client) {
        this.clientService = client;
    }

    public void afficherMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Menu Client ===");
            System.out.println("1. Ajouter Client");
            System.out.println("2. Modifier Client");
            System.out.println("3. Supprimer Client");
            System.out.println("4. Afficher Tous");
            System.out.println("5. Rechercher par ID");
            System.out.println("6. Rechercher par Nom");
            System.out.println("7. calculer solde total de client");
            System.out.println("8. compter comptes client");
            System.out.println("0. Retour");
            System.out.print("Choix: ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> ajouterClient();
                case 2 -> modifierClient();
                case 3 -> supprimerClient();
                case 4 -> afficherTous();
                case 5 -> trouverParId();
                case 6 -> trouverParNom();
                case 7 -> calculerSoldeTotalClient();
                case 8 -> compterComptesClient();
                case 0 -> back = true;
                default -> System.out.println("Choix invalide !");
            }
        }
    }

}
