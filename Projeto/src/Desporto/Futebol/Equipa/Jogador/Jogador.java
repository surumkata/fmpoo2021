package Desporto.Futebol.Equipa.Jogador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe que representa um jogador, que possui um número de camisola, um nome, uma posição, um historial dos clubes por onde passou e atributos
 */
public class Jogador implements Serializable {
    private int numero;
    private String nome;
    private String posicao;
    private List <String> historial;
    private Atributos atributos;

    //Array de strings com nomes próprios possíveis de ser escolhidos aleatoriamente
    private final String[] nomesProprios = {
            "Simao","Tiago","Joao","Nuno","Luis","Geremias","Paulo","Goncalo",
            "Pedro","Nelson","Fabio","Gil","Antonio","Miguel","Rogerio",
            "Guilherme","Jose","Chico","Rafael","Eduardo","Jonas","Rodrigo","Rui",
            "Diogo","Tomas","Tobias","Raul","Jorge","Hugo","Andre","Runlo","Ricardo",
            "Eder", "Helder","Cristiano","Armindo","Zeferino","Bernardo","Bruno","Xavier",
            "Joaquim", "Claudio", "Patricio", "Gustavo", "Ruben","Francisco", "Oscar",
            "Alexandre","Amilcar", "Jo"

    };//50

    //Array de strings com apelidos possíveis de ser escolhidos aleatoriamente
    private final String[] nomesApelidos = {
            "Sa","Silva","Barbosa","Geremias","Barroso","Cunha","Carvalho","Sousa","Carneiro",
            "Braz","Alvim","Saraiva","Dias","Fernandes","Rocha","Cardozo","Rodrigues","Ribeiro",
            "Dinis","Tina","Guimaraes","Alves","Pereira","Freitas","Queiros","Costa","Pato","Daniel",
            "Oliveira","Santos","Cerqueira","Correia","Palmeira","Faria","Fagundes","Ramos","Cruz","Ronaldo",
            "Meireles","Coelho","Afonso","Marchel", "Jo", "Jacinto", "Manafa", "Conceicao", "Leite", "Martins",
            "Leal", "Amorim"
    };//50

    /**
     * Construtor vazio de um jogador
     */
    public Jogador(){
        this.numero = 0;
        this.nome ="";
        this.posicao="";
        this.historial = new ArrayList<>();
    }

    /**
     * Construtor parametrizado de um jogador
     * @param numero Numero da camisola 
     * @param nome Nome
     * @param posicao Posição
     * @param historial Historial
     * @param atributos Atributos
     */
    public Jogador(int numero, String nome, String posicao, List<String> historial, Atributos atributos){
        if (!posicaoValida(posicao))
            this.posicao = "Guarda-Redes";
        else 
            this.posicao = posicao;
        this.numero = numero;
        this.nome = nome;
        this.historial = new ArrayList<>(historial);
        this.atributos = atributos.clone();
    }

    /**
     * Construtor cópia de um jogador
     * @param oJ Objeto original
     */
    public Jogador (Jogador oJ){
        this.numero = oJ.getNumero();
        this.nome = oJ.getNome();
        this.posicao = oJ.getPosicao();
        this.historial = new ArrayList <>(oJ.getHistorial());
        this.atributos = oJ.getAtributos();
    }

    /**
     * Construtor cópia de um jogador, mas com a posição alterada pelo argumento deste método e respetivos atributos
     * @param oJ Objeto original
     * @param posicao Posição do novo jogador
     */
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
                break;
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

    /**
     * Testa se uma posição é válida
     * @param posicao Posição a validar
     * @return true se válido, false caso contrário
     */
    private boolean posicaoValida (String posicao){
        return posicao.equals("Guarda-Redes") ||
               posicao.equals("Defesa") ||
               posicao.equals("Lateral") ||
               posicao.equals("Medio") ||
               posicao.equals("Avancado");
    }

    /**
     * Devolve o nome do jogador
     * @return Nome
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Devolve o número da camisola do jogador
     * @return Número da camisola
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Devolve os atributos de um jogador
     * @return Atributos
     */
    public Atributos getAtributos() {
        return atributos.clone();
    }

    /**
     * Devolve o historial de um jogador
     * @return Historial
     */
    public List<String> getHistorial() {
        if (this.historial != null && !this.historial.isEmpty())
            return new ArrayList<>(this.historial);
        else
            return new ArrayList<>();
    }

    /**
     * Devolve a posição de um jogador
     * @return
     */
    public String getPosicao() {
        return posicao;
    }

    /**
     * Altera o nome de um jogador
     * @param nome Novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Altera o número da camisola de um jogador
     * @param numero Novo número
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Adiciona uma equipa ao historial de um jogador
     * @param equipa Equipa a adicionar
     */
    public void addEquipaHistorial (String equipa){
        this.historial.add(equipa);
    }   

    /**
     * Altera os atributos de um jogador
     * @param atributos Novos atributos
     */
    public void setAtributos(Atributos atributos) {
        this.atributos = atributos.clone();
    }

    /**
     * Altera a posição de um jogador, alterando por sua vez os seus atributos
     * @param posicao Nova posição
     */
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

    /**
     * Método de cópia de um jogador
     */
    public Jogador clone() {
        return new Jogador(this);
    }

