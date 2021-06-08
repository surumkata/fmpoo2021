package Desporto.Futebol.Partida;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.Jogador;
import Desporto.Futebol.Equipa.Plantel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class PartidaFutebol {
    private double tempo;
    private int golosVisitante;
    private int golosVisitado;
    private int substituicoesVisitante, substituicoesVisitados;
    private int[][] subsVisitante, subsVisitada;
    private EquipaFutebol equipaVisitante, equipaVisitada;
    private LocalDate data;

    public PartidaFutebol (){
        this.tempo = 0.0;
        this.golosVisitante = 0;
        this.golosVisitado = 0;
        this.substituicoesVisitante = 0;
        this.substituicoesVisitados = 0;
        this.data = LocalDate.now();
        this.equipaVisitante = new EquipaFutebol();
        this.equipaVisitante = new EquipaFutebol();
        this.subsVisitante = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
        this.subsVisitada = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
    }

    public PartidaFutebol (PartidaFutebol partida){
        this.tempo = partida.tempo;
        this.golosVisitado = partida.golosVisitado;
        this.golosVisitante = partida.golosVisitante;
        this.substituicoesVisitados = partida.substituicoesVisitados;
        this.substituicoesVisitante = partida.substituicoesVisitante;
        this.data = partida.data; //clone?
        this.equipaVisitada = partida.equipaVisitada.clone();
        this.equipaVisitante = partida.equipaVisitante.clone();
        this.subsVisitante = partida.subsVisitante.clone();
        this.subsVisitada = partida.subsVisitada.clone();
    }

    public PartidaFutebol(EquipaFutebol equipaVisitada, EquipaFutebol equipaVisitante) {
        this.tempo = 0;
        this.golosVisitado = 0;
        this.golosVisitante = 0;
        this.substituicoesVisitante = 3;
        this.substituicoesVisitados = 3;
        this.data = LocalDate.now();
        this.equipaVisitada = equipaVisitada.clone();
        this.equipaVisitante = equipaVisitante.clone();
        this.subsVisitante = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
        this.subsVisitada = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
    }

    //Talvez nao seja necessario
    public PartidaFutebol(EquipaFutebol equipaVisitada, EquipaFutebol equipaVisitante, LocalDate d, int golosA, int golosB) {
        this.tempo = 0;
        this.golosVisitado = golosA;
        this.golosVisitante = golosB;
        this.substituicoesVisitante = 3;
        this.substituicoesVisitados = 3;
        this.data = d;
        this.equipaVisitada = equipaVisitada.clone();
        this.equipaVisitante = equipaVisitante.clone();
        this.subsVisitante = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
        this.subsVisitada = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
    }

    public PartidaFutebol clone () {
        return new PartidaFutebol(this);
    }

    public int getSubstituicoesRestantes(boolean visitado) {
        if(visitado) return substituicoesVisitados;
        else return substituicoesVisitante;
    }

    public void decSubstituicoes(boolean visitada, int n1, int n2){
        if (visitada) {
            this.subsVisitada[0][3-substituicoesVisitados] = n1;
            this.subsVisitada[1][3-substituicoesVisitados] = n2;
            this.substituicoesVisitados--;
        }
        else {
            this.subsVisitante[0][3-substituicoesVisitante] = n1;
            this.subsVisitante[1][3-substituicoesVisitante] = n2;
            this.substituicoesVisitante--;
        }
    }

    public void desgasteEquipas(){
        this.equipaVisitada.desgasteJogo();
        this.equipaVisitante.desgasteJogo();
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public Jogador getJogador(boolean visitada, int numero){
        if(visitada){
            return this.equipaVisitada.getJogador(numero);
        }
        else return this.equipaVisitante.getJogador(numero);
    }

    public Jogador getJogador(boolean visitada, String posicao){
        List<Jogador> js;

        if(visitada){
            js = this.equipaVisitada.getPlantel().getTitulares().values().stream().
                    filter(e -> e.getPosicao().equals(posicao)).collect(Collectors.toList());
        }
        else {
            js = this.equipaVisitante.getPlantel().getTitulares().values().stream().
                    filter(e -> e.getPosicao().equals(posicao)).collect(Collectors.toList());
        }
        Random i = new Random();
        return js.get(i.nextInt(js.size()));
    }

    public Map<Integer, Jogador> getEquipaTitualares(boolean visitada){
        if(visitada) return this.equipaVisitada.getPlantel().getTitulares();
        else return this.equipaVisitante.getPlantel().getTitulares();
    }

    public double getTempo() {
        return tempo;
    }

    public String getNomeEquipa(boolean visitado){
        if (visitado) {
            return this.equipaVisitada.getNome();
        }
        else return this.equipaVisitante.getNome();
    }

    public String resultado(){
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(this.equipaVisitada.getNome()).append(") ").append(this.golosVisitado);
        sb.append(" - ").append(this.golosVisitante).append(" (").append(this.equipaVisitante.getNome()).append(")");
        return sb.toString();
    }

    public void incTimer(double tempo){
        this.tempo += tempo;
    }

    public void incGolos(boolean visitada){
        if (visitada) {
            this.golosVisitado++;
        }
        else this.golosVisitante++;
    }

    public void atualizaVisitado (EquipaFutebol e){
        this.equipaVisitada = e.clone();
    }

    public void atualizaVisitante (EquipaFutebol e){
        this.equipaVisitante = e.clone();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("***Partida***\n");
        sb.append("**Resultado** ").append(resultado()).append("\n");
        sb.append("**Data** ").append(this.data.toString()).append("\n");
        sb.append("**Equipa Visitada**\n");
        sb.append(equipaVisitada.toString());
        sb.append("*Substituições*\n");
        for(int i = 0; i < 3; i++){
            if(!(this.subsVisitada[0][i] == 0)){
                sb.append(this.subsVisitada[0][i]).append("->").append(this.subsVisitada[1][i]).append("\n");
            }
        }
        sb.append("----------------------\n");
        sb.append("**Equipa Visitante**\n");
        sb.append(equipaVisitante.toString());
        sb.append("*Substituições*\n");
        for(int i = 0; i < 3; i++){
            if(!(this.subsVisitante[0][i] == 0)){
                sb.append(this.subsVisitante[0][i]).append("->").append(this.subsVisitante[1][i]).append("\n");
            }
        }
        return sb.toString();
    }



    //                                  ******       *****     ***     
    // Jogo:<EquipaCasa>,<EquipaFora>,<ScoreCasa>,<ScoreFora>,<Data>,<JogadoresCasa>,<SubstituicoesCasa>,<JogadoresFora>,<SubstituicoesFora>
    // Jogo:Sporting Club Shostakovich,Mendelssohn F. C.,0,0,2021-03-30,43,30,1,22,33,11,38,31,39,6,12,22->37,43->25,25->3,2,1,40,16,25,49,41,17,14,33,36,1->42,49->31,14->45

    public PartidaFutebol (String l){
        this();

        //completar


    }
}


