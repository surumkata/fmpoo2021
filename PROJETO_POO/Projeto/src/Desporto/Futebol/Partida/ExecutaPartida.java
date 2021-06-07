package Desporto.Futebol.Partida;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.*;

import Desporto.Futebol.ViewJogo;
import java.util.Random;

public class ExecutaPartida {
    private PartidaFutebol partida;
    private Jogador jogadorAtual;
    private Boolean casa; //0 -> Jogador é visitante //1 -> Jogador é visitado
    private Random random;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private static final double acaoRapida = 0.5, acaoLenta  = 1.5, acaoMedia = 1.0;

    public ExecutaPartida(PartidaFutebol partida) {
        this.partida = partida.clone();
        this.random = new Random();
        this.casa = random.nextBoolean();

        this.jogadorAtual = partida.getJogador(this.casa, "Medio");
    }

    public ExecutaPartida(EquipaFutebol visitado, EquipaFutebol visitante) {
        this.partida = new PartidaFutebol(visitado,visitante);
        this.random = new Random();
        this.casa = random.nextBoolean();

        this.jogadorAtual = partida.getJogador(this.casa, "Medio");
    }

    public void run (){
        ViewJogo v = new ViewJogo();
        v.comentariosJogo(partida.getTempo(), "Faltam poucos momentos para começar o encontro.");
        v.comentariosJogo(partida.getTempo(), ANSI_GREEN+"Já rola a bola."+ANSI_RESET);
        v.comentariosJogo(partida.getTempo(),jogadorAtual.getNome()+" com a bola.");
        while(this.partida.getTempo() < 90) {
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
        v.comentariosJogo(partida.getTempo(), "Final do Encontro");
        System.out.println("Resultado: "+this.partida.resultado());
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
            partida.incTimer(acaoLenta);
        }
        else {
            this.casa = !this.casa;
            if(comentario)
                v.comentariosJogo(tempo,ANSI_RED+"Errou feio a baliza do "+this.partida.getNomeEquipa(this.casa)+"."+ANSI_RESET);
            else
                v.comentariosJogo(tempo,ANSI_RED+"Que grande defesa do "+adversario.getNome()+" que evita o golo ao "+this.jogadorAtual.getNome()+"."+ANSI_RESET);
            this.jogadorAtual = adversario;
            partida.incTimer(acaoMedia);
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
}
