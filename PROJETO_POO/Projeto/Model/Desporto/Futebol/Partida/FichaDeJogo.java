package Desporto.Futebol.Partida;

import Desporto.Futebol.Equipa.EquipaFutebol;

public class FichaDeJogo {
    private EquipaFutebol equipa_visitada, equipa_visitante;

    public FichaDeJogo(FichaDeJogo fcd){
        this.equipa_visitante = fcd.equipa_visitante.clone();
        this.equipa_visitada = fcd.equipa_visitada.clone();
    }

    public FichaDeJogo clone(){
        return new FichaDeJogo(this);
    }



}
