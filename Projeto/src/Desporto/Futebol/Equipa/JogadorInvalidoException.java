package Desporto.Futebol.Equipa;

/**
 * Classe da exceção que é atirada quando se deteta um jogador inválido
 */
public class JogadorInvalidoException extends Exception {
    /**
     * Construtor da exceção vazio
     */
    public JogadorInvalidoException() {
        super();
    }

    /**
     * Construtor da exceção com uma mensagem de erro
     * @param msg Mensagem de erro
     */
    public JogadorInvalidoException(String msg) {
        super(msg);
    }
}
