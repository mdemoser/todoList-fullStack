import com.google.gson.Gson;


public class TesteGson {
    public static void main(String[] args){
        Gson gson = new Gson();

        Tarefa t = new Tarefa(1, "teste gson", false, "2026-10-20 10:00:00");
        String json = gson.toJson(t);
        System.out.println("JSON: " + json);

        Tarefa lida = gson.fromJson(json, Tarefa.class);
        System.out.println("Titulo lido: " + lida.getTitulo());
    }
}
