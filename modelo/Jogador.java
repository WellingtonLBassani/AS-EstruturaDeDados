package src.modelo;

import java.util.ArrayList;
import java.util.List;
import src.estruturas.No;

public class Jogador {
    private String nome;
    private double saldo;
    private No posicaoAtual; // Nó da Lista Circular
    private List<Imovel> propriedades;
    private boolean falido;
    private int rodadasNaPrisao; // Para a lógica da Prisão

    public Jogador(String nome, double saldoInicial) {
        this.nome = nome;
        this.saldo = saldoInicial;
        this.propriedades = new ArrayList<>();
        this.falido = false;
        this.rodadasNaPrisao = 0;
    }

    public String getNome() { return nome; }
    public double getSaldo() { return saldo; }
    public No getPosicaoAtual() { return posicaoAtual; }
    public List<Imovel> getPropriedades() { return propriedades; }
    public boolean isFalido() { return falido; }
    public int getRodadasNaPrisao() { return rodadasNaPrisao; }

    public void setSaldo(double saldo) { this.saldo = saldo; }
    public void setPosicaoAtual(No posicaoAtual) { this.posicaoAtual = posicaoAtual; }
    public void setFalido(boolean falido) { this.falido = falido; }
    public void setRodadasNaPrisao(int rodadasNaPrisao) { this.rodadasNaPrisao = rodadasNaPrisao; }


    public double calcularPatrimonio() {
        double patrimonio = saldo;
        for (Imovel imovel : propriedades) {
            patrimonio += imovel.getPrecoCompra();
        }
        return patrimonio;
    }

    public void receber(double valor) {
        this.saldo += valor;
    }

    public boolean pagar(double valor) {
        if (this.saldo >= valor) {
            this.saldo -= valor;
            return true;
        } else {
            this.falido = true;
            return false;
        }
    }

    public void comprarImovel(Imovel imovel) {
        if (pagar(imovel.getPrecoCompra())) {
            imovel.setProprietario(this);
            this.propriedades.add(imovel);
        }
    }

    public void pagarAluguel(Jogador proprietario, double valor) {
        if (pagar(valor)) {
            proprietario.receber(valor);
        }
    }

    @Override
    public String toString() {
        return String.format("Jogador: %s | Saldo: R$ %.2f | Patrimônio: R$ %.2f | Propriedades: %d",
                nome, saldo, calcularPatrimonio(), propriedades.size());
    }
}
