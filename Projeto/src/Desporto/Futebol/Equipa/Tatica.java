package Desporto.Futebol.Equipa;

import java.io.Serializable;

/**
 * Classe do esquema tático de uma equipa
 */
public class Tatica implements Serializable {
    private int nGR;
    private int nDF;
    private int nLT;
    private int nMD;
    private int nPL;

    /**
     * Construtor vazio de uma tática.
     * Por defeito, a tática é 4-4-2
     */
    public Tatica(){
        this(1,2,2,4,2);
    }

    /**
     * Construtor parametrizado de uma tática
     * @param nGR Número de guarda-redes titulares
     * @param nDF Número de defesas titulares
     * @param nLT Número de laterais titulares
     * @param nMD Número de médio titulares
     * @param nPL Número de avançados titulares
     */
    public Tatica(int nGR, int nDF, int nLT, int nMD, int nPL){
        this.setnGR(nGR);
        this.setnDF(nDF);
        this.setnLT(nLT);
        this.setnMD(nMD);
        this.setnPL(nPL);
    }

    /**
     * Construtor cópia de uma tática
     * @param outraTatica Objeto original
     */
    public Tatica(Tatica outraTatica){
        this(outraTatica.getnGR(), outraTatica.getnDF(), outraTatica.getnLT(), outraTatica.getnMD(), outraTatica.getnPL());
    }

    /**
     * Altera o valor do número de guarda-redes no onze inicial
     * @param nGR Novo valor
     */
    public void setnGR(int nGR) {
        this.nGR = nGR;
    }

    /**
     * Devolve o número de guarda-redes no onze inicial
     * @return Número de guarda-redes
     */
    public int getnGR() {
        return nGR;
    }

    /**
     * Altera o valor do número de defesas no onze inicial
     * @param nDF Novo valor
     */
    public void setnDF(int nDF) {
        this.nDF = nDF;
    }

    /**
     * Devolve o número de defesas no onze inicial
     * @return Número de defesas
     */
    public int getnDF() {
        return nDF;
    }

    /**
     * Altera o valor do número de laterais no onze inicial
     * @param nLT Novo valor
     */
    public void setnLT(int nLT) {
        this.nLT = nLT;
    }

    /**
     * Devolve o número de laterais no onze inicial
     * @return Número de laterais no onze inicial
     */
    public int getnLT() {
        return nLT;
    }

    /**
     * Altera o valor do número de médio no onze inicial
     * @param nMD Novo valor
     */
    public void setnMD(int nMD) {
        this.nMD = nMD;
    }

    /**
     * Devolve o número de médios no onze inicial
     * @return Número de médios no onze inicial
     */
    public int getnMD() {
        return nMD;
    }

    /**
     * Altera o valor do número de avançados no onze inicial
     * @param nPL Novo valor
     */
    public void setnPL(int nPL) {
        this.nPL = nPL;
    }

    /**
     * Devolve o número de avançados no onze inicial
     * @return Número de avançados no onze inicial
     */
    public int getnPL() {
        return nPL;
    }

    /**
     * Verifica se uma tática é válida: se existe um guarda-redes e se existem mais jogadores nãoguarda-redes
     * @return true se é valida, false caso contrário
     */
    public boolean taticaValida (){
        return this.getnGR() == 1 && (this.getnDF() + this.getnLT() + this.getnMD() + this.getnPL() == 10);
    }

    /**
     * Transforma uma tática numa string    
     */
    public String toString(){
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getnDF() + this.getnLT()).append("-").append(this.getnMD()).append("-").append(this.getnPL());
        return sb.toString();
    }

    /**
     * Método que copia um objeto Tatica
     */
    public Tatica clone() {
        return new Tatica(this);
    }

    /**
     * Verifica se 2 objetos Tatica são iguais
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass())
            return false;
        Tatica t = (Tatica) o;
        return this.getnGR() == t.getnGR() && this.getnDF() == t.getnDF()
                && this.getnLT() == t.getnLT() && this.getnMD() == t.getnMD() && this.getnPL() == t.getnPL();
    }
}
