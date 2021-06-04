import Desporto.Futebol.ControloDados;
import Desporto.Futebol.Equipa.Jogador.Atributos;
import Desporto.Futebol.Equipa.Jogador.Jogador;

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
            "Nome",
            "Numero",
            "Posicao",
            "Atributos",
            "Voltar"};

    public final String[] menuatributosGR = new String[]{
            "menuatributosGR",
            "Velocidade:",
            "Resistência:",
            "Destreza:",
            "Impulsão:",
            "Jogo de cabeça:",
            "Remate:",
            "Controlo de passe:",
            "Elasticidade:",
            "Reflexos:",
            "Voltar"};

    public final String[] menuatributosDF = new String[]{
            "menuatributosDF",
            "Velocidade:",
            "Resistência:",
            "Destreza:",
            "Impulsão:",
            "Jogo de cabeça:",
            "Remate:",
            "Controlo de passe:",
            "Posicionamento Defensivo:",
            "Cortes:",
            "Voltar"};

    public final String[] menuatributosMD = new String[]{
            "menuatributosDF",
            "Velocidade:",
            "Resistência:",
            "Destreza:",
            "Impulsão:",
            "Jogo de cabeça:",
            "Remate:",
            "Controlo de passe:",
            "Recuperação de Bolas:",
            "Visão de Jogo:",
            "Voltar"};


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

    public void criarJogador(){
        View jogadormenu = new View(menuCriarJogador);
        Jogador j = new Jogador();
        jogadormenu.setPreCondition(new int []{2,3},()->!j.getNome().equals(""));
        jogadormenu.setHandler(1,()->j.setNome(getNome()));
        jogadormenu.setHandler(2,()-> j.setNumero(getNumero()));
        jogadormenu.setHandler(3,()-> j.setPosicao(getPosicao()));
        jogadormenu.setHandler(4,()->j.setAtributos(getAtributos(j.getPosicao())));
    }

    public String getNome(){
        return scan.nextLine();
    }

    public int getNumero(){
        int i = scan.nextInt();
        if(i > 0 && i <= 99){
            return i;
        }
        else return getNumero();
    }

    public Atributos getAtributos(String posicao){
        if (posicao.equals("Guarda-Redes")){

        }
        else if (posicao.equals("Defesa")){

        }
        else if (posicao.equals("Medio")){

        }
        else if (posicao.equals("Avancado")){

        }
        else {

        }
    }

    public String getPosicao(){
        String posicao = scan.nextLine();
        if(posicao.equals("Defesa") || posicao.equals("Guarda-Redes") || posicao.equals("Avancado")
        || posicao.equals("Medio") || posicao.equals("Lateral")){
            return posicao;
        }
        else return getPosicao();
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
