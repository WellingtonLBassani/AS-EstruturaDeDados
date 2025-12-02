package src.estruturas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import src.modelo.Carta;

public class Pilha {
    private List<Carta> cartas;

    public Pilha() {
        this.cartas = new ArrayList<>();
    }

    public void empilhar(Carta carta) {
        cartas.add(carta);
    }

    public Carta desempilhar() {
        if (estaVazia()) {
            return null;
        }
        return cartas.remove(cartas.size() - 1);
    }

    public Carta topo() {
        if (estaVazia()) {
            return null;
        }
        return cartas.get(cartas.size() - 1);
    }

    public boolean estaVazia() {
        return cartas.isEmpty();
    }

    public int tamanho() {
        return cartas.size();
    }

    public void embaralhar() {
        Collections.shuffle(cartas);
    }
}
