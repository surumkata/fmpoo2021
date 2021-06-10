package Desporto.Futebol.Partida;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.CompPosicao;
import Desporto.Futebol.Equipa.Jogador.Jogador;
import Desporto.Futebol.Equipa.Tatica;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Classe de uma partida de futebol, onde se incluem o tempo de jogo, o número de golos da equipa da casa, o número de golos da equipa visitante,
 * as substituições das 2 equipas (identificando cada jogador pelo seu número de camisola), as respetivas equipas e a data de realização da partida
 */
public class PartidaFutebol implements Serializable {
    private double tempo;
    private int golosVisitante;
    private int golosVisitado;
    private int substituicoesVisitante, substituicoesVisitados;
    private int[][] subsVisitante, subsVisitada;
    private EquipaFutebol equipaVisitante, equipaVisitada;
    private LocalDate data;

    /**
     * Construtor vazio de uma partida de futebol
     */
    public PartidaFutebol (){
        this.tempo = 0.0;
        this.golosVisitante = 0;
        this.golosVisitado = 0;
        this.substituicoesVisitante = 0;
        this.substituicoesVisitados = 0;
        this.data = LocalDate.now();
        this.equipaVisitante = new EquipaFutebol();
        this.equipaVisitante = new EquipaFutebol();
        this.subsVisitante = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
        this.subsVisitada = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
    }

    /**
     * Construtor cópia de uma partida de futebol
     * @param partida Partida de futebol original
     */
    public PartidaFutebol (PartidaFutebol partida){
        this.tempo = partida.tempo;
        this.golosVisitado = partida.golosVisitado;
        this.golosVisitante = partida.golosVisitante;
        this.substituicoesVisitados = partida.substituicoesVisitados;
        this.substituicoesVisitante = partida.substituicoesVisitante;
        this.data = partida.data; 
        this.equipaVisitada = partida.equipaVisitada.clone();
        this.equipaVisitante = partida.equipaVisitante.clone();
        this.subsVisitante = partida.subsVisitante.clone();
        this.subsVisitada = partida.subsVisitada.clone();
    }

    /**
     * Construtor de uma partida de futebol, que recebe as 2 equipas que vão jogar
     * @param equipaVisitada Equipa da casa
     * @param equipaVisitante Equipa visitante
     */
    public PartidaFutebol(EquipaFutebol equipaVisitada, EquipaFutebol equipaVisitante) {
        this.tempo = 0;
        this.golosVisitado = 0;
        this.golosVisitante = 0;
        this.substituicoesVisitante = 3;
        this.substituicoesVisitados = 3;
        this.data = LocalDate.now();
        this.equipaVisitada = equipaVisitada.clone();
        this.equipaVisitante = equipaVisitante.clone();
        this.subsVisitante = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
        this.subsVisitada = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
    }

    /**
     * Construtor parametrizado de uma partida de futebol
     * @param equipaVisitada Equipa da casa
     * @param equipaVisitante Equipa visitante
     * @param d Data da partida
     * @param golosA Golos marcados pela equipa da casa
     * @param golosB Golos marcados pela equipa visitante
     */
    public PartidaFutebol(EquipaFutebol equipaVisitada, EquipaFutebol equipaVisitante, LocalDate d, int golosA, int golosB) {
        this.tempo = 0;
        this.golosVisitado = golosA;
        this.golosVisitante = golosB;
        this.substituicoesVisitante = 3;
        this.substituicoesVisitados = 3;
        this.data = d;
        this.equipaVisitada = equipaVisitada.clone();
        this.equipaVisitante = equipaVisitante.clone();
        this.subsVisitante = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
        this.subsVisitada = new int[][] {new int[]{0, 0, 0},new int[]{0, 0, 0}};
    }

    /**
     * Método que devolve uma cópia do objeto PartidaFutebol
     */
    public PartidaFutebol clone () {
        return new PartidaFutebol(this);
    }

    /**
     * Devolve o número de substituições que uma equipa ainda pode fazer
     * @param visitado Booleano que verifica se é a equipa visitante
     * @return Número de substituições
     */
    public int getSubstituicoesRestantes(boolean visitado) {
        if(visitado) return substituicoesVisitados;
        else return substituicoesVisitante;
    }

    /**
     * Devolve uma cópia da equipa de futebol da casa
     * @return Equipa da casa
     */
    public EquipaFutebol getEquipaVisitada() {
        return equipaVisitada.clone();
    }

