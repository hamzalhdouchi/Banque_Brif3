package dao;

import dao.Interfase.ClientDAO;
import entity.Client;
import entity.Compte;
import entity.CompteCourant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAOImpl implements ClientDAO {
    private final Connection connection;

    public ClientDAOImpl(Connection connection) {
        this.connection = connection;
    }
}
