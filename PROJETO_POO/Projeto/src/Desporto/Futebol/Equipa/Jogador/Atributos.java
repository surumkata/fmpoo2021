package Desporto.Futebol.Equipa.Jogador;

/**
 *
 */
public abstract class Atributos {
    int velocidade;
    int resistencia;
    int destreza;
    int impulsao;
    int jogoDeCabeca;
    int remate;
    int controloDePasse;

    /**
     *
     */
    public Atributos(){
        this(0,0,0,0,0,0,0);
    }

    /**
     *
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
     *
     */
    public Atributos(Atributos oAtr){
        this(oAtr.getVelocidade(), oAtr.getResistencia(), oAtr.getDestreza(), oAtr.getImpulsao(), oAtr.getJogoDeCabeca(), oAtr.getRemate(), oAtr.getControloDePasse());
    }

    /**
     *
     */
    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    /**
     *
     */
    public int getVelocidade() {
        return velocidade;
    }

    /**
     *
     */
    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    /**
     *
     */
    public int getResistencia() {
        return resistencia;
    }

    /**
     *
     */
    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    /**
     *
     */
    public int getDestreza() {
        return destreza;
    }

    /**
     *
     */
    public void setImpulsao(int impulsao) {
        this.impulsao = impulsao;
    }

    /**
     *
     */
    public int getImpulsao() {
        return impulsao;
    }

    /**
     *
     */
    public void setJogoDeCabeca(int jogoDeCabeca) {
        this.jogoDeCabeca = jogoDeCabeca;
    }

    /**
     *
     */
    public int getJogoDeCabeca() {
        return jogoDeCabeca;
    }

    /**
     *
     */
    public void setRemate(int remate) {
        this.remate = remate;
    }

    /**
     *
     */
    public int getRemate() {
        return remate;
    }

    /**
     *
     */
    public void setControloDePasse(int controloDePasse) {
        this.controloDePasse = controloDePasse;
    }

    /**
     *
     */
    public int getControloDePasse() {
        return controloDePasse;
    }

    /**
     *
     */
    public abstract Atributos clone();

    /**
     *
     */
    public abstract double overall();

    /**
     *
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
     *
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

    public int media (){
        return (getVelocidade()+getControloDePasse()+getDestreza()+getImpulsao()+getRemate()+getResistencia()+getJogoDeCabeca())/7;
    }
}
