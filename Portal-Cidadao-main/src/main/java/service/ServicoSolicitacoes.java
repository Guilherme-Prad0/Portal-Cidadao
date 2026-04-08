package service;

import model.Solicitacao;
import model.Usuario;
import model.enums.Categoria;
import model.enums.Status;
import repository.BancoSolicitacoes;
import model.HistoricoStatus;

public class ServicoSolicitacoes {

    private final BancoSolicitacoes banco;

    public ServicoSolicitacoes(BancoSolicitacoes banco) {
        this.banco = banco;
    }

    public Solicitacao criar(Categoria categoria, String descricao, String bairro, Usuario usuario) {

        if (descricao == null || descricao.isEmpty()) {
            throw new RuntimeException("Descrição obrigatória");
        }

        if (bairro == null || bairro.isEmpty()) {
            throw new RuntimeException("Bairro obrigatório");
        }

        if (usuario.isAnonimo()) {
            if (descricao.length() < 15) {
                throw new RuntimeException("Denúncia anônima precisa ser mais detalhada (mín 15 caracteres)");
            }
            System.out.println("LOG: Solicitação anônima registrada.");
        } else {
            if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
                throw new RuntimeException("Nome obrigatório");
            }
            if (usuario.getCpf() == null || usuario.getCpf().length() != 11) {
                throw new RuntimeException("CPF inválido (11 dígitos)");
            }
            if (usuario.getTelefone() == null || usuario.getTelefone().isEmpty()) {
                throw new RuntimeException("Telefone obrigatório");
            }
            if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
                throw new RuntimeException("Email inválido");
            }
        }

        Solicitacao s = new Solicitacao(categoria, descricao, bairro, usuario);
        banco.salvar(s);
        return s;
    }

    public void atualizarStatus(String protocolo, Status novoStatus, String responsavel, String comentario) {

        Solicitacao s = banco.buscar(protocolo);

        if (s == null) {
            throw new RuntimeException("Solicitação não encontrada!");
        }

        if (s.isAtrasado()) {
            if (comentario == null || comentario.trim().isEmpty()) {
                throw new RuntimeException("Solicitação atrasada! Justificativa obrigatória.");
            }
            System.out.println("Atualização com justificativa de atraso registrada.");
        }

        s.atualizarStatus(novoStatus, responsavel, comentario);
    }

    public void listar() {
        for (Solicitacao s : banco.listar()) {
            System.out.println(s);
        }
    }

    public void acompanhar(String protocolo) {
        Solicitacao s = banco.buscar(protocolo);

        if (s == null) {
            System.out.println("Protocolo não encontrado");
            return;
        }

        System.out.println(s);
        System.out.println("Histórico:");
        for (HistoricoStatus h : s.getHistorico()) {
            System.out.println(h);
        }
    }

    public void listarPorFiltro(String bairro, Categoria categoria) {

        if ((bairro == null || bairro.isEmpty()) && categoria == null) {
            listar();
            return;
        }

        for (Solicitacao s : banco.listar()) {
            boolean match = true;

            if (bairro != null && !bairro.isEmpty() && !s.getBairro().equalsIgnoreCase(bairro)) {
                match = false;
            }
            if (categoria != null && s.getCategoria() != categoria) {
                match = false;
            }
            if (match) {
                System.out.println(s);
            }
        }
    }

    public boolean protocoloValido(String protocolo) {
        if (protocolo == null || protocolo.trim().isEmpty()) return false;
        return banco.buscar(protocolo) != null;
    }
}