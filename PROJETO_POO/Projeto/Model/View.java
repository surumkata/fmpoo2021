import java.util.*;

public class View {


    // Interfaces auxiliares

    /** Functional interface para handlers. */
    public interface Handler {  // método de tratamento
        public void execute();
    }

    /** Functional interface para pré-condições. */
    public interface PreCondition {  // Predicate ?
        public boolean validate();
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
    public View(){
        this.continuacao = false;
        this.opcoes = new ArrayList<>();
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
    }

    public View(String[] opcoes) {
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
     *
     * Termina com a opção 0 (zero).
     */
    public void run() {
        int op;
        do {
            show();
            op = readOption();
            // testar pré-condição
            if (op>0 && !this.disponivel.get(op-1).validate()) {
                System.out.println("Opção indisponível! Tente novamente.");
            } else if (op>0) {
                // executar handler
                this.handlers.get(op-1).execute();
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
        this.handlers.set(i-1, h);
    }

    // Métodos auxiliares

    /** Apresentar o NewMenu */
    private void show() {
        System.out.println("\n *** "+this.opcoes.get(0)+" *** ");
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
        return op;
    }

    public void welcome(){
        System.out.println("Welcome!!!\n");
    }

    public void exitmessage(){
        System.out.println("ByeBye!!!\n");
    }

    public void lerequipa(){
        System.out.println("Qual o nome da Equipa: ");
    }


    public void atributosmessagerror(){
        System.out.println("O valor inserido não está de [0-99]\n");
    }

    public void atributosmessage(String atributo){
        System.out.println("Classifique o "+atributo+" do Jogador de [0-99]: ");
    }

    public void stop(){
        this.continuacao = false;
    }

    public void comentariosJogo (int i, String j1, String j2){
        if(i == 0)
            System.out.println(j2+" tenta roubar a bola a "+j1+" mas este passa por ele.\n");
        else if(i == 1)
            System.out.println(j2+" rouba a bola ao "+j1);
        else if(i == 2)
            System.out.println(j1+" passa para "+j2);
    }

}
