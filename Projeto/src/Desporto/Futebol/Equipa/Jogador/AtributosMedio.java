package Desporto.Futebol.Equipa.Jogador;

import java.io.Serializable;

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

    public AtributosMedio(Atributos atributos) {
        super(atributos);
        if(atributos instanceof AtributosMedio){
            this.setRecuperacaoDeBolas(((AtributosMedio) atributos).getRecuperacaoDeBolas());
            this.setVisaoDeJogo(((AtributosMedio) atributos).getVisaoDeJogo());
        }
        else{
            this.setRecuperacaoDeBolas(1);
            this.setVisaoDeJogo(1);
        }
    }

    /**
     *
     */
    public void setRecuperacaoDeBolas(int recuperacaoDeBolas) {
        if(recuperacaoDeBolas >= 1 && recuperacaoDeBolas <= 100)
            this.recuperacaoDeBolas = recuperacaoDeBolas;
        else if(recuperacaoDeBolas >= 1){
            this.recuperacaoDeBolas = 100;
        }
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
        if(visaoDeJogo >= 1 && visaoDeJogo <= 100)
            this.visaoDeJogo = visaoDeJogo;
        else if(visaoDeJogo >= 1){
            this.visaoDeJogo = 100;
        }
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
    public int overall (){
        return (int) (this.getVisaoDeJogo() * 0.2 + this.getResistencia() * 0.05 + this.getVelocidade() * 0.05 + this.getDestreza() * 0.1 +
                this.getImpulsao() * 0.05 + this.getJogoDeCabeca() * 0.1 + this.getRemate() * 0.1 +
                this.getControloDePasse() * 0.2 + this.getRecuperacaoDeBolas() * 0.15);
    }

    public void desgaste(){
        int r = (int) ((100 - getResistencia()) * 0.025) + 1;
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

    public String toFicheiro() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toFicheiro()).append(",").append(this.recuperacaoDeBolas);
        return sb.toString();
    }
}
