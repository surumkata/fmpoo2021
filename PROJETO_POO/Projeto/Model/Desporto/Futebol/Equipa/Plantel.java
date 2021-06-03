package Desporto.Futebol.Equipa;

import Desporto.Futebol.Equipa.Jogador.Jogador;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Plantel {
    private Map<Integer, Jogador> titulares;
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
        return this.titulares.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(),
                        e -> e.getValue().clone()));
    }

    public Map<Integer, Jogador> getSuplentes() {
        return this.suplentes.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(),
                        e -> e.getValue().clone()));
    }

    public void adicionaTitular(Jogador j) throws JogadorInvalidoException, TitularesFullException {
        if(this.titulares.size() == 11)
            throw new TitularesFullException("Não pode haver mais do que 11 titulares.");
        else if (this.titulares.containsKey(j.getNumero()) || this.suplentes.containsKey(j.getNumero()))
            throw new JogadorInvalidoException("Este número"+j.getNumero()+"já se encontra no plantel(nos titulares ou nos suplentes)");
        else
            this.titulares.put(j.getNumero(),j.clone());
    }

    public void adicionaSuplente(Set<Jogador> js) throws JogadorInvalidoException {
        for(Jogador j: js)
            adicionaSuplente(j);
    }

    public void adicionaSuplente(Jogador j) throws JogadorInvalidoException {
        if (this.titulares.containsKey(j.getNumero()) || this.suplentes.containsKey(j.getNumero()))
            throw new JogadorInvalidoException("Este número"+j.getNumero()+"já se encontra no plantel(nos titulares ou nos suplentes)");
        else
            this.suplentes.put(j.getNumero(),j.clone());
    }

    public Jogador getTitular (int numero){
        return this.titulares.get(numero).clone();
    }

    public Jogador getSuplente (int numero){
        return this.suplentes.get(numero).clone();
    }

    public int getnJogadoresNoPlantel() {
        return nJogadoresNoPlantel;
    }

    public void setnJogadoresNoPlantel(int nJogadoresNoPlantel) {
        this.nJogadoresNoPlantel = nJogadoresNoPlantel;
    }

    public Tatica getTatica() {
        return tatica.clone();
    }

    public void setTatica(Tatica tatica) {
        this.tatica = tatica.clone();
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
            Jogador t = getTitular(titular);
            Jogador s = getTitular(suplente);
            this.titulares.remove(titular);
            this.suplentes.remove(suplente);
            this.titulares.put(suplente,s);
            this.suplentes.put(titular,t);
            return true;
        }
        else
            return false;
    }
}
