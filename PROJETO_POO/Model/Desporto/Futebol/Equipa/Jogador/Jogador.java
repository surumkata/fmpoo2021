package Futebol.Equipa.Jogador;

import java.util.List;


public class Jogador {
    private int numero;
    private String nome;
    private String posicao;
    private List <String> historial;
    private Atributos atributos;

    public Jogador(int numero, String nome, String posicao, List<String> historial, Atributos atributos) throws PosicaoInvalidaException{
        if (posicaoValida(posicao)){
            this.numero = numero;
            this.nome = nome;
            this.posicao = posicao;
            this.historial = historial;
            this.atributos = atributos;
        }
        else
            throw new PosicaoInvalidaException("A posição "+posicao+" não existe.\n"); 
    }

    public Jogador (Jogador outroJogador){
        this.numero = outroJogador.getNumero();
        this.nome = outroJogador.getNome();
        this.posicao = outroJogador.getPosicao();
        this.historial = outroJogador.getHistorial();
        this.atributos = outroJogador.getAtributos();
    }

    private boolean posicaoValida (String posicao){
        return this.posicao.equals("Guarda-Redes") || 
               this.posicao.equals("Defesa") ||
               this.posicao.equals("Lateral") ||
               this.posicao.equals("Medio") ||
               this.posicao.equals("Avancado");
    }

    public String getNome() {
        return nome;
    }
    
    public int getNumero() {
        return numero;
    }

    public Atributos getAtributos() {
        return atributos;
    }

    public List<String> getHistorial() {
        return historial;
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

    public void setHistorial(List<String> historial) {
        this.historial = historial;
    }

    public void setAtributos(Atributos atributos) {
        this.atributos = atributos;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
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
        return this.nome == j.nome && this.numero == j.numero && this.posicao == j.posicao
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
