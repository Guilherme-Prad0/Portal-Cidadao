import java.util.*;

public class BancoSolicitacoes {

    List<Solicitacao> lista = new ArrayList<>();

    public void salvar(Solicitacao s) {
        lista.add(s);
    }

    public List<Solicitacao> listar() {
        return lista;
    }

    public Solicitacao buscar(String protocolo) {
        for (Solicitacao s : lista) {
            if (s.protocolo.equals(protocolo)) {
                return s;
            }
        }
        return null;
    }
}