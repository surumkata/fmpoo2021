package Desporto.Futebol.Equipa;

import Desporto.Futebol.Equipa.Jogador.Jogador;

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
        this(new HashMap<>(), new HashMap<>(), 0, new Tatica());
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

    public void adicionaTitular(Jogador j){
        int numero = j.getNumero();
        while (this.numeroOcupado(numero))
            numero++;
        j.setNumero(numero);
        this.titulares.put(j.getNumero(),j.clone());
        this.nJogadoresNoPlantel++;
    }

    public void adicionaSuplente(Jogador j){
        int numero = j.getNumero();
        while (this.numeroOcupado(numero))
            numero++;
        j.setNumero(numero);
        this.suplentes.put(j.getNumero(),j.clone());
        this.nJogadoresNoPlantel++;
    }

    public Jogador getTitular (int numero){
        return this.titulares.get(numero).clone();
    }

    public Jogador getSuplente (int numero){
        return this.suplentes.get(numero).clone();
    }

    public int getnJogadoresNoPlantel() {
        return this.nJogadoresNoPlantel;
    }

    public Tatica getTatica() {
        return tatica.clone();
    }

    public void setTatica(Tatica tatica) {
        this.tatica = tatica.clone();
    }

    public boolean numeroOcupado (int numero){
        return (this.titulares.containsKey(numero) || this.suplentes.containsKey(numero));
    }

    public boolean temPosicao (String posicao){
        return (this.titulares.values().stream().anyMatch(j -> j.getPosicao().equals(posicao)));
    }

    public int quantasPosicoesFaltam (){
        int i = 0;
        if(!temPosicao("Defesa"))
            i++;
        if(!temPosicao("Medio"))
            i++;
        if(!temPosicao("Lateral"))
            i++;
        if(!temPosicao("Guarda-Redes"))
            i++;
        if(!temPosicao("Avancado"))
            i++;
        return i;
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


    public void removeSuplente(int id){
        this.suplentes.remove(id);
    }

    public void removeTitular(int id){
        this.titulares.remove(id);
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
            Jogador s = getSuplente(suplente);
            this.titulares.remove(titular);
            this.suplentes.remove(suplente);
            this.titulares.put(suplente,s);
            this.suplentes.put(titular,t);
            return true;
        }
        else
            return false;
    }

    public String[][] nomesSupelentes (){
        String [] nomes = new String[this.suplentes.size()];
        String [] numeros = new String[this.suplentes.size()];
        int i = 0;
        for(Jogador j : this.suplentes.values()){
            nomes[i] = j.getNome();
            numeros[i] = Integer.toString(j.getNumero());
            i++;
        }
        return new String[][]{nomes, numeros};
    }

    public String[][] nomesTitulares (){
        String [] nomes = new String[this.titulares.size()];
        String [] numeros = new String[this.titulares.size()];
        int i = 0;
        for(Jogador j : this.titulares.values()){
            nomes[i] = j.getNome();
            numeros[i] = Integer.toString(j.getNumero());
            i++;
        }
        return new String[][]{nomes, numeros};
    }
}
