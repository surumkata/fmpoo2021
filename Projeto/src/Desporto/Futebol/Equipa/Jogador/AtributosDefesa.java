package Desporto.Futebol.Equipa.Jogador;

/**
 *
 */
public class AtributosDefesa extends Atributos {
    private int posicionamentoDefensivo;
    private int cortes;

    /**
     *
     */
    public AtributosDefesa(){
        this(0,0,0,0,0,0,0,0,0);
    }

    /**
     *
     */
    public AtributosDefesa(int vel, int res, int des, int imp, int jdc, int rem, int cdp, int pcs, int dri){
        super(vel,res,des,imp,jdc,rem,cdp);
        this.setPosicionamentoDefensivo(pcs);
        this.setCortes(dri);
    }

    /**
     *
     */
    public AtributosDefesa(AtributosDefesa oAtrDF){
        super(oAtrDF);
        this.setPosicionamentoDefensivo(oAtrDF.getPosicionamentoDefensivo());
        this.setCortes(oAtrDF.getCortes());
    }

    public AtributosDefesa(Atributos atributos, double x) {
        super(atributos,x);
        int m = this.media();
        this.setCortes((int)((x+0.15)*m));
        this.setPosicionamentoDefensivo((int)((x+0.05)*m));
    }

    /**
     *
     */
    public void setPosicionamentoDefensivo(int posicionamentoDefensivo) {
        if (posicionamentoDefensivo > 0)
            this.posicionamentoDefensivo = posicionamentoDefensivo;
        else this.posicionamentoDefensivo = 1;
    }

    /**
     *
     */
    public int getPosicionamentoDefensivo() {
        return posicionamentoDefensivo;
    }

    /**
     *
     */
    public void setCortes(int cortes) {
        if(cortes > 0)
            this.cortes = cortes;
        else this.cortes = 1;
    }

    /**
     *
     */
    public int getCortes() {
        return cortes;
    }

    /**
     *
     */
    public AtributosDefesa clone() {
        return new AtributosDefesa(this);
    }

    /**
     *
     */
    public double overall (){
        return this.getPosicionamentoDefensivo() * 0.2 + this.getResistencia() * 0.1 + this.getVelocidade() * 0.05 + this.getDestreza() * 0.05 +
                this.getImpulsao() * 0.15 + this.getJogoDeCabeca() * 0.1 + this.getRemate() * 0.05 +
                this.getControloDePasse() * 0.1 + this.getCortes() * 0.2;
    }

    public void desgaste(){
        int r = (int) ((100 - getResistencia()) * 0.05) + 1;
        this.setPosicionamentoDefensivo(getPosicionamentoDefensivo()-r);
        this.setCortes(getCortes()-r);
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
        AtributosDefesa abDF = (AtributosDefesa) o;
        return super.equals(abDF)                                   &&
                this.posicionamentoDefensivo == abDF.posicionamentoDefensivo &&
                this.cortes              == abDF.cortes;
    }

    /**
     *
     */
    public String toString() {
        return super.toString() +
                "Precisao de Cruzamentos: " + this.posicionamentoDefensivo + "\n" +
                "Reflexos: " + this.cortes + "\n";
    }
}
