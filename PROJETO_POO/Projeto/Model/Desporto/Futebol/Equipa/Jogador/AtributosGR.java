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

    /**
     *
     */
    public AtributosGR(AtributosGR oAtrGR){
        super(oAtrGR);
        this.setElasticidade(oAtrGR.getElasticidade());
        this.setReflexos(oAtrGR.getReflexos());
    }

    /**
     *
     */
    public void setElasticidade(int elasticidade) {
        this.elasticidade = elasticidade;
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
        this.reflexos = reflexos;
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
    public double overall (){
        return this.getReflexos() * 0.3 + this.getResistencia() * 0.025 + this.getVelocidade() * 0.125 + this.getDestreza() * 0.025 +
                this.getImpulsao() * 0.2 + this.getJogoDeCabeca() * 0.025 + this.getRemate() * 0.00 +
                this.getControloDePasse() * 0.1 + this.getElasticidade() * 0.2;
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
        sb.append("Reflexos: ")    .append(this.reflexos)    .append("\n");
        return sb.toString();
    }
}
