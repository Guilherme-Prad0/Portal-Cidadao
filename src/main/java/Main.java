import controller.MenuController;
import repository.BancoSolicitacoes;
import service.ServicoSolicitacoes;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BancoSolicitacoes banco    = new BancoSolicitacoes();
        ServicoSolicitacoes servico = new ServicoSolicitacoes(banco);
        MenuController controller  = new MenuController(servico, sc);

        controller.iniciar();

        sc.close();
    }
}