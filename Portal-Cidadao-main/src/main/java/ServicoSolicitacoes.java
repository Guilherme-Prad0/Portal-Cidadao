public class ServicoSolicitacoes {

    BancoSolicitacoes banco;

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
        }
        else {

            if (usuario.nome == null || usuario.nome.isEmpty()) {
                throw new RuntimeException("Nome obrigatório");
            }

            if (usuario.cpf == null || usuario.cpf.length() != 11) {
                throw new RuntimeException("CPF inválido (11 dígitos)");
            }

            if (usuario.telefone == null || usuario.telefone.isEmpty()) {
                throw new RuntimeException("Telefone obrigatório");
            }

            if (usuario.email == null || !usuario.email.contains("@")) {
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

        s.status = novoStatus;

        HistoricoStatus hist = new HistoricoStatus(novoStatus, responsavel, comentario);
        s.historico.add(hist);
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
        for (HistoricoStatus h : s.historico) {
            System.out.println(h);
        }
    }

    public void justificarAtraso(String protocolo, String justificativa) {
        Solicitacao s = banco.buscar(protocolo);

        if (s != null && s.atrasado) {
            s.justificativaAtraso = justificativa;
        }
    }

    public void listarPorFiltro(String bairro, Categoria categoria) {

        if ((bairro == null || bairro.isEmpty()) && categoria == null) {
            listar();
            return;
        }

        for (Solicitacao s : banco.listar()) {

            boolean match = true;

            if (bairro != null && !bairro.isEmpty() && !s.bairro.equalsIgnoreCase(bairro)) {
                match = false;
            }

            if (categoria != null && s.categoria != categoria) {
                match = false;
            }

            if (match) {
                System.out.println(s);
            }
        }
    }

    public boolean protocoloValido(String protocolo) {

        if (protocolo == null || protocolo.trim().isEmpty()) {
            return false;
        }

        return banco.buscar(protocolo) != null;
    }
}