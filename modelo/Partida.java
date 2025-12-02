package src.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import src.estruturas.ListaCircular;
import src.estruturas.No;

public class Partida {
    private InterfaceUsuario ui;
    private Configuracoes configuracoes;
    private List<Imovel> todosImoveis;
    private List<Jogador> jogadores;
    private ListaCircular tabuleiro;
    private Baralho baralho;
    private int rodadaAtual;

    public Partida() {
        this.ui = new InterfaceUsuario();
        this.configuracoes = new Configuracoes();
        this.todosImoveis = new ArrayList<>();
        this.jogadores = new ArrayList<>();
        this.baralho = new Baralho();
        this.rodadaAtual = 0;
        this.tabuleiro = new ListaCircular();
        inicializarTabuleiroBase();
        inicializarImoveisPadrao();
    }

    private void inicializarImoveisPadrao() {
        // 10 Imóveis pré-cadastrados para facilitar o teste
        todosImoveis.add(new Imovel("Rua da Paz", 1000.00, 100.00));
        todosImoveis.add(new Imovel("Avenida Central", 1200.00, 120.00));
        todosImoveis.add(new Imovel("Praça da Liberdade", 1400.00, 140.00));
        todosImoveis.add(new Imovel("Bairro Nobre", 1600.00, 160.00));
        todosImoveis.add(new Imovel("Condomínio Sol", 1800.00, 180.00));
        todosImoveis.add(new Imovel("Praia do Forte", 2000.00, 200.00));
        todosImoveis.add(new Imovel("Serra Verde", 2200.00, 220.00));
        todosImoveis.add(new Imovel("Vale dos Sonhos", 2400.00, 240.00));
        todosImoveis.add(new Imovel("Ilha Paradisíaca", 2600.00, 260.00));
        todosImoveis.add(new Imovel("Metrópole", 2800.00, 280.00));
        ui.exibirMensagem(String.format(">> %d imóveis padrão carregados para a partida.", todosImoveis.size()));
    }

    public InterfaceUsuario getUi() { return ui; }
    public Configuracoes getConfiguracoes() { return configuracoes; }
    public List<Jogador> getJogadores() { return jogadores; }
    public ListaCircular getTabuleiro() { return tabuleiro; }

    private void inicializarTabuleiroBase() {
        // Casas base que não são Imóveis (para garantir que a ListaCircular não seja nula antes do CRUD)
        tabuleiro.adicionarCasa(new CasaConcreta("Ponto de Partida", "Início"));
        tabuleiro.adicionarCasa(new CasaConcreta("Imposto de Renda", "Imposto"));
        tabuleiro.adicionarCasa(new CasaConcreta("Sorte ou Revés", "Sorte/Revés"));
        tabuleiro.adicionarCasa(new CasaConcreta("Restituição", "Restituição"));
        tabuleiro.adicionarCasa(new CasaConcreta("Prisão", "Prisão"));
    }

    // --- Menu Principal ---
    public void menuPrincipal() {
        int opcao = -1;
        while (opcao != 0) {
            ui.exibirMensagem("\n=========================================");
            ui.exibirMensagem("=== SIMULADOR DE JOGO DE TABULEIRO    ===");
            ui.exibirMensagem("=========================================");
            ui.exibirMensagem("Seja bem-vindo! Antes de começar, vamos configurar a partida.");
            ui.exibirMensagem("--- MENU DE CONFIGURAÇÃO ---");
            ui.exibirMensagem("1. Gerenciar Jogadores");
            ui.exibirMensagem("2. Gerenciar Imóveis");
            ui.exibirMensagem("3. Definir Configurações da Partida");
            ui.exibirMensagem("4. Iniciar Jogo");
            ui.exibirMensagem("0. Sair do Programa");
            
            opcao = ui.lerInteiro(">> Escolha uma opção");

            switch (opcao) {
                case 1:
                    menuGerenciarJogadores();
                    break;
                case 2:
                    menuGerenciarImoveis();
                    break;
                case 3:
                    menuConfiguracoesPartida();
                    break;
                case 4:
                    iniciarJogo();
                    opcao = 0; // Sai do loop de configuração após iniciar o jogo
                    break;
                case 0:
                    ui.exibirMensagem("Saindo do programa. Até logo!");
                    break;
                default:
                    ui.exibirMensagem("Opção inválida. Tente novamente.");
            }
        }
    }

