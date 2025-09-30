package dao;

import dao.enums.TypeTransaction;
import entity.Compte;
import entity.Transaction;
import dao.Interfase.TransactionDAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.stream.Collectors;

public class TransactionDAOImpl implements TransactionDAO {


    private final Connection connection;

    public TransactionDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean ajouter(Transaction transaction) {
        String sql = "insert into transaction (date_transaction,montant,type_transaction,id_compte,lieu) values (?,?,?,?,?)";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1,  Timestamp.valueOf(transaction.date()));
            ps.setDouble(2, transaction.montant());
            ps.setString(3, String.valueOf(transaction.type()));
            ps.setString(4, String.valueOf(transaction.idCompte()));
            ps.setString(5, transaction.lieu());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Erreur Ajout de la transaction "+e.getMessage());
        }
        return false;
    }

    public boolean supprimer(Transaction transaction) {
        String sql = "delete from transaction where id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(transaction.id()));
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur Ajout de la transaction "+e.getMessage());
            return false;
        }
    }

}
