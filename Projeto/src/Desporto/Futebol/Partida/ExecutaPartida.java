package Desporto.Futebol.Partida;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.*;

import Desporto.Futebol.ViewJogo;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe responsável pela execução de uma partida, que é constituída por uma partida de futebol, pelo jogador que possui a bola, 
 * um booleano a representar se é um jogador visitante ou visitado, um booleano que representa se a partida começou ou não e uma 
 * variável de instância que gera algo aleatório
 */
public class ExecutaPartida {
    private PartidaFutebol partida;
    private Jogador jogadorAtual;
    private Boolean casa; //0 -> Jogador é visitante //1 -> Jogador é visitado
    private Boolean comecou;
    private Random random;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    private static final double acaoRapida = 0.5, acaoMedia = 1.0; 

    /**
     * Construtor de uma execução de uma partida, recebendo uma partida de futebol
     * @param partida Partida de futebol a ser executada
     */
    public ExecutaPartida(PartidaFutebol partida) {
        this.partida = partida.clone();
        this.random = new Random();
        this.casa = random.nextBoolean();
        this.comecou = this.casa;
        this.jogadorAtual = partida.getJogador(this.casa, "Medio");
    }

    /**
     * Construtor de uma execução de uma partida, recebendo duas equipas de futebol
     * @param visitado Equipa da casa
     * @param visitante Equipa visitante
     */
    public ExecutaPartida(EquipaFutebol visitado, EquipaFutebol visitante) {
        this.partida = new PartidaFutebol(visitado,visitante);
        this.random = new Random();
        this.casa = random.nextBoolean();
        this.comecou = this.casa;
        this.jogadorAtual = partida.getJogador(this.casa, "Medio");
    }

