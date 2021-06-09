package Desporto.Futebol.Equipa.Jogador;

/**
 * Classe para os atributos de um avançado, onde se incluem a marcação de penaltys e a desmarcação 
 */
public class AtributosAvancado extends Atributos {
    private int penaltis;
    private int desmarcacao;

    /**
     * Construtor vazio dos atributos de um avançado
     */
    public AtributosAvancado(){
        this(1,1,1,1,1,1,1,1,1);
    }

    /**
     * Construtor parametrizado dos atributos de um avançado
     * @param vel Velocidade 
     * @param res Resistencia
     * @param des Destreza
     * @param imp Impulsão
     * @param jdc Jogo de cabeça
     * @param rem Remate
     * @param cdp Controlo de passe
     * @param pen Penaltys
     * @param dsm Desmarcação
     */
    public AtributosAvancado(int vel, int res, int des, int imp, int jdc, int rem, int cdp, int pen, int dsm){
        super(vel,res,des,imp,jdc,rem,cdp);
        this.setPenaltis(pen);
        this.setDesmarcacao(dsm);
    }

    /**
     * Construtor cópia dos atributos de um avançado
     * @param oAtrAv Objeto original
     */
    public AtributosAvancado(AtributosAvancado oAtrAv){
        super(oAtrAv);
        this.setPenaltis(oAtrAv.getPenaltis());
        this.setDesmarcacao(oAtrAv.getPenaltis());
    }

    /**
     * Construtor dos atributos de um avançado, multiplicando cada parâmetro por um valor, de forma a calcular os parâmetros-extra
     * @param atributos Atributos comuns a todos os jogadores
     * @param x Valor a multiplicar
     */
    public AtributosAvancado(Atributos atributos, double x) {
        super(atributos,x);
        int m = this.media();
        this.setDesmarcacao((int)((x+0.10)*m));
        this.setPenaltis((int)((x)*m));
    }

    /**
     * Construtor dos atributos de um avançado, recebendo quaisquer atributos
     * @param atributos Atributos comuns a todos os jogadores
     */
    public AtributosAvancado(Atributos atributos) {
        super(atributos);
        if(atributos instanceof AtributosAvancado){
            this.setDesmarcacao(((AtributosAvancado) atributos).getDesmarcacao());
            this.setPenaltis(((AtributosAvancado) atributos).getPenaltis());
        }
        else{
            this.setDesmarcacao(1);
            this.setPenaltis(1);
        }
    }

    /**
     * Altera o valor da marcação de penaltys, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param penaltis Novo valor
     */
    public void setPenaltis(int penaltis) {
        if(penaltis >= 1 && penaltis <= 100)
            this.penaltis = penaltis;
        else if(penaltis >= 1){
            this.penaltis = 100;
        }
        else this.penaltis = 1;
    }

    /**
     * Devolve a marcação de penaltys de um jogador
     * @return Marcação de penaltys de um jogador
     */
    public int getPenaltis() {
        return penaltis;
    }

    /**
     * Altera o valor da desmarcação, verificando se o valor inserido está entre 1 e 100 (inclusive)
     * @param desmarcacao Novo valor
     */
    public void setDesmarcacao(int desmarcacao) {
        if(desmarcacao >= 1 && desmarcacao <= 100)
            this.desmarcacao = desmarcacao;
        else if(desmarcacao >= 1){
            this.desmarcacao = 100;
        }
        else this.desmarcacao = 1;
    }

    /**
     * Devolve o valor da desmarcação de um jogador
     * @return Desmarcação de um jogador
     */
    public int getDesmarcacao() {
        return desmarcacao;
    }

    /**
     * Método que faz uma cópia dos atributos de avançado.
     */
    public AtributosAvancado clone() {
        return new AtributosAvancado(this);
    }

    /**
     * Calcula a habilidade global de um avançado, arredondado às unidades
     */
    public int overall (){
        return (int) (this.getDesmarcacao() * 0.15 + this.getResistencia() * 0.1 + this.getVelocidade() * 0.1 + this.getDestreza() * 0.1 +
                this.getImpulsao() * 0.1 + this.getJogoDeCabeca() * 0.15 + this.getRemate() * 0.2 +
                this.getControloDePasse() * 0.05 + this.getPenaltis() * 0.05);
    }   

    /**
     * Calcula o desgaste de um avançado ao longo de uma partida de futebol
     */
    public void desgaste(){
        int r = (int) ((100 - getResistencia()) * 0.025) + 1;
        this.setPenaltis(getPenaltis()-r);
        this.setDesmarcacao(getDesmarcacao()-r);
        this.setDestreza(getDestreza()-r);
        this.setImpulsao(getImpulsao()-r);
        this.setRemate(getRemate()-r);
        this.setControloDePasse(getControloDePasse()-r);
        this.setJogoDeCabeca(getJogoDeCabeca()-r);
        this.setVelocidade(getVelocidade()-r);
    }

    /**
     * Verifica se 2 objetos AtributosAvancado são iguais
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        AtributosAvancado abAV = (AtributosAvancado) o;
        return super.equals(abAV)                     &&
                this.penaltis == abAV.penaltis &&
                this.desmarcacao     == abAV.desmarcacao;
    }

    /**
     * Representa os atributos de um avançado numa string
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Penaltis: ").append(this.penaltis).append("\n");
        sb.append("Desmarcacao: ")    .append(this.desmarcacao)    .append("\n");
        return sb.toString();
    }

    /**
     * Representa os atributos de um avançado numa linha
     */
    public String toFicheiro() {
        return super.toFicheiro();
    }
}