    // --- Métodos de Gerenciamento (CRUD) ---

    private void menuGerenciarJogadores() {
        int opcao = -1;
        while (opcao != 4) {
            ui.exibirMensagem("\n--- Menu de Jogadores ---");
            ui.exibirMensagem(String.format("(Atualmente: %d/%d jogadores cadastrados)", jogadores.size(), 6));
            ui.exibirMensagem("1. Cadastrar Novo Jogador");
            ui.exibirMensagem("2. Listar Jogadores Cadastrados");
            ui.exibirMensagem("3. Remover Jogador");
            ui.exibirMensagem("4. Voltar ao Menu Principal");
            
            opcao = ui.lerInteiro(">> Escolha uma opção");

            switch (opcao) {
                case 1:
                    cadastrarJogador();
                    break;
                case 2:
                    listarJogadores();
                    break;
                case 3:
                    removerJogador();
                    break;
                case 4:
                    ui.exibirMensagem("Voltando ao Menu Principal...");
                    break;
                default:
                    ui.exibirMensagem("Opção inválida. Tente novamente.");
            }
        }
    }

    private void cadastrarJogador() {
        if (jogadores.size() >= 6) {
            ui.exibirMensagem("Limite máximo de 6 jogadores atingido.");
            return;
        }
        String nome = ui.lerString("Digite o nome do novo jogador");
        Jogador novoJogador = new Jogador(nome, configuracoes.getSaldoInicial());
        jogadores.add(novoJogador);
        ui.exibirMensagem(String.format("Jogador '%s' cadastrado com sucesso! Saldo inicial: R$ %.2f.", nome, configuracoes.getSaldoInicial()));
    }

    private void listarJogadores() {
        if (jogadores.isEmpty()) {
            ui.exibirMensagem("Nenhum jogador cadastrado.");
            return;
        }
        ui.exibirMensagem("\n--- Jogadores Cadastrados ---");
        for (int i = 0; i < jogadores.size(); i++) {
            Jogador j = jogadores.get(i);
            ui.exibirMensagem(String.format("%d. %s", i + 1, j.toString()));
        }
    }

    private void removerJogador() {
        if (jogadores.isEmpty()) {
            ui.exibirMensagem("Nenhum jogador para remover.");
            return;
        }
        listarJogadores();
        int indice = ui.lerInteiro("Digite o número do jogador a remover") - 1;
        if (indice >= 0 && indice < jogadores.size()) {
            Jogador removido = jogadores.remove(indice);
            ui.exibirMensagem(String.format("Jogador '%s' removido com sucesso.", removido.getNome()));
        } else {
            ui.exibirMensagem("Número de jogador inválido.");
        }
    }

    private void menuGerenciarImoveis() {
        int opcao = -1;
        while (opcao != 4) {
            ui.exibirMensagem("\n--- Menu de Imóveis ---");
            ui.exibirMensagem(String.format("(Atualmente: %d/%d imóveis cadastrados)", todosImoveis.size(), 40));
            ui.exibirMensagem("1. Cadastrar Novo Imóvel");
            ui.exibirMensagem("2. Listar Imóveis Cadastrados");
            ui.exibirMensagem("3. Remover Imóvel");
            ui.exibirMensagem("4. Voltar ao Menu Principal");
            
            opcao = ui.lerInteiro(">> Escolha uma opção");

            switch (opcao) {
                case 1:
                    cadastrarImovel();
                    break;
                case 2:
                    listarImoveis();
                    break;
                case 3:
                    removerImovel();
                    break;
                case 4:
                    ui.exibirMensagem("Voltando ao Menu Principal...");
                    break;
                default:
                    ui.exibirMensagem("Opção inválida. Tente novamente.");
            }
        }
    }

