package ui;

import enums.TypeTransaction;
import entity.Compte;
import entity.Transaction;
import service.InterfaceService.TransactionServiceInterface;
import service.TransactionService;
import util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuTransaction {

    private final TransactionServiceInterface transactionService;
    private final Scanner scanner = new Scanner(System.in);

    public MenuTransaction(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void afficherMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== MENU TRANSACTIONS =====");
            System.out.println("1. Enregistrer une transaction");
            System.out.println("2. Afficher toutes les transactions");
            System.out.println("3. Rechercher par compte");
            System.out.println("4. Rechercher par client");
            System.out.println("5. Filtrer par type");
            System.out.println("6. Filtrer par montant");
            System.out.println("7. Regrouper par type");
            System.out.println("8. Filtrer par date");
            System.out.println("0. Retour");

            System.out.print("Choix: ");
            int choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 1 -> enregistrerTransaction();
                case 2 -> afficherToutes();
                case 3 -> trouverParCompte();
                case 4 -> trouverParClient();
                case 5 -> filtrerParType();
                case 6 -> filtrerParMontant();
                case 7 -> regrouperParType();
                case 8 -> filtrerParDate();
                case 0 -> back = true;
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private void enregistrerTransaction() {
        System.out.print("ID Client: ");
        String idCompte = scanner.nextLine();
        List<Compte> comptes = transactionService.compteClient(idCompte);
        if (comptes.isEmpty()) {
            System.out.println("Le compte n'existe pas !");
        }
        System.out.println("entre le numero de compte");
        String numero = scanner.nextLine();
        Compte compte1 =  comptes.stream().filter(com -> com.getNumero().equals(numero)).findFirst().orElse(null);
        if (compte1 == null) {
            System.out.println("Le compte n'existe pas !");
            return;
        }

        System.out.println("------------------------------------");
        System.out.println("ID                     : "+ compte1.getId());
        System.out.println("numero de compte       : "+ compte1.getNumero());
        System.out.println("Solde                  : "+ compte1.getSolde());
        System.out.println("type de compte         : "+ compte1.getType());
        System.out.println("Client ID              : "+ compte1.getIdClient() );
        System.out.println("-------------------------------------");

        System.out.print("Montant: ");
        double montant = Double.parseDouble(scanner.nextLine());

        System.out.print("Lieu: ");
        String lieu = scanner.nextLine();
        String lieuValid = ValidationUtil.formaterLieu(lieu);
        System.out.println("entre le type (VERSEMENT / VIREMENT / RETRAIT)");
        String type = scanner.nextLine().toUpperCase();
        TypeTransaction typeValid = TypeTransaction.valueOf(type);
        System.out.println("fnvdcdsklcdskl");
        if (typeValid.equals(TypeTransaction.VERSEMENT)){
            double Sold = compte1.getSolde() + montant;
            LocalDateTime date = LocalDateTime.now();
            Transaction transaction = new Transaction(null,date,montant,typeValid,lieuValid,compte1.getId());
            boolean res = transactionService.enregistrerTransaction(transaction);
            if (res){
                System.out.println("transaction été insert successfully !");
            }else {
                System.out.println("Erreur de insertion !");
            }
            transactionService.mettreAJourSolde(compte1.getNumero(),Sold);
        }else if (typeValid.equals(TypeTransaction.VIREMENT))
        {
            System.out.println("Entrez le numéro du compte débiteur :");
            String numero1 = scanner.nextLine();
            List<Compte> compteList = transactionService.troveToutCompte();
            Compte compte3 = compteList.stream().filter(com -> com.getNumero().equals(numero1)).findFirst().orElse(null);

            if (compte3 == null) {
                System.out.println("Le compte n'existe pas !");
                return;
            }
            double Sold1 = compte3.getSolde() + montant;
            LocalDateTime date = LocalDateTime.now();
            Transaction transaction = new Transaction(null,date,montant,typeValid,lieuValid,compte3.getId());
            transactionService.mettreAJourSolde(compte3.getNumero(),Sold1);

            boolean res = transactionService.enregistrerTransaction(transaction);
            if (res){
                System.out.println("transaction été insert successfully !");
            }else {
                System.out.println("Erreur de insertion !");
            }
            double Sold2 = compte1.getSolde() - montant;
            if (Sold2 < 0) {
                System.out.println("Le Sold insifisent !");
            }
            transactionService.mettreAJourSolde(numero,Sold2);
            Transaction transaction1 = new Transaction(null,date,montant,typeValid,lieuValid,compte1.getId());
            boolean res1 = transactionService.enregistrerTransaction(transaction1);
            if (res1){
                System.out.println("transaction été insert successfully !");
            }else {
                System.out.println("Erreur de insertion !");
            }
        }else if (typeValid.equals(TypeTransaction.RETRAIT)){

            double Sold = compte1.getSolde() - montant;
            if (Sold < 0) {
                System.out.println("Le Sold insifisent !");
            }
            LocalDateTime date = LocalDateTime.now();
            Transaction transaction = new Transaction(null,date,montant,typeValid,lieuValid,compte1.getId());
            boolean res = transactionService.enregistrerTransaction(transaction);
            if (res){
                System.out.println("transaction été insert successfully !");
            }else {
                System.out.println("Erreur de insertion !");
            }
            transactionService.mettreAJourSolde(compte1.getNumero(),Sold);
        }
    }

    private void afficherToutes() {
        List<Transaction> list = transactionService.trouveAll();
        if (list.isEmpty()) {
            System.out.println("Aucune transaction trouvée.");
        } else {
            list.forEach(t -> {
                System.out.println("------------------------------------------------");
                System.out.println("ID                : "+ t.idCompte());
                System.out.println("Lieu              :   "+ t.lieu());
                System.out.println("Montant           : "+ t.montant());
                System.out.println("type de compte    : "+ t.type());
                System.out.println("--------------------------------------------------");
            });
        }
    }

    private void trouverParCompte() {
        System.out.print("ID Compte: ");
        String idCompte = scanner.nextLine();
        List<Transaction> list = transactionService.trouverParCompte(idCompte);
        if (list == null || list.isEmpty()) {
            System.out.println("Aucune transaction pour ce compte.");
        } else {
            list.forEach(t -> {
                System.out.println("------------------------------------------------");
                System.out.println("ID                : "+ t.idCompte());
                System.out.println("Lieu              : "+ t.lieu());
                System.out.println("Montant           : "+ t.montant());
                System.out.println("type de compte    : "+ t.type());
                System.out.println("-------------------------------------------------");

            });
        }
    }

    private void trouverParClient() {
        System.out.print("ID Client: ");
        String idClient = scanner.nextLine();
        List<Transaction> list = transactionService.trouverParClient(idClient);
        if (list.isEmpty()) {
            System.out.println("Aucune transaction pour ce client.");
        } else {
            list.forEach(t -> {
                System.out.println("------------------------------------------------");
                System.out.println("ID        : "+ t.idCompte());
                System.out.println("Lieu"+ t.lieu());
                System.out.println("Montant        : "+ t.montant());
                System.out.println("type de compte    : "+ t.type());
                System.out.println("------------------------------------------------");
            });;
        }
    }

    private void filtrerParType() {
        System.out.print("Type (VERSEMENT/RETRAIT/VIREMENT): ");
        String type = scanner.nextLine().toUpperCase();
        List<Transaction> list = transactionService.filtreParType(TypeTransaction.valueOf(type.toUpperCase()));
        if (list == null || list.isEmpty()) {
            System.out.println("Aucune transaction trouvée.");
        } else {
            list.forEach(t -> {
                System.out.println("------------------------------------------------");
                System.out.println("ID                : "+ t.idCompte());
                System.out.println("Lieu              :"+ t.lieu());
                System.out.println("Montant           : "+ t.montant());
                System.out.println("type de compte    : "+ t.type());
                System.out.println("------------------------------------------------");
            });
        }
    }

    private void filtrerParMontant() {
        System.out.print("Montant: ");
        double montant = Double.parseDouble(scanner.nextLine());
        List<Transaction> list = transactionService.filtreParMontant(montant);
        if (list == null || list.isEmpty()) {
            System.out.println("Aucune transaction trouvée.");
        } else {
            list.forEach(t -> {
                System.out.println("------------------------------------------------");
                System.out.println("ID                : "+ t.idCompte());
                System.out.println("Lieu              : "+ t.lieu());
                System.out.println("Montant           : "+ t.montant());
                System.out.println("type de compte    : "+ t.type());
                System.out.println("------------------------------------------------");
            });
        }
    }

    private void regrouperParType() {
        Map<TypeTransaction, List<Transaction>> grouped = transactionService.regrouperParType();
        if (grouped == null || grouped.isEmpty()) {
            System.out.println("Aucune transaction trouvée.");
        } else {
            grouped.forEach((type, txs) -> {
                System.out.println("== " + type + " ==");
                txs.forEach(t -> {
                    System.out.println("------------------------------------------------");
                    System.out.println("ID                : "+ t.idCompte());
                    System.out.println("Lieu              : "+ t.lieu());
                    System.out.println("Montant           : "+ t.montant());
                    System.out.println("type de compte    : "+ t.type());
                    System.out.println("-------------------------------------------------");

                });
            });
        }
    }

    private void filtrerParDate() {
        System.out.print("Date (yyyy-MM-ddTHH:mm): ");
        String dateInput = scanner.nextLine();
        LocalDateTime date = LocalDateTime.parse(dateInput);
        List<Transaction> list = transactionService.filtreParDate(date);
        if (list == null || list.isEmpty()) {
            System.out.println("Aucune transaction trouvée.");
        } else {
            list.forEach(t -> {
                System.out.println("------------------------------------------------");
                System.out.println("ID                : "+ t.idCompte());
                System.out.println("Lieu              : "+ t.lieu());
                System.out.println("Montant           : "+ t.montant());
                System.out.println("type de compte    : "+ t.type());
                System.out.println("-------------------------------------------------");

            });
        }
    }
}
