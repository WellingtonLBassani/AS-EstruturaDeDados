package src.modelo;

public abstract class Casa {
    private int id;
    private String nome;
    private String tipo;

    public Casa(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }


    public abstract void interagir(Jogador jogador, Partida partida);

    @Override
    public String toString() {
        return String.format("[%s] %s", tipo, nome);
    }
}
