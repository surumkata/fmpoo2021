package Desporto.Futebol.Equipa.Jogador;


/**
 * Classe para os atributos de um guarda-redes, onde se incluem a elasticidade e os reflexos
 */
public class AtributosGR extends Atributos {
    private int elasticidade;
    private int reflexos;

    /**
     * Construtor vazio dos atributos de um guarda-redes
     */
    public AtributosGR(){
        this(0,0,0,0,0,0,0,0,0);
    }

    /**
     * Construtor parametrizado dos atributos de um guarda-redes
     * @param vel Velocidade 
     * @param res Resistencia
     * @param des Destreza
     * @param imp Impulsão
     * @param jdc Jogo de cabeça
     * @param rem Remate
     * @param cdp Controlo de passe
     * @param ela Elasticidade
     * @param ref Reflexos
     */
    public AtributosGR(int vel, int res, int des, int imp, int jdc, int rem, int cdp, int ela, int ref){
        super(vel,res,des,imp,jdc,rem,cdp);
        this.setElasticidade(ela);
        this.setReflexos(ref);
    }

    /**
     * Construtor dos atributos de um guarda-redes, recebendo os atributos gerais e os valores para os penaltys e para a desmarcação
     * @param aot Atributos gerais
     * @param ela Elasticidade
     * @param ref Reflexos
     */
    public AtributosGR(Atributos aot, int ela, int ref){
        super(aot);
        this.setElasticidade(ela);
        this.setReflexos(ref);
    }

    /**
     * Construtor cópia dos atributos de um guarda-redes
     * @param oAtrGR Objeto original
     */
    public AtributosGR(AtributosGR oAtrGR){
        super(oAtrGR);
        this.setElasticidade(oAtrGR.getElasticidade());
        this.setReflexos(oAtrGR.getReflexos());
    }

    /**
     * Construtor dos atributos de um guarda-redes, recebendo quaisquer atributos
     * @param atributos Atributos comuns a todos os jogadores
     */
    public AtributosGR(Atributos atributos) {
        super(atributos);
        if(atributos instanceof AtributosGR){
            this.setReflexos(((AtributosGR) atributos).getReflexos());
            this.setElasticidade(((AtributosGR) atributos).getElasticidade());
        }
        else{
            this.setReflexos(1);
            this.setElasticidade(1);
        }
    }

    /**
     * Altera o valor da elasticidade, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param elasticidade Novo valor
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
     * Devolve a elasticidade de um jogador
     * @return Elasticidade
     */
    public int getElasticidade() {
        return elasticidade;
    }

    /**
     * Altera o valor dos reflexos, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param reflexos Reflexos
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
     * Devolve os reflexos de um jogador
     * @return
     */
    public int getReflexos() {
        return reflexos;
    }

    /**
     * Método que faz uma cópia dos atributos de um guarda-redes.
     */
    public AtributosGR clone() {
        return new AtributosGR(this);
    }

    /**
     * Calcula a habilidade global de um guarda-redes, arredondado às unidades
     */
    public int overall (){
        return (int) (this.getReflexos() * 0.3 + this.getResistencia() * 0.025 + this.getVelocidade() * 0.125 + this.getDestreza() * 0.025 +
                this.getImpulsao() * 0.2 + this.getJogoDeCabeca() * 0.025 + this.getRemate() * 0.00 +
                this.getControloDePasse() * 0.1 + this.getElasticidade() * 0.2);
    }

    /**
     * Calcula o desgaste de um guarda-redes ao longo de uma partida de futebol
     */
    public void desgaste(){
        int r = (int) ((100 - getResistencia()) * 0.015) + 1;
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
     * Verifica se 2 objetos AtributosGR são iguais
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
     * Representa os atributos de um guarda-redes numa string
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Elasticidade: ").append(this.elasticidade).append("\n");
        sb.append("Reflexos: ")    .append(this.reflexos);
        return sb.toString();
    }

    /**
     * Representa os atributos de um guarda-redes numa linha
     */
    public String toFicheiro() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toFicheiro()).append(",").append(this.elasticidade);
        return sb.toString();
    }
}
