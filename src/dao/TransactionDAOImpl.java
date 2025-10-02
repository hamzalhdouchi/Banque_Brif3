package dao;

import enums.TypeTransaction;
import entity.Transaction;
import dao.Interfase.TransactionDAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @Override
    public List<Transaction> trouverToutes() {

        String sql = "select * from transaction";
        List<Transaction> transactions = new ArrayList<>();
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String date_transaction = rs.getString("date_transaction");
                double montant = rs.getDouble("montant");
                String type_transaction = rs.getString("type_transaction");
                String id_compte = rs.getString("id_compte");
                String lieu = rs.getString("lieu");
                LocalDateTime date_compte = LocalDateTime.parse(date_transaction, formatter);
                TypeTransaction type = TypeTransaction.valueOf(type_transaction);
                Transaction transaction = new Transaction(id,date_compte,montant,type,lieu,id_compte);
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException e) {
            return  null;
        }

    }

    public List<Transaction> trouverParDate(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT * FROM transaction WHERE date_transaction >= ? AND date_transaction <= ?";
        List<Transaction> transactions = new ArrayList<>();
        try {
            PreparedStatement pc = connection.prepareStatement(sql);
            pc.setTimestamp(1, Timestamp.valueOf(startDate));
            pc.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet rs = pc.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                Timestamp ts = rs.getTimestamp("date_transaction");
                LocalDateTime date_compte = ts.toLocalDateTime();
                double montant = rs.getDouble("montant");
                String type_transaction = rs.getString("type_transaction");
                String id_compte = rs.getString("id_compte");
                String lieu = rs.getString("lieu");
                TypeTransaction typeT = TypeTransaction.valueOf(type_transaction);

                Transaction transaction = new Transaction(id, date_compte, montant, typeT, lieu, id_compte);
                transactions.add(transaction);
            }
            return transactions;
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des transactions : " + e.getMessage());
            return List.of();
        }
    }

}
