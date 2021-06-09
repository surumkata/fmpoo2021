package Desporto.Futebol.Equipa.Jogador;


/**
 * Classe para os atributos de um defesa, onde se incluem o posicionamento defensivo e a capacidade de cortar a bola
 */
public class AtributosDefesa extends Atributos {
    private int posicionamentoDefensivo;
    private int cortes;

    /**
     * Construtor vazio dos atributos de um defesa
     */
    public AtributosDefesa(){
        this(0,0,0,0,0,0,0,0,0);
    }

    /**
     * Construtor parametrizado dos atributos de um defesa
     * @param vel Velocidade 
     * @param res Resistencia
     * @param des Destreza
     * @param imp Impulsão
     * @param jdc Jogo de cabeça
     * @param rem Remate
     * @param cdp Controlo de passe
     * @param pcs Posicionamento defensivo
     * @param cor Corte
     */
    public AtributosDefesa(int vel, int res, int des, int imp, int jdc, int rem, int cdp, int pcs, int cor){
        super(vel,res,des,imp,jdc,rem,cdp);
        this.setPosicionamentoDefensivo(pcs);
        this.setCortes(cor);
    }

    /**
     * Construtor cópia dos atributos de um defesa
     * @param oAtrDF Objeto original
     */
    public AtributosDefesa(AtributosDefesa oAtrDF){
        super(oAtrDF);
        this.setPosicionamentoDefensivo(oAtrDF.getPosicionamentoDefensivo());
        this.setCortes(oAtrDF.getCortes());
    }

    /**
     * Construtor dos atributos de um defesa, multiplicando cada parâmetro por um valor, de forma a calcular os parâmetros-extra
     * @param atributos Atributos comuns a todos os jogadores
     * @param x Valor a multiplicar
     */
    public AtributosDefesa(Atributos atributos, double x) {
        super(atributos,x);
        int m = this.media();
        this.setCortes((int)((x+0.15)*m));
        this.setPosicionamentoDefensivo((int)((x+0.05)*m));
    }

    /**
     * Construtor dos atributos de um defesa, recebendo quaisquer atributos
     * @param atributos Atributos comuns a todos os jogadores
     */
    public AtributosDefesa(Atributos atributos) {
        super(atributos);
        if(atributos instanceof AtributosDefesa){
            this.setCortes(((AtributosDefesa) atributos).getCortes());
            this.setPosicionamentoDefensivo(((AtributosDefesa) atributos).getPosicionamentoDefensivo());
        }
        else{
            this.setCortes(1);
            this.setPosicionamentoDefensivo(1);
        }
    }

    /**
     * Altera o valor do posicionamento defensivo, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param posicionamentoDefensivo Novo valor
     */
    public void setPosicionamentoDefensivo(int posicionamentoDefensivo) {
        if(posicionamentoDefensivo >= 1 && posicionamentoDefensivo <= 100)
            this.posicionamentoDefensivo = posicionamentoDefensivo;
        else if(posicionamentoDefensivo >= 1){
            this.posicionamentoDefensivo = 100;
        }
        else this.posicionamentoDefensivo = 1;
    }

    /**
     * Devolve o posicionamento defensivo de um jogador
     * @return Posicionamento defensivo
     */
    public int getPosicionamentoDefensivo() {
        return posicionamentoDefensivo;
    }

    /**
     * Altera o valor dos cortes, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param cortes Novo valor
     */
    public void setCortes(int cortes) {
        if(cortes >= 1 && cortes <= 100)
            this.cortes = cortes;
        else if(cortes >= 1){
            this.cortes = 100;
        }
        else this.cortes = 1;
    }

    /**
     * Devolve os cortes de um jogador
     * @return Cortes
     */
    public int getCortes() {
        return cortes;
    }

    /**
     * Método que faz uma cópia dos atributos de um defesa.
     */
    public AtributosDefesa clone() {
        return new AtributosDefesa(this);
    }

    /**
     * Calcula a habilidade global de um defesa, arredondado às unidades
     */
    public int overall (){
        return (int) (this.getPosicionamentoDefensivo() * 0.2 + this.getResistencia() * 0.1 + this.getVelocidade() * 0.05 + this.getDestreza() * 0.05 +
                this.getImpulsao() * 0.15 + this.getJogoDeCabeca() * 0.1 + this.getRemate() * 0.05 +
                this.getControloDePasse() * 0.1 + this.getCortes() * 0.2);
    }

    /**
     * Calcula o desgaste de um defesa ao longo de uma partida de futebol
     */
    public void desgaste(){
        int r = (int) ((100 - getResistencia()) * 0.025) + 1;
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
     * Verifica se 2 objetos AtributosDefesa são iguais
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
     * Representa os atributos de um defesa numa string
     */
    public String toString() {
        return super.toString() +
                "Precisao de Cruzamentos: " + this.posicionamentoDefensivo + "\n" +
                "Cortes: " + this.cortes + "\n";
    }

    /**
     * Representa os atributos de um defesa numa linha
     */
    public String toFicheiro() {
        return super.toFicheiro();
    }
}
