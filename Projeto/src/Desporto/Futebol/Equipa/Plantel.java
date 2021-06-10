package Desporto.Futebol.Equipa;

import Desporto.Futebol.Equipa.Jogador.CompPosicao;
import Desporto.Futebol.Equipa.Jogador.Jogador;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que representa um plantel que é constituído por titulares, suplentes, número de jogadores e uma tática
 */
public class Plantel implements Serializable {
    private Map<Integer, Jogador> titulares;
    private Map<Integer,Jogador> suplentes;
    private int nJogadoresNoPlantel;
    private Tatica tatica;

    /**
     * Construtor parametrizado de um plantel
     * @param titulares Map dos titulares onde é associado a cada jogador o seu número de camisola
     * @param suplentes Map dos suplentes onde é associado a cada jogador o seu número de camisola
     * @param nJogadoresNoPlantel Número total de jogadores num plantel
     * @param tatica Onze inicial do plantel
     */
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

    /**
     * Construtor vazio de um plantel
     */
    public Plantel(){
        this(new HashMap<>(), new HashMap<>(), 0, new Tatica());
    }

    /**
     * Construtor cópia de um plantel
     * @param outroPlantel Objeto original
     */
    public Plantel (Plantel outroPlantel){
        this.titulares = outroPlantel.getTitulares();
        this.suplentes = outroPlantel.getSuplentes();
        this.nJogadoresNoPlantel = outroPlantel.getnJogadoresNoPlantel();
        this.tatica = outroPlantel.getTatica();
    }

