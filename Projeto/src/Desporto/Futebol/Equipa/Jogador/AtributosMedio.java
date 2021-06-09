package Desporto.Futebol.Equipa.Jogador;


/**
 * Classe para os atributos de um médio, onde se incluem a recuperação de bolas e a visão de jogo
 */
public class AtributosMedio extends Atributos {
    private int recuperacaoDeBolas;
    private int visaoDeJogo;

    /**
     * Construtor vazio dos atributos de um médio
     */
    public AtributosMedio(){
        this(0,0,0,0,0,0,0,0,0);
    }

    /**
     * Construtor parametrizado dos atributos de um médio
     * @param vel Velocidade 
     * @param res Resistencia
     * @param des Destreza
     * @param imp Impulsão
     * @param jdc Jogo de cabeça
     * @param rem Remate
     * @param cdp Controlo de passe
     * @param rdb Recuperação de bolas
     * @param vdj Visão de jogo
     */
    public AtributosMedio(int vel, int res, int des, int imp, int jdc, int rem, int cdp, int rdb, int vdj){
        super(vel,res,des,imp,jdc,rem,cdp);
        this.setRecuperacaoDeBolas(rdb);
        this.setVisaoDeJogo(vdj);
    }

    /**
     * Construtor cópia dos atributos de um médio
     * @param oAtrMD Objeto original
     */
    public AtributosMedio(AtributosMedio oAtrMD){
        super(oAtrMD);
        this.setRecuperacaoDeBolas(oAtrMD.getRecuperacaoDeBolas());
        this.setVisaoDeJogo(oAtrMD.getVisaoDeJogo());
    }

    /**
     * Construtor dos atributos de um médio, multiplicando cada parâmetro por um valor, de forma a calcular os parâmetros-extra
     * @param atributos Atributos comuns a todos os jogadores
     * @param x Valor a multiplicar
     */
    public AtributosMedio(Atributos atributos, double x) {
        super(atributos,x);
        int m = this.media();
        this.setVisaoDeJogo((int)((x+0.10)*m));
        this.setRecuperacaoDeBolas((int)((x+0.20)*m));
    }

    /**
     * Construtor dos atributos de um médio, recebendo quaisquer atributos
     * @param atributos Atributos comuns a todos os jogadores
     */
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
     * Altera o valor da recuperação de bola, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param recuperacaoDeBolas Novo valor
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
     * Devolve a recuperação de bola de um jogador
     * @return Recuperação de bola
     */
    public int getRecuperacaoDeBolas() {
        return recuperacaoDeBolas;
    }

    /**
     * Altera o valor da visão de jogo, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param visaoDeJogo Novo valor
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
     * Devolve a visão de jogo de um jogador
     * @return Visão de jogo
     */
    public int getVisaoDeJogo() {
        return visaoDeJogo;
    }

    /**
     * Método que faz uma cópia dos atributos de um médio
     */
    public AtributosMedio clone() {
        return new AtributosMedio(this);
    }

    /**
     * Calcula a habilidade global de um médio, arredondado às unidades
     */
    public int overall (){
        return (int) (this.getVisaoDeJogo() * 0.2 + this.getResistencia() * 0.05 + this.getVelocidade() * 0.05 + this.getDestreza() * 0.1 +
                this.getImpulsao() * 0.05 + this.getJogoDeCabeca() * 0.1 + this.getRemate() * 0.1 +
                this.getControloDePasse() * 0.2 + this.getRecuperacaoDeBolas() * 0.15);
    }

    /**
     * Calcula o desgaste de um médio ao longo de uma partida de futebol
     */
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
     * Verifica se 2 objetos AtributosMedio são iguais
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
     * Representa os atributos de um médio numa string
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Recuperação de Bolas: ").append(this.recuperacaoDeBolas).append("\n");
        sb.append("Visão de Jogo: ")               .append(this.visaoDeJogo)             .append("\n");
        return sb.toString();
    }

    /**
     * Representa os atributos de um médio numa linha
     */
    public String toFicheiro() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toFicheiro()).append(",").append(this.recuperacaoDeBolas);
        return sb.toString();
    }
}
