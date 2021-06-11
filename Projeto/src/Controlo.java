import Desporto.Futebol.ANSIIColour;
import Desporto.Futebol.TratamentoDados;
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

/**
 * Classe responsável por adicionar um menu a um evento da aplicação, onde se incluem um controlador de execução de partidas, um scanner,
 * array de strings para o menu principal, para o menu dos dados, para o menu das gravações os dados, para o menu das leituras dos dados,
 * para o menu da simulação de uma partida, para o menu da edição de equipas, para o menu de edição de jogadores, para o menu de criação de dados,
 * para o menu da criação de jogadores, para o menu das posições dos jogadores, para o menu da escolha do esquema tático e dos atributos dos jogadores
 */
public class Controlo implements ANSIIColour{
    private TratamentoDados cd;
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

    private final String[] menuSimulacoes = new String[]{
            "Simulacao",
            "Fazer Simulacao Rapida",
            "Fazer Simulacao Normal"};

    private final String[] menuSimulacaoRapida = new String[]{
            "Simulacao Rapida",
            "Começar",
            "Programar substituições",
            "Programar substituições"};

    private final String[] menuEditarEquipa = new String[]{
            "Equipa",
            "Editar Nome",
            "Ver/Editar Jogadores",
            "Editar Tatica",
            "Escolher Titulares",
            "Ver Histórico",
            "Fill Equipa",
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
            "3-6-1 (1GR|2LT|1DF|6MD|1AV)",
            "5-4-1 (1GR|2LT|3DF|4MD|1AV)",
            "5-3-2 (1GR|2LT|3DF|3MD|2AV)"};

    private final String[] menuAtributos = new String[]{
            "Atributos",
            "Velocidade:",
            "Resistência:",
            "Destreza:",
            "Impulsão:",
            "Jogo de cabeça:",
            "Remate:",
            "Controlo de passe:",
            "Gravar"};

    private final String[] menuatributosGR = new String[]{
            "Elasticidade:",
            "Reflexos:"};

    private final String[] menuatributosDF = new String[]{
            "Posicionamento Defensivo:",
            "Cortes:"};

    private final String[] menuatributosMD = new String[]{
            "Recuperação de Bolas:",
            "Visão de Jogo:"};

    private final String[] menuatributosLT = new String[]{
            "Precisão de cruzamentos:",
            "Drible:"};

    private final String[] menuatributosAV = new String[]{
            "Desmarcação:",
            "Penáltis:"};

    private final String[] simulacao = new String[]{
            "Simulacao",
            "Continuar Simulacao",
            "Substituicoes ",
            "Substituicoes "};

    /**
     * Construtor vazio de controlo
     */
    public Controlo() {
        this.cd = new TratamentoDados();
    }

    /**
     * Corre o programa começando pelo menu principal
     */
    public void run() {
        ViewJogo menu = new ViewJogo(menuPrincipal);
        menu.limpaTela();
        menu.welcome();
        menu.setPreCondition(1,()->cd.simulacaoPossivel());
        menu.setHandler(1, this::simularJogo);
        menu.setHandler(2, this::dados);
        menu.run();
    }

    /**
     * Menu das escolhas das equipas para jogar
     */
    private void simularJogo(){
        List<String> es = this.cd.nomesEquipasProntas();
        String [] equipasVisitadas = new String[1+es.size()];
        equipasVisitadas[0] = "Equipa Visitada";
        int x = 1;
        for(String e : es){
            equipasVisitadas[x] = e;
            x++;
        }
        AtomicBoolean control = new AtomicBoolean(false);
        String equipaVisitada = pickEquipa(equipasVisitadas,control);
        if (!control.get()) {
            ViewJogo w = new ViewJogo();
            w.welcome();
            return;
        }
        es.remove(equipaVisitada);

        String [] equipasVisitantes = new String[1+es.size()];
        equipasVisitantes[0] = "Equipa Visitante";
        x = 1;
        for(String e : es){
            equipasVisitantes[x] = e;
            x++;
        }
        control.set(false);
        String equipaVisitante = pickEquipa(equipasVisitantes, control);
        if(!control.get()){
            ViewJogo w = new ViewJogo();
            w.welcome();
            return;
        }

        ViewJogo v = new ViewJogo(menuSimulacao);
        v.limpaTela();
        v.setHandler(1,()->{escolheSimulacao(equipaVisitada,equipaVisitante);v.stop();});
        v.setHandler(2, ()->editarDados(new String[]{"",equipaVisitada,equipaVisitante,},true));
        v.run();
        v.limpaTela();
        v.welcome();

    }

