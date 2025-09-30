package dbconnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DataBase {
    private static DataBase instance = null;
    private static Connection connection;
    public static final String URL = "jdbc:mysql://localhost:3306/gestion_bancaire?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "37533753";
}
