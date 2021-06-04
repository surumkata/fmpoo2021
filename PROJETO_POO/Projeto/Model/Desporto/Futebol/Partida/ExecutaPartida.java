package Desporto.Futebol.Partida;

import Desporto.Futebol.Equipa.Jogador.*;

import java.util.Random;

public class ExecutaPartida {
    private PartidaFutebol partida;
    private Jogador jogadorAtual;
    private Boolean casa; //0 -> Jogador é visitante //1 -> Jogador é visitado

    private static final double acaoRapida = 0.75, acaoLenta  = 1.5, acaoMedia = 1.25;

    public ExecutaPartida(PartidaFutebol partida) {
        this.partida = partida.clone();
        Random random = new Random();
        this.casa = random.nextBoolean();

        this.jogadorAtual = partida.getJogador(this.casa, "Medio");
    }

    public void run (){
        if(this.jogadorAtual.getPosicao().equals("Defesa")){
            runDefesa();
        }
        else if (this.jogadorAtual.getPosicao().equals("Medio")){
            runMedio();
        }
        else if (this.jogadorAtual.getPosicao().equals("Guarda-Redes")){
            runGuardaRedes();
        }
        else if (this.jogadorAtual.getPosicao().equals("Avancado")){
            runAvancado();
        }
        else if (this.jogadorAtual.getPosicao().equals("Lateral")){
            runLateral();
        }
    }

    public void runDefesa (){
        Jogador adversario = partida.getJogador(!this.casa, "Avancado");
        double overalldif = jogadorAtual.getAtributos().overall() - adversario.getAtributos().overall();

        if(overalldif > 15){
            tentaPassar("Medio","Medio");
        }
        else{
            if(tentaRoubarDefesa(adversario)){
                this.casa = !this.casa;
                this.jogadorAtual = adversario;
            }
            tentaPassar("Medio","Medio");
        }
    }

    public void runMedio (){
        Jogador adversario = partida.getJogador(!this.casa, "Medio");
        double overalldif = jogadorAtual.getAtributos().overall() - adversario.getAtributos().overall();

        Random random = new Random();
        boolean lateral = random.nextBoolean();

        if(overalldif > 15){
                if(lateral) {
                    tentaPassar("Lateral","Lateral");
                }
                else tentaPassar("Avancado","Defesa");
        }
        else{
            if(tentaRoubarMedio(adversario)) {
                this.casa = !this.casa;
                this.jogadorAtual = adversario;
            }
            if(lateral) {
                tentaPassar("Lateral","Lateral");
            }
            else tentaPassar("Avancado","Defesa");
        }
    }

    public void runLateral(){
        Jogador adversario = partida.getJogador(!this.casa, "Lateral");
        double overalldif = jogadorAtual.getAtributos().overall() - adversario.getAtributos().overall();

        if(overalldif > 15){
            tentaPassar("Avancado","Defesa");
        }
        else{
            if(tentaRoubarLateral()){
                this.casa = !this.casa;
                this.jogadorAtual = adversario;
            }
            else tentaPassar("Avancado","Defesa");
        }
    }

    public void runAvancado(){
        Jogador adversarioGR = partida.getJogador(!this.casa, "Guarda-Redes");
        Jogador adversarioDF = partida.getJogador(!this.casa, "Guarda-Redes");
        double overalldif = jogadorAtual.getAtributos().overall() - adversarioDF.getAtributos().overall();
        if(overalldif > 15){
            tentaChutar(adversarioGR);
        }
        else{
            if(tentaRoubarAvancado(adversarioDF)){
                this.casa = !this.casa;
                this.jogadorAtual = adversarioDF;
            }
            tentaChutar(adversarioGR);
        }
    }

    public void runGuardaRedes(){
        Random random = new Random();
        int irandom = random.nextInt(101);

        if(irandom <= 50){
            tentaPassar("Medio","Medio");
        }
        else if (irandom <= 75){
            tentaPassar("Lateral","Lateral");
        }
        else{
            tentaPassar("Defesa","Avancado");
        }
    }

    public void tentaChutar(Jogador adversario){
        Random random = new Random();
        int irandom = random.nextInt(20);

        AtributosGR atrGR = (AtributosGR) adversario.getAtributos();

        int dif = this.jogadorAtual.getAtributos().getRemate() - ((atrGR.getElasticidade()+atrGR.getReflexos())/2);

        if(dif >= irandom){
            partida.incGolos(this.casa);
            this.jogadorAtual = partida.getJogador(this.casa, "Medio");
        }
        else {
            this.casa = !this.casa;
            this.jogadorAtual = adversario;
        }
    }

    public void tentaPassar(String posicaoComp, String posicaoAdv){
        Random random = new Random();
        int controloDePasse = this.jogadorAtual.getAtributos().getControloDePasse();
        partida.incTimer(acaoRapida);

        if(random.nextInt(101) <= controloDePasse){
            this.jogadorAtual = partida.getJogador(this.casa, posicaoComp);
        }
        else {
            this.casa = !this.casa;
            this.jogadorAtual = partida.getJogador(this.casa, posicaoAdv);
        }
    }

    public boolean tentaRoubarAvancado(Jogador adversario){
        Random random = new Random();
        int irandom = random.nextInt(30);
        partida.incTimer(acaoRapida);

        AtributosDefesa AtrD = (AtributosDefesa) adversario.getAtributos();

        int dif = this.jogadorAtual.getAtributos().getVelocidade() - AtrD.getCortes();

        if(dif >= 0){
            return false;
        }
        else
            return (irandom <= -dif);

    }


    public boolean tentaRoubarDefesa(Jogador adversario){
        Random random = new Random();
        int irandom = random.nextInt(30);
        partida.incTimer(acaoRapida);

        int veldif = this.jogadorAtual.getAtributos().getVelocidade() - adversario.getAtributos().getVelocidade();

        if(veldif >= 0){
            return false;
        }
        else
            return (irandom <= -veldif);
    }

    public boolean tentaRoubarLateral(){
        Random random = new Random();
        int irandom = random.nextInt(30);
        partida.incTimer(acaoRapida);

        AtributosLateral advAtrL = (AtributosLateral) this.jogadorAtual.getAtributos();

        int drible = advAtrL.getDrible();

        return (irandom <= drible);
    }

    public boolean tentaRoubarMedio(Jogador adversario){
        Random random = new Random();
        int irandom = random.nextInt(30);
        partida.incTimer(acaoRapida);

        AtributosMedio advAtrM = (AtributosMedio) adversario.getAtributos();

        int dif = this.jogadorAtual.getAtributos().getControloDePasse() - advAtrM.getRecuperacaoDeBolas();

        if(dif >= 0){
            return false;
        }
        else
            return (irandom <= -dif);
    }
}
