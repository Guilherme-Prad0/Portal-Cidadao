package controller;

import model.Solicitacao;
import model.Usuario;
import model.enums.Categoria;
import model.enums.Status;
import service.ServicoSolicitacoes;

import java.util.Scanner;

public class MenuController {

    private static final String SENHA_GESTOR = "admin";

    private final ServicoSolicitacoes servico;
    private final Scanner sc;

    public MenuController(ServicoSolicitacoes servico, Scanner sc) {
        this.servico = servico;
        this.sc      = sc;
    }

    public void iniciar() {
        int opcao = -1;

        do {
            exibirMenuPrincipal();
            opcao = lerInt();

            try {
                switch (opcao) {
                    case 1: fluxoCriarSolicitacao(); break;
                    case 2: fluxoListar();           break;
                    case 3: fluxoAcompanhar();       break;
                    case 4: fluxoPainelGestor();     break;
                    case 0: System.out.println("Saindo..."); break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private void fluxoCriarSolicitacao() {
        System.out.println("\n=== NOVA SOLICITAÇÃO ===");

        Usuario usuario = lerUsuario();
        if (usuario == null) return;

        Categoria categoria = lerCategoria();
        if (categoria == null) return;

        System.out.print("Descrição: ");
        String descricao = sc.nextLine().trim();
        if (descricao.isEmpty()) { System.out.println("Descrição não pode ser vazia!"); return; }

        System.out.print("Bairro: ");
        String bairro = sc.nextLine().trim();
        if (bairro.isEmpty()) { System.out.println("Bairro obrigatório!"); return; }

        Solicitacao s = servico.criar(categoria, descricao, bairro, usuario);
        System.out.println("Protocolo gerado: " + s.getProtocolo());
    }

    private void fluxoListar() {
        System.out.println("\n=== LISTA ===");
        servico.listar();
    }

    private void fluxoAcompanhar() {
        System.out.println("\n=== ACOMPANHAMENTO ===");
        System.out.print("Protocolo: ");
        String protocolo = sc.nextLine().trim();

        if (!servico.protocoloValido(protocolo)) {
            System.out.println("Protocolo inválido!");
            return;
        }
        servico.acompanhar(protocolo);
    }

    private void fluxoPainelGestor() {
        System.out.println("Acesso ao gestor");
        System.out.print("Digite a senha: ");
        String senha = sc.nextLine();

        if (!SENHA_GESTOR.equals(senha)) {
            System.out.println("Acesso negado!");
            return;
        }

        int opcao = -1;
        do {
            exibirMenuGestor();
            opcao = lerInt();

            switch (opcao) {
                case 1: fluxoListarComFiltro(); break;
                case 2: fluxoAtualizarStatus(); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void fluxoListarComFiltro() {
        System.out.print("Bairro (ENTER = todos): ");
        String bairro = sc.nextLine().trim();

        System.out.println("Categoria (-1 = todas):");
        for (Categoria c : Categoria.values()) {
            System.out.println(c.ordinal() + " - " + c);
        }

        int index = lerInt();
        Categoria categoria = (index == -1) ? null : Categoria.values()[index];
        servico.listarPorFiltro(bairro, categoria);
    }

    private void fluxoAtualizarStatus() {
        System.out.print("Protocolo: ");
        String protocolo = sc.nextLine().trim();

        if (!servico.protocoloValido(protocolo)) {
            System.out.println("Protocolo inválido!");
            return;
        }

        System.out.println("Novo Status:");
        for (Status st : Status.values()) {
            System.out.println(st.ordinal() + " - " + st);
        }

        int index = lerInt();
        Status status = Status.values()[index];

        System.out.print("Responsável: ");
        String responsavel = sc.nextLine();

        System.out.print("Comentário: ");
        String comentario = sc.nextLine();

        servico.atualizarStatus(protocolo, status, responsavel, comentario);
    }

    private Usuario lerUsuario() {
        System.out.print("Deseja ser anônimo? (s/n): ");
        String opcao = sc.nextLine();

        if (opcao.equalsIgnoreCase("s")) {
            return new Usuario(null, null, null, null, true);
        }

        System.out.print("Nome completo: ");
        String nome = sc.nextLine().trim();
        if (nome.isEmpty()) { System.out.println("Nome obrigatório!"); return null; }

        System.out.print("CPF (somente números): ");
        String cpf = sc.nextLine().trim();
        if (cpf.length() != 11 || !cpf.chars().allMatch(Character::isDigit)) {
            System.out.println("CPF inválido! Deve conter 11 números.");
            return null;
        }

        System.out.print("Telefone (somente números): ");
        String telefone = sc.nextLine().trim();
        if ((telefone.length() < 10 || telefone.length() > 11) || !telefone.chars().allMatch(Character::isDigit)) {
            System.out.println("Telefone inválido! Use 10 ou 11 números.");
            return null;
        }

        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Email inválido!");
            return null;
        }

        return new Usuario(nome, cpf, telefone, email, false);
    }

    private Categoria lerCategoria() {
        System.out.println("Categoria:");
        for (Categoria c : Categoria.values()) {
            System.out.println(c.ordinal() + " - " + c);
        }

        while (true) {
            System.out.print("Escolha a categoria: ");
            int index = lerInt();
            if (index >= 0 && index < Categoria.values().length) {
                return Categoria.values()[index];
            }
            System.out.println("Categoria inválida!");
        }
    }

    private int lerInt() {
        while (true) {
            if (sc.hasNextInt()) {
                int valor = sc.nextInt();
                sc.nextLine();
                return valor;
            }
            System.out.println("Digite um número válido!");
            sc.nextLine();
        }
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n===== SISTEMA DE SOLICITAÇÕES =====");
        System.out.println("1 - Criar solicitação");
        System.out.println("2 - Listar solicitações");
        System.out.println("3 - Acompanhar solicitação");
        System.out.println("4 - Painel do Gestor");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private void exibirMenuGestor() {
        System.out.println("\n=== PAINEL DO GESTOR ===");
        System.out.println("1 - Listar com filtros");
        System.out.println("2 - Atualizar status");
        System.out.println("0 - Voltar");
    }
}