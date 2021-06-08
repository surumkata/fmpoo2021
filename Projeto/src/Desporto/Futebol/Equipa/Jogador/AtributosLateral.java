package Desporto.Futebol.Equipa.Jogador;

/**
 *
 */
public class AtributosLateral extends Atributos {
    private int precisaoCruzamentos;
    private int drible;

    /**
     *
     */
    public AtributosLateral(){
        this(0,0,0,0,0,0,0,0,0);
    }

    /**
     *
     */
    public AtributosLateral(int vel, int res, int des, int imp, int jdc, int rem, int cdp, int pcs, int dri){
        super(vel,res,des,imp,jdc,rem,cdp);
        this.setPrecisaoCruzamentos(pcs);
        this.setDrible(dri);
    }

    /**
     *
     */
    public AtributosLateral(AtributosLateral oAtrLT){
        super(oAtrLT);
        this.setPrecisaoCruzamentos(oAtrLT.getPrecisaoCruzamentos());
        this.setDrible(oAtrLT.getDrible());
    }

    public AtributosLateral(Atributos atributos, double x) {
        super(atributos,x);
        int m = this.media();
        this.setDrible((int)((x+0.10)*m));
        this.setPrecisaoCruzamentos((int)((x+0.10)*m));
    }

    /**
     *
     */
    public void setPrecisaoCruzamentos(int precisaoCruzamentos) {
        if(precisaoCruzamentos > 0)
            this.precisaoCruzamentos = precisaoCruzamentos;
        else this.precisaoCruzamentos = 1;
    }

    /**
     *
     */
    public int getPrecisaoCruzamentos() {
        return precisaoCruzamentos;
    }

    /**
     *
     */
    public void setDrible(int drible) {
        if(drible > 0)
            this.drible = drible;
        else this.drible = 1;
    }

    /**
     *
     */
    public int getDrible() {
        return drible;
    }

    /**
     *
     */
    public AtributosLateral clone() {
        return new AtributosLateral(this);
    }

    /**
     *
     */
    public double overall (){
        return this.getDrible() * 0.1 + this.getResistencia() * 0.2 + this.getVelocidade() * 0.15 + this.getDestreza() * 0.1 +
                this.getImpulsao() * 0.05 + this.getJogoDeCabeca() * 0.05 + this.getRemate() * 0.05 +
                this.getControloDePasse() * 0.1 + this.getPrecisaoCruzamentos() * 0.2;
    }

    public void desgaste(){
        int r = (int) ((100 - getResistencia()) * 0.05) + 1;
        this.setDrible(getDrible()-r);
        this.setPrecisaoCruzamentos(getPrecisaoCruzamentos()-r);
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
        AtributosLateral abLT = (AtributosLateral) o;
        return super.equals(abLT)                                   &&
               this.precisaoCruzamentos == abLT.precisaoCruzamentos &&
               this.drible              == abLT.drible;
    }

    /**
     *
     */
    public String toString() {
        return super.toString() +
                "Precisao de Cruzamentos: " + this.precisaoCruzamentos + "\n" +
                "Reflexos: " + this.drible + "\n";
    }
}