    /**
     * Devolve os titulares de um plantel
     * @return Titulares de um plantel
     */
    public Map<Integer, Jogador> getTitulares() {
        return this.titulares.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().clone()));
    }

    /**
     * Devolve os suplentes de um plantel
     * @return Suplentes de um plantel
     */
    public Map<Integer, Jogador> getSuplentes() {
        return this.suplentes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().clone()));
    }

    /**
     * Provoca desgaste a todos os titulares
     */
    public void desgasteTitulares(){
        for(Jogador j : this.titulares.values()){
            j.desgastaJogador();
        }
    }

    /**
     * Caso existe um jogador titular com um dado número, provoca desgaste a esse jogador
     * @param numero Número do titular a desgastar
     */
    public void desgasteTitular(int numero){
        if(this.titulares.containsKey(numero)){
            this.titulares.get(numero).desgastaJogador();
        }
    }

    /**
     * Adiciona um jogador a um onze inicial
     * @param j Jogador a adicionar
     */
    public void adicionaTitular(Jogador j){
        int numero = j.getNumero();
        while (this.numeroOcupado(numero))
            numero++;
        j.setNumero(numero);
        this.titulares.put(j.getNumero(),j.clone());
        this.nJogadoresNoPlantel++;
    }

    /**
     * Adiciona um jogador aos suplentes
     * @param j Jogador a adicionar
     */
    public void adicionaSuplente(Jogador j){
        int numero = j.getNumero();
        while (this.numeroOcupado(numero))
            numero++;
        j.setNumero(numero);
        this.suplentes.put(j.getNumero(),j.clone());
        this.nJogadoresNoPlantel++;
    }

    /**
     * Devolve um jogador titular através de um dado número
     * @param numero Número do jogador titular a procurar
     * @return Jogador titular
     */
    public Jogador getTitular (int numero){
        if(this.titulares.containsKey(numero))
            return this.titulares.get(numero).clone();
        else return null;
    }

    /**
     * Devolve um jogador suplente através de um dado número
     * @param numero Número do jogador suplente a procurar
     * @return Jogador suplente
     */
    public Jogador getSuplente (int numero){
        if(this.suplentes.containsKey(numero))
            return this.suplentes.get(numero).clone();
        else return null;
    }

    /**
     * Remove todos os jogadores do onze inicial
     */
    public void limpaTitulares (){
        List<Jogador> l = new ArrayList<>(this.titulares.values());
        for(Jogador j : l){
            this.removeTitular(j.getNumero());
            this.adicionaSuplente(j);
        }
        this.titulares = new HashMap<>();
    }

    /**
     * Devolve o número total de jogadores num plantel
     * @return Número total de jogadores num plantel
     */
    public int getnJogadoresNoPlantel() {
        return this.nJogadoresNoPlantel;
    }

    /**
     * Devolve a tática de um plantel
     * @return Tática de um plantel
     */
    public Tatica getTatica() {
        return tatica.clone();
    }   

    /**
     * Altera a tática de um plantel
     * @param tatica Nova tática
     */
    public void setTatica(Tatica tatica) {
        this.tatica = tatica.clone();
        this.limpaTitulares();
        for(int i = 0; i < tatica.getnGR(); i++){
            Optional <Jogador> jgdOp = this.suplentes.values().stream().filter(j -> j.getPosicao().equals("Guarda-Redes")).findAny();
            if(jgdOp.isPresent()){
                Jogador jgd = jgdOp.get();
                removeSuplente(jgd.getNumero());
                adicionaTitular(jgd);
            }
        }
        for(int i = 0; i < tatica.getnDF(); i++){
            Optional <Jogador> jgdOp = this.suplentes.values().stream().filter(j -> j.getPosicao().equals("Defesa")).findAny();
            if(jgdOp.isPresent()){
                Jogador jgd = jgdOp.get();
                removeSuplente(jgd.getNumero());
                adicionaTitular(jgd);
            }
        }
        for(int i = 0; i < tatica.getnLT(); i++){
            Optional <Jogador> jgdOp = this.suplentes.values().stream().filter(j -> j.getPosicao().equals("Lateral")).findAny();
            if(jgdOp.isPresent()){
                Jogador jgd = jgdOp.get();
                removeSuplente(jgd.getNumero());
                adicionaTitular(jgd);
            }
        }
        for(int i = 0; i < tatica.getnMD(); i++){
            Optional <Jogador> jgdOp = this.suplentes.values().stream().filter(j -> j.getPosicao().equals("Medio")).findAny();
            if(jgdOp.isPresent()){
                Jogador jgd = jgdOp.get();
                removeSuplente(jgd.getNumero());
                adicionaTitular(jgd);
            }
        }
        for(int i = 0; i < tatica.getnPL(); i++){
            Optional <Jogador> jgdOp = this.suplentes.values().stream().filter(j -> j.getPosicao().equals("Avancado")).findAny();
            if(jgdOp.isPresent()){
                Jogador jgd = jgdOp.get();
                removeSuplente(jgd.getNumero());
                adicionaTitular(jgd);
            }
        }
    }

    /**
     * Verifica se um número já é ocupado por um jogador, tanto pelos titulares como pelos suplentes
     * @param numero Número a verificar
     * @return True se algum jogador já possui esse número
     */
    public boolean numeroOcupado (int numero){
        return (this.titulares.containsKey(numero) || this.suplentes.containsKey(numero));
    }

    /**
     * Verifica se já existe algum jogador com uma dada posição
     * @param posicao Posição a verificar
     * @return true se já existe alguém com uma dada posição, false caso contrário
     */
    public boolean temPosicao (String posicao){
        return (this.titulares.values().stream().anyMatch(j -> j.getPosicao().equals(posicao)));
    }   

    /**
     * Devolve o número de posições que não foram preenchidas nos titulares
     * @return Número de posições
     */
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

    /**
     * Transforma um plantel numa string
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("*Tática* ").append(this.tatica.toString()).append("\n");
        sb.append("*Titulares* \n").append(titularesToString());
        sb.append("*Suplentes* \n").append(suplentesToString());
        return sb.toString();
    }

    /**
     * Transforma um plantel numa linha
     * @return Linha com um plantel
     */
    public String toFicheiro() {
        final StringBuilder sb = new StringBuilder();
        sb.append(titularesToFicheiro());
        sb.append(suplentesToFicheiro());
        return sb.toString();
    }

    /**
     * Transforma os suplentes de um plantel numa linha
     * @return Linha com os suplentes
     */
    private String suplentesToFicheiro() {
        StringBuilder sb = new StringBuilder();
        for(Jogador j : this.suplentes.values()){
            sb.append(j.toFicheiro()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Transforma os titulares de um plantel numa linha
     * @return Linha com os titulares
     */
    private String titularesToFicheiro() {
        StringBuilder sb = new StringBuilder();
        for(Jogador j : this.titulares.values()){
            sb.append(j.toFicheiro()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Transforma os titulares numa string para aparecer no menu
     * @return String com os titulares
     */
    public String titularesToString(){
        StringBuilder sb = new StringBuilder();
        for(Jogador j : this.titulares.values()){
            sb.append("(").append(abvPosicao(j.getPosicao())).append(") ").append(j.getNome()).append(" [").append(j.getNumero()).append("]\n");
        }
        return sb.toString();
    }

    /**
     * Transforma os suplentes numa string para aparecer no menu
     * @return String com os suplentes
     */
    public String suplentesToString(){
        StringBuilder sb = new StringBuilder();
        for(Jogador j : this.suplentes.values()){
            sb.append("(").append(abvPosicao(j.getPosicao())).append(") ").append(j.getNome()).append(" [").append(j.getNumero()).append("]\n");
        }
        return sb.toString();
    }

    /**
     * Método de cópia de um objeto Plantel
     */
    public Plantel clone() {
        return new Plantel(this);
    }

    /**
     * Verifica se 2 objetos Plantel são iguais
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Plantel p = (Plantel) o;
        return this.titulares == p.titulares && this.suplentes == p.suplentes
                && this.nJogadoresNoPlantel == p.nJogadoresNoPlantel && this.tatica == p.tatica;
    }

    /**
     * Remove um suplente através do seu número da camisola
     * @param id Número do jogador a remover
     */
    public void removeSuplente(int id){
        this.suplentes.remove(id);
        this.nJogadoresNoPlantel--;
    }

    /**
     * Remove um titular através do seu número da camisola
     * @param id Número do jogador a remover
     */
    public void removeTitular(int id){
        this.titulares.remove(id);
        this.nJogadoresNoPlantel--;
    }

    /**
     * Conta quantos jogadores titulares existem numa dada posição
     * @param posicao Posição a verificar
     * @return Número de jogadores titulares com uma dada posição
     */
    public int contaPorPosicaoTitulares(String posicao){
        return (int) this.titulares.values().stream().filter(j -> j.getPosicao().equals(posicao)).count();
    }

    /**
     * Conta quantos jogadores suplentes existem numa dada posição
     * @param posicao Posição a verificar
     * @return Número de jogadores suplentes com uma dada posição
     */
    public int contaPorPosicaoSuplentes(String posicao){
        return (int) this.suplentes.values().stream().filter(j -> j.getPosicao().equals(posicao)).count();
    }

    /**
     * Verifica se um jogador (identificado pelo seu número de camisola) existe no onze inicial
     * @param id Número do jogador a verificar
     * @return true se existe, false caso contrário
     */
    public boolean existeTitular(int id){
        return this.titulares.containsKey(id);
    }

    /**
     * Verifica se um jogador (identificado pelo seu número de camisola) existe nos suplentes
     * @param id Número do jogador a verificar
     * @return true se existe, false caso contrário
     */
    public boolean existeSuplente (int id){
        return this.suplentes.containsKey(id);
    }

    /**
     * Realiza uma substituicao (o titular desaparece pois não pode voltar a entrar)
     * @param titular Número do titular a ser substituído
     * @param suplente Número do suplente a entrar
     */
    public void substituicao(int titular, int suplente) {
        if (existeTitular(titular) && existeSuplente(suplente)){
            Jogador s = getSuplente(suplente);
            this.titulares.remove(titular);
            this.suplentes.remove(suplente);
            this.titulares.put(suplente,s);
        }
    }

    /**
     * Devolve um array de Strings com os nomes, numeros, posições e overall de todos os jogadores no banco
     * @return Array de strings
     */
    public String[][] nomesSuplentes (){
        TreeSet<Jogador> tm = new TreeSet<>(new CompPosicao());
        String [] nomes = new String[this.suplentes.size()];
        String [] numeros = new String[this.suplentes.size()];
        String [] posicoes = new String[this.suplentes.size()];
        String [] overall = new String[this.suplentes.size()];
        int i = 0;

        tm.addAll(suplentes.values());

        for(Jogador j : tm){
            nomes[i] = j.getNome();
            numeros[i] = Integer.toString(j.getNumero());
            posicoes[i] = abvPosicao(j.getPosicao());
            overall[i] = Integer.toString(j.getAtributos().overall());
            i++;
        }
        return new String[][]{nomes, numeros, posicoes, overall};
    }

    /**
     * Devolve um array de Strings com os nomes, numeros, posições e overall de todos os jogadores titulares
     * @return Array de strings
     */
    public String[][] nomesTitulares (){
        TreeSet<Jogador> tm = new TreeSet<>(new CompPosicao());
        String [] nomes = new String[this.titulares.size()];
        String [] numeros = new String[this.titulares.size()];
        String [] posicoes = new String[this.titulares.size()];
        String [] overall = new String[this.titulares.size()];
        int i = 0;
        tm.addAll(titulares.values());

        for(Jogador j : tm){
            nomes[i] = j.getNome();
            numeros[i] = Integer.toString(j.getNumero());
            posicoes[i] = abvPosicao(j.getPosicao());
            overall[i] = Integer.toString(j.getAtributos().overall());
            i++;
        }
        return new String[][]{nomes, numeros, posicoes, overall};
    }

    /**
     * Devolve um array de Strings com os nomes, numeros e overal de todos os jogadores titulares de uma dada posição
     * @param posicao Posição a comparar
     * @return Array de strings
     */
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

    /**
     * Devolve um array de Strings com os nomes, numeros e overal de todos os jogadores suplentes de uma dada posição
     * @param posicao Posição a comparar
     * @return Array de strings
     */
    public String[][] nomesSuplentes(String posicao){
        List<Jogador> js =  this.suplentes.values().stream().map(Jogador::clone).filter(j -> j.getPosicao().equals(posicao)).collect(Collectors.toList());
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
        return new String[][]{nomes, numeros};
    }

    /**
     * Abrevia uma posição
     * @param posicao Posição a ser abreviada
     * @return Posição abreviada
     */
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
