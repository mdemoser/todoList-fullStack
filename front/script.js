const API = "http://localhost:8080/tarefas";

const form = document.getElementById("form-tarefa");
const inputTitulo = document.getElementById("input-titulo");
const lista = document.getElementById("lista-tarefas");

async function carregarTarefas() {
    const resposta = await fetch(API);
    const tarefas = await resposta.json();

    lista.innerHTML = "";

    tarefas.forEach(tarefa => {
        const li = document.createElement("li");
        if (tarefa.concluida) {
            li.classList.add("concluida");
        }

        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.checked = tarefa.concluida;
        checkbox.addEventListener("change", () => toggleConcluida(tarefa));

        const span = document.createElement("span");
        span.className = "titulo";
        span.textContent = tarefa.titulo;

        const btn = document.createElement("button");
        btn.className = "btn-deletar";
        btn.textContent = "Deletar";
        btn.addEventListener("click", () => deletarTarefa(tarefa.id));

        li.appendChild(checkbox);
        li.appendChild(span);
        li.appendChild(btn);
        lista.appendChild(li);
    });
}

form.addEventListener("submit", async (evento) => {
    evento.preventDefault();

    const titulo = inputTitulo.value.trim();
    if (!titulo) return;

    await fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ titulo: titulo, concluida: false })
    });

    inputTitulo.value = "";
    carregarTarefas();
});

async function toggleConcluida(tarefa) {
    await fetch(`${API}/${tarefa.id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            titulo: tarefa.titulo,
            concluida: !tarefa.concluida
        })
    });

    carregarTarefas();
}

async function deletarTarefa(id) {
    await fetch(`${API}/${id}`, { method: "DELETE" });
    carregarTarefas();
}

carregarTarefas();