    private void cadastrarImovel() {
        if (todosImoveis.size() >= 40) {
            ui.exibirMensagem("Limite máximo de 40 imóveis atingido.");
            return;
        }
        String nome = ui.lerString("Digite o nome do novo imóvel");
        double preco = ui.lerDouble("Digite o preço de compra");
        double aluguel = ui.lerDouble("Digite o valor do aluguel");
        
        Imovel novoImovel = new Imovel(nome, preco, aluguel);
        todosImoveis.add(novoImovel);
        ui.exibirMensagem(String.format("Imóvel '%s' cadastrado com sucesso! Preço: R$ %.2f, Aluguel: R$ %.2f.", nome, preco, aluguel));
    }

    private void listarImoveis() {
        if (todosImoveis.isEmpty()) {
            ui.exibirMensagem("Nenhum imóvel cadastrado.");
            return;
        }
        ui.exibirMensagem("\n--- Imóveis Cadastrados ---");
        for (int i = 0; i < todosImoveis.size(); i++) {
            Imovel imovel = todosImoveis.get(i);
            ui.exibirMensagem(String.format("%d. %s", i + 1, imovel.toString()));
        }
    }

    private void removerImovel() {
        if (todosImoveis.isEmpty()) {
            ui.exibirMensagem("Nenhum imóvel para remover.");
            return;
        }
        listarImoveis();
        int indice = ui.lerInteiro("Digite o número do imóvel a remover") - 1;
        if (indice >= 0 && indice < todosImoveis.size()) {
            Imovel removido = todosImoveis.remove(indice);
            ui.exibirMensagem(String.format("Imóvel '%s' removido com sucesso.", removido.getNome()));
        } else {
            ui.exibirMensagem("Número de imóvel inválido.");
        }
    }

    private void menuConfiguracoesPartida() {
        int opcao = -1;
        while (opcao != 4) {
            ui.exibirMensagem("\n--- Configurações da Partida ---");
            ui.exibirMensagem(String.format("1. Definir Saldo Inicial (Atual: R$ %.2f)", configuracoes.getSaldoInicial()));
            ui.exibirMensagem(String.format("2. Definir Salário por volta (Atual: R$ %.2f)", configuracoes.getSalarioPorVolta()));
            ui.exibirMensagem(String.format("3. Determinar Número Máximo de Rodadas (Atual: %d)", configuracoes.getMaxRodadas()));
            ui.exibirMensagem("4. Voltar ao Menu Principal");
            
            opcao = ui.lerInteiro(">> Escolha uma opção");

            switch (opcao) {
                case 1:
                    double novoSaldo = ui.lerDouble("Digite o novo Saldo Inicial");
                    if (novoSaldo > 0) {
                        configuracoes.setSaldoInicial(novoSaldo);
                        ui.exibirMensagem(String.format("Saldo Inicial atualizado para R$ %.2f.", novoSaldo));
                    } else {
                        ui.exibirMensagem("O saldo inicial deve ser positivo.");
                    }
                    break;
                case 2:
                    double novoSalario = ui.lerDouble("Digite o novo Salário por volta");
                    if (novoSalario > 0) {
                        configuracoes.setSalarioPorVolta(novoSalario);
                        ui.exibirMensagem(String.format("Salário por volta atualizado para R$ %.2f.", novoSalario));
                    } else {
                        ui.exibirMensagem("O salário deve ser positivo.");
                    }
                    break;
                case 3:
                    int novasRodadas = ui.lerInteiro("Digite o novo Número Máximo de Rodadas");
                    if (novasRodadas > 0) {
                        configuracoes.setMaxRodadas(novasRodadas);
                        ui.exibirMensagem(String.format("Número Máximo de Rodadas atualizado para %d.", novasRodadas));
                    } else {
                        ui.exibirMensagem("O número de rodadas deve ser positivo.");
                    }
                    break;
                case 4:
                    ui.exibirMensagem("Voltando ao Menu Principal...");
                    break;
                default:
                    ui.exibirMensagem("Opção inválida. Tente novamente.");
            }
        }
    }

