package Futebol.Equipa;

import Futebol.Equipa.Jogador.*;

import java.util.Collections;
import java.util.Map;

public class Plantel {
    private Map<Integer,Jogador> titulares;
    private Map<Integer,Jogador> suplentes;
    private int nJogadoresNoPlantel;
    private Tatica tatica;

    public Plantel(Map<Integer,Jogador> titulares, Map<Integer,Jogador> suplentes, int nJogadoresNoPlantel, Tatica tatica){
        this.titulares = titulares;
        this.suplentes = suplentes;
        this.nJogadoresNoPlantel = nJogadoresNoPlantel;
        this.tatica = tatica;
    }

    public Plantel(){
        this(Collections.emptyMap(), Collections.emptyMap(), 0, new Tatica());
    }

    public Plantel (Plantel outroPlantel){
        this.titulares = outroPlantel.getTitulares();
        this.suplentes = outroPlantel.getSuplentes();
        this.nJogadoresNoPlantel = outroPlantel.getnJogadoresNoPlantel();
        this.tatica = outroPlantel.getTatica();
    }

    public Map<Integer, Jogador> getTitulares() {
        return titulares;
    }

    public void setTitulares(Map<Integer, Jogador> titulares) {
        this.titulares = titulares;
    }

    public Map<Integer, Jogador> getSuplentes() {
        return suplentes;
    }

    public void setSuplentes(Map<Integer, Jogador> suplentes) {
        this.suplentes = suplentes;
    }

    public int getnJogadoresNoPlantel() {
        return nJogadoresNoPlantel;
    }

    public void setnJogadoresNoPlantel(int nJogadoresNoPlantel) {
        this.nJogadoresNoPlantel = nJogadoresNoPlantel;
    }

    public Tatica getTatica() {
        return tatica;
    }

    public void setTatica(Tatica tatica) {
        this.tatica = tatica;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.tatica.toString()).append("Plantel: \n").append("Titulares: ").append(this.titulares.toString()).append("\n")
        .append("Suplentes: ").append(this.suplentes.toString());
        return sb.toString();
    }

    public Plantel clone() {
        return new Plantel(this);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Plantel p = (Plantel) o;
        return this.titulares == p.titulares && this.suplentes == p.suplentes
                && this.nJogadoresNoPlantel == p.nJogadoresNoPlantel && this.tatica == p.tatica;
    }


    public boolean existeTitular(int id){
        return this.titulares.containsKey(id);
    }

    public boolean existeSuplente (int id){
        return this.suplentes.containsKey(id);
    }

    public boolean substituicao(int titular, int suplente) {
        if (existeTitular(titular) && existeSuplente(suplente)){
            this.titulares.remove(titular);
            this.suplentes.remove(suplente);
            this.titulares.put(suplente,this.suplentes.get(suplente));
            this.suplentes.put(titular,this.titulares.get(titular));
            return true;
        }
        else
            return false;
    }
}
