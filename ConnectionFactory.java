
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;



public class ConnectionFactory {
    private static final String URL = "jdbc:sqlite:todoList.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    
}