    // --- Lógica de Jogo ---

    private void construirTabuleiro() {
        // Limpa o tabuleiro base e reconstrói com os imóveis cadastrados
        tabuleiro = new ListaCircular();
        
        // 1. Adiciona a casa Início
        tabuleiro.adicionarCasa(new CasaConcreta("Ponto de Partida", "Início"));
        
        // 2. Intercala Imóveis e Casas Especiais
        int casaEspecialIndex = 0;
        
        // Casas Especiais pré-definidas (Imposto, Sorte/Revés, Restituição, Prisão)
        Casa[] casasEspeciais = {
            new CasaConcreta("Imposto de Renda", "Imposto"),
            new CasaConcreta("Sorte ou Revés", "Sorte/Revés"),
            new CasaConcreta("Restituição", "Restituição"),
            new CasaConcreta("Prisão", "Prisão")
        };

        // Adiciona os imóveis intercalados com as casas especiais
        for (int i = 0; i < todosImoveis.size(); i++) {
            tabuleiro.adicionarCasa(todosImoveis.get(i));
            
            // Adiciona uma casa especial a cada 3 imóveis (exemplo de intercalação)
            if ((i + 1) % 3 == 0 && casaEspecialIndex < casasEspeciais.length) {
                tabuleiro.adicionarCasa(casasEspeciais[casaEspecialIndex]);
                casaEspecialIndex++;
            }
        }
        
        // Adiciona as casas especiais restantes no final, se houver
        while (casaEspecialIndex < casasEspeciais.length) {
            tabuleiro.adicionarCasa(casasEspeciais[casaEspecialIndex]);
            casaEspecialIndex++;
        }
        
        ui.exibirMensagem(String.format("Tabuleiro construído com %d casas.", tabuleiro.getTamanho()));
    }

    private void iniciarJogo() {
        // Validações
        if (jogadores.size() < 2) {
            ui.exibirMensagem("O jogo não pode ser iniciado com menos de 2 jogadores.");
            return;
        }
        if (todosImoveis.size() < 10) {
            ui.exibirMensagem("O jogo não pode ser iniciado com menos de 10 imóveis cadastrados.");
            return;
        }

        // Construir o tabuleiro final
        construirTabuleiro();

        // Posicionar todos os jogadores no início
        No inicio = tabuleiro.getInicio();
        for (Jogador j : jogadores) {
            j.setPosicaoAtual(inicio);
        }

        ui.exibirMensagem("\n--- INICIANDO JOGO ---");
        ui.exibirMensagem("Validações OK. Tabuleiro construído. Jogadores posicionados.");
        ui.exibirMensagem("O jogo começou! Boa sorte!");

        // Inicia o loop principal do jogo
        loopPrincipalDoJogo();
    }

    private void loopPrincipalDoJogo() {
        while (rodadaAtual < configuracoes.getMaxRodadas() && jogadoresAtivos() > 1) {
            rodadaAtual++;
            ui.exibirMensagem("\n--- INÍCIO DA RODADA " + rodadaAtual + " ---");

            for (int i = 0; i < jogadores.size(); i++) {
                Jogador jogador = jogadores.get(i);
                if (jogador.isFalido()) continue;

                ui.exibirMensagem("\nTurno de " + jogador.getNome() + " (Saldo: R$ " + String.format("%.2f", jogador.getSaldo()) + ")");
                
                if (jogador.getRodadasNaPrisao() > 0) {
                    logicaPrisao(jogador);
                } else {
                    logicaTurnoNormal(jogador);
                }
                
                // Verifica se o jogador faliu após o turno
                if (jogador.isFalido()) {
                    ui.exibirMensagem(jogador.getNome() + " faliu e está fora do jogo!");
                }
            }
        }
        
        encerrarJogo();
    }

    private int jogadoresAtivos() {
        int ativos = 0;
        for (Jogador j : jogadores) {
            if (!j.isFalido()) {
                ativos++;
            }
        }
        return ativos;
    }

