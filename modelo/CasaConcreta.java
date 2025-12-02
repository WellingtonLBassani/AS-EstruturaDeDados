package src.modelo;

// Classe para casas simples (Início, Imposto, Restituição, Prisão, Sorte/Revés)

public class CasaConcreta extends Casa {
    public CasaConcreta(String nome, String tipo) {
        super(nome, tipo);
    }

    @Override
    public void interagir(Jogador jogador, Partida partida) {
        InterfaceUsuario ui = partida.getUi();
        
        switch (getTipo()) {
            case "Início":
                ui.exibirMensagem(String.format("Você parou na casa %s. Bom começo!", getNome()));
                break;
            case "Imposto":
                // Pagar 5% sobre o patrimônio total
                double patrimonio = jogador.calcularPatrimonio();
                double imposto = patrimonio * 0.05;
                if (jogador.pagar(imposto)) {
                    ui.exibirMensagem(String.format("Você parou na casa Imposto. Pagou R$ %.2f (5%% do seu patrimônio total de R$ %.2f) ao banco.", imposto, patrimonio));
                } else {
                    ui.exibirMensagem(String.format("Você parou na casa Imposto. Saldo insuficiente para pagar R$ %.2f. Você faliu!", imposto));
                    // Lógica de falência será tratada em Jogador.pagar()
                }
                break;
            case "Restituição":
                // Receber 10% do valor do salário
                double salario = partida.getConfiguracoes().getSalarioPorVolta();
                double restituicao = salario * 0.10;
                jogador.receber(restituicao);
                ui.exibirMensagem(String.format("Você parou na casa Restituição. Recebeu R$ %.2f (10%% do salário de R$ %.2f) do banco.", restituicao, salario));
                break;
            case "Prisão":
                // Ao parar na Prisão, o jogador não é enviado para lá, apenas visita.
                ui.exibirMensagem(String.format("Você está apenas visitando a %s. Que sorte!", getNome()));
                break;
            case "Sorte/Revés":
                partida.aplicarCartaSorteReves(jogador);
                break;
            default:
                ui.exibirMensagem(String.format("Você parou na casa %s. Nenhuma ação especial.", getNome()));
                break;
        }
    }
}