    /**
     * Devolve uma cópia da equipa de futebol visitante
     * @return Equipa visitante
     */
    public EquipaFutebol getEquipaVisitante() {
        return equipaVisitante.clone();
    }

    /**
     * Decrementa o número de substituições de uma equipa
     * @param visitada Booleano que verifica se é a equipa da casa
     * @param n1 Camisola do jogador que sai
     * @param n2 Camisola do jogador que entra
     */
    public void decSubstituicoes(boolean visitada, int n1, int n2){
        if (visitada) {
            this.subsVisitada[0][3-substituicoesVisitados] = n1;
            this.subsVisitada[1][3-substituicoesVisitados] = n2;
            this.substituicoesVisitados--;
        }
        else {
            this.subsVisitante[0][3-substituicoesVisitante] = n1;
            this.subsVisitante[1][3-substituicoesVisitante] = n2;
            this.substituicoesVisitante--;
        }
    }

    /**
     * Provoca desgaste a um jogador titular de uma equipa
     * @param visitada Booleano que verifica se um jogador é da equipa da casa
     * @param numero Número do jogador a desgastar
     */
    public void desgastaDurantePartida(boolean visitada, int numero){
        if(visitada){
            this.equipaVisitada.desgastaJogadorTitular(numero);
        }
        else this.equipaVisitante.desgastaJogadorTitular(numero);
    }

