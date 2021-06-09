package Desporto.Futebol.Equipa.Jogador;

/**
 * Classe de exceção que é atirada quando se deteta uma posição inválida
 */
public class PosicaoInvalidaException extends Exception {
    public PosicaoInvalidaException() {
        /**
         * Construtor da exceção vazio
         */
        super();
    }

    /**
     * Construtor da exceção onde apresenta uma mensagem de erro
     * @param msg Mensagem de erro
     */
    public PosicaoInvalidaException(String msg) {
        super(msg);
    }
}
