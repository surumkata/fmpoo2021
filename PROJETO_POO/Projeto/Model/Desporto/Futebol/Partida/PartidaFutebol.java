package Desporto.Futebol.Partida;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.Jogador;

import java.util.HashMap;
import java.util.Map;

public class PartidaFutebol {
    private double tempo;
    private int golosVisitante;
    private int golosVisitados;
    private int substituicoesVisitante, substituicoesVisitados;
    private EquipaFutebol equipaVisitante, equipaVisitada;

    public PartidaFutebol (PartidaFutebol partida){
        this.tempo = partida.tempo;
        this.golosVisitados = partida.golosVisitados;
        this.golosVisitante = partida.golosVisitante;
        this.substituicoesVisitados = partida.substituicoesVisitados;
        this.substituicoesVisitante = partida.substituicoesVisitante;
        this.equipaVisitada = partida.equipaVisitada.clone();
        this.equipaVisitante = partida.equipaVisitante.clone();
    }

    public PartidaFutebol clone () {
        return new PartidaFutebol(this);
    }

    public Jogador getJogador(boolean visitada, String posicao){
        if(visitada){
            return this.equipaVisitada.getPlantel().getTitulares().values().stream().
                    filter(e -> e.getPosicao().equals(posicao)).findAny().get().clone();
        }
        else return this.equipaVisitante.getPlantel().getTitulares().values().stream().
                filter(e -> e.getPosicao().equals(posicao)).findAny().get().clone();
    }

    public Map<Integer, Jogador> getEquipaTitualares(boolean visitada){
        if(visitada) return this.equipaVisitada.getPlantel().getTitulares();
        else return this.equipaVisitante.getPlantel().getTitulares();
    }

    public void incTimer(double tempo){
        this.tempo += tempo;
    }

}


