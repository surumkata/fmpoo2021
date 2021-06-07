import Desporto.Futebol.ControloDados;
import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.*;
import Desporto.Futebol.Equipa.Plantel;
import Desporto.Futebol.Equipa.Tatica;
import Desporto.Futebol.ViewJogo;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
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

    private final String[] menuSimulação = new String[]{
            "Simulação",
            "Começar Simulação",
            "Editar Equipas"};

    private final String[] menuEditarEquipa = new String[]{
            "Editar Equipa",
            "Editar Nome",
            "Editar Jogadores",
            "Editar Tatica",
            "Escolher Titulares",
            "Eliminar Equipa",
    };

    private final String[] menuEditarJogador = new String[]{
            "Editar Jogador",
            "Editar Nome",
            "Editar Numero",
            "Editar Posicao",
            "Editar Atributos",
            "Transferir Jogador",
            "Eliminar Jogador",
    };


    private final String[] menuCriarDados = new String[]{
            "Criar Dados",
            "Criar Jogador",
            "Criar Equipa"};

    private final String[] menuCriarJogador = new String[]{
            "Criar Jogador",
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
            "Tatica",
            "4-4-2 (1GR|2LT|2DF|4MD|2AV)",
            "4-3-3 (1GR|2LT|2DF|3MD|3AV)",
            "3-5-2 (1GR|2LT|3DF|3MD|2AV)",
            "3-6-1 (1GR|2LT|3DF|4MD|1AV)",
            "5-4-1 (1GR|4LT|3DF|2MD|1AV)",
            "5-3-2 (1GR|2LT|3DF|3MD|2AV)"};

    public final String[] menuatributosGR = new String[]{
            "Atributos",
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
            "Atributos",
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
            "Atributos",
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
            "Atributos",
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
            "Atributos",
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
        ViewJogo menu = new ViewJogo(menuPrincipal);
        menu.welcome();
        System.out.println("Equipas prontas-> "+cd.equipasProntas());
        menu.setPreCondition(2,()->cd.simulacaoPossivel());
        menu.setHandler(1, this::carregarDados);
        menu.setHandler(2,()->simularJogo());
        menu.run();
    }

    public void simularJogo(){
        List<String> es = this.cd.nomesEquipasProntas();
        String [] equipasVisitadas = new String[1+es.size()];
        equipasVisitadas[0] = "Equipa Visitada";
        int x = 1;
        for(String e : es){
            equipasVisitadas[x] = e;
            x++;
        }
        String equipaVisitada = picaEquipa(equipasVisitadas);
        System.out.println("Picou a equipa "+equipaVisitada);
        es.remove(equipaVisitada);

        String [] equipasVisitantes = new String[1+es.size()];
        equipasVisitantes[0] = "Equipa Visitante";
        x = 1;
        for(String e : es){
            equipasVisitantes[x] = e;
            x++;
        }
        String equipaVisitante = picaEquipa(equipasVisitantes);
        System.out.println("Picou a equipa "+equipaVisitante);

        System.out.println("Jogo entre "+equipaVisitada+" e "+equipaVisitante);

        ViewJogo v = new ViewJogo(menuSimulação);
        v.setHandler(1, ()->this.cd.simulacao(equipaVisitada,equipaVisitante));
        v.setHandler(2, ()->editarDados(new String[]{"",equipaVisitada,equipaVisitante,}, 3,true));
        v.run();


    }

    public void carregarDados(){
        ViewJogo menu = new ViewJogo(menuCarregarDados);
        String [] equipas = cd.nomesEquipas();
        int size = equipas.length+1, x = 0;
        String [] menuEquipas = new String[size];
        for (String e : equipas){
            x++;
            menuEquipas[x] = e;
        }
        menu.setHandler(1,()->{
            try {
                cd.lerFicheiro();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (PosicaoInvalidaException e1) {
                e1.printStackTrace();
            }
        });
        menu.setPreCondition(3,()->cd.existeEquipas());
        menu.setHandler(2, this::criarDados);
        menu.setHandler(3, ()->editarDados(menuEquipas, size,false));
        menu.run();
    }

    public void editarDados(String[] menu, int size, boolean simulaçao){
        menu[0] = "Editar Equipas";
        ViewJogo v = new ViewJogo(menu);
        for(int i = 1; i < size; i++){
            int finalI = i;
            v.setHandler(i,()->editarEquipa(menu[finalI],v,simulaçao));
        }
        v.run();
    }
    public void editarEquipa(String nome, ViewJogo v, boolean simulaçao){
        EquipaFutebol e = cd.getEquipaFutebol(nome);
        cd.removeEquipa(nome);

        String [] es = new String[6];
        es[0] = menuEditarEquipa[0];
        es[1] = menuEditarEquipa[1]+" ["+e.getNome()+"]";
        es[2] = menuEditarEquipa[2];
        if(e.podeUsarTatica(e.getPlantel().getTatica()))
            es[3] = menuEditarEquipa[3]+" ["+e.getPlantel().getTatica().toString()+"]";
        else es[3] = menuEditarEquipa[3];
        es[4] = menuEditarEquipa[4];
        es[5] = menuEditarEquipa[5];
        AtomicBoolean eliminacao = new AtomicBoolean(false);
        while(auxEditarEquipa(es,e,eliminacao,simulaçao)){
            if(!e.getNome().equals(""))
                es[1] = menuEditarJogador[1]+" ["+e.getNome()+"]";
            if(e.podeUsarTatica(e.getPlantel().getTatica()))
                es[3] = menuEditarEquipa[3]+" ["+e.getPlantel().getTatica().toString()+"]";
            else es[3] = menuEditarEquipa[3];
        }
        if(!eliminacao.get()) cd.criarEquipa(e);
        v.stop();
    }

    public boolean auxEditarEquipa(String [] ss, EquipaFutebol e, AtomicBoolean eliminacao, boolean simulaçao){
        ViewJogo v = new ViewJogo(ss);
        AtomicBoolean control = new AtomicBoolean(false);
        v.setPreCondition(1,()->!simulaçao);
        v.setPreCondition(2,()->!simulaçao && e.getPlantel().getnJogadoresNoPlantel() > 0);
        v.setPreCondition(3,()->e.getPlantel().getTitulares().size() == 11);
        v.setPreCondition(4,()->e.podeUsarTatica(e.getPlantel().getTatica()));
        v.setPreCondition(5,()->!simulaçao);
        v.setHandler(1,()->e.setNome(auxScan(v,control)));
        v.setHandler(2,()->editarJogadores(e,control,v));
        v.setHandler(3,()->escolheTatica(e,control,v));
        v.setHandler(4,()->escolheTitulares(e,control,v));
        v.setHandler(5,()->eliminar(eliminacao,v));
        v.run();
        return control.get();
    }

    public void escolheTitulares(EquipaFutebol e, AtomicBoolean control, ViewJogo menuEquipa){
        menuEquipa.stop();
        control.set(true);
        Plantel p = e.getPlantel();
        p.limpaTitulares();
        Tatica t = p.getTatica();

        String[] posicoes = {"Guarda-Redes","Defesa","Lateral","Medio","Avancado"};
        int [] nposicoes = {t.getnGR(),t.getnDF(),t.getnLT(),t.getnMD(),t.getnPL()};

        int x;
        for(x = 0; x < 5; x++){
            picaTitulares(p,posicoes[x],nposicoes[x]);
        }

        e.setPlantel(p);
    }

    public String picaEquipa (String[] equipasAEscolher){
        AtomicInteger x = new AtomicInteger(1);
        ViewJogo v = new ViewJogo(equipasAEscolher);
        for(int i = 1; i < equipasAEscolher.length; i++){
            int finalI = i;
            v.setHandler(i,()->auxAtomicInteger(v, finalI,x));
        }
        v.run();


        return equipasAEscolher[x.get()];
    }


    public void picaTitulares (Plantel p, String posicao, int np){
        int x;

        while (np > 0){
            String [][] jogadores = p.nomesSuplentes(posicao);
            int tam = jogadores[0].length;
            tam++;
            String [] menu = new String[tam];
            menu[0] = posicao;
            for(x = 0; x < jogadores[0].length;){
                x++;
                menu[x] = jogadores[0][x-1]+"["+jogadores[1][x-1]+"]";
                System.out.println(menu[x]);
            }
            int n = auxPicaTitulares(menu, jogadores[1]);
            Jogador j = p.getSuplente(n);
            p.removeSuplente(n);
            p.adicionaTitular(j);
            np--;
        }
    }

    public int auxPicaTitulares(String[] menu, String[] numeros){
        AtomicInteger x = new AtomicInteger(Integer.parseInt(numeros[0]));
        ViewJogo v = new ViewJogo(menu);
        for(int i = 1; i < menu.length; i++){
            int finalI = i-1;
            v.setHandler(i,()->auxAtomicInteger(v, Integer.parseInt(numeros[finalI]),x));
        }
        v.run();
        return x.get();
    }

    public void auxAtomicInteger(ViewJogo v, int i, AtomicInteger x){
        v.stop();
        x.set(i);
    }

    public void editarJogadores (EquipaFutebol e, AtomicBoolean control, ViewJogo menuEquipa){
        menuEquipa.stop();
        control.set(true);
        int x;
        String [][] jogadores = e.nomesJogadores();
        int tam = jogadores[0].length;
        tam++;
        String [] menu = new String[tam];
        menu[0] = "";
        for(x = 0; x < jogadores[0].length;){
            x++;
            menu[x] = jogadores[0][x-1]+"["+jogadores[1][x-1]+"]";
        }
        ViewJogo v = new ViewJogo(menu);
        for(int i = 1; i < tam; i++){
            int finalI = i-1;
            v.setHandler(i,()->editarJogador(e, Integer.parseInt(jogadores[1][finalI]),v));
        }
        v.run();
    }

    public void editarJogador(EquipaFutebol e, int numero, ViewJogo v){
        Jogador j = e.getJogador(numero);
        e.removePlantel(numero);

        String [] js = new String[7];
        js[0] = menuEditarJogador[0];
        js[1] = menuEditarJogador[1] + " [" +j.getNome()+ "]";
        js[2] = menuEditarJogador[2] + " [" +j.getNumero()+ "]";
        js[3] = menuEditarJogador[3] + " [" + j.getPosicao()+ "]";
        js[4] = menuEditarJogador[4] + " [" + j.getAtributos().overall()+ "]";
        js[5] = menuEditarJogador[5];
        js[6] = menuEditarJogador[6];
        AtomicBoolean transferencia = new AtomicBoolean(false);
        while(auxEditarJogador(js,j,transferencia)){
            if(!j.getNome().equals(""))
                js[1] = menuEditarJogador[1]+" ["+j.getNome()+"]";
            if(j.getNumero() != 0)
                js[2] = menuEditarJogador[2]+" ["+j.getNumero()+"]";
            if(!j.getPosicao().equals("")) {
                js[3] = menuEditarJogador[3] + " [" + j.getPosicao() + "]";
                js[4] = menuEditarJogador[4] + " [" + j.getAtributos().overall() + "]";
            }
        }
        if(!transferencia.get()) e.adicionaPlantel(j);

        v.stop();
    }

    public boolean auxEditarJogador(String[] ss, Jogador j, AtomicBoolean transferencia){
        ViewJogo editJ = new ViewJogo(ss);
        AtomicBoolean control = new AtomicBoolean(false);
        editJ.setHandler(1,()->j.setNome(auxScan(editJ,control)));
        editJ.setHandler(2,()-> j.setNumero(auxScanInt(editJ,control)));
        editJ.setHandler(3,()-> j.setPosicao(escolhePosicao(editJ,control)));
        editJ.setHandler(4,()->j.setAtributos(getAtributos(j.getPosicao(),editJ,control)));
        editJ.setHandler(5,()->transferencia(j,transferencia,editJ));
        editJ.setHandler(6,()->eliminar(transferencia,editJ));
        editJ.run();
        return control.get();
    }

    public void eliminar (AtomicBoolean transferencia, ViewJogo editJ){
        transferencia.set(true);
        editJ.stop();
    }

    public void transferencia (Jogador j, AtomicBoolean transferencia, ViewJogo editJ){
        transferencia.set(true);
        ViewJogo v = new ViewJogo();
        v.lerequipa();
        String nomeEquipa = this.scan.nextLine();
        cd.colocaJogador(j,nomeEquipa);
        editJ.stop();
    }

    public String auxScan(ViewJogo v, AtomicBoolean control){
        System.out.print("@: ");
        v.stop();
        control.set(true);
        return this.scan.nextLine();
    }

    public int auxScanInt(ViewJogo v, AtomicBoolean control){
        System.out.print("@: ");
        int i = scan.nextInt();
        if(i > 0 && i <= 99){
            v.stop();
            control.set(true);
            return i;
        }
        else return auxScanInt(v,control);
    }

    public void criarDados(){
        ViewJogo menu = new ViewJogo(menuCriarDados);
        menu.setHandler(1, this::criarJogador);
        menu.setHandler(2, this::criarEquipa);
        menu.run();
    }

    public void criarJogador(){
        Jogador j = new Jogador();
        String [] js = new String[6];
        js[0] = menuCriarJogador[0];
        js[1] = menuCriarJogador[1]+" []";
        js[2] = menuCriarJogador[2]+" []";
        js[3] = menuCriarJogador[3]+" []";
        js[4] = menuCriarJogador[4];
        js[5] = menuCriarJogador[5];
        while(auxCriarJogador(js,j)){
            if(!j.getNome().equals(""))
                js[1] = menuCriarJogador[1]+" ["+j.getNome()+"]";
            if(j.getNumero() != 0)
                js[2] = menuCriarJogador[2]+" ["+j.getNumero()+"]";
            if(!j.getPosicao().equals("")) {
                js[3] = menuCriarJogador[3] + " [" + j.getPosicao() + "]";
                js[4] = menuCriarJogador[4] + " [" + j.getAtributos().overall() + "]";
            }
        }
    }

    public boolean auxCriarJogador (String[] ss, Jogador j){
        ViewJogo jogadormenu = new ViewJogo(ss);
        AtomicBoolean control = new AtomicBoolean(false);
        jogadormenu.setPreCondition(new int []{2,3,4},()->!j.getNome().equals(""));
        jogadormenu.setPreCondition(4,()->!j.getPosicao().equals(""));
        jogadormenu.setPreCondition(5,()->j.getNumero()!=0 && !j.getPosicao().equals("") && j.getAtributos().overall() != 0);
        jogadormenu.setHandler(1,()->j.setNome(auxScan(jogadormenu,control)));
        jogadormenu.setHandler(2,()-> j.setNumero(auxScanInt(jogadormenu,control)));
        jogadormenu.setHandler(3,()-> j.setPosicao(escolhePosicao(jogadormenu,control)));
        jogadormenu.setHandler(4,()->j.setAtributos(getAtributos(j.getPosicao(),jogadormenu,control)));
        jogadormenu.setHandler(5,()->colocaJogadarNaEquipa(j,jogadormenu));
        jogadormenu.run();
        return control.get();
    }

    public void colocaJogadarNaEquipa (Jogador j, ViewJogo v){
        ViewJogo aux = new ViewJogo();
        v.lerequipa();
        String nome = scan.nextLine();
        while(nome.equals("")){
            nome = scan.nextLine();
        }
        cd.colocaJogador(j,nome);
        v.stop();
    }

    public String escolhePosicao(ViewJogo v, AtomicBoolean control){
        ViewJogo posicaomenu = new ViewJogo(menuPosicao);
        String [] posicoes = {"","Guarda-Redes", "Defesa", "Lateral", "Medio","Avancado"};

        AtomicInteger x = new AtomicInteger(0);
        posicaomenu.setHandler(1, ()->x.set(auxEscolhe(1,posicaomenu)));
        posicaomenu.setHandler(2, ()->x.set(auxEscolhe(2,posicaomenu)));
        posicaomenu.setHandler(3, ()->x.set(auxEscolhe(3,posicaomenu)));
        posicaomenu.setHandler(4, ()->x.set(auxEscolhe(4,posicaomenu)));
        posicaomenu.setHandler(5, ()->x.set(auxEscolhe(5,posicaomenu)));
        posicaomenu.run();
        v.stop();
        control.set(true);
        return posicoes[x.intValue()];
    }


    public void escolheTatica(EquipaFutebol e, AtomicBoolean control, ViewJogo v){
        v.stop();
        control.set(true);
        ViewJogo taticamenu = new ViewJogo(menuEscolheTatica);
        Tatica[] taticas = {new Tatica(1,2,2,4,2),
                            new Tatica(1,2,2,3,3),
                            new Tatica(1,3,2,3,2),
                            new Tatica(1,3,2,4,1),
                            new Tatica(1,3,4,2,1),
                            new Tatica(1,3,2,3,2)};

        AtomicInteger x = new AtomicInteger();
        int i;
        for(i = 1; i <= 6; i++){
            int finalI = i;
            taticamenu.setPreCondition(i,()->e.podeUsarTatica(taticas[finalI -1]));
            taticamenu.setHandler(i, ()->x.set(auxEscolhe(finalI-1,taticamenu)));
        }
        taticamenu.run();
        e.setTatica(taticas[x.intValue()]);
    }

    public int auxEscolhe (int i, ViewJogo v){
        v.stop();
        return i;
    }

    public void criarEquipa(){
        System.out.println("Escolhe um nome para a tua equipa.");
        String nome = nomeEquipa();
        cd.criarEquipa(nome);
        System.out.println("Equipa criada com sucesso.");
    }

    public String nomeEquipa(){
        String nome = scan.nextLine();
        if(!cd.existeEquipa(nome))
            return nome;
        else {
            System.out.println("Esse nome já pertence a uma equipa, escolhe outro.");
            return nomeEquipa();
        }
    }

    public int scanAtributo(String atributo){
        ViewJogo v = new ViewJogo();
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
        ViewJogo menu = new ViewJogo(menuatributosGR);
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
        ViewJogo menu = new ViewJogo(menuatributosDF);
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
        ViewJogo menu = new ViewJogo(menuatributosMD);
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
        ViewJogo menu = new ViewJogo(menuatributosAV);
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
        ViewJogo menu = new ViewJogo(menuatributosLT);
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


    public Atributos getAtributos(String posicao, ViewJogo v, AtomicBoolean control){
        v.stop();
        control.set(true);
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
        ViewJogo viewJogo = new ViewJogo();
        viewJogo.exitmessage();
        System.exit(0);
    }
}
