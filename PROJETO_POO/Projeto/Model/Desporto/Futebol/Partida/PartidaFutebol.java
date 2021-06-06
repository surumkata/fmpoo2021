package Desporto.Futebol.Partida;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.Jogador;

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

    public PartidaFutebol (PartidaFutebol partida){
        this.tempo = partida.tempo;
        this.golosVisitado = partida.golosVisitado;
        this.golosVisitante = partida.golosVisitante;
        this.substituicoesVisitados = partida.substituicoesVisitados;
        this.substituicoesVisitante = partida.substituicoesVisitante;
        this.equipaVisitada = partida.equipaVisitada.clone();
        this.equipaVisitante = partida.equipaVisitante.clone();
    }

    public PartidaFutebol(EquipaFutebol equipaVisitada, EquipaFutebol equipaVisitante) {
        this.tempo = 0;
        this.golosVisitado = 0;
        this.golosVisitante = 0;
        this.substituicoesVisitante = 3;
        this.substituicoesVisitados = 3;
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

}


