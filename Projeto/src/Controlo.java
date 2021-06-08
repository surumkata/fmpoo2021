import Desporto.Futebol.ControloDados;
import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.*;
import Desporto.Futebol.Equipa.Plantel;
import Desporto.Futebol.Equipa.Tatica;
import Desporto.Futebol.Partida.ExecutaPartida;
import Desporto.Futebol.Partida.PartidaFutebol;
import Desporto.Futebol.ViewJogo;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Controlo {
    private ControloDados cd;
    private final Scanner scan = new Scanner(System.in);
    private final String[] menuPrincipal = new String[]{
            "Menu Principal",
            "Simular uma partida",
            "Dados"};

    private final String[] menuDados = new String[]{
            "Menu Dados",
            "Carregar dados",
            "Criar dados",
            "Ver/Editar dados",
            "Gravar dados",
            "Reset dados"};

    private final String[] gravarDados = new String[]{
            "Gravar Dados",
            "Gravar ficheiro",
            "Gravar objeto",};

    private final String[] lerDados = new String[]{
            "Ler Dados",
            "Ler ficheiro",
            "Ler objeto",};

    private final String[] menuSimulacao = new String[]{
            "Simulacao",
            "Começar Simulacao",
            "Editar Equipas"};

    private final String[] menuEditarEquipa = new String[]{
            "Equipa",
            "Editar Nome",
            "Ver/Editar Jogadores",
            "Editar Tatica",
            "Escolher Titulares",
            "Ver Histórico",
            "Eliminar Equipa",
    };

    private final String[] menuEditarJogador = new String[]{
            "Jogador",
            "Editar Nome",
            "Editar Numero",
            "Editar Posicao",
            "Editar Atributos",
            "Transferir Jogador",
            "Ver Jogador",
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

    public final String[] menuAtributos = new String[]{
            "Atributos",
            "Velocidade:",
            "Resistência:",
            "Destreza:",
            "Impulsão:",
            "Jogo de cabeça:",
            "Remate:",
            "Controlo de passe:",
            "Gravar"};

    public final String[] menuatributosGR = new String[]{
            "Elasticidade:",
            "Reflexos:"};

    public final String[] menuatributosDF = new String[]{
            "Posicionamento Defensivo:",
            "Cortes:"};

    public final String[] menuatributosMD = new String[]{
            "Recuperação de Bolas:",
            "Visão de Jogo:"};

    public final String[] menuatributosLT = new String[]{
            "Precisão de cruzamentos:",
            "Drible:"};

    public final String[] menuatributosAV = new String[]{
            "Desmarcação:",
            "Penáltis:"};

    private final String[] simulacao = new String[]{
            "Simulacao",
            "Continuar Simulacao",
            "Substituicoes ",
            "Substituicoes "};


    public Controlo() {
        this.cd = new ControloDados();
    }


    public void run() {
        ViewJogo menu = new ViewJogo(menuPrincipal);
        menu.welcome();
        menu.setPreCondition(2,()->cd.simulacaoPossivel());
        menu.setHandler(1, this::simularJogo);
        menu.setHandler(2, this::dados);
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
        AtomicBoolean control = new AtomicBoolean(false);
        String equipaVisitada = picaEquipa(equipasVisitadas,control);
        if(!control.get())
            return;
        es.remove(equipaVisitada);

        String [] equipasVisitantes = new String[1+es.size()];
        equipasVisitantes[0] = "Equipa Visitante";
        x = 1;
        for(String e : es){
            equipasVisitantes[x] = e;
            x++;
        }
        control.set(false);
        String equipaVisitante = picaEquipa(equipasVisitantes, control);
        if(!control.get())
            return;
        System.out.println("Jogo entre "+equipaVisitada+" e "+equipaVisitante);

        ViewJogo v = new ViewJogo(menuSimulacao);
        v.setHandler(1, ()->simulacao(equipaVisitada,equipaVisitante));
        v.setHandler(2, ()->editarDados(new String[]{"",equipaVisitada,equipaVisitante,}, 3,true));
        v.run();


    }

    public void simulacao(String equipaVisitada, String equipaVisitante){
        EquipaFutebol visitadaO = this.cd.getEquipaFutebol(equipaVisitada);
        EquipaFutebol visitanteO = this.cd.getEquipaFutebol(equipaVisitante);

        ExecutaPartida exp = new ExecutaPartida(visitadaO,visitanteO);
        AtomicBoolean intervalo = new AtomicBoolean(false);
        boolean posIntervalo = false;

        String resultado = exp.getPartida().resultado();
        AtomicBoolean substituicoes = new AtomicBoolean(false);

        while(!exp.run(intervalo,substituicoes)){
            EquipaFutebol finalVisitada = exp.getPartida().getEquipaVisitada();
            EquipaFutebol finalVisitante = exp.getPartida().getEquipaVisitante();
            resultado = exp.getPartida().resultado();
            if(!posIntervalo && intervalo.get()) {
                posIntervalo = true;
                simulacao[0] = "Intervalo " + resultado;
            }
            else{
                simulacao[0] = "Simulacao " + resultado;
            }
            //if(!substituicoes.get()){
            //    visitada.desgasteJogo();
            //    visitante.desgasteJogo();
            //}
            //else substituicoes.set(false);
            substituicoes.set(false);

            AtomicInteger s1 = new AtomicInteger(exp.getSubsRestantes(true));
            AtomicInteger s2 = new AtomicInteger(exp.getSubsRestantes(false));
            simulacao[2] = "Substituicoes "+finalVisitada.getNome()+" ["+s1.get()+" substituições restantes]";
            simulacao[3] = "Substituicoes "+finalVisitante.getNome()+" ["+s2.get()+" substituições restantes]";
            ViewJogo menuS = new ViewJogo(simulacao);
            AtomicBoolean substituicao = new AtomicBoolean(false);
            menuS.setPreCondition(2,()->(s1.get() > 0));
            menuS.setPreCondition(3,()->(s2.get() > 0));
            AtomicInteger n1 = new AtomicInteger(0);
            AtomicInteger n2 = new AtomicInteger(0);
            menuS.setHandler(1, menuS::stop);
            menuS.setHandler(2, ()->{
                String comentario = substituicoes(finalVisitada,substituicao,n1,n2);
                if(substituicao.get()) {
                    s1.set(s1.get() - 1);
                    substituicoes.set(true);
                    exp.decSubs(true,comentario,n1.get(),n2.get());
                    menuS.stop();
                }
            });
            menuS.setHandler(3, ()->{
                String comentario = substituicoes(finalVisitante,substituicao,n1,n2);
                if(substituicao.get()) {
                    s2.set(s2.get() - 1);
                    substituicoes.set(true);
                    exp.decSubs(false,comentario,n1.get(),n2.get());
                    menuS.stop();
                }
            });
            menuS.run();
            exp.atualizaEquipa(true,finalVisitada);
            exp.atualizaEquipa(false,finalVisitante);
        }

        exp.atualizaEquipa(false,visitanteO);
        exp.atualizaEquipa(true,visitadaO);
        System.out.println(resultado);
        PartidaFutebol p = exp.getPartida();
        this.cd.adicionaPartida(p);
    }



    public String substituicoes(EquipaFutebol e, AtomicBoolean substituicao, AtomicInteger numeroTitular, AtomicInteger numeroATrocar){
        Plantel p = e.getPlantel();
        int x;
        String [][] titulares = p.nomesTitulares();
        String [][] suplentes = p.nomesSupelentes();
        int tamT = titulares[0].length;
        int tamS = suplentes[0].length;
        String [] menu = new String[tamT+1];
        menu[0] = "1º Jogador da troca";
        for(x = 1; x <= tamT;x++){
            int overall = Integer.parseInt(titulares[3][x-1]);
            if(overall >= 70)
                menu[x] = "\u001B[32m["+titulares[1][x-1]+"] "+titulares[0][x-1]+" ["+titulares[2][x-1]+"] ("+overall+")\u001B[0m";
            else if (overall >= 40)
                menu[x] = "\u001B[33m["+titulares[1][x-1]+"] "+titulares[0][x-1]+" ["+titulares[2][x-1]+"] ("+overall+")\u001B[0m";
            else menu[x] = "\u001B[31m["+titulares[1][x-1]+"] "+titulares[0][x-1]+" ["+titulares[2][x-1]+"] ("+overall+")\u001B[0m";
        }
        ViewJogo mT = new ViewJogo(menu);
        AtomicInteger index = new AtomicInteger(0);
        for(int i = 1; i < tamT+1; i++){
            int finalI = i;
            mT.setHandler(i,()-> {
                mT.stop();
                numeroTitular.set(Integer.parseInt(titulares[1][finalI-1]));
                index.set(finalI-1);
            });
        }
        mT.run();
        if(numeroTitular.get() != 0) {
            menu = new String[tamT + tamS];
            menu[0] = "2º Jogador da troca [" + titulares[2][index.get()] + "]";
            int y = 0;
            if (index.get() == 0) y++;
            int overallT = Integer.parseInt(titulares[3][index.get()]);
            for (x = 1; y < tamT; x++, y++) {
                int overallS = Integer.parseInt(titulares[3][y]);
                if (overallS > overallT && titulares[2][index.get()].equals(titulares[2][y]))
                    menu[x] = "\u001B[32m[" + titulares[1][y] + "] " + titulares[0][y] + " [" + titulares[2][y] + "] (" + titulares[3][y] + ")\u001B[0m";
                else if (titulares[2][index.get()].equals(titulares[2][y]))
                    menu[x] = "\u001B[33m[" + titulares[1][y] + "] " + titulares[0][y] + " [" + titulares[2][y] + "] (" + titulares[3][y] + ")\u001B[0m";
                else
                    menu[x] = "\u001B[31m[" + titulares[1][y] + "] " + titulares[0][y] + " [" + titulares[2][y] + "] (" + titulares[3][y] + ")\u001B[0m";

                if (y == index.get() - 1) {
                    y++;
                }
            }
            menu[x - 1] = menu[x - 1] + "\n**Suplentes**";
            for (y = 0; y < tamS; y++) {
                int overallS = Integer.parseInt(suplentes[3][y]);
                if (overallS > overallT && titulares[2][index.get()].equals(suplentes[2][y]))
                    menu[y + x] = "\u001B[32m[" + suplentes[1][y] + "] " + suplentes[0][y] + " [" + suplentes[2][y] + "] (" + suplentes[3][y] + ")\u001B[0m";
                else if (titulares[2][index.get()].equals(suplentes[2][y]))
                    menu[y + x] = "\u001B[33m[" + suplentes[1][y] + "] " + suplentes[0][y] + " [" + suplentes[2][y] + "] (" + suplentes[3][y] + ")\u001B[0m";
                else
                    menu[y + x] = "\u001B[31m[" + suplentes[1][y] + "] " + suplentes[0][y] + " [" + suplentes[2][y] + "] (" + suplentes[3][y] + ")\u001B[0m";

            }
            AtomicBoolean suplente = new AtomicBoolean(false);
            int i;
            ViewJogo mS = new ViewJogo(menu);
            for (i = 1; i < tamT; i++) {
                int finalI = i;
                mS.setHandler(i, () -> {
                    mS.stop();
                    numeroATrocar.set(Integer.parseInt(titulares[1][finalI - 1]));
                });
            }
            for (; i < tamT + tamS; i++) {
                int finalI = i - tamT;
                mS.setHandler(i, () -> {
                    mS.stop();
                    numeroATrocar.set(Integer.parseInt(suplentes[1][finalI]));
                    suplente.set(true);
                });
            }
            mS.run();
            int n1 = numeroTitular.get(), n2 = numeroATrocar.get();
            if(n2 != 0) {
                String posicaoT = e.getJogador(numeroTitular.get()).getPosicao();
                String posicaoS = e.getJogador(numeroATrocar.get()).getPosicao();
                String nomeT = e.getJogador(n1).getNome();
                String nomeS = e.getJogador(n2).getNome();
                if (!posicaoT.equals(posicaoS) && !suplente.get()) {
                    e.trocaPosicao(n2, posicaoT);
                    e.trocaPosicao(n1, posicaoS);
                } else if (!posicaoT.equals(posicaoS)) {
                    e.substiuicaoJogo(n1, n2);
                    e.trocaPosicao(n2, posicaoT);
                    substituicao.set(true);
                    return "\u001B[31mSai o número " + n1 + ", " + nomeT + ". \u001B[32mEntra para o seu lugar, o número " + n2 + ", " + nomeS + ".\u001B[0m";
                } else if (suplente.get()) {
                    e.substiuicaoJogo(n1, n2);
                    substituicao.set(true);
                    return "\u001B[31mSai o número " + n1 + ", " + nomeT + ". \u001B[32mEntra para o seu lugar, o número " + n2 + ", " + nomeS + ".\u001B[0m";
                }
            }
        }
        return "";
    }

    public void dados(){
        ViewJogo menu = new ViewJogo(menuDados);
        String [] equipas = cd.nomesEquipas();
        int size = equipas.length+1, x = 0;
        String [] menuEquipas = new String[size];
        for (String e : equipas){
            x++;
            menuEquipas[x] = e;
        }
        menu.setHandler(1,()->{lerDados();menu.stop();});
        menu.setPreCondition(3,()->cd.existeEquipas());
        menu.setHandler(2, this::criarDados);
        menu.setHandler(3, ()->{editarDados(menuEquipas, size,false);menu.stop();});
        menu.setHandler(4, ()->{gravarDados();menu.stop();});
        menu.setHandler(5,()->{resetDados();menu.stop();});
        menu.run();
    }

    public void gravarDados(){
        ViewJogo v = new ViewJogo(gravarDados);
        v.setHandler(1,()->{
            System.out.println("Nome do ficheiro: ");
            String ficheiro = scan.nextLine();
            try {
                cd.escreverFicheiro(ficheiro);
            } catch (IOException e) {
                e.printStackTrace();
            }
            v.stop();
        });
        v.setHandler(2,()->{
            System.out.println("Nome do ficheiro objeto: ");
            String ficheiro = scan.nextLine();
            try {
                cd.gravarFicheiroObjeto(ficheiro);
            } catch (IOException e) {
                e.printStackTrace();
            }
            v.stop();
        });
        v.run();
    }

    public void lerDados(){
        ViewJogo v = new ViewJogo(lerDados);
        v.setHandler(1,()->{
            try {
                System.out.println("Nome do ficheiro: ");
                String ficheiro = scan.nextLine();
                cd.lerFicheiro(ficheiro);
                v.stop();
            } catch (IOException | PosicaoInvalidaException e1) {
                e1.printStackTrace();
            }
        });
        v.setHandler(2,()->{
            try {
                System.out.println("Nome do ficheiro objeto: ");
                String ficheiro = scan.nextLine();
                cd = cd.lerFicheiroObjeto(ficheiro);
                v.stop();
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        v.run();
    }

    public void resetDados(){
        this.cd = new ControloDados();
    }

    public void editarDados(String[] menu, int size, boolean simulacao){
        menu[0] = "Editar Equipas";
        ViewJogo v = new ViewJogo(menu);
        for(int i = 1; i < size; i++){
            int finalI = i;
            v.setHandler(i,()->{
                editarEquipa(menu[finalI],simulacao);
                v.stop();
            });
        }
        v.run();
    }
    public void editarEquipa(String nome, boolean simulacao){
        EquipaFutebol e = cd.getEquipaFutebol(nome);
        cd.removeEquipa(nome);

        String [] es = new String[7];
        es[0] = menuEditarEquipa[0];
        es[1] = menuEditarEquipa[1]+" ["+e.getNome()+"]";
        es[2] = menuEditarEquipa[2];
        if(e.podeUsarTatica(e.getPlantel().getTatica()))
            es[3] = menuEditarEquipa[3]+" ["+e.getPlantel().getTatica().toString()+"]";
        else es[3] = menuEditarEquipa[3];
        es[4] = menuEditarEquipa[4];
        es[5] = menuEditarEquipa[5];
        es[6] = menuEditarEquipa[6];
        AtomicBoolean eliminacao = new AtomicBoolean(false);
        while(auxEditarEquipa(es,e,eliminacao,simulacao)){
            if(!e.getNome().equals(""))
                es[1] = menuEditarEquipa[1]+" ["+e.getNome()+"]";
            if(e.podeUsarTatica(e.getPlantel().getTatica()))
                es[3] = menuEditarEquipa[3]+" ["+e.getPlantel().getTatica().toString()+"]";
            else es[3] = menuEditarEquipa[3];
        }
        if(!eliminacao.get()) cd.criarEquipa(e);
    }

    public boolean auxEditarEquipa(String [] ss, EquipaFutebol e, AtomicBoolean eliminacao, boolean simulacao){
        ViewJogo v = new ViewJogo(ss);
        AtomicBoolean control = new AtomicBoolean(false);
        v.setPreCondition(2,()->!simulacao && e.getPlantel().getnJogadoresNoPlantel() > 0);
        v.setPreCondition(3,()->e.getPlantel().getTitulares().size() == 11);
        v.setPreCondition(4,()->e.podeUsarTatica(e.getPlantel().getTatica()));
        v.setPreCondition(new int[]{1,5,6},()->!simulacao);
        v.setHandler(1,()->{
            e.setNome(auxScan());
            v.stop();
            control.set(true);
        });
        v.setHandler(2,()->{
            editarJogadores(e);
            v.stop();
        });
        v.setHandler(3,()->{
            escolheTatica(e);
            v.stop();
            control.set(true);

        });
        v.setHandler(4,()->{
            escolheTitulares(e);
            control.set(true);
            v.stop();
        });
        v.setHandler(5,()->mostrarHistorico(e.getNome()));
        v.setHandler(6,()->{eliminacao.set(true);v.stop();});
        v.run();
        return control.get();
    }

    public void mostrarHistorico (String equipa){
        String [] menu = this.cd.historicoString(equipa);
        List<PartidaFutebol> l = this.cd.historico(equipa);
        ViewJogo m = new ViewJogo(menu);
        for(int i = 1; i < menu.length; i++){
            int finalI = i-1;
            m.setHandler(i,()->{
                System.out.println(l.get(finalI).toString());
                ViewJogo aux = new ViewJogo(new String[] {""});
                aux.run();
            });
        }
        m.run();
    }

    public void escolheTitulares(EquipaFutebol e){
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

    public String picaEquipa (String[] equipasAEscolher, AtomicBoolean control){
        AtomicInteger x = new AtomicInteger(1);
        ViewJogo v = new ViewJogo(equipasAEscolher);
        for(int i = 1; i < equipasAEscolher.length; i++){
            int finalI = i;
            v.setHandler(i,()->{
                v.stop();
                x.set(finalI);
                control.set(true);
            });
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
            v.setHandler(i,()->{
                v.stop();
                x.set(Integer.parseInt(numeros[finalI]));
            });
        }
        v.run();
        return x.get();
    }

    public void editarJogadores (EquipaFutebol e){
        int x;
        String [][] jogadores = e.nomesJogadores();
        int tam = jogadores[0].length;
        tam++;
        String [] menu = new String[tam];
        menu[0] = "Jogadores";
        for(x = 0; x < jogadores[0].length;){
            x++;
            menu[x] = jogadores[0][x-1]+"["+jogadores[1][x-1]+"]";
        }
        ViewJogo v = new ViewJogo(menu);
        for(int i = 1; i < tam; i++){
            int finalI = i-1;
            v.setHandler(i,()->{
                editarJogador(e, Integer.parseInt(jogadores[1][finalI]));
                v.stop();
            });
        }
        v.run();
    }

    public void editarJogador(EquipaFutebol e, int numero){
        Jogador j = e.getJogador(numero);
        e.removePlantel(numero);

        String [] js = new String[8];
        js[0] = menuEditarJogador[0];
        js[1] = menuEditarJogador[1] + " [" +j.getNome()+ "]";
        js[2] = menuEditarJogador[2] + " [" +j.getNumero()+ "]";
        js[3] = menuEditarJogador[3] + " [" + j.getPosicao()+ "]";
        js[4] = menuEditarJogador[4] + " [" + j.getAtributos().overall()+ "]";
        js[5] = menuEditarJogador[5];
        js[6] = menuEditarJogador[6];
        js[7] = menuEditarJogador[7];
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
    }

    public boolean auxEditarJogador(String[] ss, Jogador j, AtomicBoolean transferencia){
        ViewJogo editJ = new ViewJogo(ss);
        AtomicBoolean control = new AtomicBoolean(false);
        editJ.setHandler(1,()->{
            j.setNome(auxScan());
            editJ.stop();
            control.set(true);
        });
        editJ.setHandler(2,()-> {
            j.setNumero(auxScanInt());
            editJ.stop();
            control.set(true);
        });
        editJ.setHandler(3,()->{
            j.setPosicao(escolhePosicao());
            editJ.stop();
            control.set(true);
        });
        editJ.setHandler(4,()->{
            j.setAtributos(getAtributos(j.getAtributos()));
            editJ.stop();
            control.set(true);
        });
        editJ.setHandler(5,()-> {
            transferencia.set(true);
            editJ.lerEquipa();
            String nomeEquipa = this.scan.nextLine();
            cd.colocaJogador(j,nomeEquipa);
            editJ.stop();
        });
        editJ.setHandler(6,()->{
            System.out.println(j.toString());
            ViewJogo aux = new ViewJogo(new String[] {""});
            aux.run();
        });
        editJ.setHandler(7,()->{
            transferencia.set(true);
            editJ.stop();
        });
        editJ.run();
        return control.get();
    }

    public String auxScan(){
        System.out.print("@: ");
        return this.scan.nextLine();
    }

    public int auxScanInt(){
        System.out.print("@: ");
        int i = scan.nextInt();
        if(i > 0 && i <= 99){
            return i;
        }
        else return auxScanInt();
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
        AtomicBoolean control = new AtomicBoolean(true);
        jogadormenu.setPreCondition(new int []{2,3,4},()->!j.getNome().equals(""));
        jogadormenu.setPreCondition(4,()->!j.getPosicao().equals(""));
        jogadormenu.setPreCondition(5,()->j.getNumero()!=0 && !j.getPosicao().equals("") && j.getAtributos().overall() != 0);
        jogadormenu.setHandler(1,()->{
            j.setNome(auxScan());
            jogadormenu.stop();
        });
        jogadormenu.setHandler(2,()->{
            j.setNumero(auxScanInt());
            jogadormenu.stop();
        });
        jogadormenu.setHandler(3,()->{
            j.setPosicao(escolhePosicao());
            jogadormenu.stop();
        });
        jogadormenu.setHandler(4,()->{
            j.setAtributos(getAtributos(j.getAtributos()));
            jogadormenu.stop();});
        jogadormenu.setHandler(5,()->{
            jogadormenu.lerEquipa();
            String nome = scan.nextLine();
            colocaJogadarNaEquipa(j,nome);
            jogadormenu.stop();
            control.set(false);
        });
        jogadormenu.run();
        return control.get();
    }

    public void colocaJogadarNaEquipa (Jogador j, String nome){
        while(nome.equals("")){
            nome = scan.nextLine();
        }
        cd.colocaJogador(j,nome);
    }

    public String escolhePosicao(){
        ViewJogo menu = new ViewJogo(menuPosicao);
        String [] posicoes = {"","Guarda-Redes", "Defesa", "Lateral", "Medio","Avancado"};

        AtomicInteger x = new AtomicInteger(0);
        menu.setHandler(1, ()->{ x.set(1);menu.stop(); });
        menu.setHandler(2, ()->{ x.set(2);menu.stop(); });
        menu.setHandler(3, ()->{ x.set(3);menu.stop(); });
        menu.setHandler(4, ()->{ x.set(4);menu.stop(); });
        menu.setHandler(5, ()->{ x.set(5);menu.stop(); });
        menu.run();
        return posicoes[x.intValue()];
    }


    public void escolheTatica(EquipaFutebol e){
        ViewJogo menu = new ViewJogo(menuEscolheTatica);
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
            menu.setPreCondition(i,()->e.podeUsarTatica(taticas[finalI -1]));
            menu.setHandler(i, ()->{x.set(finalI-1);menu.stop();});
        }
        menu.run();
        e.setTatica(taticas[x.intValue()]);
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
        v.atributosMessage(atributo);
        int i = this.scan.nextInt();
        if(i >= 1 && i <= 100)
            return i;
        else {
            v.atributosMessageError();
            return scanAtributo(atributo);
        }
    }

    public Atributos getAtributosGR(String[] menu, Atributos aot, AtomicBoolean control, AtomicBoolean gravar){
        ViewJogo v = new ViewJogo(menu);
        AtributosGR atr = new AtributosGR(aot);
        v.setHandler(1,()->{atr.setVelocidade(scanAtributo("Velocidade"));v.stop();control.set(true);});
        v.setHandler(2,()->{atr.setResistencia(scanAtributo("Resistência"));v.stop();control.set(true);});
        v.setHandler(3,()->{atr.setDestreza(scanAtributo("Destreza"));v.stop();control.set(true);});
        v.setHandler(4,()->{atr.setImpulsao(scanAtributo("Impulsão"));v.stop();control.set(true);});
        v.setHandler(5,()->{atr.setJogoDeCabeca(scanAtributo("Jogo de Cabeça"));v.stop();control.set(true);});
        v.setHandler(6,()->{atr.setRemate(scanAtributo("Remate"));v.stop();control.set(true);});
        v.setHandler(7,()->{atr.setControloDePasse(scanAtributo("Controlo de Passe"));v.stop();control.set(true);});
        v.setHandler(8,()->{atr.setReflexos(scanAtributo("Reflexos"));v.stop();control.set(true);});
        v.setHandler(9,()->{atr.setElasticidade(scanAtributo("Elastecidade"));v.stop();control.set(true);});
        v.setHandler(10,()->{gravar.set(true);v.stop();});
        v.run();
        return atr;
    }

    public Atributos getAtributosDF(String[] menu, Atributos aot, AtomicBoolean control, AtomicBoolean gravar){
        ViewJogo v = new ViewJogo(menu);
        AtributosDefesa atr = new AtributosDefesa(aot);
        v.setHandler(1,()->{atr.setVelocidade(scanAtributo("Velocidade"));v.stop();control.set(true);});
        v.setHandler(2,()->{atr.setResistencia(scanAtributo("Resistência"));v.stop();control.set(true);});
        v.setHandler(3,()->{atr.setDestreza(scanAtributo("Destreza"));v.stop();control.set(true);});
        v.setHandler(4,()->{atr.setImpulsao(scanAtributo("Impulsão"));v.stop();control.set(true);});
        v.setHandler(5,()->{atr.setJogoDeCabeca(scanAtributo("Jogo de Cabeça"));v.stop();control.set(true);});
        v.setHandler(6,()->{atr.setRemate(scanAtributo("Remate"));v.stop();control.set(true);});
        v.setHandler(7,()->{atr.setControloDePasse(scanAtributo("Controlo de Passe"));v.stop();control.set(true);});
        v.setHandler(8,()->{atr.setCortes(scanAtributo("Cortes"));v.stop();control.set(true);});
        v.setHandler(9,()->{atr.setPosicionamentoDefensivo(scanAtributo("Posicionamento Defensivo"));v.stop();control.set(true);});
        v.setHandler(10,()->{gravar.set(true);v.stop();});
        v.run();
        return atr;
    }

    public Atributos getAtributosMD(String[] menu,Atributos aot, AtomicBoolean control, AtomicBoolean gravar){
        ViewJogo v = new ViewJogo(menu);
        AtributosMedio atr = new AtributosMedio(aot);
        v.setHandler(1,()->{atr.setVelocidade(scanAtributo("Velocidade"));v.stop();control.set(true);});
        v.setHandler(2,()->{atr.setResistencia(scanAtributo("Resistência"));v.stop();control.set(true);});
        v.setHandler(3,()->{atr.setDestreza(scanAtributo("Destreza"));v.stop();control.set(true);});
        v.setHandler(4,()->{atr.setImpulsao(scanAtributo("Impulsão"));v.stop();control.set(true);});
        v.setHandler(5,()->{atr.setJogoDeCabeca(scanAtributo("Jogo de Cabeça"));v.stop();control.set(true);});
        v.setHandler(6,()->{atr.setRemate(scanAtributo("Remate"));v.stop();control.set(true);});
        v.setHandler(7,()->{atr.setControloDePasse(scanAtributo("Controlo de Passe"));v.stop();control.set(true);});
        v.setHandler(8,()->{atr.setRecuperacaoDeBolas(scanAtributo("Recuperação de Bolas"));v.stop();control.set(true);});
        v.setHandler(9,()->{atr.setVisaoDeJogo(scanAtributo("Visão de Jogo"));v.stop();control.set(true);});
        v.setHandler(10,()->{gravar.set(true);v.stop();});
        v.run();
        return atr;
    }

    public Atributos getAtributosAV(String[] menu, Atributos aot, AtomicBoolean control, AtomicBoolean gravar){
        ViewJogo v = new ViewJogo(menu);
        AtributosAvancado atr = new AtributosAvancado(aot);
        v.setHandler(1,()->{atr.setVelocidade(scanAtributo("Velocidade"));v.stop();control.set(true);});
        v.setHandler(2,()->{atr.setResistencia(scanAtributo("Resistência"));v.stop();control.set(true);});
        v.setHandler(3,()->{atr.setDestreza(scanAtributo("Destreza"));v.stop();control.set(true);});
        v.setHandler(4,()->{atr.setImpulsao(scanAtributo("Impulsão"));v.stop();control.set(true);});
        v.setHandler(5,()->{atr.setJogoDeCabeca(scanAtributo("Jogo de Cabeça"));v.stop();control.set(true);});
        v.setHandler(6,()->{atr.setRemate(scanAtributo("Remate"));v.stop();control.set(true);});
        v.setHandler(7,()->{atr.setControloDePasse(scanAtributo("Controlo de Passe"));v.stop();control.set(true);});
        v.setHandler(8,()->{atr.setDesmarcacao(scanAtributo("Desmarcação"));v.stop();control.set(true);});
        v.setHandler(9,()->{atr.setPenaltis(scanAtributo("Penáltis"));v.stop();control.set(true);});
        v.setHandler(10,()->{gravar.set(true);v.stop();});
        v.run();
        return atr;
    }

    public Atributos getAtributosLT(String[] menu, Atributos aot, AtomicBoolean control, AtomicBoolean gravar){
        ViewJogo v = new ViewJogo(menu);
        AtributosLateral atr = new AtributosLateral(aot);
        v.setHandler(1,()->{atr.setVelocidade(scanAtributo("Velocidade"));v.stop();control.set(true);});
        v.setHandler(2,()->{atr.setResistencia(scanAtributo("Resistência"));v.stop();control.set(true);});
        v.setHandler(3,()->{atr.setDestreza(scanAtributo("Destreza"));v.stop();control.set(true);});
        v.setHandler(4,()->{atr.setImpulsao(scanAtributo("Impulsão"));v.stop();control.set(true);});
        v.setHandler(5,()->{atr.setJogoDeCabeca(scanAtributo("Jogo de Cabeça"));v.stop();control.set(true);});
        v.setHandler(6,()->{atr.setRemate(scanAtributo("Remate"));v.stop();control.set(true);});
        v.setHandler(7,()->{atr.setControloDePasse(scanAtributo("Controlo de Passe"));v.stop();control.set(true);});
        v.setHandler(8,()->{atr.setPrecisaoCruzamentos(scanAtributo("Precisão de Cruzamentos"));v.stop();control.set(true);});
        v.setHandler(9,()->{atr.setDrible(scanAtributo("Drible"));v.stop();control.set(true);});
        v.setHandler(10,()->{gravar.set(true);v.stop();});
        v.run();
        return atr;
    }


    public Atributos getAtributos(Atributos aot){
        String [] menu = new String[11];
        menu[0] = menuAtributos[0];
        menu[10] = menuAtributos[8];
        AtomicBoolean control = new AtomicBoolean(true);
        AtomicBoolean gravar = new AtomicBoolean(false);
        Atributos novos = aot.clone();
        while(control.get()) {
            control.set(false);
            menu[1] = menuAtributos[1]+" ["+novos.getVelocidade()+"]";
            menu[2] = menuAtributos[2]+" ["+novos.getResistencia()+"]";
            menu[3] = menuAtributos[3]+" ["+novos.getDestreza()+"]";
            menu[4] = menuAtributos[4]+" ["+novos.getImpulsao()+"]";
            menu[5] = menuAtributos[5]+" ["+novos.getJogoDeCabeca()+"]";
            menu[6] = menuAtributos[6]+" ["+novos.getRemate()+"]";
            menu[7] = menuAtributos[7]+" ["+novos.getControloDePasse()+"]";
            if(novos instanceof AtributosGR) {
                menu[8] = menuatributosGR[0] + " [" + ((AtributosGR) novos).getElasticidade() + "]";
                menu[9] = menuatributosGR[1] + " [" + ((AtributosGR) novos).getReflexos() + "]";
                novos = getAtributosGR(menu, novos, control, gravar);
            }
            else if(novos instanceof AtributosDefesa) {
                menu[8] = menuatributosDF[0] + " [" + ((AtributosDefesa) novos).getPosicionamentoDefensivo() + "]";
                menu[9] = menuatributosDF[1] + " [" + ((AtributosDefesa) novos).getCortes() + "]";
                novos = getAtributosDF(menu, novos, control, gravar);
            }
            else if(novos instanceof AtributosMedio) {
                menu[8] = menuatributosMD[0] + " [" + ((AtributosMedio) novos).getRecuperacaoDeBolas() + "]";
                menu[9] = menuatributosMD[1] + " [" + ((AtributosMedio) novos).getVisaoDeJogo() + "]";
                novos = getAtributosMD(menu, novos, control, gravar);
            }
            else if(novos instanceof AtributosAvancado) {
                menu[8] = menuatributosAV[0] + " [" + ((AtributosAvancado) novos).getDesmarcacao() + "]";
                menu[9] = menuatributosAV[1] + " [" + ((AtributosAvancado) novos).getPenaltis() + "]";
                novos = getAtributosAV(menu, novos, control, gravar);
            }
            else if(novos instanceof AtributosLateral) {
                menu[8] = menuatributosLT[0] + " [" + ((AtributosLateral) novos).getPrecisaoCruzamentos() + "]";
                menu[9] = menuatributosLT[1] + " [" + ((AtributosLateral) novos).getDrible() + "]";
                novos = getAtributosLT(menu, novos, control, gravar);
            }
            else return aot;
        }
        if(gravar.get()) return novos;
        else return aot;
    }
}