    /**
     * Mostra no ecrã os vários momentos de um jogo de futebol: mensagem de inicio de jogo, de intevalo, de final de jogo,etc.
     * @param intervalo Booleano que verifica se estamos no intervalo ou não
     * @param substituicoes Boolean que verifica se ja foram efetuadas substituições ou não
     * @return true se não não foram efetuadas substituições e se a partida já terminou, false caso contrário
     */
    public boolean run (AtomicBoolean intervalo, AtomicBoolean substituicoes){
        if(!substituicoes.get()) {
            ViewJogo v = new ViewJogo();
            if (this.partida.getTempo() == 0) {
                v.comentariosJogo(partida.getTempo(), ANSI_CYAN + "Faltam poucos momentos para começar o encontro." + ANSI_RESET);
                v.comentariosJogo(partida.getTempo(), ANSI_CYAN + "Já rola a bola." + ANSI_RESET);
                v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome() + " com a bola.");
            }
            if (this.partida.getTempo() == 45) {
                this.casa = !this.comecou;
                this.jogadorAtual = partida.getJogador(this.casa, "Medio");
                v.comentariosJogo(45.0, ANSI_CYAN + "Os jogadores voltaram do intervalo prontos para retomar a partida" + ANSI_RESET);
                v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome() + " com a bola.");
            }
            double controlTime = this.partida.getTempo();
            while (this.partida.getTempo() - controlTime < 5 && (this.partida.getTempo() < 45 || intervalo.get()) && (this.partida.getTempo() < 90)) {
                if (this.jogadorAtual.getPosicao().equals("Defesa")) {
                    runDefesa(v);
                } else if (this.jogadorAtual.getPosicao().equals("Medio")) {
                    runMedio(v);
                } else if (this.jogadorAtual.getPosicao().equals("Guarda-Redes")) {
                    runGuardaRedes(v);
                } else if (this.jogadorAtual.getPosicao().equals("Avancado")) {
                    runAvancado(v);
                } else if (this.jogadorAtual.getPosicao().equals("Lateral")) {
                    runLateral(v);
                }
            }
            if (this.partida.getTempo() >= 45 && !intervalo.get()) {
                intervalo.set(true);
                this.partida.setTempo(45.0);
                v.comentariosJogo(partida.getTempo(), ANSI_CYAN + "O árbitro apita, ambas as equipas saem para o intervalo." + ANSI_RESET);
            } else if (this.partida.getTempo() >= 90)
                v.comentariosJogo(partida.getTempo(), ANSI_CYAN + "O árbitro apita, é o final do encontro." + ANSI_RESET);
            return (this.partida.getTempo() >= 90);
        }
        return false;
    }

    /**
     * Provoca uma ação a um defesa comparando com o overall de um avançado adversário
     * @param v Menu
     */
    public void runDefesa (ViewJogo v){
        Jogador adversario = partida.getJogador(!this.casa, "Avancado");
        double overalldif = (double) jogadorAtual.getAtributos().overall() - adversario.getAtributos().overall();
        if(overalldif > 15){
            this.partida.desgastaDurantePartida(this.casa,this.jogadorAtual.getNumero());
            v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome()+" tenta passar a bola.");
            tentaPassar("Medio","Medio",v);
        }
        else{
            this.partida.desgastaDurantePartida(this.casa,this.jogadorAtual.getNumero());
            this.partida.desgastaDurantePartida(!this.casa,adversario.getNumero());
            if(tentaRoubarDefesa(adversario,v)){
                v.comentariosJogo(partida.getTempo(),"A pressão alta resultou.");
                this.casa = !this.casa;
                this.jogadorAtual = adversario;
            }
            else {
                v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome() + " deixa o avançado para trás e tenta passar a bola");
                tentaPassar("Medio", "Medio", v);
            }
        }
    }

    /**
     * Provoca uma ação a um médio comparando com o overall de um médio adversário
     * @param v Menu
     */
    public void runMedio (ViewJogo v){
        Jogador adversario = partida.getJogador(!this.casa, "Medio");
        double overalldif = (double) jogadorAtual.getAtributos().overall() - adversario.getAtributos().overall();

        boolean lateral = random.nextBoolean();

        if(overalldif > 15){
            this.partida.desgastaDurantePartida(this.casa,this.jogadorAtual.getNumero());
            v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome()+" pode passar a bola.");
            if(lateral) {
                tentaPassar("Lateral","Lateral",v);
            }
            else tentaPassar("Avancado","Defesa",v);
        }
        else{
            this.partida.desgastaDurantePartida(this.casa,this.jogadorAtual.getNumero());
            this.partida.desgastaDurantePartida(!this.casa,adversario.getNumero());
            if(tentaRoubarMedio(adversario,v)) {
                this.casa = !this.casa;
                this.jogadorAtual = adversario;
                v.comentariosJogo(partida.getTempo(),jogadorAtual.getNome()+" levou a melhor sobre o seu adversário.");
            }
            else {
                v.comentariosJogo(partida.getTempo(),jogadorAtual.getNome()+" levou a melhor sobre o seu adversário e pode passar a bola.");
                if (lateral) {
                    tentaPassar("Lateral", "Lateral", v);
                } else tentaPassar("Avancado", "Defesa", v);
            }
        }
    }

    /**
     * Provoca uma ação a um lateral comparando com o overall de um lateral adversário
     * @param v Menu
     */
    public void runLateral(ViewJogo v){
        Jogador adversario = partida.getJogador(!this.casa, "Lateral");
        double overalldif = (double) jogadorAtual.getAtributos().overall() - adversario.getAtributos().overall();

        if(overalldif > 15){
            this.partida.desgastaDurantePartida(this.casa,this.jogadorAtual.getNumero());
            v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome()+" pode cruzar para a área.");
            tentaPassar("Avancado","Defesa",v);
        }
        else{
            this.partida.desgastaDurantePartida(this.casa,this.jogadorAtual.getNumero());
            this.partida.desgastaDurantePartida(!this.casa,adversario.getNumero());
            if(tentaRoubarLateral(adversario,v)){
                this.casa = !this.casa;
                v.comentariosJogo(partida.getTempo(),adversario.getNome()+" ganha o duelo com "+jogadorAtual.getNome()+".");
                this.jogadorAtual = adversario;
            }
            else {
                v.comentariosJogo(partida.getTempo(),jogadorAtual.getNome()+" finta o "+adversario.getNome()+" e pode cruzar.");
                tentaPassar("Avancado","Defesa",v);
            }
        }
    }

    /**
     * Provoca uma ação a um avançado comparando com o overall do guarda-redes ou de um defesa adversário 
     * @param v Menu
     */
    public void runAvancado(ViewJogo v){
        Jogador adversarioGR = partida.getJogador(!this.casa, "Guarda-Redes");
        Jogador adversarioDF = partida.getJogador(!this.casa, "Defesa");
        double overalldif = (double) jogadorAtual.getAtributos().overall() - adversarioDF.getAtributos().overall();
        if(overalldif > 15){
            this.partida.desgastaDurantePartida(this.casa,this.jogadorAtual.getNumero());
            this.partida.desgastaDurantePartida(!this.casa,adversarioGR.getNumero());
            tentaChutar(adversarioGR,v);
        }
        else{
            this.partida.desgastaDurantePartida(this.casa,this.jogadorAtual.getNumero());
            this.partida.desgastaDurantePartida(!this.casa,adversarioDF.getNumero());
            if(tentaRoubarAvancado(adversarioDF,v)){
                this.casa = !this.casa;
                this.jogadorAtual = adversarioDF;
            }
            else {
                v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome() + " deixou o defesa para trás.");
                tentaChutar(adversarioGR, v);
            }
        }
    }

    /**
     * Provoca uma ação a um guarda-redes conforme o seu desgaste ao longo da partida
     * @param v Menu
     */
    public void runGuardaRedes(ViewJogo v){
        int irandom = random.nextInt(101);
        this.partida.desgastaDurantePartida(this.casa,this.jogadorAtual.getNumero());

        if(irandom <= 70){
            v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome()+" chuta a bola para o meio campo.");
            tentaPassar("Medio","Medio",v);
        }
        else if (irandom <= 85){
            v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome()+" joga uma bola alta na linha.");
            tentaPassar("Lateral","Lateral",v);
        }
        else{
            v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome()+" opta por jogar curto.");
            tentaPassar("Defesa","Avancado",v);
        }
    }

    /**
     * Mostra se um jogador conseguiu rematar para a baliza através de diferentes mensagens no menu.
     * @param adversario Jogador adversário
     * @param v Menu
     */
    public void tentaChutar(Jogador adversario, ViewJogo v){
        int irandom = random.nextInt(400);
        boolean comentario = random.nextBoolean();
        double tempo = this.partida.getTempo();
        if(comentario)
            v.comentariosJogo(tempo, ANSI_YELLOW+"O "+this.jogadorAtual.getNome()+" encontra-se enquadrado com a baliza."+ANSI_RESET);
        else
            v.comentariosJogo(tempo, ANSI_YELLOW+"O "+this.jogadorAtual.getNome()+" pode rematar à baliza."+ANSI_RESET);
        AtributosGR atrGR = (AtributosGR) adversario.getAtributos();

        int dif = 100 + this.jogadorAtual.getAtributos().getRemate() - ((atrGR.getElasticidade()+atrGR.getReflexos())/2);
        v.comentariosJogo(tempo, "Rematouu!");
        if(dif >= irandom){
            comentario = random.nextBoolean();
            if(comentario)
                v.comentariosJogo(tempo,ANSI_GREEN+"Golo!!! É do "+this.partida.getNomeEquipa(this.casa)+", o "+adversario.getNome()+" esticou-se todo, mas não alcançou a bola."+ANSI_RESET);
            else
                v.comentariosJogo(tempo,ANSI_GREEN+"Marcou, está lá dentro, mais um para a conta do "+this.jogadorAtual.getNome()+"."+ANSI_RESET);
            partida.incGolos(this.casa);
            this.casa = !this.casa;
            this.jogadorAtual = partida.getJogador(this.casa, "Medio");
            partida.incTimer(acaoMedia);
        }
        else {
            this.casa = !this.casa;
            if(comentario)
                v.comentariosJogo(tempo,ANSI_RED+"Que falhanço de "+this.jogadorAtual.getNome()+ "! Não acertou na baliza do "+this.partida.getNomeEquipa(this.casa)+"."+ANSI_RESET);
            else
                v.comentariosJogo(tempo,ANSI_RED+"Grande defesa do "+adversario.getNome()+" que evita o golo quase certo ao "+this.jogadorAtual.getNome()+"."+ANSI_RESET);
            this.jogadorAtual = adversario;
            partida.incTimer(acaoRapida);
        }
    }

    /**
     * Mostra se um jogador consegue passar a bola
     * @param posicaoComp Posição do jogador que possui a bola
     * @param posicaoAdv Posição do adversário que pode intersetar a bola
     * @param v Menu
     */
    public void tentaPassar(String posicaoComp, String posicaoAdv, ViewJogo v){
        int controloDePasse = this.jogadorAtual.getAtributos().getControloDePasse();
        partida.incTimer(acaoRapida);

        boolean comentario = random.nextBoolean();
        if(random.nextInt(101) <= controloDePasse){
            this.jogadorAtual = partida.getJogador(this.casa, posicaoComp);
            if(comentario) v.comentariosJogo(partida.getTempo(),"A bola chega até aos pés de "+jogadorAtual.getNome()+".");
            else v.comentariosJogo(partida.getTempo(),"Que grande passe para "+jogadorAtual.getNome()+".");
        }
        else {
            this.casa = !this.casa;
            this.jogadorAtual = partida.getJogador(this.casa, posicaoAdv);
            if(comentario) v.comentariosJogo(partida.getTempo(),jogadorAtual.getNome()+" interceta a bola.");
            else v.comentariosJogo(partida.getTempo(), "Que passe feio!");
        }
    }

    /**
     * Verifica se um defesa consegue roubar a bola a um avançado
     * @param adversario Jogador adversário que quer roubar a bola
     * @param v Menu
     * @return true se conseguiu roubar a bola, false caso contrário
     */
    public boolean tentaRoubarAvancado(Jogador adversario, ViewJogo v){
        int irandom = random.nextInt(201);
        partida.incTimer(acaoRapida);

        AtributosDefesa atrD = (AtributosDefesa) adversario.getAtributos();

        int dif = 100 + this.jogadorAtual.getAtributos().getVelocidade() - atrD.getCortes();
        v.comentariosJogo(partida.getTempo(),adversario.getNome()+" não quer deixá-lo chutar.");

        return (dif >= irandom);
    }

    /**
     * Verifica se um jogador adversário consegue roubar a bola a um jogador com a bola
     * @param adversario Jogador que quer roubar a bola
     * @param v Menu
     * @return true se conseguiu roubar a bola, false caso contrário
     */
    public boolean tentaRoubarDefesa(Jogador adversario, ViewJogo v){
        int irandom = random.nextInt(201);
        partida.incTimer(acaoRapida);

        int dif = 100 + this.jogadorAtual.getAtributos().getVelocidade() - adversario.getAtributos().getVelocidade();
        v.comentariosJogo(partida.getTempo(),adversario.getNome()+" está a fazer pressão alta.");
        return (dif >= irandom);
    }

    /**
     * Verifica se um lateral consegue roubar a bola a um jogador com a bola
     * @param adversario Jogador que quer roubar a bola
     * @param v Menu
     * @return true se consegue roubar a bola, false caso contrário
     */
    public boolean tentaRoubarLateral(Jogador adversario, ViewJogo v){
        int irandom = random.nextInt(201);
        partida.incTimer(acaoRapida);

        AtributosLateral advAtrL = (AtributosLateral) this.jogadorAtual.getAtributos();

        int dif = 100 + advAtrL.getDrible() - adversario.getAtributos().getVelocidade();
        v.comentariosJogo(partida.getTempo(),adversario.getNome()+" não vai facilitar a vida a "+jogadorAtual.getNome()+".");
        return (dif >= irandom);
    }

    /**
     * Verifica se um médio consegue roubar a bola a um jogador com a bola
     * @param adversario Jogador que quer roubar a bola
     * @param v Menu
     * @return true se consegue roubar a bola, false caso contrário
     */
    public boolean tentaRoubarMedio(Jogador adversario, ViewJogo v){
        int irandom = random.nextInt(201);
        partida.incTimer(acaoRapida);

        AtributosMedio advAtrM = (AtributosMedio) adversario.getAtributos();

        int dif = 100 + this.jogadorAtual.getAtributos().getControloDePasse() - advAtrM.getRecuperacaoDeBolas();
        v.comentariosJogo(partida.getTempo(),adversario.getNome()+" encontra-se no caminho.");

        return (dif >= irandom);
    }

    /**
     * Devolve uma cópia da partida da execução de uma partida de futebol
     * @return Cópia da partida de futebol
     */
    public PartidaFutebol getPartida() {
        return partida.clone();
    }

    /**
     * Devolve o número de substituições que a equipa adversária ainda pode fazer
     * @param visitado Booleano a indicar se é a equipa visitante
     * @return Número de substituições que ainda podem ser feitas
     */
    public int getSubsRestantes (boolean visitado){
        return this.partida.getSubstituicoesRestantes(visitado);
    }

    /**
     * Apresenta o comentário de uma substituição, indicando quem entra em campo e quem sai
     * @param visitado Booleano a indicar se é a equipa visitante 
     * @param com Comentário a aparecer no menu
     * @param n1 Jogador que sai da partida
     * @param n2 Jogador que entra na partida
     */
    public void decSubs (boolean visitado, String com, int n1, int n2){
        this.partida.decSubstituicoes(visitado, n1, n2);
        ViewJogo v = new ViewJogo();
        v.comentariosJogo(this.partida.getTempo(), com);
    }

    /**
     * Devolve uma cópia de uma equipa de futebol atualizada
     * @param visitado Booleano a verificar se é equipa visitada
     * @param e Equipa de futebol original
     */
    public void atualizaEquipa(boolean visitado, EquipaFutebol e){
        if(visitado) this.partida.atualizaVisitado(e);
        else this.partida.atualizaVisitante(e);
    }
}
