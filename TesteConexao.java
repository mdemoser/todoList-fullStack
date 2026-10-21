import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TesteConexao {
    public static void main(String[] args) throws SQLException{
        String url = "jdbc:sqlite:todoList.db";

     try (Connection conn = DriverManager.getConnection(url);
    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tarefas");
    ResultSet rs = stmt.executeQuery()){

    while (rs.next()) {
        int id  = rs.getInt("id");
        String titulo = rs.getString("titulo");
        int concluida = rs.getInt("concluida");
        String dataCriacao = rs.getString("data_criacao");

        System.out.println(id + " | " + titulo + " | " + concluida + " | " + dataCriacao);
    }    
    }

    }
}