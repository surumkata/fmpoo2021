package Desporto.Futebol.Partida;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.Jogador;

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
    private EquipaFutebol equipaVisitante, equipaVisitada;
    private LocalDate data;

    public PartidaFutebol (){
        this.tempo = 0.0;
        this.golosVisitante = 0;
        this.golosVisitado = 0;
        this.substituicoesVisitante = 0;
        this.substituicoesVisitados = 0;
        this.data = null;
        this.equipaVisitante = new EquipaFutebol();
        this.equipaVisitante = new EquipaFutebol();
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
    }

    //Talvez nao seja necessario
    public PartidaFutebol(EquipaFutebol equipaVisitada, EquipaFutebol equipaVisitante, LocalDate d) {
        this.tempo = 0;
        this.golosVisitado = 0;
        this.golosVisitante = 0;
        this.substituicoesVisitante = 3;
        this.substituicoesVisitados = 3;
        this.data = d;
        this.equipaVisitada = equipaVisitada.clone();
        this.equipaVisitante = equipaVisitante.clone();
    }

    public PartidaFutebol clone () {
        return new PartidaFutebol(this);
    }

    public void decSubstituicoes(boolean visitada){
        if (visitada) {
            this.substituicoesVisitados--;
        }
        else this.substituicoesVisitante--;
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

    public LocalDate stringToLocalDateTime(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(s, formatter);
    }

    //                                  ******       *****     ***     
    // Jogo:<EquipaCasa>,<EquipaFora>,<ScoreCasa>,<ScoreFora>,<Data>,<JogadoresCasa>,<SubstituicoesCasa>,<JogadoresFora>,<SubstituicoesFora>
    // Jogo:Sporting Club Shostakovich,Mendelssohn F. C.,0,0,2021-03-30,43,30,1,22,33,11,38,31,39,6,12,22->37,43->25,25->3,2,1,40,16,25,49,41,17,14,33,36,1->42,49->31,14->45

    public PartidaFutebol (String l){
        this();
        String[] parametros = l.split(",");
        this.golosVisitado = Integer.parseInt(parametros[2]);
        this.golosVisitante = Integer.parseInt(parametros[3]);
        this.data = stringToLocalDateTime(parametros[4]);
        //completar


    }
}