    private void logicaPrisao(Jogador jogador) {
        ui.exibirMensagem(jogador.getNome() + " está na Prisão. Tentativa " + jogador.getRodadasNaPrisao() + " de 3.");
        
        if (jogador.getRodadasNaPrisao() == 3) {
            ui.exibirMensagem(jogador.getNome() + " foi libertado por ter cumprido a pena.");
            jogador.setRodadasNaPrisao(0);
            return; // Joga normalmente no próximo turno
        }

        if (ui.confirmarAcao("Tentar tirar dados duplos para sair da Prisão?")) {
            int[] dados = lancarDados();
            if (dados[0] == dados[1]) {
                ui.exibirMensagem("Parabéns! Dados duplos! " + jogador.getNome() + " está livre!");
                jogador.setRodadasNaPrisao(0);
                moverJogador(jogador, dados[0] + dados[1], true);
            } else {
                ui.exibirMensagem("Não foi desta vez. Permanece na Prisão.");
                jogador.setRodadasNaPrisao(jogador.getRodadasNaPrisao() + 1);
            }
        } else {
            jogador.setRodadasNaPrisao(jogador.getRodadasNaPrisao() + 1);
        }
    }

    private void logicaTurnoNormal(Jogador jogador) {
        ui.lerString("Pressione ENTER para lançar os dados...");
        int[] dados = lancarDados();
        moverJogador(jogador, dados[0] + dados[1], true);
        
        // Lógica de rodada extra (se aplicável)
        if (dados[0] == dados[1]) {
            ui.exibirMensagem("Dados duplos! Você tem direito a mais um turno.");
            logicaTurnoNormal(jogador);
        }
    }

    private void encerrarJogo() {
        ui.exibirMensagem("\n=========================================");
        ui.exibirMensagem("=== FIM DE JOGO - RESULTADO FINAL     ===");
        ui.exibirMensagem("=========================================");

        // Vencedor simples (maior patrimônio)
        Jogador vencedor = null;
        double maiorPatrimonio = -1;
        
        for (Jogador j : jogadores) {
            if (j.calcularPatrimonio() > maiorPatrimonio) {
                maiorPatrimonio = j.calcularPatrimonio();
                vencedor = j;
            }
        }
        
        if (vencedor != null) {
            ui.exibirMensagem(String.format("\nO VENCEDOR é: %s com um patrimônio total de R$ %.2f!", vencedor.getNome(), vencedor.calcularPatrimonio()));
        } else {
            ui.exibirMensagem("O jogo terminou sem um vencedor claro.");
        }
    }

    /**
     * Simula o lançamento de dois dados.
     * @return Um array de 2 inteiros (resultado dos dados).
     */
    private int[] lancarDados() {
        Random random = new Random();
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        ui.exibirMensagem(String.format("Dados: %d e %d. Total: %d.", dado1, dado2, dado1 + dado2));
        return new int[]{dado1, dado2};
    }

    /**
     * Move o jogador no tabuleiro e verifica a passagem pelo Início.
     * @param jogador O jogador a ser movido.
     * @param passos O número de casas a avançar.
     * @param verificaPassagem Se deve verificar a passagem pelo Início (false para cartas).
     */
    public void moverJogador(Jogador jogador, int passos, boolean verificaPassagem) {
        No atual = jogador.getPosicaoAtual();
        No inicio = tabuleiro.getInicio();
        int casasPercorridas = 0;

        for (int i = 0; i < passos; i++) {
            atual = atual.getProximo();
            casasPercorridas++;
            
            // Verifica se passou pelo Início
            if (verificaPassagem && atual == inicio && casasPercorridas < passos) {
                jogador.receber(configuracoes.getSalarioPorVolta());
                ui.exibirMensagem(String.format("Parabéns! %s passou pelo Início e recebeu R$ %.2f de salário.", jogador.getNome(), configuracoes.getSalarioPorVolta()));
            }
        }

        jogador.setPosicaoAtual(atual);
        ui.exibirMensagem(String.format("%s avançou %d casas e parou em: %s", jogador.getNome(), passos, atual.getDado().toString()));
        
        // Interage com a casa onde parou
        atual.getDado().interagir(jogador, this);
    }

