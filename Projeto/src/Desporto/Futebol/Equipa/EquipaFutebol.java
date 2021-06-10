package Desporto.Futebol.Equipa;

import Desporto.Futebol.Equipa.Jogador.Jogador;

import java.io.Serializable;
import java.util.Set;

/**
 * Classe que representa uma equipa de futebol que possui um nome e um plantel
 */
public class EquipaFutebol implements Serializable {
    private String nome;
    private Plantel plantel;

    /**
     * Construtor vazio para uma equipa de futebol
     */
    public EquipaFutebol(){
        this("");
    }

    /**
     * Construtor de uma equipa de futebol com apenas um nome
     * @param nome Nome da equipa
     */
    public EquipaFutebol(String nome){
        this.nome = nome;
        this.plantel = new Plantel();
    }

    /**
     * Construtor parametrizado de uma equipa de jogador
     * @param nome Nome da equipa
     * @param plantel Plantel 
     */
    public EquipaFutebol(String nome, Plantel plantel){
        this.nome = nome;
        this.plantel = plantel.clone();
    }

    /**
     * Construtor de cópia de uma equipa de futebol
     * @param equipa Objeto original
     */
    public EquipaFutebol (EquipaFutebol equipa){
        this.nome = equipa.nome;
        this.plantel = equipa.plantel.clone();
    }

    /**
     * Verifica se um plantel tem 11 jogadores titulares e se possui uma tática válida
     * @return true se possui, false caso contrário
     */
    public boolean equipaPronta(){
        return this.plantel.getTitulares().size() == 11 && this.plantel.getTatica().taticaValida();
    }

    /**
     * Devolve o nome de uma equipa
     * @return Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome de uma equipa
     * @param nome Novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Altera a tática atual, testando se é possível aplicar uma nova tática
     * @param t Nova tática
     */
    public void setTatica (Tatica t){
        if(this.podeUsarTatica(t)) this.plantel.setTatica(t);
    }

    /**
     * Devolve o plantel
     * @return Plantel
     */
    public Plantel getPlantel() {
        return plantel.clone();
    }

    /**
     * Altera o plantel atual
     * @param plantel Novo Plantel
     */
    public void setPlantel(Plantel plantel) {
        this.plantel = plantel.clone();
    }   

    /**
     * Dado o número da camisola de um jogador, remove-o de um plantel
     * @param numero Número da camisola do jogador a remover
     */
    public void removePlantel (int numero){
        if(this.plantel.existeTitular(numero)){
            this.plantel.removeTitular(numero);
        }
        else if(this.plantel.existeSuplente(numero)){
            this.plantel.removeSuplente(numero);
        }
    }

    /**
     * Devolve um jogador através do seu número da camisola
     * @param numero Número de um jogador a procurar
     * @return Jogador desejado
     */
    public Jogador getJogador (int numero){
        if(this.plantel.existeTitular(numero)){
            return this.plantel.getTitular(numero);
        }
        else if(this.plantel.existeSuplente(numero)) {
            return this.plantel.getSuplente(numero);
        }
        else {
            return null;
        }
    }   

    /**
     * Conta o número de jogadores que existem num plantel com uma dada posição
     * @param posicao Posição dos jogadores a procurar
     * @return Número de jogadores com uma dada posição
     */
    public int contaPorPosicao (String posicao){
        return this.plantel.contaPorPosicaoTitulares(posicao) + this.plantel.contaPorPosicaoSuplentes(posicao);
    }

    /**
     * Verifica se é possível usar uma dada tática
     * @param tatica Tática a verificar se pode ser aplicada
     * @return true se é possível, false caso contrário
     */
    public boolean podeUsarTatica (Tatica tatica){
        return (this.contaPorPosicao("Guarda-Redes") >= tatica.getnGR() &&
                this.contaPorPosicao("Defesa")  >= tatica.getnDF() &&
                this.contaPorPosicao("Lateral") >= tatica.getnLT() &&
                this.contaPorPosicao("Medio")   >= tatica.getnMD() &&
                this.contaPorPosicao("Avancado") >= tatica.getnPL());
    }

