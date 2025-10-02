package service.InterfaceService;

import entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientServiceInterface {

    boolean ajouterClient(Client client);

    boolean modifierClient(Client client);

    boolean supprimerClient(String id);

    Optional<Client> trouverClientParId(String id);

    List<Client> trouverTousLesClients();

    Optional<Client> trouverClientParNom(String nom);

    double calculerSoldeTotalClient(String idClient);

    int compterComptesClient(String idClient);
}
