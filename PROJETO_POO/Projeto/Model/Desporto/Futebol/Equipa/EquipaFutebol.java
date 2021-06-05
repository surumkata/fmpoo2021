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

    public void removePlantel (int numero){
        if(this.plantel.existeTitular(numero)){
            this.plantel.removeTitular(numero);
        }
        else if(this.plantel.existeSuplente(numero)){
            this.plantel.removeSuplente(numero);
        }
    }

    public Jogador getJogador (int numero){
        if(this.plantel.existeTitular(numero)){
            return this.plantel.getTitular(numero);
        }
        else if(this.plantel.existeSuplente(numero)) {
            return this.plantel.getSuplente(numero);
        }
        else return null;
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

        int sizeDisponivel = 11 - this.plantel.getTitulares().size() - this.plantel.quantasPosicoesFaltam();
        //System.out.println("Faltam "+this.plantel.quantasPosicoesFaltam()+" posicoes");
        //System.out.println(sizeDisponivel+" lugares disponiveis para as posicoes ja postas");
        if(!this.plantel.temPosicao(j.getPosicao()) || sizeDisponivel > 0) {
            //System.out.println("A adicionar aos titulares um " + j.getPosicao());
            this.plantel.adicionaTitular(j);
        }
        else
            this.plantel.adicionaSuplente(j);
        }

    public void adicionaPlantel(Set<Jogador> js){
        for(Jogador j : js){
            adicionaPlantel(j);
        }
    }

    public EquipaFutebol clone (){
        return new EquipaFutebol(this);
    }

    public String[][] nomesJogadores (){
        String[][] titulares = this.plantel.nomesTitulares();
        String[][] supelentes = this.plantel.nomesSupelentes();
        String[][] jogadores = new String [2][titulares[0].length+supelentes[0].length];
        int i = 0, j = 0;
        for(String s : titulares[0]){
            jogadores[0][i] = s;
            i++;
        }
        for(String s : titulares[1]){
            jogadores[1][j] = s;
            j++;
        }
        for(String s : supelentes[0]){
            jogadores[0][i] = s;
            i++;
        }
        for(String s : supelentes[1]){
            jogadores[1][j] = s;
            j++;
        }
        return jogadores;
    }


}
