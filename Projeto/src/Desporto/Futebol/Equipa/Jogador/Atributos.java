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
     *
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
     *
     */
    public int getVelocidade() {
        return velocidade;
    }

    /**
     *
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
     *
     */
    public int getResistencia() {
        return resistencia;
    }

    /**
     *
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
     *
     */
    public int getDestreza() {
        return destreza;
    }

    /**
     *
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
     *
     */
    public int getImpulsao() {
        return impulsao;
    }

    /**
     *
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
     *
     */
    public int getJogoDeCabeca() {
        return jogoDeCabeca;
    }

    /**
     *
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
     *
     */
    public int getRemate() {
        return remate;
    }

    /**
     *
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
    public abstract int overall();

    public abstract void desgaste();

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
