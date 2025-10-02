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

    public MenuClient(ClientServiceInterface client) {
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

    private void ajouterClient() {

        System.out.println("-----------------Ajouter Client---------------");
        System.out.print("Entre ID (format CNE-12345): ");
        String id = scanner.nextLine();
        boolean ValidID = ValidationUtil.estIdValide(id);
        if (!ValidID) {
            System.out.println("The id is not valid "+id);
            return;
        }


        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        boolean ValidClient = ValidationUtil.estNomValide(nom);
        if (!ValidClient) {
            System.out.println("The nom is not valid "+ nom);
            return;
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();
        boolean ValidEmail = ValidationUtil.estEmailValide(email);
        if (!ValidEmail) {
            System.out.println("The email is not valid "+ email);
            return;
        }

        Client client = new Client(id, nom, email);
        boolean valide = clientService.ajouterClient(client);
        if(valide){
            System.out.println("Client ajouté avec succès !");
            return;
        } else {
            System.out.println("Erreur lors de l'ajout.");
            return;
        }
    }

    private void modifierClient() {
        System.out.print("ID du client à modifier: ");
        String id = scanner.nextLine();
        boolean ValidID = ValidationUtil.estIdValide(id);
        if (!ValidID) {
            System.out.println("The id is not valid " + id);
        }
        Optional<Client> optionalClient = clientService.trouverClientParId(id);
        if (optionalClient.isEmpty()) {
            System.out.println("Client introuvable !");
            return;
        }

        System.out.print("Nouveau nom: ");
        String nom = scanner.nextLine();
        boolean ValidClient = ValidationUtil.estNomValide(nom);
        if (!ValidClient) {
            System.out.println("The nom is not valid "+nom);
        }

        System.out.print("Nouvel email: ");
        String email = scanner.nextLine();
        boolean ValidEmail = ValidationUtil.estEmailValide(email);
        if (!ValidEmail) {
            System.out.println("The email is not valid "+ email);
        }

        Client client = new Client(id, nom, email);
        if (clientService.modifierClient(client)) {
            System.out.println("Client modifié avec succès !");
        } else {
            System.out.println("Erreur lors de la modification.");
        }
    }

    private void supprimerClient() {
        System.out.print("ID du client à supprimer: ");
        String id = scanner.nextLine();
        boolean ValidID = ValidationUtil.estIdValide(id);
        if (!ValidID) {
            System.out.println("The id is not valid "+id);
        }

        if (clientService.supprimerClient(id)) {
            System.out.println("Client supprimé avec succès !");
        } else {
            System.out.println("Erreur lors de la suppression.");
        }
    }

    private void afficherTous() {
        List<Client> clients = clientService.trouverTousLesClients();
        if (clients == null || clients.isEmpty()) {
            System.out.println("Aucun client trouvé.");
            return;
        }
        clients.forEach(c-> {
            System.out.println("-----------------------------------");
            System.out.println("ID       : "+c.id());
            System.out.println("Nom       : "+c.nom());
            System.out.println("Email     : "+c.email());
            System.out.println("------------------------------------");
        });
    }

    private void trouverParId() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        boolean ValidID = ValidationUtil.estIdValide(id);
        if (!ValidID) {
            System.out.println("The id is not valid "+id);
        }

        Optional<Client> client = clientService.trouverClientParId(id);
        if (client.isPresent()) {

        }
        client.ifPresentOrElse(
                c -> {
                    System.out.println("------------------------------");
                    System.out.println("ID     : " + c.id());
                    System.out.println("Nom    : " + c.nom());
                    System.out.println("Email  : " + c.email());
                    System.out.println("------------------------------");
                },
                () -> System.out.println("Client introuvable !")
        );
    }

    private void trouverParNom() {
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        boolean ValidClient = ValidationUtil.estNomValide(nom);
        if (!ValidClient) {
            System.out.println("The nom is not valid "+nom);
        }

        Optional<Client> client = clientService.trouverClientParNom(nom);
        client.ifPresentOrElse(
                c -> {
                    System.out.println("-----------------------------");
                    System.out.println("ID     : " + c.id());
                    System.out.println("Nom    : " + c.nom());
                    System.out.println("Email  : " + c.email());
                    System.out.println("-----------------------------");
                },
                () -> System.out.println("Client introuvable !")
        );
    }

    public void calculerSoldeTotalClient(){
        System.out.println("------------ calculerSoldeTotalClient ----------");
        System.out.println("Entre ID de Compte");
        String id = scanner.nextLine();
        boolean ValidID = ValidationUtil.estIdValide(id);
        if (!ValidID) {
            System.out.println("The id is not valid "+id);
        }
        double solde = clientService.calculerSoldeTotalClient(id);
        System.out.println("------------- SOLD TOTAL ------------");
        System.out.println("SOLD TOTAL : " + solde);
        System.out.println("-------------------------------------");
    }


    public void compterComptesClient(){
        System.out.println("----------- compter Comptes Client -----------");
        System.out.println("Entre ID de Compte");
        String id = scanner.nextLine();
        boolean ValidID = ValidationUtil.estIdValide(id);
        if (!ValidID) {
            System.out.println("The id is not valid "+id);
        }
        int compte = clientService.compterComptesClient(id);
        System.out.println("------------ COMPTER SOLD TOTAL ------------");
        System.out.println("COMPTER SOLD TOTAL : " + compte);
        System.out.println("--------------------------------------------");
    }



}
