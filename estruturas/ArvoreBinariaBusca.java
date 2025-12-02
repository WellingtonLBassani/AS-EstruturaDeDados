package src.estruturas;

import java.util.ArrayList;
import java.util.List;
import src.modelo.Jogador;

public class ArvoreBinariaBusca {
    private NoArvore raiz;

    private static class NoArvore {
        Jogador jogador;
        NoArvore esquerda;
        NoArvore direita;

        public NoArvore(Jogador jogador) {
            this.jogador = jogador;
            this.esquerda = null;
            this.direita = null;
        }
    }

    public void inserir(Jogador jogador) {
        raiz = inserirRecursivo(raiz, jogador);
    }

    private NoArvore inserirRecursivo(NoArvore raiz, Jogador jogador) {
        if (raiz == null) {
            return new NoArvore(jogador);
        }

        if (jogador.calcularPatrimonio() < raiz.jogador.calcularPatrimonio()) {
            raiz.esquerda = inserirRecursivo(raiz.esquerda, jogador);
        } else {
            raiz.direita = inserirRecursivo(raiz.direita, jogador);
        }

        return raiz;
    }


    public List<Jogador> getRanking() {
        List<Jogador> ranking = new ArrayList<>();
        percursoInverso(raiz, ranking);
        return ranking;
    }

    private void percursoInverso(NoArvore no, List<Jogador> ranking) {
        if (no != null) {
            percursoInverso(no.direita, ranking);
            ranking.add(no.jogador);
            percursoInverso(no.esquerda, ranking);
        }
    }

    public void limpar() {
        this.raiz = null;
    }
}
