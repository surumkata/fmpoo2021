package Desporto.Futebol.Equipa.Jogador;

/**
 *
 */
public class AtributosMedio extends Atributos {
    private int recuperacaoDeBolas;
    private int visaoDeJogo;

    /**
     *
     */
    public AtributosMedio(){
        this(0,0,0,0,0,0,0,0,0);
    }

    /**
     *
     */
    public AtributosMedio(int vel, int res, int des, int imp, int jdc, int rem, int cdp, int rdb, int vdj){
        super(vel,res,des,imp,jdc,rem,cdp);
        this.setRecuperacaoDeBolas(rdb);
        this.setVisaoDeJogo(vdj);
    }

    /**
     *
     */
    public AtributosMedio(AtributosMedio oAtrMD){
        super(oAtrMD);
        this.setRecuperacaoDeBolas(oAtrMD.getRecuperacaoDeBolas());
        this.setVisaoDeJogo(oAtrMD.getVisaoDeJogo());
    }


    public AtributosMedio(Atributos atributos, double x) {
        super(atributos,x);
        int m = this.media();
        this.setVisaoDeJogo((int)((x+0.10)*m));
        this.setRecuperacaoDeBolas((int)((x+0.20)*m));
    }

    /**
     *
     */
    public void setRecuperacaoDeBolas(int recuperacaoDeBolas) {
        if(recuperacaoDeBolas > 0)
            this.recuperacaoDeBolas = recuperacaoDeBolas;
        else this.recuperacaoDeBolas = 1;
    }

    /**
     *
     */
    public int getRecuperacaoDeBolas() {
        return recuperacaoDeBolas;
    }

    /**
     *
     */
    public void setVisaoDeJogo(int visaoDeJogo) {
        if(visaoDeJogo > 0)
            this.visaoDeJogo = visaoDeJogo;
        else this.visaoDeJogo = 1;
    }

    /**
     *
     */
    public int getVisaoDeJogo() {
        return visaoDeJogo;
    }

    /**
     *
     */
    public AtributosMedio clone() {
        return new AtributosMedio(this);
    }

    /**
     *
     */
    public double overall (){
        return this.getVisaoDeJogo() * 0.2 + this.getResistencia() * 0.05 + this.getVelocidade() * 0.05 + this.getDestreza() * 0.1 +
                this.getImpulsao() * 0.05 + this.getJogoDeCabeca() * 0.1 + this.getRemate() * 0.1 +
                this.getControloDePasse() * 0.2 + this.getRecuperacaoDeBolas() * 0.15;
    }

    public void desgaste(){
        int r = (int) ((100 - getResistencia()) * 0.05) + 1;
        this.setVisaoDeJogo(getRecuperacaoDeBolas()-r);
        this.setRecuperacaoDeBolas(getRecuperacaoDeBolas()-r);
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
        AtributosMedio abMD = (AtributosMedio) o;
        return super.equals(abMD)                                 &&
               this.recuperacaoDeBolas == abMD.recuperacaoDeBolas &&
               this.visaoDeJogo        == abMD.visaoDeJogo;
    }

    /**
     *
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Recuperação de Bolas: ").append(this.recuperacaoDeBolas).append("\n");
        sb.append("Visão de Jogo: ")               .append(this.visaoDeJogo)             .append("\n");
        return sb.toString();
    }
}
