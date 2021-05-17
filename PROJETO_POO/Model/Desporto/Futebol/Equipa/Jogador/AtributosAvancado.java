package Futebol.Equipa.Jogador;

/**
 *
 */
public class AtributosAvancado extends Atributos {
    private int penaltis;
    private int desmarcacao;

    /**
     *
     */
    public AtributosAvancado(){
        this(0,0,0,0,0,0,0,0,0);
    }

    /**
     *
     */
    public AtributosAvancado(int vel, int res, int des, int imp, int jdc, int rem, int cdp, int pen, int dsm){
        super(vel,res,des,imp,jdc,rem,cdp);
        this.setPenaltis(pen);
        this.setDesmarcacao(dsm);
    }

    /**
     *
     */
    public AtributosAvancado(AtributosAvancado oAtrAv){
        super(oAtrAv);
        this.setPenaltis(oAtrAv.getPenaltis());
        this.setDesmarcacao(oAtrAv.getPenaltis());
    }

    /**
     *
     */
    public void setPenaltis(int penaltis) {
        this.penaltis = penaltis;
    }

    /**
     *
     */
    public int getPenaltis() {
        return penaltis;
    }

    /**
     *
     */
    public void setDesmarcacao(int desmarcacao) {
        this.desmarcacao = desmarcacao;
    }

    /**
     *
     */
    public int getDesmarcacao() {
        return desmarcacao;
    }

    /**
     *
     */
    public AtributosAvancado clone() {
        return new AtributosAvancado(this);
    }

    /**
     *
     */
    public double overall (){
        return this.getDesmarcacao() * 0.15 + this.getResistencia() * 0.1 + this.getVelocidade() * 0.1 + this.getDestreza() * 0.1 +
                this.getImpulsao() * 0.1 + this.getJogoDeCabeca() * 0.15 + this.getRemate() * 0.2 +
                this.getControloDePasse() * 0.05 + this.getPenaltis() * 0.05;
    }

    /**
     *
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        AtributosAvancado abAV = (AtributosAvancado) o;
        return super.equals(abAV)                     &&
                this.penaltis == abAV.penaltis &&
                this.desmarcacao     == abAV.desmarcacao;
    }

    /**
     *
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Elasticidade: ").append(this.penaltis).append("\n");
        sb.append("Reflexos: ")    .append(this.desmarcacao)    .append("\n");
        return sb.toString();
    }
}
