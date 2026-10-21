# Todo List Fullstack

Projeto de aprendizado: todo list com backend Java puro, SQLite e front HTML/CSS/JS.

## Stack
- Java (HttpServer, JDBC, Gson)
- SQLite (via sqlite-jdbc)
- HTML, CSS, JavaScript (fetch)

## Como rodar
1. `javac -cp ".:lib/*" *.java`
2. `java -cp ".:lib/*" Main`
3. Em outro terminal: `cd front && python3 -m http.server 5500`
4. Abrir `http://localhost:5500`

## O que aprendi
- JDBC na unha (Connection, PreparedStatement, ResultSet)
- DAO (encapsulamento do acesso a dados)
- Servidor HTTP com `com.sun.net.httpserver`
- API REST (GET, POST, PUT, DELETE)
- CORS e preflight (OPTIONS)
- Front consumindo API com `fetch` e `async/await`
