package dao;

import java.sql.Connection;

public class CompteDAOImpl {

    private static Connection connection;

    public CompteDAOImpl(Connection connection) {
        this.connection = connection;
    }
}
