package src.modelo;

public class Carta {
    private String descricao;
    private String tipoAcao; // Ex: GANHA_DINHEIRO, PERDE_DINHEIRO, AVANCA, VAI_PARA_PRISAO
    private double valor; // Valor da transação ou número de casas a avançar

    public Carta(String descricao, String tipoAcao, double valor) {
        this.descricao = descricao;
        this.tipoAcao = tipoAcao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipoAcao() {
        return tipoAcao;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Carta: " + descricao;
    }
}
