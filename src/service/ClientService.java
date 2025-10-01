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

}
