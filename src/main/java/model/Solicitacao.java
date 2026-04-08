package model;

import model.enums.Categoria;
import model.enums.Status;
import model.enums.Prioridade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Solicitacao {

    private final String protocolo;
    private final Categoria categoria;
    private final String descricao;
    private final String bairro;
    private final Usuario usuario;
    private final Prioridade prioridade;
    private final LocalDateTime dataCriacao;
    private final LocalDateTime prazo;

    private Status status;

    private final List<HistoricoStatus> historico = new ArrayList<>();

    private boolean atrasado = false;
    private String justificativaAtraso;

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public Solicitacao(Categoria categoria, String descricao, String bairro, Usuario usuario) {
        this.protocolo = gerarProtocolo();
        this.categoria = categoria;
        this.descricao = descricao;
        this.bairro = bairro;
        this.usuario = usuario;
        this.status = Status.ABERTO;
        this.prioridade = definirPrioridade();
        this.dataCriacao = LocalDateTime.now();
        this.prazo = definirPrazo();
    }

    public void atualizarStatus(Status novoStatus, String responsavel, String comentario) {
        this.status = novoStatus;
        historico.add(new HistoricoStatus(novoStatus, responsavel, comentario));
    }

    @Override
    public String toString() {
        return "\nProtocolo: " + protocolo +
                "\nCategoria: "  + categoria +
                "\nDescrição: "  + descricao +
                "\nCPF: "        + usuario.getCpfMascarado() +
                "\nBairro: "     + bairro +
                "\nPrioridade: " + prioridade +
                "\nPrazo: "      + prazo.format(fmt) +
                "\nStatus: "     + status +
                (isAtrasado() ? "\nProtocolo Atrasado!" : "");
    }

    private static int contador = 1;

    private String gerarProtocolo() {
        int ano = java.time.LocalDate.now().getYear();
        return "SOL-" + ano + "-" + String.format("%04d", contador++);
    }

    private Prioridade definirPrioridade() {

        if (categoria == Categoria.SAUDE ||
                categoria == Categoria.SEGURANCA_ESCOLAR) {
            return Prioridade.ALTA;
        }
        if (categoria == Categoria.ILUMINACAO ||
                categoria == Categoria.BURACO) {
            return Prioridade.MEDIA;
        }
        return Prioridade.BAIXA;
    }

    private LocalDateTime definirPrazo() {
        switch (prioridade){
            case ALTA: return dataCriacao.plusDays(1);
            case MEDIA: return dataCriacao.plusDays(3);
            case BAIXA: return dataCriacao.plusDays(7);
            default: return dataCriacao.plusDays(5);
        }
    }

    public boolean isAtrasado() {
        return LocalDateTime.now().isAfter(prazo) && status != Status.ENCERRADO;
    }

    public String getProtocolo()               { return protocolo; }
    public Categoria getCategoria()            { return categoria; }
    public String getBairro()                  { return bairro; }
    public List<HistoricoStatus> getHistorico() { return historico; }


    public void setAtrasado(boolean atrasado)              { this.atrasado = atrasado; }
    public void setJustificativaAtraso(String justificativa) { this.justificativaAtraso = justificativa; }


}