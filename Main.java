import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/tarefas", new TarefasHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor rodando em http://localhost:8080/tarefas");
    }

    static class TarefasHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String metodo = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if ("OPTIONS".equals(metodo)) {
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            }

            if ("GET".equals(metodo)) {
                try {
                    TarefaDAO dao = new TarefaDAO();
                    List<Tarefa> tarefas = dao.listar();
                    Gson gson = new Gson();
                    String json = gson.toJson(tarefas);

                    exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                    exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

                    byte[] resposta = json.getBytes(StandardCharsets.UTF_8);
                    exchange.sendResponseHeaders(200, resposta.length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(resposta);
                    os.close();
                } catch (Exception e) {
                    responderErro(exchange, 500, e.getMessage());
                }
                return;
            }

            if ("POST".equals(metodo)) {
                try {
                    String body = lerBody(exchange);
                    Gson gson = new Gson();
                    Tarefa t = gson.fromJson(body, Tarefa.class);

                    TarefaDAO dao = new TarefaDAO();
                    dao.inserir(t);

                    responder(exchange, 201, "{\"status\":\"criado\"}");
                } catch (Exception e) {
                    responderErro(exchange, 500, e.getMessage());
                }
                return;
            }

            if ("PUT".equals(metodo)) {
                int id = extrairId(path);
                if (id == -1) {
                    responderErro(exchange, 400, "ID ausente na URL");
                    return;
                }
                try {
                    String body = lerBody(exchange);
                    Gson gson = new Gson();
                    Tarefa t = gson.fromJson(body, Tarefa.class);
                    t.setId(id);

                    TarefaDAO dao = new TarefaDAO();
                    dao.atualizar(t);

                    responder(exchange, 200, "{\"status\":\"atualizado\"}");
                } catch (Exception e) {
                    responderErro(exchange, 500, e.getMessage());
                }
                return;
            }

            if ("DELETE".equals(metodo)) {
                int id = extrairId(path);
                if (id == -1) {
                    responderErro(exchange, 400, "ID ausente na URL");
                    return;
                }
                try {
                    TarefaDAO dao = new TarefaDAO();
                    dao.deletar(id);

                    responder(exchange, 200, "{\"status\":\"deletado\"}");
                } catch (Exception e) {
                    responderErro(exchange, 500, e.getMessage());
                }
                return;
            }

            responderErro(exchange, 405, "Método não permitido");
        }

        private static int extrairId(String path) {
            if (path.equals("/tarefas") || path.equals("/tarefas/")) {
                return -1;
            }
            try {
                String idStr = path.substring("/tarefas/".length());
                return Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        private static String lerBody(HttpExchange exchange) throws IOException {
            InputStream is = exchange.getRequestBody();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] dados = new byte[1024];
            int lidos;
            while ((lidos = is.read(dados)) != -1) {
                buffer.write(dados, 0, lidos);
            }
            return buffer.toString(StandardCharsets.UTF_8);
        }

        private static void responder(HttpExchange exchange, int status, String json) throws IOException {
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(status, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }

        private static void responderErro(HttpExchange exchange, int status, String mensagem) throws IOException {
            responder(exchange, status, "{\"erro\":\"" + mensagem + "\"}");
        }
    }
}
