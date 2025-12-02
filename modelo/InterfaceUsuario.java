package src.modelo;

import java.util.Scanner;

public class InterfaceUsuario {
    private Scanner scanner;

    public InterfaceUsuario() {
        this.scanner = new Scanner(System.in);
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(">> " + mensagem);
    }

    public boolean confirmarAcao(String pergunta) {
        System.out.print(">> " + pergunta + " (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        return resposta.equals("S");
    }

    public String lerString(String prompt) {
        System.out.print(">> " + prompt + ": ");
        return scanner.nextLine().trim();
    }

    public int lerInteiro(String prompt) {
        while (true) {
            try {
                System.out.print(">> " + prompt + ": ");
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                exibirMensagem("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }

    public double lerDouble(String prompt) {
        while (true) {
            try {
                System.out.print(">> " + prompt + ": ");
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                exibirMensagem("Entrada inválida. Por favor, digite um número decimal (use ponto ou vírgula).");
            }
        }
    }
}