    /**
     * Menu para a escolha do tipo de simulação.
     * @param equipaVisitada Equipa da casa que vai jogar
     * @param equipaVisitante Equipa visitante que vai jogar
     */
    private void escolheSimulacao (String equipaVisitada, String equipaVisitante){
        ViewJogo v = new ViewJogo(menuSimulacoes);
        v.setHandler(1,()-> simulacaoRapida(equipaVisitada,equipaVisitante));
        v.setHandler(2,()-> simulacao(equipaVisitada,equipaVisitante));
        v.run();
    }


    /**
     * Simula uma partida de futebol, mas não mostra os comentários no menu
     * @param equipaVisitada Equipa da casa que vai jogar
     * @param equipaVisitante Equipa visitante que vai jogar
     */
    private void simulacaoRapida(String equipaVisitada, String equipaVisitante) {
        String[] menu = new String[4];
        menu[0] = menuSimulacaoRapida[0];
        menu[1] = menuSimulacaoRapida[1];
        EquipaFutebol visitadaO = this.cd.getEquipaFutebol(equipaVisitada);
        EquipaFutebol visitanteO = this.cd.getEquipaFutebol(equipaVisitante);
        AtomicBoolean comecar = new AtomicBoolean(false);
        AtomicBoolean sair = new AtomicBoolean(false);
        AtomicInteger s1 = new AtomicInteger(3);
        AtomicInteger s2 = new AtomicInteger(3);
        int[][] subs1 = new int[][]{new int[]{0, 0, 0}, new int[]{0, 0, 0}};
        int[][] subs2 = new int[][]{new int[]{0, 0, 0}, new int[]{0, 0, 0}};
        EquipaFutebol equipa1 = visitadaO.clone();
        EquipaFutebol equipa2 = visitanteO.clone();
        while (!comecar.get() && !sair.get()) {
            sair.set(true);
            menu[2] = menuSimulacaoRapida[2] + " " + equipaVisitada + " [" + s1.get() + " substituições restantes]";
            menu[3] = menuSimulacaoRapida[3] + " " + equipaVisitante + " [" + s2.get() + " substituições restantes]";
            ViewJogo v = new ViewJogo(menu);
            v.setPreCondition(2, () -> (s1.get() > 0));
            v.setPreCondition(3, () -> (s2.get() > 0));
            AtomicInteger n1 = new AtomicInteger(0);
            AtomicInteger n2 = new AtomicInteger(0);
            AtomicBoolean substituicao = new AtomicBoolean(false);
            v.setHandler(1, () -> {
                comecar.set(true);
                v.stop();
                sair.set(false);
            });
            EquipaFutebol finalEquipa = equipa1;
            EquipaFutebol finalEquipa2 = equipa2;
            v.setHandler(2, () -> {
                substituicoes(finalEquipa, substituicao, n1, n2);
                if (substituicao.get()) {
                    subs1[0][3 - s1.get()] = n1.get();
                    subs1[1][3 - s1.get()] = n2.get();
                    s1.set(s1.get() - 1);
                }
                v.stop();
                sair.set(false);
            });
            v.setHandler(3, () -> {
                substituicoes(finalEquipa2, substituicao, n1, n2);
                if (substituicao.get()) {
                    subs2[0][3 - s2.get()] = n1.get();
                    subs2[1][3 - s2.get()] = n2.get();
                    s2.set(s2.get() - 1);
                }
                v.stop();
                sair.set(false);
            });
            v.run();
        }
        if(!sair.get()){
            ExecutaPartida exp = new ExecutaPartida(visitadaO, visitanteO);
            exp.setComentariosON(false);
            AtomicBoolean intervalo = new AtomicBoolean(false);
            AtomicBoolean ignorar = new AtomicBoolean(false);
            boolean posIntervalo = false;
            while (!exp.run(intervalo, ignorar)) {
                if (!posIntervalo && intervalo.get()) {
                    posIntervalo = true;
                    equipa1 = visitadaO.clone();
                    equipa2 = visitanteO.clone();
                    for (int i = 0; i < 3; i++) {
                        if (subs1[0][i] != 0) {
                            equipa1.substiuicaoJogo(subs1[0][i], subs1[1][i]);
                            exp.decSubs(true, "", subs1[0][i], subs1[1][i]);
                        }
                        if (subs2[0][i] != 0) {
                            equipa2.substiuicaoJogo(subs2[0][i], subs2[1][i]);
                            exp.decSubs(false, "", subs2[0][i], subs2[1][i]);
                        }
                    }
                    exp.atualizaEquipa(true, equipa1);
                    exp.atualizaEquipa(true, equipa2);
                }
            }
            exp.atualizaEquipa(true, visitadaO);
            exp.atualizaEquipa(false, visitanteO);
            PartidaFutebol p = exp.getPartida();
            ViewJogo v = new ViewJogo();
            v.resultado(equipaVisitada, equipaVisitante, p.getGolosVisitado(), p.getGolosVisitante());
            this.cd.adicionaPartida(p);
        }
    }


