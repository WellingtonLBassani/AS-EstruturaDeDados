package src.modelo;

import src.estruturas.Pilha;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {
    private Pilha pilhaCartas;
    private List<Carta> cartasDescartadas;
    private final int MIN_CARTAS = 16;

    public Baralho() {
        this.pilhaCartas = new Pilha();
        this.cartasDescartadas = new ArrayList<>();
        inicializarBaralho();
    }

    private void inicializarBaralho() {
        // Criar no mínimo 16 cartas variadas
        List<Carta> todasCartas = new ArrayList<>();
        
        // Ganha Dinheiro
        todasCartas.add(new Carta("Você ganhou na loteria! Receba R$ 5.000,00.", "GANHA_DINHEIRO", 5000.0));
        todasCartas.add(new Carta("Seu investimento rendeu. Receba R$ 2.000,00.", "GANHA_DINHEIRO", 2000.0));
        todasCartas.add(new Carta("Aniversário! Receba R$ 500,00 de cada jogador.", "RECEBE_DE_TODOS", 500.0));
        
        // Perde Dinheiro
        todasCartas.add(new Carta("Pague taxa de manutenção. Pague R$ 1.500,00 ao banco.", "PERDE_DINHEIRO", 1500.0));
        todasCartas.add(new Carta("Multa por excesso de velocidade. Pague R$ 800,00 ao banco.", "PERDE_DINHEIRO", 800.0));
        todasCartas.add(new Carta("Pague taxa de seguro. Pague R$ 200,00 ao banco.", "PERDE_DINHEIRO", 200.0));
        
        // Movimentação
        todasCartas.add(new Carta("Avance para o Início. Receba salário ao passar.", "AVANCA_PARA_INICIO", 0.0));
        todasCartas.add(new Carta("Avance 3 casas.", "AVANCA_CASAS", 3.0));
        todasCartas.add(new Carta("Volte 2 casas.", "VOLTA_CASAS", 2.0));
        
        // Prisão (Requisito obrigatório)
        todasCartas.add(new Carta("Vá para a Prisão. Não passe pelo Início.", "VAI_PARA_PRISAO", 0.0));
        
        // Outras
        todasCartas.add(new Carta("Seu imóvel foi valorizado. Receba R$ 1.000,00.", "GANHA_DINHEIRO", 1000.0));
        todasCartas.add(new Carta("Pague taxa de rua. Pague R$ 100,00 por imóvel.", "PAGA_POR_IMOVEL", 100.0));
        todasCartas.add(new Carta("Ganhe uma rodada extra.", "RODADA_EXTRA", 0.0));
        todasCartas.add(new Carta("Pague R$ 300,00 para cada jogador.", "PAGA_PARA_TODOS", 300.0));
        todasCartas.add(new Carta("Receba R$ 4.000,00 de herança.", "GANHA_DINHEIRO", 4000.0));
        todasCartas.add(new Carta("Seu carro quebrou. Pague R$ 1.200,00.", "PERDE_DINHEIRO", 1200.0));


        // Embaralha e empilha
        Collections.shuffle(todasCartas);
        for (Carta carta : todasCartas) {
            pilhaCartas.empilhar(carta);
        }
    }

    public Carta puxarCarta() {
        if (pilhaCartas.estaVazia()) {
            reembaralhar();
        }
        Carta carta = pilhaCartas.desempilhar();
        cartasDescartadas.add(carta);
        return carta;
    }

    private void reembaralhar() {
        // Move as cartas descartadas de volta para a pilha
        for (Carta carta : cartasDescartadas) {
            pilhaCartas.empilhar(carta);
        }
        cartasDescartadas.clear();
        pilhaCartas.embaralhar();
        System.out.println(">> O baralho de Sorte/Revés foi reembaralhado!");
    }

    public int getMinCartas() {
        return MIN_CARTAS;
    }
}