    /**
     * Provoca desgaste a todos os titulares
     */
    public void desgasteJogo (){
        this.plantel.desgasteTitulares();
    }

    /**
     * Provoca desgaste a um dado jogador titular que é procurado pelo seu número da camisola
     * @param numero Número do jogador a ser desgastado
     */
    public void desgastaJogadorTitular(int numero){
        this.plantel.desgasteTitular(numero);
    }

    /**
     * Adiciona um jogador a um plantel, verificando se pode ser adicionado aos titulares ou aos suplentes
     * @param j Jogador a adicionar
     */
    public void adicionaPlantel(Jogador j){
        boolean x = false;
        int numero = j.getNumero();
        if(this.plantel.getnJogadoresNoPlantel() < 22) {
            while (this.plantel.numeroOcupado(numero)) {
                if (!x) {
                    x = true;
                }
                numero++;
            }
            j.setNumero(numero);
            int sizeDisponivel = 11 - this.plantel.getTitulares().size() - this.plantel.quantasPosicoesFaltam();
            if (!this.plantel.temPosicao(j.getPosicao()) || sizeDisponivel > 0)
                this.plantel.adicionaTitular(j);
            else
                this.plantel.adicionaSuplente(j);
        }
    }

    /**
     * Adiciona uma set de jogadores a um plantel
     * @param js Set de jogadores
     */
    public void adicionaPlantel(Set<Jogador> js){
        for(Jogador j : js){
            adicionaPlantel(j);
        }
    }

    /**
     * Troca um jogador de posição, criando um novo com o número original e a posição desejada
     * @param numero Número do jogador a alterar
     * @param posicao Posição desejada
     */
    public void trocaPosicao (int numero, String posicao){
        Jogador j = this.getJogador(numero);
        Jogador novo = new Jogador(j,posicao);
        this.plantel.removeTitular(numero);
        this.plantel.adicionaTitular(novo);
    }

    /**
     * Substitui um titular por um suplente
     * @param titular Número da camisola do titular
     * @param suplente Número da camisola de um suplente
     */
    public void substiuicaoJogo (int titular, int suplente){
        this.plantel.substituicao(titular, suplente);
    }

    /**
     * Transforma o objeto EquipaFutebol numa string
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("*Nome* ").append(this.nome).append("\n");
        sb.append(this.plantel.toString());
        return sb.toString();
    }

    /**
     * Transforma uma equipa de futebol numa linha
     * @return Linha com uma equipad de futebol
     */
    public String toFicheiro(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.nome).append("\n");
        sb.append(this.plantel.toFicheiro());
        return sb.toString();
    }

    /**
     * Método que copia um objeto EquipaFutebol
     */
    public EquipaFutebol clone (){
        return new EquipaFutebol(this);
    }

    /**
     * Cria um array de strings onde se associa o nome dos jogadores a um caractere que identifica se é titular ou suplente
     * @return Array de strings com os jogadores
     */
    public String[][] nomesJogadores (){
        String[][] titulares = this.plantel.nomesTitulares();
        String[][] suplentes = this.plantel.nomesSuplentes();
        String[][] jogadores = new String [3][titulares[0].length+ suplentes[0].length];
        int i, j;

        for(i = 0; i < titulares[0].length;i++){
            jogadores[0][i] = "*"+titulares[0][i]+"*";
            if(Integer.parseInt(titulares[1][i]) < 10)
                jogadores[1][i] = "0"+titulares[1][i];
            else jogadores[1][i] = titulares[1][i];
            jogadores[2][i] = titulares[2][i];
        }
        for(j = 0; j < suplentes[0].length;j++,i++){
            jogadores[0][i] = suplentes[0][j];
            if(Integer.parseInt(suplentes[1][j]) < 10)
                jogadores[1][i] = "0"+suplentes[1][j];
            else jogadores[1][i] = suplentes[1][j];
            jogadores[2][i] = suplentes[2][j];
        }
        return jogadores;
    }
}
