package service;

import dao.CompteDAOImpl;
import dao.Interfase.ClientDAO;
import dao.Interfase.CompteDAO;
import entity.Client;
import entity.Compte;
import util.ValidationUtil;

import java.util.List;
import java.util.Optional;

public class ClientService {

    private final ClientDAO clientDAO;
    private final CompteDAO compteDAO;

    public ClientService(ClientDAO clientDAO, CompteDAO compteDAO) {
        this.clientDAO = clientDAO;
        this.compteDAO = compteDAO;
    }

    public boolean ajouterClient(Client client) {

        Client client1 = new Client(client.id(), client.nom(), client.email());

        boolean ajouter = clientDAO.ajouter(client1);
        return ajouter;
    }

    public boolean modifierClient(Client client) {

        Client client1 = new Client(client.id(), client.nom(), client.email());
        boolean modifier = clientDAO.modifier(client1);
        return modifier;
    }

    public boolean supprimerClient(String id) {

        boolean sup =  clientDAO.supprimer(id);
        return sup;
    }

    public Optional<Client> trouverClientParId(String id) {

        Optional<Client> client = clientDAO.trouverParId(id);
        if (client.isPresent()) {
            return  client;
        }
        return Optional.empty();
    }

    public List<Client> trouverTousLesClients() {

        List<Client> client =  clientDAO.trouverTous();
        return client;
    }

    public Optional<Client> trouverClientParNom(String nom) {

        List<Client> clients = clientDAO.trouverTous();
        Client newClient = null;

        newClient = clients.stream().filter(p -> p.nom().equals(nom)).findFirst().orElse(null);

        if (newClient == null) {
            return Optional.empty();
        }
        return Optional.of(newClient);
    }

    public double calculerSoldeTotalClient(String idClient) {

        List<Compte>  comptes = compteDAO.trouverTous();

        double total = comptes.stream().filter(p -> p.getIdClient().equals(idClient)).mapToDouble(p -> p.getSolde()).sum();
        return total;
    }

    public int compterComptesClient(String idClient) {

        List<Compte>  comptes = compteDAO.trouverTous();

        long  total = comptes.stream().filter(p -> p.getIdClient().equals(idClient)).count();
        return (int) total;
    }

}