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

    @Override
    public boolean ajouter(Client client) {


        String sql = "insert into client(id ,nom , email) values (?,?,?)";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,client.id());
            ps.setString(2,client.nom());
            ps.setString(3,client.email());
            ps.execute();
            return true;

        } catch (SQLException e) {
            return  false;
        }
    }


    @Override
    public boolean modifier(Client client) {
        String sql = "update client set nom = ?, email = ? where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,client.nom());
            ps.setString(2,client.email());
            ps.setString(3,client.id());
            ps.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }
}
