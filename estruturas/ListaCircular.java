package src.estruturas;

import src.modelo.Casa;

public class ListaCircular {
    private No inicio;
    private No ultimo;
    private int tamanho;

    public ListaCircular() {
        this.inicio = null;
        this.ultimo = null;
        this.tamanho = 0;
    }

    public void adicionarCasa(Casa casa) {
        No novoNo = new No(casa);
        if (inicio == null) {
            inicio = novoNo;
            ultimo = novoNo;
            novoNo.setProximo(inicio); // Circular
        } else {
            novoNo.setProximo(inicio);
            ultimo.setProximo(novoNo);
            ultimo = novoNo;
        }
        casa.setId(tamanho); // Define um ID baseado na ordem de inserção
        tamanho++;
    }

    public No getInicio() {
        return inicio;
    }

    public int getTamanho() {
        return tamanho;
    }


    public No avancar(No atual, int passos) {
        No temp = atual;
        for (int i = 0; i < passos; i++) {
            temp = temp.getProximo();
        }
        return temp;
    }
}
