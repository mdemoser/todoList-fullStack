public class Tarefa {
    
    private int id;
    private String titulo;
    private boolean concluida;
    private String dataCriacao;



    public Tarefa(){

    }


    public  Tarefa(int id,String titulo, boolean concluida, String dataCriacao){
        this.id = id;
        this.titulo = titulo;
        this.concluida = concluida;
        this.dataCriacao = dataCriacao;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida){
        this.concluida = concluida;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao){
        this.dataCriacao = dataCriacao;
    
    }
}