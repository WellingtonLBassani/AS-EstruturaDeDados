package src.modelo;

public class Configuracoes {
    private double saldoInicial;
    private double salarioPorVolta;
    private int maxRodadas;

    public Configuracoes() {
        // Valores padrão
        this.saldoInicial = 25000.00;
        this.salarioPorVolta = 2000.00;
        this.maxRodadas = 50;
    }

    // --- Getters e Setters ---
    public double getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(double saldoInicial) { this.saldoInicial = saldoInicial; }

    public double getSalarioPorVolta() { return salarioPorVolta; }
    public void setSalarioPorVolta(double salarioPorVolta) { this.salarioPorVolta = salarioPorVolta; }

    public int getMaxRodadas() { return maxRodadas; }
    public void setMaxRodadas(int maxRodadas) { this.maxRodadas = maxRodadas; }
}
