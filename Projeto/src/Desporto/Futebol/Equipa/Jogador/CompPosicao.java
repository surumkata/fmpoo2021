package Desporto.Futebol.Equipa.Jogador;


import Desporto.Futebol.Equipa.Jogador.Jogador;

import java.util.Comparator;

public class CompPosicao implements Comparator<Jogador> {

    @Override
    public int compare(Jogador j1, Jogador j2){

        return j1.compareTo(j2);
    }
}