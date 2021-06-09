package Desporto.Futebol.Equipa.Jogador;

import java.io.Serializable;

/**
 * Classe abstrata para os atributos comuns a todos os jogadores: velocidade, resistência, impulsão, jogo de cabeça, remate e controlo de passe.
 */
public abstract class Atributos implements Serializable {
    int velocidade;
    int resistencia;
    int destreza;
    int impulsao;
    int jogoDeCabeca;
    int remate;
    int controloDePasse;

    /**
     * Construtor parametrizado da classe Atributos
     * @param vel Velocidade 
     * @param res Resistencia
     * @param des Destreza
     * @param imp Impulsão
     * @param jdc Jogo de cabeça
     * @param rem Remate
     * @param cdp Controlo de passe
     */
    public Atributos(int vel, int res, int des, int imp, int jdc, int rem, int cdp){
        this.setVelocidade(vel);
        this.setResistencia(res);
        this.setDestreza(des);
        this.setImpulsao(imp);
        this.setJogoDeCabeca(jdc);
        this.setRemate(rem);
        this.setControloDePasse(cdp);
    }

    /**
     * Construtor de cópia de Atributos
     * @param oAtr Objeto original
     */
    public Atributos(Atributos oAtr){
        this(oAtr.getVelocidade(), oAtr.getResistencia(), oAtr.getDestreza(), oAtr.getImpulsao(), oAtr.getJogoDeCabeca(), oAtr.getRemate(), oAtr.getControloDePasse());
    }

    /**
     * Construtor de cópia de Atributos, multiplicando um valor em cada parâmetro
     * @param oAtr Atributo original
     * @param x Valor a multiplicar
     */
    public Atributos(Atributos oAtr, double x){
        this((int) (oAtr.getVelocidade() * x),
                (int) (oAtr.getResistencia() * x),
                (int) (oAtr.getDestreza() * x),
                (int) (oAtr.getImpulsao() * x),
                (int) (oAtr.getJogoDeCabeca() * x),
                (int) (oAtr.getRemate() * x),
                (int) (oAtr.getControloDePasse() * x));
    }

    /**
     * Altera o valor da velocidade, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param velocidade Novo valor
     */
    public void setVelocidade(int velocidade) {
        if(velocidade >= 1 && velocidade <= 100)
            this.velocidade = velocidade;
        else if(velocidade > 0){
            this.velocidade = 100;
        }
        else this.velocidade = 1;
    }

    /**
     * Devolve a velocidade de um Jogador
     * @return Velocidade
     */
    public int getVelocidade() {
        return velocidade;
    }

    /**
     * Altera o valor da resistência, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param resistencia Novo valor
     */
    public void setResistencia(int resistencia) {
        if(resistencia >= 1 && resistencia <= 100)
            this.resistencia = resistencia;
        else if(resistencia >= 1){
            this.resistencia = 100;
        }
        else this.resistencia = 1;
    }

    /**
     * Devolve a resistência de um jogador
     * @return Resistência
     */
    public int getResistencia() {
        return resistencia;
    }

    /**
     * Altera o valor da destreza, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param destreza Novo valor
     */
    public void setDestreza(int destreza) {
        if(destreza >= 1 && destreza <= 100)
            this.destreza = destreza;
        else if(destreza >= 1){
            this.destreza = 100;
        }
        else this.destreza = 1;
    }

    /**
     * Devolve o destreza de um jogador
     * @return Destreza
     */
    public int getDestreza() {
        return destreza;
    }

    /**
     * 
     * Altera o valor da impulsão, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param impulsao Novo valor
     */
    public void setImpulsao(int impulsao) {
        if(impulsao >= 1 && impulsao <= 100)
            this.impulsao = impulsao;
        else if(impulsao >= 1){
            this.impulsao = 100;
        }
        else this.impulsao = 1;
    }

    /**
     * Devolve a impulsão de um jogador
     * @return Impulsão
     */
    public int getImpulsao() {
        return impulsao;
    }

