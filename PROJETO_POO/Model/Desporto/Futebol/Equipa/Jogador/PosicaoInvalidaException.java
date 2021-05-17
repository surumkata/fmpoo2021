package Futebol.Equipa.Jogador;

public class PosicaoInvalidaException extends Exception {
    public PosicaoInvalidaException() {
        super();
    }

    public PosicaoInvalidaException(String msg) {
        super(msg);
    }
}
