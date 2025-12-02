package src.modelo;

public class Imovel extends Casa {
    private double precoCompra;
    private double valorAluguel;
    private Jogador proprietario;
    private boolean hipotecado;

    public Imovel(String nome, double precoCompra, double valorAluguel) {
        super(nome, "Imóvel");
        this.precoCompra = precoCompra;
        this.valorAluguel = valorAluguel;
        this.proprietario = null;
        this.hipotecado = false;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public double getValorAluguel() {
        return valorAluguel;
    }

    public Jogador getProprietario() {
        return proprietario;
    }

    public void setProprietario(Jogador proprietario) {
        this.proprietario = proprietario;
    }

    public boolean isHipotecado() {
        return hipotecado;
    }

    public void setHipotecado(boolean hipotecado) {
        this.hipotecado = hipotecado;
    }

    @Override
    public void interagir(Jogador jogador, Partida partida) {
        if (proprietario == null) {
            // Opção de Compra
            partida.getUi().exibirMensagem(String.format("Você parou em %s. Preço: R$ %.2f. Aluguel: R$ %.2f.", getNome(), precoCompra, valorAluguel));
            if (jogador.getSaldo() >= precoCompra) {
                if (partida.getUi().confirmarAcao("Deseja comprar este imóvel? (S/N)")) {
                    jogador.comprarImovel(this);
                    partida.getUi().exibirMensagem(String.format("Parabéns! Você comprou %s por R$ %.2f.", getNome(), precoCompra));
                }
            } else {
                partida.getUi().exibirMensagem("Saldo insuficiente para comprar este imóvel.");
            }
        } else if (proprietario != jogador) {
            // Pagamento de Aluguel
            if (!hipotecado) {
                partida.getUi().exibirMensagem(String.format("Você parou em %s, que pertence a %s. Aluguel: R$ %.2f.", getNome(), proprietario.getNome(), valorAluguel));
                jogador.pagarAluguel(proprietario, valorAluguel);
                partida.getUi().exibirMensagem(String.format("Você pagou R$ %.2f de aluguel para %s.", valorAluguel, proprietario.getNome()));
            } else {
                partida.getUi().exibirMensagem(String.format("Você parou em %s, mas está hipotecado. Não há aluguel a pagar.", getNome()));
            }
        } else {
            // Imóvel próprio
            partida.getUi().exibirMensagem(String.format("Você parou em %s. É seu!", getNome()));
        }
    }

    @Override
    public String toString() {
        String status = proprietario == null ? "Livre" : "Dono: " + proprietario.getNome();
        if (hipotecado) {
            status += " (Hipotecado)";
        }
        return String.format("[Imóvel] %s - Preço: R$ %.2f - Aluguel: R$ %.2f - %s", getNome(), precoCompra, valorAluguel, status);
    }
}
