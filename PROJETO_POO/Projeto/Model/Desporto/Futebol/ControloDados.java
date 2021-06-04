package Desporto.Futebol;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Partida.PartidaFutebol;

import java.util.List;
import java.util.Map;

public class ControloDados {
    private Map<String, EquipaFutebol> equipas;
    private List<PartidaFutebol> partidas;



    public boolean existeEquipa(String equipa){
        return this.equipas.containsKey(equipa);
    }

    public void criarEquipa(String nomeEquipa){
        EquipaFutebol equipaNova = new EquipaFutebol(nomeEquipa);
    }
}
