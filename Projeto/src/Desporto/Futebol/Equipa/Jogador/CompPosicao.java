package Desporto.Futebol.Equipa.Jogador;


import java.util.Comparator;

/**
 * Classe que implementa um comparador de jogadores, que está presente na classe jogador. 
 */
public class CompPosicao implements Comparator<Jogador> {

    @Override
    /**
     * Comparador de jogadores
     */
    public int compare(Jogador j1, Jogador j2){
        return j1.compareTo(j2);
    }
}