    public void aplicarCartaSorteReves(Jogador jogador) {
        Carta carta = baralho.puxarCarta();
        ui.exibirMensagem(String.format("Você tirou uma carta de Sorte/Revés: %s", carta.getDescricao()));

        switch (carta.getTipoAcao()) {
            case "GANHA_DINHEIRO":
                jogador.receber(carta.getValor());
                ui.exibirMensagem(String.format("Você recebeu R$ %.2f.", carta.getValor()));
                break;
            case "PERDE_DINHEIRO":
                if (jogador.pagar(carta.getValor())) {
                    ui.exibirMensagem(String.format("Você pagou R$ %.2f.", carta.getValor()));
                } else {
                    ui.exibirMensagem("Saldo insuficiente. Você faliu!");
                }
                break;
            case "AVANCA_CASAS":
                moverJogador(jogador, (int) carta.getValor(), false);
                break;
            case "VOLTA_CASAS":
                int casas = (int) carta.getValor();
                int tamanhoTabuleiro = tabuleiro.getTamanho();
                int passos = tamanhoTabuleiro - casas;
                moverJogador(jogador, passos, false);
                break;
            case "VAI_PARA_PRISAO":
                enviarParaPrisao(jogador);
                break;
            case "AVANCA_PARA_INICIO":
                No inicio = tabuleiro.getInicio();
                jogador.setPosicaoAtual(inicio);
                jogador.receber(configuracoes.getSalarioPorVolta());
                ui.exibirMensagem("Você avançou para o Início e recebeu seu salário!");
                break;
            case "RECEBE_DE_TODOS":
                double valorReceber = carta.getValor();
                for (Jogador outro : jogadores) {
                    if (outro != jogador && !outro.isFalido()) {
                        if (outro.pagar(valorReceber)) {
                            jogador.receber(valorReceber);
                        } else {
                            ui.exibirMensagem(String.format("%s faliu ao tentar pagar R$ %.2f para %s.", outro.getNome(), valorReceber, jogador.getNome()));
                        }
                    }
                }
                ui.exibirMensagem(String.format("Você recebeu R$ %.2f de cada jogador.", valorReceber));
                break;
            case "PAGA_PARA_TODOS":
                double valorPagar = carta.getValor();
                double totalPagar = valorPagar * (jogadores.size() - 1);
                if (jogador.pagar(totalPagar)) {
                    for (Jogador outro : jogadores) {
                        if (outro != jogador && !outro.isFalido()) {
                            outro.receber(valorPagar);
                        }
                    }
                    ui.exibirMensagem(String.format("Você pagou R$ %.2f para cada jogador.", valorPagar));
                } else {
                    ui.exibirMensagem("Saldo insuficiente. Você faliu!");
                }
                break;
            case "PAGA_POR_IMOVEL":
                double totalPagarImovel = carta.getValor() * jogador.getPropriedades().size();
                if (jogador.pagar(totalPagarImovel)) {
                    ui.exibirMensagem(String.format("Você pagou R$ %.2f de taxa por seus %d imóveis.", totalPagarImovel, jogador.getPropriedades().size()));
                } else {
                    ui.exibirMensagem("Saldo insuficiente. Você faliu!");
                }
                break;
            case "RODADA_EXTRA":
                // Lógica de rodada extra será tratada no loop principal
                ui.exibirMensagem("Você ganhou uma rodada extra! Jogue novamente.");
                break;
        }
    }

    private void enviarParaPrisao(Jogador jogador) {
        // Encontra a casa Prisão (ID 4 no tabuleiro base)
        No prisao = tabuleiro.getInicio();
        for (int i = 0; i < 4; i++) {
            prisao = prisao.getProximo();
        }
        jogador.setPosicaoAtual(prisao);
        jogador.setRodadasNaPrisao(1); // Marca que o jogador está na prisão
        ui.exibirMensagem(String.format("%s foi enviado para a Prisão!", jogador.getNome()));
    }
}
