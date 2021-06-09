package Desporto.Futebol.Equipa.Jogador;


/**
 * Classe para os atributos de um lateral, onde se incluem a precisão de cruzamentos e o drible
 */
public class AtributosLateral extends Atributos{
    private int precisaoCruzamentos;
    private int drible;

    /**
     * Construtor vazio dos atributos de um lateral
     */
    public AtributosLateral(){
        this(0,0,0,0,0,0,0,0,0);
    }

    /**
     * Construtor parametrizado dos atributos de um lateral
     * @param vel Velocidade 
     * @param res Resistencia
     * @param des Destreza
     * @param imp Impulsão
     * @param jdc Jogo de cabeça
     * @param rem Remate
     * @param cdp Controlo de passe
     * @param pcs Precisão dos cruzamentos
     * @param dri Drible
     */
    public AtributosLateral(int vel, int res, int des, int imp, int jdc, int rem, int cdp, int pcs, int dri){
        super(vel,res,des,imp,jdc,rem,cdp);
        this.setPrecisaoCruzamentos(pcs);
        this.setDrible(dri);
    }

    /**
     * Construtor cópia dos atributos de um lateral
     * @param oAtrLT Objeto original
     */
    public AtributosLateral(AtributosLateral oAtrLT){
        super(oAtrLT);
        this.setPrecisaoCruzamentos(oAtrLT.getPrecisaoCruzamentos());
        this.setDrible(oAtrLT.getDrible());
    }

    /**
     * Construtor dos atributos de um lateral, multiplicando cada parâmetro por um valor, de forma a calcular os parâmetros-extra
     * @param atributos Atributos comuns a todos os jogadores
     * @param x Valor a multiplicar
     */
    public AtributosLateral(Atributos atributos, double x) {
        super(atributos,x);
        int m = this.media();
        this.setDrible((int)((x+0.10)*m));
        this.setPrecisaoCruzamentos((int)((x+0.10)*m));
    }

    /**
     * Construtor dos atributos de um lateral, recebendo quaisquer atributos
     * @param atributos Atributos comuns a todos os jogadores
     */
    public AtributosLateral(Atributos atributos) {
        super(atributos);
        if(atributos instanceof AtributosLateral){
            this.setPrecisaoCruzamentos(((AtributosLateral) atributos).getPrecisaoCruzamentos());
            this.setDrible(((AtributosLateral) atributos).getDrible());
        }
        else{
            this.setPrecisaoCruzamentos(1);
            this.setDrible(1);
        }
    }

    /**
     * Altera o valor da precisão dos cruzamentos, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param precisaoCruzamentos Novo valor
     */
    public void setPrecisaoCruzamentos(int precisaoCruzamentos) {
        if(precisaoCruzamentos >= 1 && precisaoCruzamentos <= 100)
            this.precisaoCruzamentos = precisaoCruzamentos;
        else if(precisaoCruzamentos >= 1){
            this.precisaoCruzamentos = 100;
        }
        else this.precisaoCruzamentos = 1;
    }

    /**
     * Devolve a precisão de cruzamentos de um jogador
     * @return Precisão de cruzamentos
     */
    public int getPrecisaoCruzamentos() {
        return precisaoCruzamentos;
    }

    /**
     * Altera o valor do drible, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param drible Novo valor
     */
    public void setDrible(int drible) {
        if(drible >= 1 && drible <= 100)
            this.drible = drible;
        else if(drible >= 1){
            this.drible = 100;
        }
        else this.drible = 1;
    }

    /**
     * Devolve o drible de um jogador
     * @return Drible
     */
    public int getDrible() {
        return drible;
    }

    /**
     * Método que faz uma cópia dos atributos de um lateral
     */
    public AtributosLateral clone() {
        return new AtributosLateral(this);
    }

    /**
     * Calcula a habilidade global de um lateral, arredondado às unidades
     */
    public int overall (){
        return (int) (this.getDrible() * 0.1 + this.getResistencia() * 0.2 + this.getVelocidade() * 0.15 + this.getDestreza() * 0.1 +
                this.getImpulsao() * 0.05 + this.getJogoDeCabeca() * 0.05 + this.getRemate() * 0.05 +
                this.getControloDePasse() * 0.1 + this.getPrecisaoCruzamentos() * 0.2);
    }

    /**
     * Calcula o desgaste de um lateral ao longo de uma partida de futebol
     */
    public void desgaste(){
        int r = (int) ((100 - getResistencia()) * 0.025) + 1;
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
     * Verifica se 2 objetos AtributosLateral são iguais
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
     * Representa os atributos de um lateral numa string
     */
    public String toString() {
        return super.toString() +
                "Precisao de Cruzamentos: " + this.precisaoCruzamentos + "\n" +
                "Drible: " + this.drible + "\n";
    }

    /**
     * Representa os atributos de um lateral numa linha
     */
    public String toFicheiro() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toFicheiro()).append(",").append(this.precisaoCruzamentos);
        return sb.toString();
    }
}
