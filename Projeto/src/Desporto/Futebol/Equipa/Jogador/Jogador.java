package Desporto.Futebol.Equipa.Jogador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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

    public int habilidadeExtra () throws PosicaoInvalidaException{
        int habExt = 0;
        Random num = new Random();
        switch(this.getPosicao()){
            case "Guarda-Redes":
            habExt = getAtributos().media() + ((AtributosGR) getAtributos()).getElasticidade()
            + ((AtributosGR) getAtributos()).getReflexos();
                break;
            case "Defesa":
            habExt = getAtributos().media() + ((AtributosDefesa) this.getAtributos()).getPosicionamentoDefensivo() + 
                    ((AtributosDefesa) this.getAtributos()).getCortes() + 5 - num.nextInt(10);
                break;
            case "Medio":
            habExt = getAtributos().media() + ((AtributosMedio) this.getAtributos()).getRecuperacaoDeBolas() + 
                    ((AtributosMedio) this.getAtributos()).getVisaoDeJogo() + 5 - num.nextInt(10);
                break;
            case "Lateral":
            habExt = getAtributos().media() + ((AtributosLateral) this.getAtributos()).getPrecisaoCruzamentos() + 
                    ((AtributosLateral) this.getAtributos()).getDrible() + 5 - num.nextInt(10);
                break;
            case "Avancado":
            habExt = getAtributos().media() + ((AtributosAvancado) this.getAtributos()).getPenaltis() + 
                    ((AtributosAvancado) this.getAtributos()).getDesmarcacao() + 5 - num.nextInt(10);
                break;
            default:
                throw new PosicaoInvalidaException();
        }
        return habExt;
    }

    public Jogador (String linha) throws PosicaoInvalidaException {
        this();
        String[] parametros = linha.split(",");
        String[] aux = parametros[0].split(":");
        switch(aux[0]){
                case "Guarda-Redes":
                this.setNome(aux[1]);
                this.setNumero(Integer.parseInt(parametros[1]));
                this.setPosicao(aux[0]);
                this.historial = new ArrayList<>();
                this.setAtributos(new AtributosGR(Integer.parseInt(parametros[2]),
                                               Integer.parseInt(parametros[3]),
                                               Integer.parseInt(parametros[4]),
                                               Integer.parseInt(parametros[5]),
                                               Integer.parseInt(parametros[6]),
                                               Integer.parseInt(parametros[7]),
                                               Integer.parseInt(parametros[8]),
                                               Integer.parseInt(parametros[9]),
                                               this.habilidadeExtra()));
                break;
                case "Defesa":
                this.setNome(aux[1]);
                this.setNumero(Integer.parseInt(parametros[1]));
                this.setPosicao(aux[0]);
                this.historial = new ArrayList<>();
                this.setAtributos(new AtributosDefesa(Integer.parseInt(parametros[2]),
                                                   Integer.parseInt(parametros[3]),
                                                   Integer.parseInt(parametros[4]),
                                                   Integer.parseInt(parametros[5]),
                                                   Integer.parseInt(parametros[6]),
                                                   Integer.parseInt(parametros[7]),
                                                   Integer.parseInt(parametros[8]),
                                                   this.habilidadeExtra(),
                                                   this.habilidadeExtra()));
                break;
                case "Medio":
                this.setNome(aux[1]);
                this.setNumero(Integer.parseInt(parametros[1]));
                this.setPosicao(aux[0]);
                this.historial = new ArrayList<>();
                this.setAtributos(new AtributosMedio(Integer.parseInt(parametros[2]),
                                                  Integer.parseInt(parametros[3]),
                                                  Integer.parseInt(parametros[4]),
                                                  Integer.parseInt(parametros[5]),
                                                  Integer.parseInt(parametros[6]),
                                                  Integer.parseInt(parametros[7]),
                                                  Integer.parseInt(parametros[8]),
                                                  Integer.parseInt(parametros[9]),
                                                  this.habilidadeExtra()));
                    break;
                case "Lateral":
                this.setNome(aux[1]);
                this.setNumero(Integer.parseInt(parametros[1]));
                this.setPosicao(aux[0]);
                this.historial = new ArrayList<>();
                this.setAtributos(new AtributosLateral(Integer.parseInt(parametros[2]),
                                                    Integer.parseInt(parametros[3]),
                                                    Integer.parseInt(parametros[4]),
                                                    Integer.parseInt(parametros[5]),
                                                    Integer.parseInt(parametros[6]),
                                                    Integer.parseInt(parametros[7]),
                                                    Integer.parseInt(parametros[8]),
                                                    Integer.parseInt(parametros[9]),
                                                    this.habilidadeExtra()));
                    break;
                case "Avancado":
                this.setNome(aux[1]);
                this.setNumero(Integer.parseInt(parametros[1]));
                this.setPosicao(aux[0]);
                this.historial = new ArrayList<>();
                this.setAtributos(new AtributosAvancado(Integer.parseInt(parametros[2]),
                                                     Integer.parseInt(parametros[3]),
                                                     Integer.parseInt(parametros[4]),
                                                     Integer.parseInt(parametros[5]),
                                                     Integer.parseInt(parametros[6]),
                                                     Integer.parseInt(parametros[7]),
                                                     Integer.parseInt(parametros[8]),
                                                     this.habilidadeExtra(),
                                                     this.habilidadeExtra()));
                    break;
                default:
                    throw new PosicaoInvalidaException();
        }
    }

}
