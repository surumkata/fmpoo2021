package Desporto.Futebol.Equipa;

import Desporto.Futebol.Equipa.Jogador.Jogador;

import java.util.Set;

public class EquipaFutebol{
    private String nome;
    private Plantel plantel;


    public EquipaFutebol(String nome, Plantel plantel){
        this.nome = nome;
        this.plantel = plantel.clone();
    }

    public EquipaFutebol (EquipaFutebol equipa){
        this.nome = equipa.nome;
        this.plantel = equipa.plantel.clone();
    }

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

    public EquipaFutebol clone (){
        return new EquipaFutebol(this);
    }

}