    /**
     * Altera o valor do tempo de jogo
     * @param tempo Novo valor
     */
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }   

    /**
     * Devolve um jogador de uma equipa de futebol
     * @param visitada Booleano que verifica se um jogador é da equipa da casa
     * @param numero Número do jogador a descobrir
     * @return Jogador pretendido
     */
    public Jogador getJogador(boolean visitada, int numero){
        if(visitada){
            return this.equipaVisitada.getJogador(numero);
        }
        else return this.equipaVisitante.getJogador(numero);
    }

    /**
     * Devolve um jogador aleatório da equipa da casa ou da equipa visitante de uma dada posição
     * @param visitada Booleano que verifica se um jogador é da equipa da casa
     * @param posicao Posição do jogador a descobrir
     * @return Jogador aleatório
     */
    public Jogador getJogador(boolean visitada, String posicao){
        List<Jogador> js;

        if(visitada){
            js = this.equipaVisitada.getPlantel().getTitulares().values().stream().
                    filter(e -> e.getPosicao().equals(posicao)).collect(Collectors.toList());
        }
        else {
            js = this.equipaVisitante.getPlantel().getTitulares().values().stream().
                    filter(e -> e.getPosicao().equals(posicao)).collect(Collectors.toList());
        }
        Random i = new Random();
        if(js.size()>0)
            return js.get(i.nextInt(js.size()));
        else
            return null;
    }

    /**
     * Devolve um map de jogadores titulares, onde a cada jogador é associado o seu número de camisola
     * @param visitada Booleano que verifica se um jogador é da equipa da casa
     * @return Map de jogadores titulares
     */
    public Map<Integer, Jogador> getEquipaTitulares(boolean visitada){
        if(visitada) return this.equipaVisitada.getPlantel().getTitulares();
        else return this.equipaVisitante.getPlantel().getTitulares();
    }

    /**
     * Devolve o valor do tempo de uma partida
     * @return Tempo
     */
    public double getTempo() {
        return tempo;
    }

    /**
     * Devolve o nome de uma equipa
     * @param visitado Booleano a verificar se é a equipa da casa
     * @return Nome da equipa
     */
    public String getNomeEquipa(boolean visitado){
        if (visitado) {
            return this.equipaVisitada.getNome();
        }
        else return this.equipaVisitante.getNome();
    }

    /**
     * Devolve o resultado em forma de string
     * @return
     */
    public String resultado(){
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(this.equipaVisitada.getNome()).append(") ").append(this.golosVisitado);
        sb.append(" - ").append(this.golosVisitante).append(" (").append(this.equipaVisitante.getNome()).append(")");
        return sb.toString();
    }

    /**
     * Incrementa o tempo com um dado valor
     * @param tempo Valor a acrescentar
     */
    public void incTimer(double tempo){
        this.tempo += tempo;
    }

    /**
     * Incrementa o número de golos de uma equipa de futebol
     * @param visitada Booleano que verifica se é a equipa da casa
     */
    public void incGolos(boolean visitada){
        if (visitada) {
            this.golosVisitado++;
        }
        else this.golosVisitante++;
    }


    /**
     * Atualiza uma equipa (casa ou fora de acordo com o booleano)
     * @param e Equipa de futebol da casa
     * @param visitada Booleano que verifica se é a equipa da casa
     */
    public void atualizaEquipa (EquipaFutebol e, boolean visitada){
        if(visitada)
            atualizaVisitado(e);
        else
            atualizaVisitante(e);
    }

    /**
     * Atualiza a equipa da casa
     * @param e Equipa de futebol da casa
     */
    public void atualizaVisitado (EquipaFutebol e){
        this.equipaVisitada = e.clone();
    }

    /**
     * Atualiza a equipa visitante
     * @param e Equipa de futebol visitante
     */
    public void atualizaVisitante (EquipaFutebol e){
        this.equipaVisitante = e.clone();
    }

    /**
     * Transforma o objeto PartidaFutebol numa string
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("***Partida***\n");
        sb.append("**Resultado** ").append(resultado()).append("\n");
        sb.append("**Data** ").append(this.data.toString()).append("\n");
        sb.append(campo(tatica(true),tatica(false)));
        sb.append("**Equipa Visitada**\n");
        sb.append(equipaVisitada.toString());
        sb.append("*Substituições*\n");
        for(int i = 0; i < 3; i++){
            if(this.subsVisitada[0][i] != 0)
                sb.append(this.subsVisitada[0][i]).append("->").append(this.subsVisitada[1][i]).append("\n");
        }
        sb.append("----------------------\n");
        sb.append("**Equipa Visitante**\n");
        sb.append(equipaVisitante.toString());
        sb.append("*Substituições*\n");
        for(int i = 0; i < 3; i++){
            if(this.subsVisitante[0][i] != 0){
                sb.append(this.subsVisitante[0][i]).append("->").append(this.subsVisitante[1][i]).append("\n");
            }
        }
        return sb.toString();
    }

    public String campo(String[] e1, String[] e2){
        StringBuilder sb = new StringBuilder();
        sb.append("      _______________________________________  \n");
        sb.append("     |                   |                   |  \n");
        sb.append("     |____ ").append(e1[4]).append(e1[6]).append("          |          ").append(e2[6]).append(e2[4]).append(" ____|  \n");
        sb.append("     |__  |").append(e1[1]).append("  ").append(e1[8]).append("    ").append(e1[14]).append("  |  ").append(e2[14]).append("    ").append(e2[8]).append("  ").append(e2[1]).append("|  __|  \n");
        sb.append("    .|  | |.   ").append(e1[9]).append("       .|.       ").append(e2[9]).append("   .| |  |. \n");
        sb.append("    ||").append(e1[0]).append("| |").append(e1[2]).append("  ").append(e1[10]).append(" ").append(e1[13]).append(" ").append(e1[15]).append("( | )").append(e2[15]).append(" ").append(e2[13]).append(" ").append(e2[10]).append("  ").append(e2[2]).append("| |").append(e2[0]).append("|| \n");
        sb.append("    '|__| |'   ").append(e1[11]).append("       '|'       ").append(e2[11]).append("   '| |__|' \n");
        sb.append("     |__  |").append(e1[3]).append("  ").append(e1[12]).append("    ").append(e1[16]).append("  |  ").append(e2[16]).append("    ").append(e2[12]).append("  ").append(e2[3]).append("|  __|  \n");
        sb.append("     |____ ").append(e1[5]).append(e1[7]).append("          |          ").append(e2[7]).append(e2[5]).append(" ____|  \n");
        sb.append("     |___________________|___________________|  \n");
        return sb.toString();
    }

    public String[] tatica(boolean visitado){
        Tatica t;
        if (visitado)
            t = this.equipaVisitada.getPlantel().getTatica();
        else t = this.equipaVisitante.getPlantel().getTatica();

        Tatica[] taticas = {new Tatica(1,2,2,4,2), //4-4-2
                new Tatica(1,2,2,3,3), //4-3-3
                new Tatica(1,3,2,3,2), //3-5-2
                new Tatica(1,3,2,4,1), //3-6-1
                new Tatica(1,3,4,2,1), //5-4-1
                new Tatica(1,3,2,3,2)}; //5-3-2

        String [] s = new String[17];
        for(int i = 0; i < 17; i++){
            s[i] = "";
        }
        if(t.equals(taticas[0])){ //4-4-2 || 2 - 6 - 7 - 8 - 12 - 15
            s[2] = s[6] = s[7] = s[8] = s[12] = s[15] = "  ";
        }
        else if(t.equals(taticas[1])){ //4-3-3 || 2 - 6 - 7 - 9 - 11 - 13
            s[2] = s[6] = s[7] = s[9] = s[11] = s[13] = "  ";
        }
        else if(t.equals(taticas[2])){ //3-5-2 || 4 - 5 - 9 - 11 - 13 - 15
            s[4] = s[5] = s[9] = s[11] = s[13] = s[15] = "  ";
        }
        else if(t.equals(taticas[3])){ //3-6-1 || 4 - 5 - 9 - 11 - 14 - 16
            s[4] = s[5] = s[9] = s[11] = s[14] = s[16] = "  ";
        }
        else if(t.equals(taticas[4])){ //5-4-1 || 6 - 7 - 9 - 11 - 14 - 16
            s[6] = s[7] = s[9] = s[11] = s[14] = s[16] = "  ";

        }
        else if(t.equals(taticas[5])){ //5-3-2 || 6 - 7 - 9 - 11 - 13 - 15
            s[6] = s[7] = s[9] = s[11] = s[13] = s[15] = "  ";
        }
        String[] n = numerosTitulares(visitado);
        int x, y = 0;
        for(x = 0; y <= 10; x++){
            if(!s[x].equals("  ")) {
                s[x] = n[y];
                y++;
            }
        }
        return s;
    }



/*
//4-4-2 || 2 - 6 - 7 - 8 - 12 - 15
//4-3-3 || 2 - 6 - 7 - 9 - 11 - 13
//3-5-2 || 4 - 5 - 9 - 11 - 13 - 15
//3-6-1 || 4 - 5 - 9 - 11 - 14 - 16
//5-4-1 || 6 - 7 - 9 - 11 - 14 - 16
//5-3-2 || 6 - 7 - 9 - 11 - 13 - 15
    public String campo(String[] numeros){
        StringBuilder sb = new StringBuilder();
        sb.append("      _______________________________________  \n");
        sb.append("     |                   |                   |  \n");
        sb.append("     |____ 0406          |          LTLT ____|  \n");
        sb.append("     |__  |01  08    14  |  AV    MD  DF|  __|  \n");
        sb.append("    .|  | |.   09       .|.       MD   .| |  |. \n");
        sb.append("    ||00| |02  10 13 15( | )15 13 10  02| |00|| \n");
        sb.append("    '|__| |'   11       '|'       MD   '| |__|' \n");
        sb.append("     |____|03  12    16  |  AV    MD  DF|____|  \n");
        sb.append("     |     0507          |          LTLT     |  \n");
        sb.append("     |___________________|___________________|  \n");
        return sb.toString();
    }
 */

    /**
     * Transforma uma partida de futebol numa linha
     * @return Linha com o objeto PartidaFutebol
     */
    public String toFicheiro() {
        StringBuilder sb = new StringBuilder();
        sb.append(equipaVisitada.getNome()).append(",");
        sb.append(equipaVisitante.getNome()).append(",");
        sb.append(golosVisitado).append(",").append(golosVisitante).append(",");
        sb.append(data.toString());
        sb.append(numerosEquipa(true));
        sb.append(numerosEquipa(false));
        sb.append("\n");
        return sb.toString();
    }

    /**
     * String com os números das camisolas de todos os jogadores titulares de uma equipa
     * @param visitado Booleano que verifica se é equipa da casa
     * @return String com os números das camisolas
     */
    private String numerosEquipa(boolean visitado) {
        StringBuilder sb = new StringBuilder();
        EquipaFutebol e;
        int [][]subs;
        if(visitado) {
            e = this.equipaVisitada;
            subs = this.subsVisitada;
        }
        else {
            e = this.equipaVisitante;
            subs = this.subsVisitante;
        }
        for(int n : e.getPlantel().getTitulares().keySet()){
            sb.append(",").append(n);
        }

        for(int i = 0; i < 3; i++){
            sb.append(",");
            if(subs[0][i] != 0){
                sb.append(subs[0][i]).append("->").append(subs[1][i]);
            }
        }
        return sb.toString();
    }

    public String[] numerosTitulares(boolean visitado){
        String[] numeros = new String [11];
        Map <Integer,Jogador> titulares = this.getEquipaTitulares(visitado);
        TreeSet<Jogador> tm = new TreeSet<>(new CompPosicao());

        tm.addAll(titulares.values());
        int x = 0;
        for(Jogador j : tm){
            int n = j.getNumero();
            if(n > 9){
                numeros[x] = Integer.toString(n);
            }
            else numeros[x] = "0"+n;
            x++;
        }
        return numeros;
    }
}


