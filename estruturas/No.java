package src.estruturas;

import src.modelo.Casa;

public class No {
    private Casa dado;
    private No proximo;

    public No(Casa dado) {
        this.dado = dado;
        this.proximo = null;
    }

    public Casa getDado() {
        return dado;
    }

    public void setDado(Casa dado) {
        this.dado = dado;
    }

    public No getProximo() {
        return proximo;
    }

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }
}
