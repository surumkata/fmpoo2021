package Desporto.Futebol.Equipa.Jogador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Jogador implements Serializable {
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
        if (!posicaoValida(posicao)){
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

    public Jogador (Jogador oJ, String posicao){
        this(oJ);
        this.posicao = posicao;
        String posicaoJ = oJ.getPosicao();
        switch (posicao) {
            case "Guarda-Redes":
                this.atributos = new AtributosGR(oJ.getAtributos());
                break;
            case "Defesa":
                switch (posicaoJ) {
                    case "Lateral":
                        this.atributos = new AtributosDefesa(oJ.getAtributos(), 0.70);
                        break;
                    case "Medio":
                        this.atributos = new AtributosDefesa(oJ.getAtributos(), 0.85);
                        break;
                    case "Avancado":
                        this.atributos = new AtributosDefesa(oJ.getAtributos(), 0.60);
                        break;
                    case "Guarda-Redes":
                        this.atributos = new AtributosDefesa(oJ.getAtributos(), 0.30);
                        break;
                    default:
                        break;
                }
                break;
            case "Medio":
                switch (posicaoJ) {
                    case "Lateral":
                        this.atributos = new AtributosMedio(oJ.getAtributos(), 0.50);
                        break;
                    case "Defesa":
                    case "Avancado":
                        this.atributos = new AtributosMedio(oJ.getAtributos(), 0.80);
                        break;
                    case "Guarda-Redes":
                        this.atributos = new AtributosMedio(oJ.getAtributos(), 0.30);
                        break;
                    default:
                        break;
                }
                break;
            case "Avancado":
                switch (posicaoJ) {
                    case "Lateral":
                        this.atributos = new AtributosAvancado(oJ.getAtributos(), 0.75);
                        break;
                    case "Defesa":
                    case "Medio":
                        this.atributos = new AtributosAvancado(oJ.getAtributos(), 0.85);
                        break;
                    case "Guarda-Redes":
                        this.atributos = new AtributosAvancado(oJ.getAtributos(), 0.30);
                        break;
                    default:
                        break;
                }
            case "Lateral":
                switch (posicaoJ) {
                    case "Avancado":
                        this.atributos = new AtributosLateral(oJ.getAtributos(), 0.90);
                        break;
                    case "Defesa":
                        this.atributos = new AtributosLateral(oJ.getAtributos(), 0.60);
                        break;
                    case "Medio":
                        this.atributos = new AtributosLateral(oJ.getAtributos(), 0.30);
                        break;
                    case "Guarda-Redes":
                        this.atributos = new AtributosLateral(oJ.getAtributos(), 0.30);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
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
        if (this.historial != null && !this.historial.isEmpty())
            return new ArrayList<>(this.historial);
        else
            return new ArrayList<>();
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
        switch (this.posicao) {
            case "Defesa":
                if(this.atributos != null)
                    this.atributos = new AtributosDefesa(this.atributos);
                else
                    this.atributos = new AtributosDefesa();
                break;
            case "Medio":
                if(this.atributos != null)
                    this.atributos = new AtributosMedio(this.atributos);
                else
                    this.atributos = new AtributosMedio();
                break;
            case "Lateral":
                if(this.atributos != null)
                    this.atributos = new AtributosLateral(this.atributos);
                else
                    this.atributos = new AtributosLateral();
                break;
            case "Avancado":
                if(this.atributos != null)
                    this.atributos = new AtributosAvancado(this.atributos);
                else
                    this.atributos = new AtributosAvancado();
                break;
            default:
                if(this.atributos != null)
                    this.atributos = new AtributosGR(this.atributos);
                else
                    this.atributos = new AtributosGR();
                break;
        }
    }

    public Jogador clone() {
        return new Jogador(this);
    }

    public void desgastaJogador (){
        this.atributos.desgaste();
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
        sb.append("Historial: ").append(this.getHistorial().toString()).append("\n");
        sb.append(this.getAtributos().toString());
        return sb.toString();
    }

    public int habilidadeExtra (int media){
        Random num = new Random();
        return media + 5 - num.nextInt(10);
    }

    public Jogador (String linha) throws PosicaoInvalidaException {
        this();
        String[] parametros = linha.split(",");
        if(!jogadorValido(parametros))
            return;
        String[] aux = parametros[0].split(":");
        int [] habilidades = new int[8];
        for(int i = 0; i < parametros.length-2; i++){

            try {
                habilidades[i] = Integer.parseInt(parametros[i + 2]);
            }
            catch(NumberFormatException e){
                habilidades[i] = 1;
            }
        }
        int media = 0;
        for(int i = 0; i < 7; i++){
            media += habilidades[i];
        }
        media /= 7;
        this.setNome(aux[1]);
        try{
            this.setNumero(Integer.parseInt(parametros[1]));
        }
        catch(NumberFormatException e){
            this.setNumero(0);
        }
        this.setPosicao(aux[0]);
        this.historial = new ArrayList<>();
        switch(aux[0]){
                case "Guarda-Redes":
                this.setAtributos(new AtributosGR(habilidades[0],
                                                  habilidades[1],
                                                  habilidades[2],
                                                  habilidades[3],
                                                  habilidades[4],
                                                  habilidades[5],
                                                  habilidades[6],
                                                  habilidades[7],
                                                  habilidadeExtra(media)));
                break;
                case "Defesa":
                this.setAtributos(new AtributosDefesa(habilidades[0],
                                                      habilidades[1],
                                                      habilidades[2],
                                                      habilidades[3],
                                                      habilidades[4],
                                                      habilidades[5],
                                                      habilidades[6],
                                                      this.habilidadeExtra(media),
                                                      this.habilidadeExtra(media)));
                break;
                case "Medio":
                this.setAtributos(new AtributosMedio(habilidades[0],
                                                     habilidades[1],
                                                     habilidades[2],
                                                     habilidades[3],
                                                     habilidades[4],
                                                     habilidades[5],
                                                     habilidades[6],
                                                     habilidades[7],
                                                     this.habilidadeExtra(media)));
                    break;
                case "Lateral":
                this.setAtributos(new AtributosLateral(habilidades[0],
                                                       habilidades[1],
                                                       habilidades[2],
                                                       habilidades[3],
                                                       habilidades[4],
                                                       habilidades[5],
                                                       habilidades[6],
                                                       habilidades[7],
                                                       this.habilidadeExtra(media)));
                    break;
                case "Avancado":
                this.setAtributos(new AtributosAvancado(habilidades[0],
                                                        habilidades[1],
                                                        habilidades[2],
                                                        habilidades[3],
                                                        habilidades[4],
                                                        habilidades[5],
                                                        habilidades[6],
                                                        this.habilidadeExtra(media),
                                                        this.habilidadeExtra(media)));
                    break;
                default:
                    throw new PosicaoInvalidaException();
        }
    }

    public boolean jogadorValido (String[] parametros){
        String [] aux = parametros[0].split(":");
        if (aux[0].equals("Guarda-Redes") || aux[0].equals("Lateral") || aux[0].equals("Medio"))
            return parametros.length == 10 && aux.length == 2;
        else if (aux[0].equals("Defesa") || aux[0].equals("Avancado"))
            return parametros.length == 9 && aux.length == 2;
        else
            return false;
    }

    public String toFicheiro() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getPosicao()).append(":");
        sb.append(this.getNome()).append(",");
        sb.append(this.getNumero()).append(",");
        sb.append(this.getAtributos().toFicheiro());
        return sb.toString();
    }
}
