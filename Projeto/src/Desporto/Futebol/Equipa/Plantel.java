package Desporto.Futebol.Equipa;

import Desporto.Futebol.Equipa.Jogador.Jogador;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Plantel implements Serializable {
    private Map<Integer, Jogador> titulares;
    private Map<Integer,Jogador> suplentes;
    private int nJogadoresNoPlantel;
    private Tatica tatica;

    public Plantel(Map<Integer,Jogador> titulares, Map<Integer,Jogador> suplentes, int nJogadoresNoPlantel, Tatica tatica){
        this.titulares = new HashMap<>();
        this.suplentes = new HashMap<>();
        for(Jogador j : titulares.values()){
            this.adicionaTitular(j);
        }
        for(Jogador j : suplentes.values()){
            this.adicionaSuplente(j);
        }
        this.nJogadoresNoPlantel = nJogadoresNoPlantel;
        this.tatica = tatica.clone();
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
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().clone()));
    }

    public Map<Integer, Jogador> getSuplentes() {
        return this.suplentes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().clone()));
    }

    public void desgasteTitulares(){
        for(Jogador j : this.titulares.values()){
            j.desgastaJogador();
        }
    }

    public void desgasteTitular(int numero){
        if(this.titulares.containsKey(numero)){
            this.titulares.get(numero).desgastaJogador();
        }
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
        if(this.titulares.containsKey(numero))
            return this.titulares.get(numero).clone();
        else return null;
    }

    public Jogador getSuplente (int numero){
        if(this.suplentes.containsKey(numero))
            return this.suplentes.get(numero).clone();
        else return null;
    }

    public void limpaTitulares (){
        List<Jogador> l = new ArrayList<>(this.titulares.values());
        for(Jogador j : l){
            this.removeTitular(j.getNumero());
            this.adicionaSuplente(j);
        }
        this.titulares = new HashMap<>();
    }

    public int getnJogadoresNoPlantel() {
        return this.nJogadoresNoPlantel;
    }

    public Tatica getTatica() {
        return tatica.clone();
    }

    public void setTatica(Tatica tatica) {
        this.tatica = tatica.clone();
        this.limpaTitulares();
        for(int i = 0; i < tatica.getnGR(); i++){
            Jogador jgd = this.suplentes.values().stream().filter(j -> j.getPosicao().equals("Guarda-Redes")).findAny().get();
            removeSuplente(jgd.getNumero());
            adicionaTitular(jgd);
        }
        for(int i = 0; i < tatica.getnDF(); i++){
            Jogador jgd = this.suplentes.values().stream().filter(j -> j.getPosicao().equals("Defesa")).findAny().get();
            removeSuplente(jgd.getNumero());
            adicionaTitular(jgd);
        }
        for(int i = 0; i < tatica.getnLT(); i++){
            Jogador jgd = this.suplentes.values().stream().filter(j -> j.getPosicao().equals("Lateral")).findAny().get();
            removeSuplente(jgd.getNumero());
            adicionaTitular(jgd);
        }
        for(int i = 0; i < tatica.getnMD(); i++){
            Jogador jgd = this.suplentes.values().stream().filter(j -> j.getPosicao().equals("Medio")).findAny().get();
            removeSuplente(jgd.getNumero());
            adicionaTitular(jgd);
        }
        for(int i = 0; i < tatica.getnPL(); i++){
            Jogador jgd = this.suplentes.values().stream().filter(j -> j.getPosicao().equals("Avancado")).findAny().get();
            removeSuplente(jgd.getNumero());
            adicionaTitular(jgd);
        }
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
        sb.append("*Tática* ").append(this.tatica.toString()).append("\n");
        sb.append("*Titulares* \n").append(titularesToString());
        sb.append("*Suplentes* \n").append(suplentesToString());
        return sb.toString();
    }

    public String toFicheiro() {
        final StringBuilder sb = new StringBuilder();
        sb.append(titularesToFicheiro());
        sb.append(suplentesToFicheiro());
        return sb.toString();
    }

    private String suplentesToFicheiro() {
        StringBuilder sb = new StringBuilder();
        for(Jogador j : this.suplentes.values()){
            sb.append(j.toFicheiro()).append("\n");
        }
        return sb.toString();
    }

    private String titularesToFicheiro() {
        StringBuilder sb = new StringBuilder();
        for(Jogador j : this.titulares.values()){
            sb.append(j.toFicheiro()).append("\n");
        }
        return sb.toString();
    }

    public String titularesToString(){
        StringBuilder sb = new StringBuilder();
        for(Jogador j : this.titulares.values()){
            sb.append("(").append(abvPosicao(j.getPosicao())).append(") ").append(j.getNome()).append(" [").append(j.getNumero()).append("]\n");
        }
        return sb.toString();
    }

    public String suplentesToString(){
        StringBuilder sb = new StringBuilder();
        for(Jogador j : this.suplentes.values()){
            sb.append("(").append(abvPosicao(j.getPosicao())).append(") ").append(j.getNome()).append(" [").append(j.getNumero()).append("]\n");
        }
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

    public int contaPorPosicaoTitulares(String posicao){
        return (int) this.titulares.values().stream().filter(j -> j.getPosicao().equals(posicao)).count();
    }

    public int contaPorPosicaoSuplentes(String posicao){
        return (int) this.suplentes.values().stream().filter(j -> j.getPosicao().equals(posicao)).count();
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
            return true;
        }
        else
            return false;
    }

    public String[][] nomesSupelentes (){
        String [] nomes = new String[this.suplentes.size()];
        String [] numeros = new String[this.suplentes.size()];
        String [] posicoes = new String[this.titulares.size()];
        String [] overall = new String[this.titulares.size()];
        int i = 0;
        for(Jogador j : this.suplentes.values()){
            nomes[i] = j.getNome();
            numeros[i] = Integer.toString(j.getNumero());
            posicoes[i] = abvPosicao(j.getPosicao());
            overall[i] = Integer.toString(j.getAtributos().overall());
            i++;
        }
        return new String[][]{nomes, numeros, posicoes, overall};
    }

    public String[][] nomesTitulares (){
        String [] nomes = new String[this.titulares.size()];
        String [] numeros = new String[this.titulares.size()];
        String [] posicoes = new String[this.titulares.size()];
        String [] overall = new String[this.titulares.size()];
        int i = 0;
        for(Jogador j : this.titulares.values()){
            nomes[i] = j.getNome();
            numeros[i] = Integer.toString(j.getNumero());
            posicoes[i] = abvPosicao(j.getPosicao());
            overall[i] = Integer.toString(j.getAtributos().overall());
            i++;
        }
        return new String[][]{nomes, numeros, posicoes, overall};
    }

    public String[][] nomesTitulares(String posicao){
        String [] nomes = new String[this.titulares.size()];
        String [] numeros = new String[this.titulares.size()];
        int i = 0;
        for(Jogador j : this.titulares.values()){
            if(j.getPosicao().equals(posicao)) {
                nomes[i] = j.getNome();
                numeros[i] = Integer.toString(j.getNumero());
                i++;
            }
        }
        return new String[][]{nomes, numeros};
    }

    public String[][] nomesSuplentes(String posicao){
        List<Jogador> js =  this.suplentes.values().stream().map(Jogador::clone).filter(j -> j.getPosicao().equals(posicao)).collect(Collectors.toList());
        System.out.println(js.stream().map(j -> j.getNome()).collect(Collectors.toList()));
        int tam = js.size();
        String [] nomes = new String[tam];
        String [] numeros = new String[tam];
        int i = 0;
        for(Jogador j : js){
            if(j.getPosicao().equals(posicao)) {
                nomes[i] = j.getNome();
                numeros[i] = Integer.toString(j.getNumero());
                i++;
            }
        }
        System.out.println(Arrays.toString(nomes));
        return new String[][]{nomes, numeros};
    }

    public String abvPosicao(String posicao){
        if(posicao.equals("Guarda-Redes")){
            return "GR";
        }
        else if(posicao.equals("Lateral")){
            return "LT";
        }
        else if(posicao.equals("Defesa")){
            return "DF";
        }
        else if(posicao.equals("Medio")){
            return "MD";
        }
        else if(posicao.equals("Avancado")){
            return "AV";
        }
        return "";

    }
}
