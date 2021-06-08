package Desporto.Futebol.Partida;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.*;

import Desporto.Futebol.ViewJogo;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public ExecutaPartida(PartidaFutebol partida) {
        this.partida = partida.clone();
        this.random = new Random();
        this.casa = random.nextBoolean();
        this.comecou = this.casa;

        this.jogadorAtual = partida.getJogador(this.casa, "Medio");
    }

    public ExecutaPartida(EquipaFutebol visitado, EquipaFutebol visitante) {
        this.partida = new PartidaFutebol(visitado,visitante);
        this.random = new Random();
        this.casa = random.nextBoolean();
        this.comecou = this.casa;

        this.jogadorAtual = partida.getJogador(this.casa, "Medio");
    }

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
            else
                this.partida.desgasteEquipas();
            return (this.partida.getTempo() >= 90);
        }
        return false;
    }

    public void runDefesa (ViewJogo v){
        Jogador adversario = partida.getJogador(!this.casa, "Avancado");
        double overalldif = jogadorAtual.getAtributos().overall() - adversario.getAtributos().overall();
        if(overalldif > 15){
            v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome()+" tenta passar a bola.");
            tentaPassar("Medio","Medio",v);
        }
        else{
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

    public void runMedio (ViewJogo v){
        Jogador adversario = partida.getJogador(!this.casa, "Medio");
        double overalldif = jogadorAtual.getAtributos().overall() - adversario.getAtributos().overall();

        Random random = new Random();
        boolean lateral = random.nextBoolean();

        if(overalldif > 15){
            v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome()+" pode passar a bola.");
            if(lateral) {
                tentaPassar("Lateral","Lateral",v);
            }
            else tentaPassar("Avancado","Defesa",v);
        }
        else{
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

    public void runLateral(ViewJogo v){
        Jogador adversario = partida.getJogador(!this.casa, "Lateral");
        double overalldif = jogadorAtual.getAtributos().overall() - adversario.getAtributos().overall();

        if(overalldif > 15){
            v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome()+" pode cruzar para a área.");
            tentaPassar("Avancado","Defesa",v);
        }
        else{
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

    public void runAvancado(ViewJogo v){
        Jogador adversarioGR = partida.getJogador(!this.casa, "Guarda-Redes");
        Jogador adversarioDF = partida.getJogador(!this.casa, "Defesa");
        double overalldif = jogadorAtual.getAtributos().overall() - adversarioDF.getAtributos().overall();
        if(overalldif > 15){
            tentaChutar(adversarioGR,v);
        }
        else{
            if(tentaRoubarAvancado(adversarioDF,v)){
                Random random = new Random();
                this.casa = !this.casa;
                this.jogadorAtual = adversarioDF;
            }
            else {
                v.comentariosJogo(partida.getTempo(), jogadorAtual.getNome() + " deixou o defesa para trás.");
                tentaChutar(adversarioGR, v);
            }
        }
    }

    public void runGuardaRedes(ViewJogo v){
        int irandom = random.nextInt(101);

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
                v.comentariosJogo(tempo,ANSI_GREEN+"Golo!!! É do "+this.partida.getNomeEquipa(this.casa)+", o "+adversario.getNome()+" até voou, mas não alcançou a bola."+ANSI_RESET);
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
                v.comentariosJogo(tempo,ANSI_RED+"Errou feio a baliza do "+this.partida.getNomeEquipa(this.casa)+"."+ANSI_RESET);
            else
                v.comentariosJogo(tempo,ANSI_RED+"Que grande defesa do "+adversario.getNome()+" que evita o golo ao "+this.jogadorAtual.getNome()+"."+ANSI_RESET);
            this.jogadorAtual = adversario;
            partida.incTimer(acaoRapida);
        }
    }

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

    public boolean tentaRoubarAvancado(Jogador adversario, ViewJogo v){
        int irandom = random.nextInt(201);
        partida.incTimer(acaoRapida);

        AtributosDefesa AtrD = (AtributosDefesa) adversario.getAtributos();

        int dif = 100 + this.jogadorAtual.getAtributos().getVelocidade() - AtrD.getCortes();
        v.comentariosJogo(partida.getTempo(),adversario.getNome()+" não quer deixá-lo chutar.");

        return (dif >= irandom);
    }


    public boolean tentaRoubarDefesa(Jogador adversario, ViewJogo v){
        int irandom = random.nextInt(201);
        partida.incTimer(acaoRapida);

        int dif = 100 + this.jogadorAtual.getAtributos().getVelocidade() - adversario.getAtributos().getVelocidade();
        v.comentariosJogo(partida.getTempo(),adversario.getNome()+" está a fazer pressão alta.");
        return (dif >= irandom);
    }

    public boolean tentaRoubarLateral(Jogador adversario, ViewJogo v){
        int irandom = random.nextInt(201);
        partida.incTimer(acaoRapida);

        AtributosLateral advAtrL = (AtributosLateral) this.jogadorAtual.getAtributos();

        int dif = 100 + advAtrL.getDrible() - adversario.getAtributos().getVelocidade();
        v.comentariosJogo(partida.getTempo(),adversario.getNome()+" não vai fazer a vida fácil a "+jogadorAtual.getNome()+".");
        return (dif >= irandom);
    }

    public boolean tentaRoubarMedio(Jogador adversario, ViewJogo v){
        int irandom = random.nextInt(201);
        partida.incTimer(acaoRapida);

        AtributosMedio advAtrM = (AtributosMedio) adversario.getAtributos();

        int dif = 100 + this.jogadorAtual.getAtributos().getControloDePasse() - advAtrM.getRecuperacaoDeBolas();
        v.comentariosJogo(partida.getTempo(),adversario.getNome()+" encontra-se no caminho.");

        return (dif >= irandom);
    }

    public PartidaFutebol getPartida() {
        return partida.clone();
    }

    public int getSubsRestantes (boolean visitado){
        return this.partida.getSubstituicoesRestantes(visitado);
    }

    public void decSubs (boolean visitado, String com, int n1, int n2){
        this.partida.decSubstituicoes(visitado, n1, n2);
        ViewJogo v = new ViewJogo();
        v.comentariosJogo(this.partida.getTempo(), com);
    }

    public void atualizaEquipa(boolean visitado, EquipaFutebol e){
        if(visitado) this.partida.atualizaVisitado(e);
        else this.partida.atualizaVisitante(e);
    }
}
