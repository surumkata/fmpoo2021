package Desporto.Futebol.Partida;

import Desporto.Futebol.Equipa.Jogador.Jogador;

import java.util.Map;
import java.util.Random;

public class ExecutaPartida {
    private PartidaFutebol partida;
    private Jogador jogadorAtual;
    private Boolean jogadorVisitado; //0 -> Jogador é visitante //1 -> Jogador é visitado

    private static final double acaoRapida = 0.75, acaoLenta  = 1.5, acaoMedia = 1.25;

    public ExecutaPartida(PartidaFutebol partida) {
        this.partida = partida.clone();
        Random random = new Random();
        this.jogadorVisitado = random.nextBoolean();

        this.jogadorAtual = partida.getJogador(this.jogadorVisitado, "Medio");
    }

    public void run (){
        Map<Integer,Jogador> titularesVistado = this.partida.getEquipaTitualares(true);
        Map<Integer,Jogador> titularesVistante = this.partida.getEquipaTitualares(false);

        if(this.jogadorAtual.getPosicao().equals("Defesa")){
            runDefesa();
        }
    }

    public void runDefesa (){
        Jogador adversario = partida.getJogador(!this.jogadorVisitado, "Avancado");
        double overalldif = jogadorAtual.getAtributos().overall() - adversario.getAtributos().overall();

        if(overalldif > 15){
            if(tentaPassar()){
                this.jogadorAtual = partida.getJogador(this.jogadorVisitado, "Medio");
            }
            else {
                this.jogadorVisitado = !this.jogadorVisitado;
                this.jogadorAtual = partida.getJogador(this.jogadorVisitado, "Medio");
            }
        }
        else{
            if(tentaRoubarDefesa(adversario)){
                this.jogadorVisitado = !this.jogadorVisitado;
                this.jogadorAtual = adversario;
            }
            else if(tentaPassar()) {
                this.jogadorAtual = partida.getJogador(this.jogadorVisitado, "Medio");
            }
        }
    }


    public boolean tentaPassar(){
        Random random = new Random();
        int controloDePasse = this.jogadorAtual.getAtributos().getControloDePasse();
        partida.incTimer(acaoRapida);

        return (random.nextInt(101) <= controloDePasse);
    }

    public boolean tentaRoubarDefesa(Jogador adversario){
        Random random = new Random();
        int irandom = random.nextInt(30);

        int veldif = this.jogadorAtual.getAtributos().getVelocidade() - adversario.getAtributos().getVelocidade();

        if(veldif >= 0){
            return false;
        }
        else
            return (irandom <= -veldif);
    }
}
