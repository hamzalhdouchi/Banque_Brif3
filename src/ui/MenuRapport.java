package ui;

import enums.TypeTransaction;
import service.InterfaceService.RapportServiceInterface;
import service.RapportService;
import entity.Client;
import entity.Compte;
import entity.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuRapport {

    private final RapportServiceInterface rapportService;
    private final Scanner scanner;

    public MenuRapport(RapportService rapportService) {
        this.rapportService = rapportService;
        this.scanner = new Scanner(System.in);
    }

    public void afficherMenu() {
        int choix = -1;
        while (choix != 0) {
            System.out.println("\n===== MENU RAPPORTS BANCAIRES =====");
            System.out.println("1. Top 5 Clients par solde");
            System.out.println("2. Rapport mensuel (transactions)");
            System.out.println("3. Transactions suspectes (par montant)");
            System.out.println("4. Transactions suspectes (par lieu différent)");
            System.out.println("5. Comptes inactifs");
            System.out.println("6. Détection opérations multiples en moins d'une minute");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();

            switch (choix) {
                case 1 -> afficherTopClients();
                case 2 -> afficherRapportMensuel();
                case 3 -> afficherTransactionsSuspectesMontant();
                case 4 -> afficherTransactionsSuspectesLieu();
                case 5 -> afficherComptesInactifs();
                case 6 -> afficherOperationsMoinsMinute();
                case 0 -> System.out.println("Au revoir ");
                default -> System.out.println("Choix invalide");
            }
        }
    }

    private void afficherTopClients() {
        System.out.println("\n--- Top 5 Clients par Solde ---");
        List<Client> clients = rapportService.genererTop5ClientsParSolde();

        clients.forEach(c -> {
            System.out.println("-----------------------------------");
            System.out.println("ID       : " + c.id());
            System.out.println("Nom      : " + c.nom());
            System.out.println("Email    : " + c.email());
            System.out.println("-----------------------------------");
        });
    }

    private void afficherTransactionsSuspectesMontant() {
        System.out.println("\n--- Transactions suspectes ---");
        System.out.println("entre le montant suspectes");
        double montant = scanner.nextDouble();
        scanner.nextLine();
        List<Transaction> txs = rapportService.TransactionsSuspectesParMontant(montant);

        txs.forEach(t -> {
            System.out.println("------------------------------------------------");
            System.out.println("ID Compte        : " + t.idCompte());
            System.out.println("Lieu             : " + t.lieu());
            System.out.println("Montant          : " + t.montant());
            System.out.println("Type Transaction : " + t.type());
            System.out.println("Date             : " + t.date());
            System.out.println("------------------------------------------------");
        });
    }

    private void afficherTransactionsSuspectesLieu() {
        System.out.print("Entrez un lieu de référence : ");
        String lieu = scanner.next();
        List<Transaction> txs = rapportService.TransactionsSuspectesParLeiu(lieu);

        System.out.println("--- Transactions suspectes (lieu différent de " + lieu + ") ---");
        txs.forEach(t -> {
            System.out.println("------------------------------------------------");
            System.out.println("ID Compte        : " + t.idCompte());
            System.out.println("Lieu             : " + t.lieu());
            System.out.println("Montant          : " + t.montant());
            System.out.println("Type Transaction : " + t.type());
            System.out.println("Date             : " + t.date());
            System.out.println("------------------------------------------------");
        });
    }

    private void afficherComptesInactifs() {
        System.out.print("Nombre de mois d'inactivité : ");
        int mois = scanner.nextInt();
        List<Compte> comptes = rapportService.InactifCompte(mois);

        System.out.println("--- Comptes inactifs depuis " + mois + " mois ---");
        comptes.forEach(compte -> {
            System.out.println("------------------------------------");
            System.out.println("ID                   : " + compte.getId());
            System.out.println("Numéro de compte     : " + compte.getNumero());
            System.out.println("Solde                : " + compte.getSolde());
            System.out.println("Type de compte       : " + compte.getType());
            System.out.println("Client ID            : " + compte.getIdClient());
            System.out.println("------------------------------------");
        });
    }

    private void afficherOperationsMoinsMinute() {
        System.out.print("Max transactions par minute : ");
        int max = scanner.nextInt();
        List<Transaction> txs = rapportService.operationsMoinsMinute(max);

        System.out.println("--- Transactions suspectes (plus de " + max + " en moins d'une minute) ---");
        txs.forEach(t -> {
            System.out.println("------------------------------------------------");
            System.out.println("ID Compte        : " + t.idCompte());
            System.out.println("Lieu             : " + t.lieu());
            System.out.println("Montant          : " + t.montant());
            System.out.println("Type Transaction : " + t.type());
            System.out.println("Date             : " + t.date());
            System.out.println("------------------------------------------------");
        });
    }

    private void afficherRapportMensuel() {
        System.out.print("Entrez l'année : ");
        int annee = scanner.nextInt();
        System.out.print("Entrez le mois (1-12) : ");
        int mois = scanner.nextInt();

        Map<String, Object> rapport = rapportService.genererRapportMensuel(annee, mois);

        System.out.println("\n================ RAPPORT MENSUEL =================");
        System.out.println("Année : " + annee + " | Mois : " + mois);
        System.out.println("-------------------------------------------------");

        // Volume total
        double totalVolume = (double) rapport.get("totalVolume");
        System.out.println("Volume total des transactions : " + totalVolume);

        System.out.println("-------------------------------------------------");
        System.out.println("Nombre de transactions par type : ");
        Map<TypeTransaction, Long> countByType = (Map<TypeTransaction, Long>) rapport.get("countByType");
        countByType.forEach((type, count) -> {
            System.out.println("   ➡ " + type + " : " + count);
        });

        System.out.println("=================================================\n");
    }


}
