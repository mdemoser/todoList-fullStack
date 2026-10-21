
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TarefaDAO {
    public List<Tarefa> listar() throws SQLException {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "Select * FROM tarefas";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tarefas");
            ResultSet rs = stmt.executeQuery()){


            while (rs.next()){
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                boolean concluida = rs.getInt("concluida") == 1;
                String dataCriacao = rs.getString("data_criacao");

                tarefas.add(new Tarefa(id, titulo, concluida, dataCriacao));
        }
        }

        return tarefas;
    }

        public void inserir(Tarefa t) throws SQLException {
            String sql = "INSERT INTO tarefas (titulo, concluida) VALUES(?, ?)";
        
            try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){

                    stmt.setString(1, t.getTitulo());
                    stmt.setInt(2, t.isConcluida() ? 1 : 0);

                    stmt.executeUpdate();
                }
        }

    public void atualizar(Tarefa t) throws SQLException {
        String sql = "UPDATE tarefas SET titulo = ?, concluida = ? WHERE id = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt  = conn.prepareStatement(sql)){

                stmt.setString(1, t.getTitulo());
                stmt.setInt(2, t.isConcluida() ? 1 : 0);
                stmt.setInt(3, t.getId());

                stmt.executeUpdate();
            }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        

        try (Connection conn  = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setInt(1, id);

                stmt.executeUpdate();
            }    
    }

}
