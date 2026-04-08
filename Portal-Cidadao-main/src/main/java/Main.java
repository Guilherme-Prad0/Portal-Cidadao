import model.Solicitacao;
import model.Usuario;
import model.enums.Categoria;
import model.enums.Status;
import repository.BancoSolicitacoes;
import service.ServicoSolicitacoes;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BancoSolicitacoes banco = new BancoSolicitacoes();
        ServicoSolicitacoes service = new ServicoSolicitacoes(banco);

        int opcao = -1;

        do {
            System.out.println("\n===== SISTEMA DE SOLICITAÇÕES =====");
            System.out.println("1 - Criar solicitação");
            System.out.println("2 - Listar solicitações");
            System.out.println("3 - Acompanhar solicitação");
            System.out.println("4 - Painel do Gestor");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            if (!sc.hasNextInt()) {
                System.out.println("Digite um número válido!");
                sc.nextLine();
                continue;
            }

            opcao = sc.nextInt();
            sc.nextLine();

            try {
                switch (opcao) {

                    // ================= CRIAR =================
                    case 1:
                        System.out.println("\n=== NOVA SOLICITAÇÃO ===");

                        System.out.print("Deseja ser anônimo? (s/n): ");
                        String opAnon = sc.nextLine();

                        Usuario user;

                        if (opAnon.equalsIgnoreCase("s")) {
                            user = new Usuario(null, null, null, null, true);
                        } else {

                            System.out.print("Nome completo: ");
                            String nome = sc.nextLine().trim();
                            if (nome.isEmpty()) {
                                System.out.println("Nome obrigatório!");
                                break;
                            }

                            // ===== CPF =====
                            System.out.print("CPF (somente números): ");
                            String cpf = sc.nextLine().trim();

                            boolean cpfValido = true;

                            if (cpf.length() != 11) {
                                cpfValido = false;
                            }

                            for (int i = 0; i < cpf.length(); i++) {
                                if (!Character.isDigit(cpf.charAt(i))) {
                                    cpfValido = false;
                                    break;
                                }
                            }

                            if (!cpfValido) {
                                System.out.println("CPF inválido! Deve conter 11 números.");
                                break;
                            }

                            // ===== TELEFONE =====
                            System.out.print("Telefone (somente números): ");
                            String telefone = sc.nextLine().trim();

                            boolean telefoneValido = true;

                            if (telefone.length() < 10 || telefone.length() > 11) {
                                telefoneValido = false;
                            }

                            for (int i = 0; i < telefone.length(); i++) {
                                if (!Character.isDigit(telefone.charAt(i))) {
                                    telefoneValido = false;
                                    break;
                                }
                            }

                            if (!telefoneValido) {
                                System.out.println("Telefone inválido! Use 10 ou 11 números.");
                                break;
                            }

                            // ===== EMAIL =====
                            System.out.print("Email: ");
                            String email = sc.nextLine().trim();

                            if (!email.contains("@") || !email.contains(".")) {
                                System.out.println("Email inválido!");
                                break;
                            }

                            user = new Usuario(nome, cpf, telefone, email, false);
                        }

                        // ===== CATEGORIA =====
                        System.out.println("model.enums.Categoria:");
                        for (Categoria c : Categoria.values()) {
                            System.out.println(c.ordinal() + " - " + c);
                        }

                        int cat;

                        while (true) {
                            System.out.print("Escolha a categoria: ");

                            if (!sc.hasNextInt()) {
                                System.out.println("Digite um número válido!");
                                sc.nextLine();
                                continue;
                            }

                            cat = sc.nextInt();
                            sc.nextLine();

                            if (cat >= 0 && cat < Categoria.values().length) break;

                            System.out.println("model.enums.Categoria inválida!");
                        }

                        Categoria categoria = Categoria.values()[cat];

                        // ===== DESCRIÇÃO =====
                        System.out.print("Descrição: ");
                        String desc = sc.nextLine().trim();

                        if (desc.isEmpty()) {
                            System.out.println("Descrição não pode ser vazia!");
                            break;
                        }

                        // ===== BAIRRO =====
                        System.out.print("Bairro: ");
                        String bairro = sc.nextLine().trim();

                        if (bairro.isEmpty()) {
                            System.out.println("Bairro obrigatório!");
                            break;
                        }

                        Solicitacao s = service.criar(categoria, desc, bairro, user);

                        System.out.println("Protocolo gerado: " + s.protocolo);
                        break;

                    // ================= LISTAR =================
                    case 2:
                        System.out.println("\n=== LISTA ===");
                        service.listar();
                        break;

                    // ================= ACOMPANHAR =================
                    case 3:
                        System.out.println("\n=== ACOMPANHAMENTO ===");

                        System.out.print("Protocolo: ");
                        String protocoloBusca = sc.nextLine().trim();

                        if (!service.protocoloValido(protocoloBusca)) {
                            System.out.println("Protocolo inválido!");
                            break;
                        }

                        service.acompanhar(protocoloBusca);
                        break;

                    // ================= GESTOR =================
                    case 4:
                        System.out.println("🔐 Acesso ao gestor (senha: admin)");
                        System.out.print("Digite a senha: ");
                        String senha = sc.nextLine();

                        if (!senha.equals("admin")) {
                            System.out.println("Acesso negado!");
                            break;
                        }

                        int opGestor = -1;

                        do {
                            System.out.println("\n=== PAINEL DO GESTOR ===");
                            System.out.println("1 - Listar com filtros");
                            System.out.println("2 - Atualizar status");
                            System.out.println("0 - Voltar");

                            if (!sc.hasNextInt()) {
                                System.out.println("Digite um número válido!");
                                sc.nextLine();
                                continue;
                            }

                            opGestor = sc.nextInt();
                            sc.nextLine();

                            switch (opGestor) {

                                case 1:
                                    System.out.print("Bairro (ENTER = todos): ");
                                    String b = sc.nextLine().trim();

                                    System.out.println("model.enums.Categoria (-1 = todas): ");
                                    for (Categoria c : Categoria.values()) {
                                        System.out.println(c.ordinal() + " - " + c);
                                    }

                                    int cIndex = sc.nextInt();
                                    sc.nextLine();

                                    Categoria catFiltro = (cIndex == -1) ? null : Categoria.values()[cIndex];

                                    service.listarPorFiltro(b, catFiltro);
                                    break;

                                case 2:
                                    System.out.print("Protocolo: ");
                                    String prot = sc.nextLine().trim();

                                    if (!service.protocoloValido(prot)) {
                                        System.out.println("Protocolo inválido!");
                                        break;
                                    }

                                    System.out.println("Novo model.enums.Status:");
                                    for (Status st : Status.values()) {
                                        System.out.println(st.ordinal() + " - " + st);
                                    }

                                    int stIndex = sc.nextInt();
                                    sc.nextLine();

                                    Status status = Status.values()[stIndex];

                                    System.out.print("Responsável: ");
                                    String resp = sc.nextLine();

                                    System.out.print("Comentário: ");
                                    String comentario = sc.nextLine();

                                    service.atualizarStatus(prot, status, resp, comentario);
                                    break;
                            }

                        } while (opGestor != 0);

                        break;

                    case 0:
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);

        sc.close();
    }
}