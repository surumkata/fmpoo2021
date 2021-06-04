package Desporto.Futebol.Equipa.Jogador;

import java.util.ArrayList;
import java.util.List;


public class Jogador {
    private int numero;
    private String nome;
    private String posicao;
    private List <String> historial;
    private Atributos atributos;

    public Jogador(){
        this.numero = 0;
        this.nome ="";
        this.posicao="";
        this.historial = new ArrayList<>();
    }

    public Jogador(int numero, String nome, String posicao, List<String> historial, Atributos atributos){
        if (posicaoValida(posicao)){
            this.posicao = "Guarda-Redes";
        }
        else this.posicao = posicao;
        this.numero = numero;
        this.nome = nome;
        this.historial = new ArrayList<>(historial);
        this.atributos = atributos.clone();
    }

    public Jogador (Jogador oJ){
        this.numero = oJ.getNumero();
        this.nome = oJ.getNome();
        this.posicao = oJ.getPosicao();
        this.historial = new ArrayList <>(oJ.getHistorial());
        this.atributos = oJ.getAtributos();
    }

    private boolean posicaoValida (String posicao){
        return posicao.equals("Guarda-Redes") ||
               posicao.equals("Defesa") ||
               posicao.equals("Lateral") ||
               posicao.equals("Medio") ||
               posicao.equals("Avancado");
    }

    public String getNome() {
        return nome;
    }
    
    public int getNumero() {
        return numero;
    }

    public Atributos getAtributos() {
        return atributos.clone();
    }

    public List<String> getHistorial() {
        return new ArrayList<>(this.historial);
    }

    public String getPosicao() {
        return posicao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void addEquipaHistorial (String equipa){
        this.historial.add(equipa);
    }

    public void setAtributos(Atributos atributos) {
        this.atributos = atributos.clone();
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
        if (this.posicao.equals("Defesa")){
            this.atributos = new AtributosDefesa();
        }
        else if(this.posicao.equals("Medio")){
            this.atributos = new AtributosMedio();
        }
        else if(this.posicao.equals("Lateral")){
            this.atributos = new AtributosLateral();
        }
        else if(this.posicao.equals("Avancado")){
            this.atributos = new AtributosAvancado();
        }
        else
            this.atributos = new AtributosGR();
    }

    public Jogador clone() {
        return new Jogador(this);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Jogador j = (Jogador) o;
        return this.nome.equals(j.nome) && this.numero == j.numero && this.posicao.equals(j.posicao)
                && this.historial.equals(j.historial) && this.atributos.equals(j.atributos);
    }
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(this.getNome()).append("\n");
        sb.append("Numero: ").append(this.getNumero()).append("\n");
        sb.append("Posição: ").append(this.getPosicao()).append("\n");
        sb.append("Historial de clubes: ").append(this.getHistorial().toString()).append("\n");
        sb.append("Atributos; ").append(this.getAtributos().toString()).append("\n");
        return sb.toString();
    }
}
