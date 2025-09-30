package dao;

import dao.Interfase.CompteDAO;
import dao.enums.TypeCompte;
import entity.Compte;
import entity.CompteCourant;
import entity.CompteEpargne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompteDAOImpl implements CompteDAO {

    private static Connection connection;

    public CompteDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean ajouter(Compte compte) {
        String sql = "insert into compte(numero,solde,id_client,type_compte,decouvert_autorise,taux_interet) VALUES (?,?,?,?,?,?)";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, compte.getNumero());
            ps.setDouble(2, compte.getSolde());
            ps.setString(3,compte.getIdClient());
            ps.setString(4,compte.getType().toString());
            if (compte instanceof CompteCourant){
                ps.setDouble(5,((CompteCourant) compte).getDecouvertAutorise());
                ps.setDouble(6,0);
            }else if (compte instanceof CompteEpargne){
                ps.setDouble(5,0);
                ps.setDouble(6,((CompteEpargne) compte).getTauxInteret());
            }
            ps.executeUpdate();
            System.out.println("the nemuro de votre compte   : " +  compte.getNumero());
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());

        }
        return false;
    }

    @Override
    public void modifier(Compte compte) {

        String sql;
        if (compte instanceof CompteCourant){
            sql = "UPDATE compte set solde = ?, numero = ?, decouvert_autorise  = ?  WHERE id = ?";

            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setDouble(1,compte.getSolde());
                ps.setString(2,compte.getNumero());
                ps.setDouble(3,((CompteCourant) compte).getDecouvertAutorise());
                ps.setString(4,compte.getId());
                ps.executeUpdate();
                System.out.println("compte modifier");
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }else if (compte instanceof CompteEpargne){
            sql = "UPDATE compte set solde = ?,numero = ?,   taux_interet = ? WHERE numero = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setDouble(1,compte.getSolde());
                ps.setString(2,compte.getNumero());
                ps.setDouble(3,((CompteEpargne) compte).getTauxInteret());
                ps.setString(4,compte.getNumero());
                System.out.println("compte modifier");
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
    }
}
