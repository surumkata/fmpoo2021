import Desporto.Futebol.ControloDados;
import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.*;
import Desporto.Futebol.Equipa.JogadorInvalidoException;
import Desporto.Futebol.Equipa.Tatica;
import Desporto.Futebol.Equipa.TitularesFullException;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Controlo {
    private ControloDados cd;
    private Scanner scan = new Scanner(System.in);
    private final String[] menuPrincipal = new String[]{
            "Menu Principal",
            "Alterar Dados",
            "Simular uma Partida"};

    private final String[] menuCarregarDados = new String[]{
            "Menu Dados",
            "Carregar dados",
            "Criar dados",
            "Editar dados"};

    private final String[] menuCriarDados = new String[]{
            "Criar Dados",
            "Criar Jogador",
            "Criar Equipa"};

    private final String[] menuCriarEquipa = new String[]{
            "Criar Equipa",
            "Nome",
            "Tatica",
            "Selecionar Titulares"};

    private final String[] menuCriarJogador = new String[]{
            "menuCriarJogador",
            "Nome",
            "Numero",
            "Posicao",
            "Atributos",
            "Equipa"};

    private final String[] menuPosicao = new String[]{
            "Posiçoes",
            "Guarda-Redes",
            "Defesa",
            "Lateral",
            "Medio",
            "Avancado"};


    private final String[] menuEscolheTatica = new String[]{
            "menuEscolheTatica",
            "4-4-2 (1GR|2LT|2DF|4MD|2AV)",
            "4-3-3 (1GR|2LT|2DF|3MD|3AV)",
            "3-5-2 (1GR|2LT|3DF|3MD|2AV)",
            "3-6-1 (1GR|2LT|3DF|4MD|1AV)",
            "5-4-1 (1GR|4LT|3DF|2MD|1AV)",
            "5-3-2 (1GR|2LT|3DF|3MD|2AV)"};

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
            "Voltar e Gravar"};

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
            "Voltar e Gravar"};

    public final String[] menuatributosMD = new String[]{
            "menuatributosMD",
            "Velocidade:",
            "Resistência:",
            "Destreza:",
            "Impulsão:",
            "Jogo de cabeça:",
            "Remate:",
            "Controlo de passe:",
            "Recuperação de Bolas:",
            "Visão de Jogo:",
            "Voltar e Gravar"};

    public final String[] menuatributosLT = new String[]{
            "menuatributosLT",
            "Velocidade:",
            "Resistência:",
            "Destreza:",
            "Impulsão:",
            "Jogo de cabeça:",
            "Remate:",
            "Controlo de passe:",
            "Precisão de cruzamentos:",
            "Drible:",
            "Voltar e Gravar"};

    public final String[] menuatributosAV = new String[]{
            "menuatributosAV",
            "Velocidade:",
            "Resistência:",
            "Destreza:",
            "Impulsão:",
            "Jogo de cabeça:",
            "Remate:",
            "Controlo de passe:",
            "Desmarcação:",
            "Penáltis:",
            "Voltar e Gravar"};


    public Controlo() {
        this.cd = new ControloDados();
    }


    public void run() throws IOException, ClassNotFoundException {
        View menu = new View(menuPrincipal);
        menu.welcome();
        menu.setPreCondition(2,()->cd.simulacaoPossivel());
        menu.setHandler(1, this::carregarDados);
        //menu.setHandler(2,()->EnterState());
        menu.setHandler(3, this::terminar);
        menu.run();
    }

    public void carregarDados(){
        View menu = new View(menuCarregarDados);
        //menu.setHandler(1,()->lerFicheiro());
        menu.setPreCondition(3,()->cd.existeEquipas());
        menu.setHandler(2, this::criarDados);
        menu.setHandler(3, this::editarDados);
        menu.run();
    }

    public void editarDados(){
        System.out.println("Para ja nao da amg\n");
    }

    public void criarDados(){
        View menu = new View(menuCriarDados);
        menu.setHandler(1, this::criarJogador);
        menu.setHandler(2, this::criarEquipa);
        menu.run();
    }

    public void criarJogador(){
        View jogadormenu = new View(menuCriarJogador);
        Jogador j = new Jogador();
        jogadormenu.setPreCondition(new int []{2,3,4},()->!j.getNome().equals(""));
        jogadormenu.setPreCondition(4,()->!j.getPosicao().equals(""));
        jogadormenu.setPreCondition(5,()->!j.getPosicao().equals("") && j.getAtributos().overall() != 0);
        jogadormenu.setHandler(1,()->j.setNome(getNome()));
        jogadormenu.setHandler(2,()-> j.setNumero(getNumero()));
        jogadormenu.setHandler(3,()-> j.setPosicao(escolhePosicao()));
        jogadormenu.setHandler(4,()->j.setAtributos(getAtributos(j.getPosicao())));
        jogadormenu.setHandler(5,()->colocaJogadarNaEquipa(j,jogadormenu));
        jogadormenu.run();
    }

    public void colocaJogadarNaEquipa (Jogador j, View v){
        System.out.println("Nome da equipa: ");
        String nome = scan.nextLine();
        while(nome.equals("")){
            nome = scan.nextLine();
        }
        cd.colocaJogador(j,nome);
        v.stop();
    }

    public void criarEquipa(){
        View equipamenu = new View(menuCriarEquipa);
        EquipaFutebol e = new EquipaFutebol();
        equipamenu.setPreCondition(new int []{2,3},()->!e.getNome().equals("") && e.getPlantel().getTitulares().size() == 11);
        equipamenu.setHandler(1, () -> e.setNome(nomeEquipa()));
        equipamenu.setHandler(2, () -> e.setTatica(escolheTatica()));
        //equipamenu.setHandler(3, () -> e.setTatica(escolheTatica())); //escolher titulares
        equipamenu.run();
        cd.criarEquipa(e.getNome());
    }

    public String escolhePosicao(){
        View posicaomenu = new View(menuPosicao);
        String [] posicoes = {"","Guarda-Redes", "Defesa", "Lateral", "Medio","Avancado"};

        AtomicInteger x = new AtomicInteger(0);
        posicaomenu.setHandler(1, ()->x.set(auxEscolhe(1,posicaomenu)));
        posicaomenu.setHandler(2, ()->x.set(auxEscolhe(2,posicaomenu)));
        posicaomenu.setHandler(3, ()->x.set(auxEscolhe(3,posicaomenu)));
        posicaomenu.setHandler(4, ()->x.set(auxEscolhe(4,posicaomenu)));
        posicaomenu.setHandler(5, ()->x.set(auxEscolhe(5,posicaomenu)));
        posicaomenu.setHandler(6, ()->x.set(auxEscolhe(6,posicaomenu)));
        posicaomenu.run();
        return posicoes[x.intValue()];
    }


    public Tatica escolheTatica(){
        View taticamenu = new View(menuEscolheTatica);
        Tatica[] taticas = {new Tatica(1,2,2,4,2),
                            new Tatica(1,2,2,3,3),
                            new Tatica(1,3,2,3,2),
                            new Tatica(1,3,2,4,1),
                            new Tatica(1,3,4,2,1),
                            new Tatica(1,3,2,3,2)};

        AtomicInteger x = new AtomicInteger();
        taticamenu.setHandler(1, ()->x.set(auxEscolhe(0,taticamenu)));
        taticamenu.setHandler(2, ()->x.set(auxEscolhe(1,taticamenu)));
        taticamenu.setHandler(3, ()->x.set(auxEscolhe(2,taticamenu)));
        taticamenu.setHandler(4, ()->x.set(auxEscolhe(3,taticamenu)));
        taticamenu.setHandler(5, ()->x.set(auxEscolhe(4,taticamenu)));
        taticamenu.setHandler(6, ()->x.set(auxEscolhe(5,taticamenu)));
        taticamenu.run();
        return taticas[x.intValue()];
    }

    public int auxEscolhe (int i, View v){
        v.stop();
        return i;
    }

    public String nomeEquipa(){
        String nome = scan.nextLine();
        if(!cd.existeEquipa(nome))
            return nome;
        else {
            System.out.println("Esse nome já pertence a uma equipa, escolhe outro.\n");
            return nomeEquipa();
        }
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

    public int scanAtributo(String atributo){
        View v = new View();
        v.atributosmessage(atributo);
        int i = this.scan.nextInt();
        if(i >= 0 && i <= 99)
            return i;
        else {
            v.atributosmessagerror();
            return scanAtributo(atributo);
        }
    }

    public Atributos getAtributosGR(){
        View menu = new View(menuatributosGR);
        AtributosGR atr = new AtributosGR();
        menu.setHandler(1,()->atr.setVelocidade(scanAtributo("Velocidade")));
        menu.setHandler(2,()->atr.setResistencia(scanAtributo("Resistência")));
        menu.setHandler(3,()->atr.setDestreza(scanAtributo("Destreza")));
        menu.setHandler(4,()->atr.setImpulsao(scanAtributo("Impulsão")));
        menu.setHandler(5,()->atr.setJogoDeCabeca(scanAtributo("Jogo de Cabeça")));
        menu.setHandler(6,()->atr.setRemate(scanAtributo("Remate")));
        menu.setHandler(7,()->atr.setControloDePasse(scanAtributo("Controlo de Passe")));
        menu.setHandler(8,()->atr.setReflexos(scanAtributo("Reflexos")));
        menu.setHandler(9,()->atr.setElasticidade(scanAtributo("Elastecidade")));
        menu.setHandler(10, menu::stop);
        menu.run();
        return atr;
    }

    public Atributos getAtributosDF(){
        View menu = new View(menuatributosDF);
        AtributosDefesa atr = new AtributosDefesa();
        menu.setHandler(1,()->atr.setVelocidade(scanAtributo("Velocidade")));
        menu.setHandler(2,()->atr.setResistencia(scanAtributo("Resistência")));
        menu.setHandler(3,()->atr.setDestreza(scanAtributo("Destreza")));
        menu.setHandler(4,()->atr.setImpulsao(scanAtributo("Impulsão")));
        menu.setHandler(5,()->atr.setJogoDeCabeca(scanAtributo("Jogo de Cabeça")));
        menu.setHandler(6,()->atr.setRemate(scanAtributo("Remate")));
        menu.setHandler(7,()->atr.setControloDePasse(scanAtributo("Controlo de Passe")));
        menu.setHandler(8,()->atr.setCortes(scanAtributo("Cortes")));
        menu.setHandler(9,()->atr.setPosicionamentoDefensivo(scanAtributo("Posicionamento Defensivo")));
        menu.setHandler(10,()->menu.stop());
        menu.run();
        return atr;
    }

    public Atributos getAtributosMD(){
        View menu = new View(menuatributosMD);
        AtributosMedio atr = new AtributosMedio();
        menu.setHandler(1,()->atr.setVelocidade(scanAtributo("Velocidade")));
        menu.setHandler(2,()->atr.setResistencia(scanAtributo("Resistência")));
        menu.setHandler(3,()->atr.setDestreza(scanAtributo("Destreza")));
        menu.setHandler(4,()->atr.setImpulsao(scanAtributo("Impulsão")));
        menu.setHandler(5,()->atr.setJogoDeCabeca(scanAtributo("Jogo de Cabeça")));
        menu.setHandler(6,()->atr.setRemate(scanAtributo("Remate")));
        menu.setHandler(7,()->atr.setControloDePasse(scanAtributo("Controlo de Passe")));
        menu.setHandler(8,()->atr.setRecuperacaoDeBolas(scanAtributo("Recuperação de Bolas")));
        menu.setHandler(9,()->atr.setVisaoDeJogo(scanAtributo("Visão de Jogo")));
        menu.setHandler(10,()->menu.stop());
        menu.run();
        return atr;
    }

    public Atributos getAtributosAV(){
        View menu = new View(menuatributosAV);
        AtributosAvancado atr = new AtributosAvancado();
        menu.setHandler(1,()->atr.setVelocidade(scanAtributo("Velocidade")));
        menu.setHandler(2,()->atr.setResistencia(scanAtributo("Resistência")));
        menu.setHandler(3,()->atr.setDestreza(scanAtributo("Destreza")));
        menu.setHandler(4,()->atr.setImpulsao(scanAtributo("Impulsão")));
        menu.setHandler(5,()->atr.setJogoDeCabeca(scanAtributo("Jogo de Cabeça")));
        menu.setHandler(6,()->atr.setRemate(scanAtributo("Remate")));
        menu.setHandler(7,()->atr.setControloDePasse(scanAtributo("Controlo de Passe")));
        menu.setHandler(8,()->atr.setDesmarcacao(scanAtributo("Desmarcação")));
        menu.setHandler(9,()->atr.setPenaltis(scanAtributo("Penáltis")));
        menu.setHandler(10,()->menu.stop());
        menu.run();
        return atr;
    }

    public Atributos getAtributosLT(){
        View menu = new View(menuatributosLT);
        AtributosLateral atr = new AtributosLateral();
        menu.setHandler(1,()->atr.setVelocidade(scanAtributo("Velocidade")));
        menu.setHandler(2,()->atr.setResistencia(scanAtributo("Resistência")));
        menu.setHandler(3,()->atr.setDestreza(scanAtributo("Destreza")));
        menu.setHandler(4,()->atr.setImpulsao(scanAtributo("Impulsão")));
        menu.setHandler(5,()->atr.setJogoDeCabeca(scanAtributo("Jogo de Cabeça")));
        menu.setHandler(6,()->atr.setRemate(scanAtributo("Remate")));
        menu.setHandler(7,()->atr.setControloDePasse(scanAtributo("Controlo de Passe")));
        menu.setHandler(8,()->atr.setPrecisaoCruzamentos(scanAtributo("Precisão de Cruzamentos")));
        menu.setHandler(9,()->atr.setDrible(scanAtributo("Drible")));
        menu.setHandler(10,()->menu.stop());
        menu.run();
        return atr;
    }


    public Atributos getAtributos(String posicao){
        if (posicao.equals("Guarda-Redes")){
            return getAtributosGR();
        }
        else if (posicao.equals("Defesa")){
            return getAtributosDF();
        }
        else if (posicao.equals("Medio")){
            return getAtributosMD();
        }
        else if (posicao.equals("Avancado")){
            return getAtributosAV();
        }
        else {
            return getAtributosLT();
        }
    }


    public void terminar(){
        View view = new View();
        view.exitmessage();
        System.exit(0);
    }
}
