package ui;

import enums.TypeCompte;
import entity.Compte;
import entity.CompteCourant;
import entity.CompteEpargne;
import service.CompteService;
import service.InterfaceService.CompteServiceInterface;
import util.ValidationUtil;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuCompte {

    private final CompteService compteService;
    private final Scanner scanner = new Scanner(System.in);

    public MenuCompte(CompteService compteService) {
        this.compteService = compteService;
    }

    public void afficherMenu() {
        boolean quit = false;

        while (!quit) {
            System.out.println("\n===== Gestion des Comptes =====");
            System.out.println("1. Ajouter un compte");
            System.out.println("2. Modifier un compte");
            System.out.println("3. Supprimer un compte");
            System.out.println("4. Afficher tous les comptes");
            System.out.println("5. Rechercher par ID");
            System.out.println("6. Rechercher par Client");
            System.out.println("7. Rechercher par Numéro");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");

            int choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 1 -> ajouterCompte();
                case 2 -> modifierCompte();
                case 3 -> supprimerCompte();
                case 4 -> afficherTous();
                case 5 -> rechercherParId();
                case 6 -> rechercherParClient();
                case 7 -> rechercherParNumero();
                case 0 -> quit = true;
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private void ajouterCompte() {
        System.out.print("ID Client: ");
        String idClient = scanner.nextLine();
        System.out.print("Numéro min 5 lettre et nombre: ");
        String numero = scanner.nextLine();
        boolean ValidNumero = ValidationUtil.NumeroValide(numero);
        if (!ValidNumero) {
            System.out.println("Le Numero invalide !");
        }
        System.out.print("Solde: ");
        double solde = Double.parseDouble(scanner.nextLine());
        System.out.println("Entre Type (courant/Epagne)");
        System.out.println("1. Compte Courant");
        System.out.println("2. Compte Epagne");
        int choix = scanner.nextInt();
        scanner.nextLine();
        if (choix == 1) {
            System.out.println("------------ Compte Courant -------------");
            System.out.println("Entre decouvert autourse entre(0,0 - 5000,0)");
            double decouvertAutorise = Double.parseDouble(scanner.nextLine());
            boolean validDecouvert = ValidationUtil.estDecouvertValide(decouvertAutorise);

            if (validDecouvert) {

                Compte compte = new CompteCourant(null, numero, solde, idClient,decouvertAutorise, TypeCompte.COURANT);
                boolean res = compteService.ajouterCompte(compte);
                if (res){
                    System.out.println("the compte create successfully");
                }else {
                    System.out.println("the compte create failed");
                }
            }else {
                System.out.println("le decouvert pas valide !");
            }

        }else if (choix == 2) {
            System.out.println("------------ Compte Epagne -------------");
            System.out.println("Entre le tauxInteret entre(0,01, 10,0)");
            double tauxInteret = scanner.nextDouble();
            boolean validTauxInteret = ValidationUtil.estTauxInteretValide(tauxInteret);
            if (validTauxInteret) {
                Compte compte= new CompteEpargne(null,numero,solde,idClient,tauxInteret,TypeCompte.EPARGNE);
                boolean res = compteService.ajouterCompte(compte);
                if (res){
                    System.out.println("the compte create successfully");
                }else  {
                    System.out.println("the compte create failed");
                }
            }else  {
                System.out.println("le tauxInteret valide !");
            }

        }

    }

    private void modifierCompte() {
        System.out.print("Numero du compte à modifier: ");
        String id = scanner.nextLine();
        Optional<Compte> optCompte = compteService.trouverParNumero(id);

        if (optCompte.isPresent()) {
            Compte compte = optCompte.get();
            if (compte instanceof CompteEpargne) {
                CompteEpargne compteEpargne = (CompteEpargne) compte;
            } else if (compte instanceof CompteCourant) {
                CompteCourant compteCourant = (CompteCourant) compte;
            } else {
                System.out.println("Type de compte inconnu !");
            }
            System.out.println("------------------------------------");
            System.out.println("ID                     : " + compte.getId());
            System.out.println("numero de compte       : " + compte.getNumero());
            System.out.println("Solde                  : " + compte.getSolde());
            System.out.println("type de compte         : " + compte.getType());
            System.out.println("Client ID              : " + compte.getIdClient());
            if (compte instanceof CompteEpargne) {
                CompteEpargne compteEpargne = (CompteEpargne) compte;
                System.out.println("Taux d'interet     : " + compteEpargne.getTauxInteret());
            } else if (compte instanceof CompteCourant) {
                CompteCourant compteCourant = (CompteCourant) compte;
                System.out.println("Decouvert autorisé : " + compteCourant.getDecouvertAutorise());
            }
            System.out.println("-------------------------------------");


            System.out.println("-------------- UPDATE ----------------- ");

            System.out.println("1 : SOLD");
            System.out.println("2 : NUMERO");
            if (compte.getType() == TypeCompte.COURANT) {
                System.out.println("3 : Decouvert autorisé");
            } else {
                System.out.println("3 : Taux d'interet");
            }
            System.out.println("4 : TOUT");

            int choix = scanner.nextInt();
            scanner.nextLine();
            if (choix < 1 || choix > 6) {
                System.out.println("choix invalide !");
            }

            switch (choix) {
                case 1:
                    System.out.println("------------ Modification SOLD -------------");
                    System.out.println("Entre SOLD");
                    double solde = scanner.nextDouble();
                    scanner.nextLine();
                    if (solde <= 0) {
                        System.out.println("solde invalide !");
                    }
                    if (compte instanceof CompteCourant) {
                        CompteCourant compteCourant = (CompteCourant) compte;
                        Compte compte1 = new CompteCourant(compte.getId(), compte.getNumero(), solde, compte.getIdClient(), compteCourant.getDecouvertAutorise(), TypeCompte.COURANT);
                        compteService.modifierCompte(compte1);
                    } else {
                        CompteEpargne compteEpargne = (CompteEpargne) compte;

                        Compte compte1 = new CompteCourant(compte.getId(), compte.getNumero(), solde, compte.getIdClient(), compteEpargne.getTauxInteret(), TypeCompte.COURANT);
                        compteService.modifierCompte(compte1);
                    }
                    break;
                case 2:
                    System.out.println("------------ Modification NUMERO -------------");
                    System.out.println("Entre NUMERO min 5 lettre et numbre");
                    String numero = scanner.nextLine();
                    boolean validN = ValidationUtil.NumeroValide(numero);
                    if (compte instanceof CompteCourant) {
                        CompteCourant compteCourant = (CompteCourant) compte;
                        Compte compte1 = new CompteCourant(compte.getId(), numero, compte.getSolde(), compte.getIdClient(), compteCourant.getDecouvertAutorise(), TypeCompte.COURANT);
                        compteService.modifierCompte(compte1);
                    } else {
                        CompteEpargne compteEpargne = (CompteEpargne) compte;
                        Compte compte1 = new CompteCourant(compte.getId(), numero, compte.getSolde(), compte.getIdClient(), compteEpargne.getTauxInteret(), TypeCompte.COURANT);
                        compteService.modifierCompte(compte1);
                    }
                    break;
                case 3:
                    if (compte.getType() == TypeCompte.COURANT) {
                        System.out.println("------------ Modification Decouvert autorisé -------------");
                        System.out.println("Entre Decouvert");
                        double soldeActuel = scanner.nextDouble();
                        boolean decouvertAutorise = ValidationUtil.estDecouvertValide(soldeActuel);
                        if (decouvertAutorise) {
                            Compte compte1 = new CompteCourant(compte.getId(), compte.getNumero(), compte.getSolde(), compte.getIdClient(), soldeActuel, TypeCompte.COURANT);
                            compteService.modifierCompte(compte1);
                        } else {
                            System.out.println("le decouvert pas valide !");
                        }
                    } else {
                        System.out.println("----------------Taux d'interet -------------------------");
                        System.out.println("Entre TauxInteret");
                        double tauxInteret = scanner.nextDouble();
                        boolean valid = ValidationUtil.estTauxInteretValide(tauxInteret);
                        if (valid) {
                            Compte compte1 = new CompteEpargne(compte.getId(), compte.getNumero(), compte.getSolde(), compte.getIdClient(), tauxInteret, TypeCompte.COURANT);
                            compteService.modifierCompte(compte1);
                        } else {
                            System.out.println("le tauxInteret valide !");
                        }
                    }
                    break;
                case 4:
                    System.out.println("------------ Modification TOUT --------------");
                    System.out.println("entre Numero");
                    String numeroT = scanner.nextLine();
                    System.out.println("entre Solde");
                    double soldeT = scanner.nextInt();
                    if (compte.getType() == TypeCompte.COURANT) {
                        System.out.println("entre le Decouvert autorisé");
                        double decouvertAutorise = scanner.nextDouble();
                        boolean valid = ValidationUtil.estDecouvertValide(decouvertAutorise);
                        if (valid) {
                            Compte compte1 = new CompteCourant(compte.getId(), numeroT, soldeT, compte.getIdClient(), decouvertAutorise, TypeCompte.COURANT);
                            compteService.modifierCompte(compte1);
                        }
                    } else {
                        System.out.println("entre le tauxInteret");
                        double tauxInteret = scanner.nextDouble();
                        boolean valid = ValidationUtil.estTauxInteretValide(tauxInteret);
                        if (valid) {
                            Compte compte1 = new CompteEpargne(compte.getId(), numeroT, soldeT, compte.getIdClient(), tauxInteret, TypeCompte.COURANT);
                            compteService.modifierCompte(compte1);
                        }
                    }
                    break;
                default:
                    System.out.println("Choisissez une choix Valid !");
            }
        }
    }

    private void supprimerCompte() {
        System.out.print("Numero du compte à supprimer: ");
        String numero = scanner.nextLine();
        if (compteService.supprimerCompte(numero)) {
            System.out.println("Compte supprimé !");
        } else {
            System.out.println("Suppression échouée.");
        }
    }

    private void afficherTous() {
        List<Compte> comptes = compteService.trouverTo();
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte trouvé.");
        } else {
            comptes.stream().forEach(c -> {
                System.out.println("------------------------------------");
                System.out.println("ID                     : "+ c.getId());
                System.out.println("numero de compte       : "+ c.getNumero());
                System.out.println("Solde                  : "+ c.getSolde());
                System.out.println("type de compte         : "+ c.getType());
                System.out.println("Client ID              : "+ c.getIdClient() );

                if (c instanceof CompteCourant) {
                    CompteCourant cc = (CompteCourant) c;
                    System.out.println("Découvert autorisé : " + cc.getDecouvertAutorise());
                } else if (c instanceof CompteEpargne) {
                    CompteEpargne ce = (CompteEpargne) c;
                    System.out.println("Taux d'intérêt     : " + ce.getTauxInteret());
                }
                System.out.println("-------------------------------------");

            });
        }
    }

    private void rechercherParId() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        Compte c = compteService.trouverParId(id).get();

        System.out.println("------------------------------------");
        System.out.println("ID                     : "+ c.getId());
        System.out.println("numero de compte       : "+ c.getNumero());
        System.out.println("Solde                  : "+ c.getSolde());
        System.out.println("type de compte         : "+ c.getType());
        System.out.println("Client ID              : "+ c.getIdClient() );
        if (c instanceof CompteCourant) {
            CompteCourant cc = (CompteCourant) c;
            System.out.println("Découvert autorisé     : " + cc.getDecouvertAutorise());
        } else if (c instanceof CompteEpargne) {
            CompteEpargne ce = (CompteEpargne) c;
            System.out.println("Taux d'intérêt         : " + ce.getTauxInteret());
        }
        System.out.println("-------------------------------------");

    }

    private void rechercherParClient() {
        System.out.print("ID Client: ");
        String idClient = scanner.nextLine();
        List<Compte> comptes = compteService.trouverParClient(idClient);
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte trouvé pour ce client.");
        } else {
            comptes.stream().forEach(compte -> {
                System.out.println("------------------------------------");
                System.out.println("ID                     : "+ compte.getId());
                System.out.println("numero de compte       : "+ compte.getNumero());
                System.out.println("Solde                  : "+ compte.getSolde());
                System.out.println("type de compte         : "+ compte.getType());
                System.out.println("Client ID              : "+ compte.getIdClient() );
                if(compte instanceof  CompteCourant ){
                    CompteCourant cc = (CompteCourant ) compte ;
                    System.out.println("Decouvert autorisé : " + cc.getDecouvertAutorise());
                }else{
                    CompteEpargne ce = (CompteEpargne) compte ;
                    System.out.println("Taux d'interet     : "+ ce.getTauxInteret());
                }
                System.out.println("------------------------------------");
            });
        }
    }

    private void rechercherParNumero() {
        System.out.print("Numéro du compte: ");
        String numero = scanner.nextLine();
        Compte compte  = compteService.trouverParNumero(numero).get();

        System.out.println("------------------------------------");
        System.out.println("ID                     : "+ compte.getId());
        System.out.println("numero de compte       : "+ compte.getNumero());
        System.out.println("Solde                  : "+ compte.getSolde());
        System.out.println("type de compte         : "+ compte.getType());
        System.out.println("Client ID              : "+ compte.getIdClient() );
        if(compte instanceof CompteCourant){
            CompteCourant cc = (CompteCourant) compte;
            System.out.println("Decouvert autorisé : " + cc.getDecouvertAutorise());
        }else{
            CompteEpargne ce = (CompteEpargne) compte ;
            System.out.println("Taux d'interet     : "+ ce.getTauxInteret());
        }
        System.out.println("------------------------------------");

    }

}
