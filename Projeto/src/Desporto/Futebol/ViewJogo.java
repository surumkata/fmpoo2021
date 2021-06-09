package Desporto.Futebol;

import java.util.*;

/**
 * Classe para o menu do jogo
 */
public class ViewJogo {


    // Interfaces auxiliares

    /** Functional interface para handlers. */
    public interface Handler {  // método de tratamento
        void execute();
    }

    /** Functional interface para pré-condições. */
    public interface PreCondition {  // Predicate ?
        boolean validate();
    }

    // Varíável de classe para suportar leitura

    private static Scanner is = new Scanner(System.in);

    // Variáveis de instância

    private boolean continuacao;
    private List<String> opcoes;            // Lista de opções
    private List<PreCondition> disponivel;  // Lista de pré-condições
    private List<Handler> handlers;         // Lista de handlers

    // Construtor

    /**
     * Constructor for objects of class NewMenu
     */
    public ViewJogo(){
        this.continuacao = false;
        this.opcoes = new ArrayList<>();
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
    }

    public ViewJogo(String[] opcoes) {
        this.continuacao = true;
        this.opcoes = Arrays.asList(opcoes);
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.opcoes.forEach(s-> {
            this.disponivel.add(()->true);
            this.handlers.add(()->System.out.println("\nATENÇÃO: Opção não implementada!"));
        });
    }

    // Métodos de instância

    /**
     * Correr o NewMenu.
     * Termina com a opção 0 (zero).
     */
    public void run() {
        int op;
        do {
            show();
            op = readOption();
            // testar pré-condição
            if (op>0 && !this.disponivel.get(op).validate()) {
                System.out.println("Opção indisponível! Tente novamente.");
            } else if (op>0) {
                // executar handler
                this.handlers.get(op).execute();
            }
        } while (op != 0 && continuacao);
    }

    /**
     * Método que regista uma uma pré-condição numa opção do NewMenu.
     *
     * @param i índice da opção (começa em 1)
     * @param b pré-condição a registar
     */
    public void setPreCondition(int i, PreCondition b) {
        this.disponivel.set(i,b);
    }

    public void setPreCondition(int[] is, PreCondition b) {
        for(int i : is) {
            this.disponivel.set(i, b);
        }
    }

    /**
     * Método para registar um handler numa opção do NewMenu.
     *
     * @param i indice da opção  (começa em 1)
     * @param h handlers a registar
     */
    public void setHandler(int i, Handler h) {
        this.handlers.set(i, h);
    }

    // Métodos auxiliares

    /** Apresentar o NewMenu */
    private void show() {
        System.out.println(" *** "+this.opcoes.get(0)+" *** ");
        for (int i=1; i<this.opcoes.size(); i++) {
            System.out.print(i);
            System.out.print(" - ");
            System.out.println(this.disponivel.get(i).validate()?this.opcoes.get(i):"---");
        }
        System.out.println("0 - Sair");
    }

    /** Ler uma opção válida */
    private int readOption() {
        int op;
        //Scanner is = new Scanner(System.in);

        System.out.print("Opção: ");
        try {
            String line = is.nextLine();
            op = Integer.parseInt(line);
        }
        catch (NumberFormatException e) { // Não foi inscrito um int
            op = -1;
        }
        if (op<0 || op>=this.opcoes.size()) {
            System.out.println("Opção Inválida!!!");
            op = -1;
        }
        System.out.print("\n");
        return op;
    }

    /**
     * Mensagem de boas vindas
     */
    public void welcome(){
        System.out.println("███████╗███╗░░░███╗  ██████╗░░█████╗░░█████╗░  ██████╗░░█████╗░██████╗░░░███╗░░");
        System.out.println("██╔════╝████╗░████║  ██╔══██╗██╔══██╗██╔══██╗  ╚════██╗██╔══██╗╚════██╗░████║░░");
        System.out.println("█████╗░░██╔████╔██║  ██████╔╝██║░░██║██║░░██║  ░░███╔═╝██║░░██║░░███╔═╝██╔██║░░");
        System.out.println("██╔══╝░░██║╚██╔╝██║  ██╔═══╝░██║░░██║██║░░██║  ██╔══╝░░██║░░██║██╔══╝░░╚═╝██║░░");
        System.out.println("██║░░░░░██║░╚═╝░██║  ██║░░░░░╚█████╔╝╚█████╔╝  ███████╗╚█████╔╝███████╗███████╗");
        System.out.println("\u001B[32m                          ___________________________                         ");
        System.out.println("                         |             |             |                        ");
        System.out.println("                         |___          |          ___|                        ");
        System.out.println("                         |_  |         |         |  _|                        ");
        System.out.println("                        .| | |.       ,|.       .| | |.                       ");
        System.out.println("                        || | | )     ( | )     ( | | ||                       ");
        System.out.println("                        '|_| |'       `|'       `| |_|'                       ");
        System.out.println("                         |___|         |         |___|                        ");
        System.out.println("                         |             |             |                        ");
        System.out.println("                         |_____________|_____________|               \u001B[0m");
    }

    /**
     * Limpa o ecrã
     */
    public void limpaTela (){
        System.out.print("\033[H\033[2J");
    }
    
    /**
     * Mensagem de leitura do nome da equipa
     */
    public void lerEquipa(){
        System.out.print("Qual o nome da Equipa: ");
    }


    /**
     * Mensagem de erro de atribuição de valor para os atributos
     */
    public void atributosMessageError(){
        System.out.println("O valor inserido não está de [1-100]\n");
    }

    /**
     * Mensagem de classificação de um atributo
     * @param atributo
     */
    public void atributosMessage(String atributo){
        System.out.print("Classifique o "+atributo+" do Jogador de [1-100]: ");
    }

    /**
     * Parar menu
     */
    public void stop(){
        this.continuacao = false;
    }

    /**
     * Apresenta os comentários de um jogo num menu
     * @param tempo Tempo de jogo
     * @param comentario Comentário a aparecer
     */
    public void comentariosJogo (double tempo, String comentario){
        if(tempo < 10) System.out.println("0"+tempo+"': "+comentario);
        else System.out.println(tempo+"': "+comentario);
    }
}