    /**
     * Altera o valor do jogo de cabeça, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param jogoDeCabeca Novo valor
     */
    public void setJogoDeCabeca(int jogoDeCabeca) {
        if(jogoDeCabeca >= 1 && jogoDeCabeca <= 100)
            this.jogoDeCabeca = jogoDeCabeca;
        else if(jogoDeCabeca >= 1){
            this.jogoDeCabeca = 100;
        }
        else this.jogoDeCabeca = 1;
    }

    /**
     * Devolve o jogo de cabeça de um jogador
     * @return Jogo de cabeça
     */
    public int getJogoDeCabeca() {
        return jogoDeCabeca;
    }

    /**
     * Altera o valor do remate, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param remate Novo valor
     */
    public void setRemate(int remate) {
        if(remate >= 1 && remate <= 100)
            this.remate = remate;
        else if(remate >= 1){
            this.remate = 100;
        }
        else this.remate = 1;
    }

    /**
     * Devolve o remate de um jogador
     * @return Remate
     */
    public int getRemate() {
        return remate;
    }

    /**
     * Altera o valor do controlo de passe, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param controloDePasse Novo valor
     */
    public void setControloDePasse(int controloDePasse) {
        if(controloDePasse >= 1 && controloDePasse <= 100)
            this.controloDePasse = controloDePasse;
        else if(controloDePasse >= 1){
            this.controloDePasse = 100;
        }
        else this.controloDePasse = 1;
    }

    /**
     * Devolve o controlo de passe de um jogador
     * @return Controlo de passe
     */
    public int getControloDePasse() {
        return controloDePasse;
    }

    /**
     * Método abstrato de cópia 
     */
    public abstract Atributos clone();

    /**
     * Método abstrato do calculo da habilidade global de um jogador
     * @return Habilidade global de um jogador
     */
    public abstract int overall();

    /**
     * Método abstrato do desgaste de um jogador ao longo do jogo
     */
    public abstract void desgaste();

    /**
     * Verifica se 2 objetos Atributos são iguais
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Atributos ab = (Atributos) o;
        return this.velocidade      == ab.velocidade       &&
               this.resistencia     == ab.resistencia      &&
               this.destreza        == ab.destreza         &&
               this.impulsao        == ab.impulsao         &&
               this.jogoDeCabeca    == ab.jogoDeCabeca     &&
               this.remate          == ab.remate           &&
               this.controloDePasse == ab.controloDePasse;
    }

    /**
     * Representa os atributos em forma de string
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Atributos:")         .append("\n");
        sb.append("Velocidade: ")       .append(this.velocidade)     .append("\n");
        sb.append("Resistência: ")      .append(this.resistencia)    .append("\n");
        sb.append("Destreza: ")         .append(this.destreza)       .append("\n");
        sb.append("Impulsão: ")         .append(this.impulsao)       .append("\n");
        sb.append("Jogo de Cabeça: ")   .append(this.jogoDeCabeca)   .append("\n");
        sb.append("Remate: ")           .append(this.remate)         .append("\n");
        sb.append("Controlo de Passe: ").append(this.controloDePasse).append("\n");
        return sb.toString();
    }

    /**
     * Devolve a média aritmética dos parâmetros da classe atributos
     * @return Média aritmética dos parâmetros da classe atributos
     */
    public int media (){
        return (getVelocidade()+getControloDePasse()+getDestreza()+getImpulsao()+getRemate()+getResistencia()+getJogoDeCabeca())/7;
    }

    /**
     * Representa o objeto Atributos num ficheiro
     * @return Linha com a informação dos atributos
     */
    public String toFicheiro() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.velocidade)     .append(",");
        sb.append(this.resistencia)    .append(",");
        sb.append(this.destreza)       .append(",");
        sb.append(this.impulsao)       .append(",");
        sb.append(this.jogoDeCabeca)   .append(",");
        sb.append(this.remate)         .append(",");
        sb.append(this.controloDePasse);
        return sb.toString();
    }
}