    /**
     * Provoca desgaste a um jogador
     */
    public void desgastaJogador (){
        this.atributos.desgaste();
    }

    /**
     * Verifica se 2 objetos Jogador são iguais
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Jogador j = (Jogador) o;
        return this.nome.equals(j.nome) && this.numero == j.numero && this.posicao.equals(j.posicao)
                && this.historial.equals(j.historial) && this.atributos.equals(j.atributos);
    }

    /**
     * Transforma o objeto Jogador numa string
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(this.getNome()).append("\n");
        sb.append("Numero: ").append(this.getNumero()).append("\n");
        sb.append("Posição: ").append(this.getPosicao()).append("\n");
        sb.append("Historial: ").append(this.getHistorial().toString()).append("\n");
        sb.append(this.getAtributos().toString());
        return sb.toString();
    }

    /**
     * Cálculo de uma habilidade extra de um jogador (necessário para preencher os campos em falta vindos do ficheiro logs.txt)
     * @param media Média dos parâmetros gerais de um jogador
     * @return Habilidade extra
     */
    public int habilidadeExtra (int media){
        Random num = new Random();
        return media + 5 - num.nextInt(10);
    }

    /**
     * Construtor de um jogador proveniente de uma linha de um ficheiro. 
     * Efetua os devidos parses.
     * @param linha Linha de um ficheiro
     * @throws PosicaoInvalidaException Exceção caso se detete uma posição inválida
     */
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

    /**
     * Dado um array de strings oriundos de um split de uma linha do ficheiro, testa correspondem a parâmetros válidos de um jogador
     * @param parametros Array de strings
     * @return true se corresponde a um jogador, false caso contrário
     */
    public boolean jogadorValido (String[] parametros){
        String [] aux = parametros[0].split(":");
        if (aux[0].equals("Guarda-Redes") || aux[0].equals("Lateral") || aux[0].equals("Medio"))
            return parametros.length == 10 && aux.length == 2;
        else if (aux[0].equals("Defesa") || aux[0].equals("Avancado"))
            return parametros.length == 9 && aux.length == 2;
        else
            return false;
    }

    /**
     * Transforma o objeto jogador numa linha de um ficheiro
     * @return String de um ficheiro
     */
    public String toFicheiro() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getPosicao()).append(":");
        sb.append(this.getNome()).append(",");
        sb.append(this.getNumero()).append(",");
        sb.append(this.getAtributos().toFicheiro());
        return sb.toString();
    }

    /**
     * Método que compara os jogadores. O critério é a camisola do jogador.
     * Se o resultado for > 0, o jogador atual vem em primeiro lugar;
     * Se o resultado for < 0, o jogador j vem em primeiro lugar
     * Caso contrário, os 2 jogadores são iguais
     * @param j Objeto jogador original
     * @return Valor > 0, < 0 ou = 0
     */
    public int compareTo(Jogador j){
        int c = this.posicaoNivel() - j.posicaoNivel();
        return (c != 0) ? c : this.getNumero()-j.getNumero();
    }

    /**
     * Devolve um número identificativo para cada posição
     * @return 1 a 5 caso seja uma posição válida, 0 caso contrário
     */
    private int posicaoNivel(){
        int pn = 0;
        if(this.getPosicao().equals("Guarda-Redes"))
            pn = 1;
        else if(this.getPosicao().equals("Defesa"))
            pn = 2;
        else if(this.getPosicao().equals("Lateral"))
            pn = 3;
        else if(this.getPosicao().equals("Medio"))
            pn = 4;
        else if(this.getPosicao().equals("Avancado"))
            pn = 5;
        return pn;
    }

    /**
     * Método que gere jogadores aleatórios.
     */
    public void random(){
        Random r = new Random();

        int n1 = r.nextInt(50);
        int n2 = r.nextInt(50);
        this.nome = nomesProprios[n1]+" "+nomesApelidos[n2];
        String [] posicoes = {"Guarda-Redes","Defesa","Lateral","Medio","Avancado"};
        int pos = r.nextInt(5);
        this.posicao = posicoes[pos];
        int [] atb = new int[9];
        for(int i = 0; i < 9;i++){
            atb[i] = r.nextInt(101);
        }
        while(this.numero == 0){
            this.numero =  r.nextInt(100);
        }
        if(this.posicaoNivel() == 1){
            this.atributos = new AtributosGR(atb[0],atb[1],atb[2],atb[3],
                    atb[4],atb[5],atb[6],atb[7],atb[8]);
        }
        else if(this.posicaoNivel() == 2){
            this.atributos = new AtributosDefesa(atb[0],atb[1],atb[2],atb[3],
                    atb[4],atb[5],atb[6],atb[7],atb[8]);
        }
        else if(this.posicaoNivel() == 3){
            this.atributos = new AtributosLateral(atb[0],atb[1],atb[2],atb[3],
                    atb[4],atb[5],atb[6],atb[7],atb[8]);
        }
        else if(this.posicaoNivel() == 4){
            this.atributos = new AtributosMedio(atb[0],atb[1],atb[2],atb[3],
                    atb[4],atb[5],atb[6],atb[7],atb[8]);
        }
        else if(this.posicaoNivel() == 5){
            this.atributos = new AtributosAvancado(atb[0],atb[1],atb[2],atb[3],
                    atb[4],atb[5],atb[6],atb[7],atb[8]);
        }

    }

}
