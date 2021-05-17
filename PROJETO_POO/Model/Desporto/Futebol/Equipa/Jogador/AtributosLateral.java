package Futebol.Equipa.Jogador;

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

    /**
     *
     */
    public void setPrecisaoCruzamentos(int precisaoCruzamentos) {
        this.precisaoCruzamentos = precisaoCruzamentos;
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
        this.drible = drible;
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