    /**
     * Simula uma partida de futebol com as várias mensagens para os vários eventos de um jogo de futebol
     * @param equipaVisitada Nome da equipa da casa
     * @param equipaVisitante Nome da equipa visitante
     */
    private void simulacao(String equipaVisitada, String equipaVisitante){
        EquipaFutebol visitadaO = this.cd.getEquipaFutebol(equipaVisitada);
        EquipaFutebol visitanteO = this.cd.getEquipaFutebol(equipaVisitante);

        ExecutaPartida exp = new ExecutaPartida(visitadaO,visitanteO);
        AtomicBoolean intervalo = new AtomicBoolean(false);
        boolean posIntervalo = false;

        String resultado;
        AtomicBoolean substituicoes = new AtomicBoolean(false);
        AtomicBoolean continuar = new AtomicBoolean(true);

        while(!exp.run(intervalo,substituicoes)){
            continuar.set(false);
            PartidaFutebol p = exp.getPartida();
            EquipaFutebol finalVisitada = p.getEquipaVisitada();
            EquipaFutebol finalVisitante = p.getEquipaVisitante();
            resultado = p.resultado();
            if(!posIntervalo && intervalo.get()) {
                posIntervalo = true;
                simulacao[0] = "Intervalo ";
            }
            else
                simulacao[0] = "Simulacao ";
            substituicoes.set(false);

            AtomicInteger s1 = new AtomicInteger(exp.getSubsRestantes(true));
            AtomicInteger s2 = new AtomicInteger(exp.getSubsRestantes(false));
            simulacao[2] = "Substituicoes "+finalVisitada.getNome()+" ["+s1.get()+" substituições restantes]";
            simulacao[3] = "Substituicoes "+finalVisitante.getNome()+" ["+s2.get()+" substituições restantes]";
            ViewJogo menuS = new ViewJogo(simulacao);

            menuS.mostraCampo("\n**Resultado** "+resultado+"\n"+p.campo(p.tatica(true),p.tatica(false)));
            AtomicBoolean substituicao = new AtomicBoolean(false);
            menuS.setPreCondition(2,()->(s1.get() > 0));
            menuS.setPreCondition(3,()->(s2.get() > 0));
            AtomicInteger n1 = new AtomicInteger(0);
            AtomicInteger n2 = new AtomicInteger(0);
            menuS.setHandler(1,()->{menuS.stop();continuar.set(true);});
            menuS.setHandler(2,()->{
                String comentario = substituicoes(finalVisitada,substituicao,n1,n2);
                if(substituicao.get()) {
                    s1.set(s1.get() - 1);
                    substituicoes.set(true);
                    exp.decSubs(true,comentario,n1.get(),n2.get());
                    menuS.stop();
                }
                continuar.set(true);
            });
            menuS.setHandler(3, ()->{
                String comentario = substituicoes(finalVisitante,substituicao,n1,n2);
                if(substituicao.get()) {
                    s2.set(s2.get() - 1);
                    substituicoes.set(true);
                    exp.decSubs(false,comentario,n1.get(),n2.get());
                    menuS.stop();
                    continuar.set(true);
                }
            });
            menuS.run();
            exp.atualizaEquipa(true,finalVisitada);
            exp.atualizaEquipa(false,finalVisitante);
            if(!continuar.get())
                break;
        }

        if(continuar.get()) {
            exp.atualizaEquipa(false, visitanteO);
            exp.atualizaEquipa(true, visitadaO);
            ViewJogo v = new ViewJogo();
            PartidaFutebol p = exp.getPartida();
            v.resultado(equipaVisitada,equipaVisitante,p.getGolosVisitado(),p.getGolosVisitante());
            this.cd.adicionaPartida(p);
        }
    }

