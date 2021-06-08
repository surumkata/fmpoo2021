package Desporto.Futebol.Equipa;

import java.io.Serializable;

public class Tatica implements Serializable {
    private int nGR;
    private int nDF;
    private int nLT;
    private int nMD;
    private int nPL;

    public Tatica(){
        this(1,2,2,4,2);
    }

    public Tatica(int nGR, int nDF, int nLT, int nMD, int nPL){
        this.setnGR(nGR);
        this.setnDF(nDF);
        this.setnLT(nLT);
        this.setnMD(nMD);
        this.setnPL(nPL);
    }

    public Tatica(Tatica outraTatica){
        this(outraTatica.getnGR(), outraTatica.getnDF(), outraTatica.getnLT(), outraTatica.getnMD(), outraTatica.getnPL());
    }

    public void setnGR(int nGR) {
        this.nGR = nGR;
    }

    public int getnGR() {
        return nGR;
    }

    public void setnDF(int nDF) {
        this.nDF = nDF;
    }

    public int getnDF() {
        return nDF;
    }

    public void setnLT(int nLT) {
        this.nLT = nLT;
    }

    public int getnLT() {
        return nLT;
    }

    public void setnMD(int nMD) {
        this.nMD = nMD;
    }

    public int getnMD() {
        return nMD;
    }

    public void setnPL(int nPL) {
        this.nPL = nPL;
    }

    public int getnPL() {
        return nPL;
    }

    public boolean taticaValida (){
        return this.getnGR() == 1 && (this.getnDF() + this.getnLT() + this.getnMD() + this.getnPL() == 10);
    }

    public String toString(){
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getnDF() + this.getnLT()).append("-").append(this.getnMD()).append("-").append(this.getnPL());
        return sb.toString();
    }

    public Tatica clone() {
        Tatica tatica = new Tatica(this);
        return tatica;
    }

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
