package Desporto.Futebol.Equipa.Jogador;

/**
 *
 */
public class AtributosGR extends Atributos {
    private int elasticidade;
    private int reflexos;

    /**
     *
     */
    public AtributosGR(){
        this(0,0,0,0,0,0,0,0,0);
    }

    /**
     *
     */
    public AtributosGR(int vel, int res, int des, int imp, int jdc, int rem, int cdp, int ela, int ref){
        super(vel,res,des,imp,jdc,rem,cdp);
        this.setElasticidade(ela);
        this.setReflexos(ref);
    }

    public AtributosGR(Atributos aot, int ela, int ref){
        super(aot);
        this.setElasticidade(ela);
        this.setReflexos(ref);
    }

    /**
     *
     */
    public AtributosGR(AtributosGR oAtrGR){
        super(oAtrGR);
        this.setElasticidade(oAtrGR.getElasticidade());
        this.setReflexos(oAtrGR.getReflexos());
    }

    public AtributosGR(Atributos oAtr){
        super(oAtr);
        this.setElasticidade(1);
        this.setReflexos(1);
    }

    /**
     *
     */
    public void setElasticidade(int elasticidade) {
        if(elasticidade >= 1 && elasticidade <= 100)
            this.elasticidade = elasticidade;
        else if(elasticidade >= 1){
            this.elasticidade = 100;
        }
        else this.elasticidade = 1;
    }

    /**
     *
     */
    public int getElasticidade() {
        return elasticidade;
    }

    /**
     *
     */
    public void setReflexos(int reflexos) {
        if(reflexos >= 1 && reflexos <= 100)
            this.reflexos = reflexos;
        else if(reflexos >= 1){
            this.reflexos = 100;
        }
        else this.reflexos = 1;
    }

    /**
     *
     */
    public int getReflexos() {
        return reflexos;
    }

    /**
     *
     */
    public AtributosGR clone() {
        return new AtributosGR(this);
    }

    /**
     *
     */
    public int overall (){
        return (int) (this.getReflexos() * 0.3 + this.getResistencia() * 0.025 + this.getVelocidade() * 0.125 + this.getDestreza() * 0.025 +
                this.getImpulsao() * 0.2 + this.getJogoDeCabeca() * 0.025 + this.getRemate() * 0.00 +
                this.getControloDePasse() * 0.1 + this.getElasticidade() * 0.2);
    }

    public void desgaste(){
        int r = (int) ((100 - getResistencia()) * 0.05) + 1;
        this.setElasticidade(getElasticidade()-r);
        this.setReflexos(getReflexos()-r);
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
        AtributosGR abGR = (AtributosGR) o;
        return super.equals(abGR)                     &&
               this.elasticidade == abGR.elasticidade &&
               this.reflexos     == abGR.reflexos;
    }

    /**
     *
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Elasticidade: ").append(this.elasticidade).append("\n");
        sb.append("Reflexos: ")    .append(this.reflexos);
        return sb.toString();
    }

    public String toFicheiro() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toFicheiro()).append(",").append(this.elasticidade);
        return sb.toString();
    }
}