    /**
     * Efetua uma substituição, mostrando uma mensagem a anunciar esse evento
     * @param e Equipa de futebol que vai efetuar uma substituição
     * @param substituicao Booleano verifica se foi feita a substituição
     * @param numeroTitular Número da camisola do jogador em campo
     * @param numeroATrocar Número da camisola do substituto
     * @return Mensagem a anunciar a substituição
     */
    private String substituicoes(EquipaFutebol e, AtomicBoolean substituicao, AtomicInteger numeroTitular, AtomicInteger numeroATrocar){
        Plantel p = e.getPlantel();
        int x;
        String [][] titulares = p.nomesTitulares();
        String [][] suplentes = p.nomesSuplentes();
        int tamT = titulares[0].length;
        int tamS = suplentes[0].length;
        String [] menu = new String[tamT+1];
        menu[0] = "1º Jogador da troca";
        for(x = 1; x <= tamT;x++){
            int overall = Integer.parseInt(titulares[3][x-1]);
            if(overall >= 70)
                menu[x] = ANSI_GREEN+titulares[1][x-1]+"] "+titulares[0][x-1]+" ["+titulares[2][x-1]+"] ("+overall+")"+ANSI_RESET;
            else if (overall >= 40)
                menu[x] = ANSI_YELLOW+titulares[1][x-1]+"] "+titulares[0][x-1]+" ["+titulares[2][x-1]+"] ("+overall+")"+ANSI_RESET;
            else menu[x] = ANSI_YELLOW+titulares[1][x-1]+"] "+titulares[0][x-1]+" ["+titulares[2][x-1]+"] ("+overall+")"+ANSI_RESET;
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
                    menu[x] = ANSI_GREEN + titulares[1][y] + "] " + titulares[0][y] + " [" + titulares[2][y] + "] (" + titulares[3][y] + ANSI_RESET;
                else if (titulares[2][index.get()].equals(titulares[2][y]))
                    menu[x] = ANSI_YELLOW + titulares[1][y] + "] " + titulares[0][y] + " [" + titulares[2][y] + "] (" + titulares[3][y] + ANSI_RESET;
                else
                    menu[x] = ANSI_RED + titulares[1][y] + "] " + titulares[0][y] + " [" + titulares[2][y] + "] (" + titulares[3][y] + ANSI_RESET;

                if (y == index.get() - 1) {
                    y++;
                }
            }
            menu[x - 1] = menu[x - 1] + "\n**Suplentes**";
            for (y = 0; y < tamS; y++) {
                int overallS = Integer.parseInt(suplentes[3][y]);
                if (overallS > overallT && titulares[2][index.get()].equals(suplentes[2][y]))
                    menu[y + x] = ANSI_GREEN + suplentes[1][y] + "] " + suplentes[0][y] + " [" + suplentes[2][y] + "] (" + suplentes[3][y] + ANSI_RESET;
                else if (titulares[2][index.get()].equals(suplentes[2][y]))
                    menu[y + x] = ANSI_YELLOW + suplentes[1][y] + "] " + suplentes[0][y] + " [" + suplentes[2][y] + "] (" + suplentes[3][y] + ANSI_RESET;
                else
                    menu[y + x] = ANSI_RED + suplentes[1][y] + "] " + suplentes[0][y] + " [" + suplentes[2][y] + "] (" + suplentes[3][y] + ANSI_RESET;

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

    /**
     * Menu para ler, criar, editar, gravar e fazer reset de dados
     */
    private void dados(){
        ViewJogo menu = new ViewJogo(menuDados);
        menu.limpaTela();

        menu.setHandler(1,()->{lerDados();menu.stop();});
        menu.setPreCondition(3,()->cd.existeEquipas());
        menu.setHandler(2, this::criarDados);
        menu.setHandler(3, ()->editarDados(null,false));
        menu.setHandler(4, this::gravarDados);
        menu.setHandler(5, this::resetDados);
        menu.run();
        menu.limpaTela();
        menu.welcome();
    }

    /**
     * Menu da gravação de dados
     */
    private void gravarDados(){
        ViewJogo v = new ViewJogo(gravarDados);
        
        v.setHandler(1,()->{
            v.scanFicheiro(false);
            String ficheiro = scan.nextLine();
            try {
                cd.escreverFicheiro(ficheiro);
            } catch (IOException e) {
                e.printStackTrace();
            }
            v.stop();
        });
        v.setHandler(2,()->{
            v.scanFicheiro(true);
            String ficheiro = scan.nextLine();
            try {
                cd.gravarFicheiroObjeto(ficheiro);
            } catch (IOException e) {
                e.printStackTrace();
            }
            v.stop();
        });
        v.run();
        v.limpaTela();
    }

    /**
     * Menu da leitura de dados
     */
    private void lerDados(){
        ViewJogo v = new ViewJogo(lerDados);
        
        v.setHandler(1,()->{
            try {
                v.scanFicheiro(false);
                String ficheiro = scan.nextLine();
                cd.lerFicheiro(ficheiro);
                v.stop();
            } catch (IOException | PosicaoInvalidaException e1) {
                e1.printStackTrace();
            }
        });
        v.setHandler(2,()->{
            try {
                v.scanFicheiro(true);
                String ficheiro = scan.nextLine();
                cd = cd.lerFicheiroObjeto(ficheiro);
                v.stop();
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        v.run();
        v.limpaTela();
    }

    /**
     * Reset dos dados
     */
    private void resetDados(){
        this.cd = new TratamentoDados();
    }

    /**
     * Menu para a edição de dados
     * @param menuSimulacao caso seja uma simulação necessita do menu com as equipas
     * @param simulacao true(se for na simulação) false(se não)
     */
    private void editarDados(String[] menuSimulacao, boolean simulacao){
        ViewJogo v;
        String[] equipas = cd.nomesEquipas();
        int size = equipas.length + 1;
        String[] menuEquipas = new String[size];
        if(!simulacao) {
            int x = 0;
            for (String e : equipas) {
                x++;
                menuEquipas[x] = e;
            }
            menuEquipas[0] = "Editar Equipas";
            v = new ViewJogo(menuEquipas);
        }
        else {
            v = new ViewJogo(menuSimulacao);
            size = 3;
        }
        for(int i = 1; i < size; i++){
            int finalI = i;
            v.setHandler(i,()->{
                if(simulacao) editarEquipa(menuSimulacao[finalI], true);
                else editarEquipa(menuEquipas[finalI],false);
                v.stop();
            });
        }
        v.run();
        v.limpaTela();
    }
    /**
     * Menu para a edição de uma equipa
     * @param nome Nome da equipa a editar
     * @param simulacao Booleano que verifica se está a decorrer um jogo
     */
    private void editarEquipa(String nome, boolean simulacao){
        EquipaFutebol e = cd.getEquipaFutebol(nome);
        cd.removeEquipa(nome);

        String [] es = new String[8];
        es[0] = menuEditarEquipa[0];
        es[1] = menuEditarEquipa[1]+" ["+e.getNome()+"]";
        es[2] = menuEditarEquipa[2];
        if(e.podeUsarTatica(e.getPlantel().getTatica()))
            es[3] = menuEditarEquipa[3]+" ["+e.getPlantel().getTatica().toString()+"]";
        else es[3] = menuEditarEquipa[3];
        es[4] = menuEditarEquipa[4];
        es[5] = menuEditarEquipa[5];
        es[6] = menuEditarEquipa[6];
        es[7] = menuEditarEquipa[7];
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

    /**
     * Controlador para o menu da edição de equipa
     * @param ss Array de strings com os parâmetros do menu
     * @param e Equipa de futebol a alterar
     * @param eliminacao Booleano que verifica se é para eliminar a equipa
     * @param simulacao Booleano que verifica se está a jogar
     * @return true se bem sucedido, false caso contrário
     */
    private boolean auxEditarEquipa(String [] ss, EquipaFutebol e, AtomicBoolean eliminacao, boolean simulacao){
        ViewJogo v = new ViewJogo(ss);
        v.limpaTela();
        AtomicBoolean control = new AtomicBoolean(false);
        v.setPreCondition(2,()->!simulacao && e.getPlantel().getnJogadoresNoPlantel() > 0);
        v.setPreCondition(3,()->e.getPlantel().getTitulares().size() == 11);
        v.setPreCondition(4,()->e.podeUsarTatica(e.getPlantel().getTatica()));
        v.setPreCondition(new int[]{1,5,6,7},()->!simulacao);
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
        v.setHandler(6,()->{this.cd.fillEquipa(e);v.stop();});
        v.setHandler(7,()->{eliminacao.set(true);v.stop();});
        v.run();
        return control.get();
    }

    /**
     * Menu para o histórico das partidas efetuadas por uma dada equipa
     * @param equipa Equipa a saber o histórico
     */
    private void mostrarHistorico (String equipa){
        String [] menu = this.cd.historicoString(equipa);
        List<PartidaFutebol> l = this.cd.historico(equipa);
        ViewJogo m = new ViewJogo(menu);
        m.limpaTela();
        for(int i = 1; i < menu.length; i++){
            int finalI = i-1;
            m.setHandler(i,()->{
                ViewJogo aux = new ViewJogo(new String[] {""});
                aux.historico(l.get(finalI).toString());
                aux.run();
            });
        }
        m.run();
        m.limpaTela();
    }

    /**
     * Escolhe os titulares de uma equipa de futebol
     * @param e Equipa de futebol onde se encontram os jogadores
     */
    private void escolheTitulares(EquipaFutebol e){
        Plantel p = e.getPlantel();
        p.limpaTitulares();
        Tatica t = p.getTatica();

        String[] posicoes = {"Guarda-Redes","Defesa","Lateral","Medio","Avancado"};
        int [] nposicoes = {t.getnGR(),t.getnDF(),t.getnLT(),t.getnMD(),t.getnPL()};

        int x;
        for(x = 0; x < 5; x++){
            pickTitulares(p,posicoes[x],nposicoes[x]);
        }

        e.setPlantel(p);
    }

    /**
     * Escolhe a equipa desejada pelo utilizador
     * @param equipasAEscolher Array de strings com as equipas por escolher
     * @param control Controlador
     * @return Equipa escolhida
     */
    private String pickEquipa (String[] equipasAEscolher, AtomicBoolean control){
        AtomicInteger x = new AtomicInteger(1);
        ViewJogo v = new ViewJogo(equipasAEscolher);
        v.limpaTela();
        for(int i = 1; i < equipasAEscolher.length; i++){
            int finalI = i;
            v.setHandler(i,()->{
                v.stop();
                x.set(finalI);
                control.set(true);
            });
        }
        v.run();
        v.limpaTela();

        return equipasAEscolher[x.get()];
    }

    /**
     * Menu para a escolha dos titulares para o onze inicial
     * @param p Plantel para onde se vai escolher os vários jogadores
     * @param posicao Posição para onde se vai escolher jogadores
     * @param np Número de posições por escolher
     */
    private void pickTitulares (Plantel p, String posicao, int np){
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
            int n = auxPickTitulares(menu, jogadores[1]);
            Jogador j = p.getSuplente(n);
            p.removeSuplente(n);
            p.adicionaTitular(j);
            np--;
        }
    }

    /**
     * Controlador para o menu da escolha dos titulares
     * @param menu Array de strings com os parâmetros do menu
     * @param numeros Array de strings com os números dos jogadores (em forma de string) a serem escolhidos para titulares
     * @return Número do jogador
     */
    private int auxPickTitulares(String[] menu, String[] numeros){
        AtomicInteger x = new AtomicInteger(Integer.parseInt(numeros[0]));
        ViewJogo v = new ViewJogo(menu);
        v.limpaTela();
        for(int i = 1; i < menu.length; i++){
            int finalI = i-1;
            v.setHandler(i,()->{
                v.stop();
                x.set(Integer.parseInt(numeros[finalI]));
            });
        }
        v.run();
        v.limpaTela();
        return x.get();
    }

    /**
     * Edita vários jogadores de uma equipa de futebol
     * @param e Equipa de futebol com os jogadores a serem alterados
     */
    private void editarJogadores (EquipaFutebol e){
        int x;
        String [][] jogadores = e.nomesJogadores();
        int tam = jogadores[0].length;
        tam++;
        String [] menu = new String[tam];
        menu[0] = "Jogadores";
        for(x = 0; x < jogadores[0].length;){
            x++;
            menu[x] = "["+jogadores[1][x-1]+"] "+jogadores[0][x-1]+" ("+jogadores[2][x-1]+")";
        }
        ViewJogo v = new ViewJogo(menu);
        v.limpaTela();
        for(int i = 1; i < tam; i++){
            int finalI = i-1;
            v.setHandler(i,()->{
                editarJogador(e, Integer.parseInt(jogadores[1][finalI]));
                v.stop();
            });
        }
        v.run();
        v.limpaTela();
    }

    /**
     * Menu para a edição de um jogador
     * @param e Equipa de futebol onde o jogador se encontra
     * @param numero Número do jogador a encontrar
     */
    private void editarJogador(EquipaFutebol e, int numero){
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

    /**
     * Controlador para o menu da edição de jogadores
     * @param ss Array de strings com os parâmetros do menu
     * @param j Jogador a ser alterado
     * @param transferencia Booleano que verifica que um jogador vai ser transferido
     * @return true se bem sucedido, false caso contrário
     */
    private boolean auxEditarJogador(String[] ss, Jogador j, AtomicBoolean transferencia){
        ViewJogo editJ = new ViewJogo(ss);
        editJ.limpaTela();
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
            ViewJogo aux = new ViewJogo(new String[] {""});
            aux.run();
        });
        editJ.setHandler(7,()->{
            transferencia.set(true);
            editJ.stop();
        });
        editJ.run();
        editJ.limpaTela();
        return control.get();
    }

    /**
     * Menu para detetar uma string introduzida pelo utilizador
     * @return String introduzida pelo utilizador
     */
    private String auxScan(){
        ViewJogo v = new ViewJogo();
        v.scan();
        return this.scan.nextLine();
    }

    /**
     * Controlador para o menu de introdução de strings
     * @return Valor entre 0 e 100
     */
    private int auxScanInt(){
        ViewJogo v = new ViewJogo();
        int i = -1;
        while(i == -1) {
            v.scan();
            try {
                i = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                i = -1;
            }
        }
        if(i > 0 && i <= 99){
            return i;
        }
        else return auxScanInt();
    }

    /**
     * Menu para a criação de dados
     */
    private void criarDados(){
        ViewJogo menu = new ViewJogo(menuCriarDados);
        menu.setHandler(1, ()->{criarJogador();menu.stop();});
        menu.setHandler(2, ()->{criarEquipa();menu.stop();});
        menu.run();
    }

    /**
     * Menu para a criação de um jogador
     */
    private void criarJogador(){
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

    /**
     * Controlador para o menu da criação de um jogador
     * @param ss Array de strings com os parâmetros de um menu
     * @param j Jogador a criar
     * @return true se foi bem sucedido, false caso contrário
     */
    private boolean auxCriarJogador (String[] ss, Jogador j){
        ViewJogo jogadormenu = new ViewJogo(ss);
        jogadormenu.limpaTela();
        AtomicBoolean control = new AtomicBoolean(false);
        jogadormenu.setPreCondition(new int []{2,3,4},()->!j.getNome().equals(""));
        jogadormenu.setPreCondition(4,()->!j.getPosicao().equals(""));
        jogadormenu.setPreCondition(5,()->j.getNumero()!=0 && !j.getPosicao().equals("") && j.getAtributos().overall() != 0);
        jogadormenu.setHandler(1,()->{
            j.setNome(auxScan());
            jogadormenu.stop();
            control.set(true);
        });
        jogadormenu.setHandler(2,()->{
            j.setNumero(auxScanInt());
            jogadormenu.stop();
            control.set(true);
        });
        jogadormenu.setHandler(3,()->{
            j.setPosicao(escolhePosicao());
            jogadormenu.stop();
            control.set(true);
        });
        jogadormenu.setHandler(4,()->{
            j.setAtributos(getAtributos(j.getAtributos()));
            jogadormenu.stop();
            control.set(true);
        });
        jogadormenu.setHandler(5,()->{
            jogadormenu.lerEquipa();
            String nome = scan.nextLine();
            colocaJogadarNaEquipa(j,nome);
            jogadormenu.stop();
        });
        jogadormenu.run();
        jogadormenu.limpaTela();
        return control.get();
    }

    /**
     * Coloca um jogador numa equipa
     * @param j Jogador
     * @param nome Nome do jogador à escolha
     */
    private void colocaJogadarNaEquipa (Jogador j, String nome){
        while(nome.equals("")){
            nome = scan.nextLine();
        }
        cd.colocaJogador(j,nome);
    }

    /**
     * Escolhe a posição de um jogador
     * @return String com a posição escolhida
     */
    private String escolhePosicao(){
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

    /**
     * Altera a tática de uma equipa de futebol
     * @param e Equipa de futebol 
     */
    private void escolheTatica(EquipaFutebol e){
        ViewJogo menu = new ViewJogo(menuEscolheTatica);
        Tatica[] taticas = {new Tatica(1,2,2,4,2),
                            new Tatica(1,2,2,3,3),
                            new Tatica(1,3,2,3,2),
                            new Tatica(1,1,2,6,1),
                            new Tatica(1,3,2,4,1),
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

    /**
     * Cria uma equipa com um dado nome
     */
    private void criarEquipa(){
        ViewJogo v = new ViewJogo();
        v.scanEquipa();
        String nome = nomeEquipa();
        cd.criarEquipa(nome);
        v.sucessoEquipa(true);
    }

    /**
     * Devolve o nome de uma equipa à escolha
     * @return Nome da equipa
     */
    private String nomeEquipa(){
        ViewJogo v = new ViewJogo();
        v.scanEquipa();
        String nome = scan.nextLine();
        if(!cd.existeEquipa(nome))
            return nome;
        else {
            v.sucessoEquipa(false);
            return nomeEquipa();
        }
    }

    /**
     * Devolve o valor de um atributo à escolha
     * @param atributo Atributo para o qual se quer atribuir um valor
     * @return Valor para o atributo
     */
    private int scanAtributo(String atributo){
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

    /**
     * Devolve os atributos de um guarda-redes em forma de menu
     * @param menu Menu a ser alterado
     * @param aot Atributos de um guarda-redes
     * @param control Booleano para alterar o estado
     * @param gravar Booleano para gravar o objeto
     * @return Atributos de um guarda-redes em forma de um menu
     */
    private Atributos getAtributosGR(String[] menu, Atributos aot, AtomicBoolean control, AtomicBoolean gravar){
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

    /**
     * Devolve os atributos de um defesa em forma de menu
     * @param menu Menu a ser alterado
     * @param aot Atributos de um defesa
     * @param control Booleano para alterar o estado
     * @param gravar Booleano para gravar o objeto
     * @return Atributos de um defesa em forma de um menu
     */
    private Atributos getAtributosDF(String[] menu, Atributos aot, AtomicBoolean control, AtomicBoolean gravar){
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

    /**
     * Devolve os atributos de um médio em forma de menu
     * @param menu Menu a ser alterado
     * @param aot Atributos de um médio
     * @param control Booleano para alterar o estado
     * @param gravar Booleano para gravar o objeto
     * @return Atributos de um médio em forma de um menu
     */
    private Atributos getAtributosMD(String[] menu,Atributos aot, AtomicBoolean control, AtomicBoolean gravar){
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

    /**
     * Devolve os atributos de um avançado em forma de menu
     * @param menu Menu a ser alterado
     * @param aot Atributos de um avançado
     * @param control Booleano para alterar o estado
     * @param gravar Booleano para gravar o objeto
     * @return Atributos de um avançado em forma de um menu
     */
    private Atributos getAtributosAV(String[] menu, Atributos aot, AtomicBoolean control, AtomicBoolean gravar){
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

    /**
     * Devolve os atributos de um lateral em forma de menu
     * @param menu Menu a ser alterado
     * @param aot Atributos de um lateral
     * @param control Booleano para alterar o estado
     * @param gravar Booleano para gravar o objeto
     * @return Atributos de um lateral em forma de um menu
     */
    private Atributos getAtributosLT(String[] menu, Atributos aot, AtomicBoolean control, AtomicBoolean gravar){
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

    /**
     * Devolve os atributos de um jogador em forma de menu
     * @param aot Atributos de um jogador
     * @return Atributos em forma de menu
     */
    private Atributos getAtributos(Atributos aot){
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
