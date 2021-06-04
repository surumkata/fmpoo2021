package Desporto.Futebol.Equipa;

import Desporto.Futebol.Equipa.Jogador.Jogador;

import java.util.Set;

public class EquipaFutebol{
    private String nome;
    private Plantel plantel;


    public EquipaFutebol(){
        this("");
    }

    public EquipaFutebol(String nome){
        this.nome = nome;
        this.plantel = new Plantel();
    }

    public EquipaFutebol(String nome, Plantel plantel){
        this.nome = nome;
        this.plantel = plantel.clone();
    }

    public EquipaFutebol (EquipaFutebol equipa){
        this.nome = equipa.nome;
        this.plantel = equipa.plantel.clone();
    }

    public boolean equipaPronta(){
        return this.plantel.getTitulares().size() == 11 && this.plantel.getTatica().taticaValida();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTatica (Tatica t){
        this.plantel.setTatica(t);
    }

    public Plantel getPlantel() {
        return plantel.clone();
    }

    public void setPlantel(Plantel plantel) {
        this.plantel = plantel.clone();
    }

    public void adicionaPlantel(Jogador j){
        boolean x = false;
        int numero = j.getNumero();
        while(this.plantel.numeroOcupado(numero)) {
            if(!x){
                System.out.println("O número do jogador já está ocupado, a gerar um novo\n");
                x = true;
            }
            numero++;
        }
        j.setNumero(numero);

        int sizeDisponivel = 11 - this.plantel.getTitulares().size() + this.plantel.quantasPosicoesFaltam();
        if(!this.plantel.temPosicao(j.getPosicao()) || sizeDisponivel > 0)
            this.plantel.adicionaTitular(j);
        else
            this.plantel.adicionaSuplente(j);
        }

    public void adicionaPlantel(Set<Jogador> js){
        plantel.adicionaSuplente(js);
    }

    public EquipaFutebol clone (){
        return new EquipaFutebol(this);
    }

}
