import dao.ClientDAOImpl;
import dao.CompteDAOImpl;
import dao.Interfase.ClientDAO;
import dao.Interfase.CompteDAO;
import dao.Interfase.TransactionDAO;
import dbconnection.DataBase;
import service.ClientService;
import service.CompteService;
import service.RapportService;
import service.TransactionService;
import ui.*;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        Connection c = DataBase.getInstance().getConnection();
        ClientDAO clientDAO = new ClientDAOImpl(c);
        CompteDAO compteDAO = new CompteDAOImpl(c);
        TransactionDAO transactionDAO = new dao.TransactionDAOImpl(c);
        ClientService clientService = new ClientService(clientDAO,compteDAO);
        CompteService compteService = new CompteService(compteDAO);
        TransactionService transactionService = new TransactionService(transactionDAO,compteDAO);
        RapportService rapportService = new RapportService(compteService,clientService,transactionService);
        MenuClient client = new MenuClient(clientService);
        MenuTransaction transaction = new MenuTransaction(transactionService);
        MenuCompte compte = new MenuCompte(compteService);
        MenuRapport MenuRapport = new MenuRapport(rapportService);
        MenuPrincipal menu = new MenuPrincipal(transaction,client,compte,MenuRapport);
        menu.menuPrincipal();


    }
}