package Desporto.Futebol.Equipa.Jogador;

import java.text.DecimalFormat;

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

        public AtributosAvancado(Atributos atributos, double x) {
        super(atributos,x);
        int m = this.media();
        this.setDesmarcacao((int)((x+0.10)*m));
        this.setPenaltis((int)((x)*m));
    }

    /**
     *
     */
    public void setPenaltis(int penaltis) {
        if(penaltis >= 1 && penaltis <= 100)
            this.penaltis = penaltis;
        else if(penaltis >= 1){
            this.penaltis = 100;
        }
        else this.penaltis = 1;
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
        if(desmarcacao >= 1 && desmarcacao <= 100)
            this.desmarcacao = desmarcacao;
        else if(desmarcacao >= 1){
            this.desmarcacao = 100;
        }
        else this.desmarcacao = 1;
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
    public int overall (){
        return (int) (this.getDesmarcacao() * 0.15 + this.getResistencia() * 0.1 + this.getVelocidade() * 0.1 + this.getDestreza() * 0.1 +
                this.getImpulsao() * 0.1 + this.getJogoDeCabeca() * 0.15 + this.getRemate() * 0.2 +
                this.getControloDePasse() * 0.05 + this.getPenaltis() * 0.05);
    }

    public void desgaste(){
        int r = (int) ((100 - getResistencia()) * 0.05) + 1;
        this.setPenaltis(getPenaltis()-r);
        this.setDesmarcacao(getDesmarcacao()-r);
        this.setDestreza(getDestreza()-r);
        this.setImpulsao(getImpulsao()-r);
        this.setRemate(getRemate()-r);
        this.setControloDePasse(getControloDePasse()-r);
        this.setJogoDeCabeca(getJogoDeCabeca()-r);
        this.setVelocidade(getVelocidade()-r);
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
        sb.append("Penaltis: ").append(this.penaltis).append("\n");
        sb.append("Desmarcacao: ")    .append(this.desmarcacao)    .append("\n");
        return sb.toString();
    }

    public String toFicheiro() {
        return super.toFicheiro();
    }
}
