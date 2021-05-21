package Desporto.Futebol.Equipa;

import Desporto.Equipa;
import Desporto.Futebol.Equipa.Jogador.Jogador;

import java.util.Set;

public class EquipaFutebol extends Equipa {
    private Plantel plantel;

    public Plantel getPlantel() {
        return plantel.clone();
    }

    public void setPlantel(Plantel plantel) {
        this.plantel = plantel.clone();
    }

    public void adicionaPlantel(Jogador j) throws JogadorInvalidoException{
        if (this.plantel.getTitulares().containsKey(j.getNumero()) || this.plantel.getSuplentes().containsKey(j.getNumero()))
            throw new JogadorInvalidoException("Este número"+j.getNumero()+"já se encontra no plantel.");
        else
            this.plantel.adicionaSuplente(j); //o adicionaSuplente já faz um clone do Jogador.
    }

    public void adicionaPlantel(Set<Jogador> js) throws JogadorInvalidoException {
        plantel.adicionaSuplente(js);
    }
}
