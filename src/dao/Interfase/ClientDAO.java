package dao.Interfase;
import entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDAO {
    boolean ajouter(Client client);

    boolean modifier(Client client);

    boolean supprimer(String id);

    Optional<Client> trouverParId(String id);

    List<Client> trouverTous();

}