import Desporto.Futebol.ControloDados;

import java.io.IOException;
import java.util.Scanner;

public class Controlo {
    private ControloDados cd;
    private Scanner scan = new Scanner(System.in);
    private final String[] menuPrincipal = new String[]{
            "menuPrincipal",
            "Carregar Dados",
            "Jogar"};

    private final String[] menuCarregarDados = new String[]{
            "menuCarregarDados",
            "Carregar log",
            "Criar dados novos,",
            "Voltar"};

    private final String[] menuCriarDados = new String[]{
            "menuCriarDados",
            "Criar Jogador",
            "Criar Equipa",
            "Voltar"};

    private final String[] menuCriarJogador = new String[]{
            "menuCriarJogador",
            "Editar Nome",
            "Editar Numero",
            "Editar Equipa",
            "Editar "};


    public Controlo(){
        this.cd = new ControloDados();
    }


    public void run() throws IOException, ClassNotFoundException {
        View menu = new View(menuPrincipal);
        menu.welcome();
        menu.setHandler(1,()->carregarDados());
        //menu.setHandler(2,()->EnterState());
        menu.setHandler(3,()->terminar());
        menu.run();
    }

    public void carregarDados(){
        View menu = new View(menuCarregarDados);
        //menu.setHandler(1,()->lerFicheiro());
        menu.setHandler(2,()->criarDados());
        //menu.setHandler(3,()->run());
        menu.run();
    }

    public void criarDados(){
        View menu = new View(menuCriarDados);
        //menu.setHandler(1,()->criarJogador());
        menu.setHandler(2,()->criarEquipa());
        menu.setHandler(3,()->carregarDados());
        menu.run();
    }

    public void criarEquipa(){
        View v = new View();
        v.lerequipa();
        String nomeEquipa = scan.nextLine();

        if(!cd.existeEquipa(nomeEquipa)){
            cd.criarEquipa(nomeEquipa);
        }
        else {
            criarEquipa();
        }
    }


    public void terminar(){
        View view = new View();
        view.exitmessage();
        System.exit(0);
    }
}
