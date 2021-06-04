package Desporto.Futebol;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.*;
import Desporto.Futebol.Equipa.JogadorInvalidoException;
import Desporto.Futebol.Equipa.TitularesFullException;
import Desporto.Futebol.Partida.PartidaFutebol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControloDados {
    private Map<String, EquipaFutebol> equipas;
    private List<PartidaFutebol> partidas;

    public ControloDados() {
        this.equipas = new HashMap<>();
        this.partidas = new ArrayList<>();

        EquipaFutebol eA = new EquipaFutebol("EquipaA");
        EquipaFutebol eB = new EquipaFutebol("EquipaB");

        List<String> historialA = new ArrayList<>();
        historialA.add("EquipaA");
        List<String> historialB = new ArrayList<>();
        historialB.add("EquipaB");

        Atributos atrGR = new AtributosGR(50,50,50,50,50,50,50,50,50);
        Atributos atrDF = new AtributosDefesa(50,50,50,50,50,50,50,50,50);
        Atributos atrLT = new AtributosLateral(50,50,50,50,50,50,50,50,50);
        Atributos atrMD = new AtributosMedio(50,50,50,50,50,50,50,50,50);
        Atributos atrAV = new AtributosAvancado(50,50,50,50,50,50,50,50,50);

        Jogador j1 = new Jogador(1,"JogadorA","Guarda-Redes",historialA,atrGR);eA.adicionaPlantel(j1);
        Jogador j2 = new Jogador(2,"JogadorB","Guarda-Redes",historialA,atrDF);eA.adicionaPlantel(j2);
        Jogador j3 = new Jogador(3,"JogadorC","Guarda-Redes",historialA,atrDF);eA.adicionaPlantel(j3);
        Jogador j4 = new Jogador(4,"JogadorD","Guarda-Redes",historialA,atrLT);eA.adicionaPlantel(j4);
        Jogador j5 = new Jogador(5,"JogadorE","Guarda-Redes",historialA,atrLT);eA.adicionaPlantel(j5);
        Jogador j6 = new Jogador(6,"JogadorF","Guarda-Redes",historialA,atrMD);eA.adicionaPlantel(j6);
        Jogador j7 = new Jogador(7,"JogadorG","Guarda-Redes",historialA,atrMD);eA.adicionaPlantel(j7);
        Jogador j8 = new Jogador(8,"JogadorH","Guarda-Redes",historialA,atrMD);eA.adicionaPlantel(j8);
        Jogador j9 = new Jogador(9,"JogadorI","Guarda-Redes",historialA,atrMD);eA.adicionaPlantel(j9);
        Jogador j10 = new Jogador(10,"JogadorJ","Guarda-Redes",historialA,atrAV);eA.adicionaPlantel(j10);
        Jogador j11 = new Jogador(11,"JogadorK","Guarda-Redes",historialA,atrAV);eA.adicionaPlantel(j11);
        Jogador j12 = new Jogador(1,"JogadorL","Guarda-Redes",historialB,atrGR);eB.adicionaPlantel(j12);
        Jogador j13 = new Jogador(2,"JogadorM","Guarda-Redes",historialB,atrDF);eB.adicionaPlantel(j13);
        Jogador j14 = new Jogador(3,"JogadorN","Guarda-Redes",historialB,atrDF);eB.adicionaPlantel(j14);
        Jogador j15 = new Jogador(4,"JogadorO","Guarda-Redes",historialB,atrLT);eB.adicionaPlantel(j15);
        Jogador j16 = new Jogador(5,"JogadorP","Guarda-Redes",historialB,atrLT);eB.adicionaPlantel(j16);
        Jogador j17 = new Jogador(6,"JogadorQ","Guarda-Redes",historialB,atrMD);eB.adicionaPlantel(j17);
        Jogador j18 = new Jogador(7,"JogadorR","Guarda-Redes",historialB,atrMD);eB.adicionaPlantel(j18);
        Jogador j19 = new Jogador(8,"JogadorS","Guarda-Redes",historialB,atrMD);eB.adicionaPlantel(j19);
        Jogador j20 = new Jogador(9,"JogadorT","Guarda-Redes",historialB,atrMD);eB.adicionaPlantel(j20);
        Jogador j21 = new Jogador(10,"JogadorU","Guarda-Redes",historialB,atrAV);eB.adicionaPlantel(j21);
        Jogador j22 = new Jogador(11,"JogadorV","Guarda-Redes",historialB,atrAV);eB.adicionaPlantel(j22);

        this.equipas.put("EquipaA",eA);
        this.equipas.put("EquipaB",eB);

    }

    public boolean simulacaoPossivel (){
        return equipasProntas() >= 2;
    }

    public int equipasProntas(){
        int x = 0;
        for(EquipaFutebol ef : equipas.values()){
            if(ef.equipaPronta()) x++;
        }
        return x;
    }

    public boolean existeEquipas (){
        return (this.equipas.size()>0);
    }

    public boolean existeEquipa(String equipa){
        return this.equipas.containsKey(equipa);
    }

    public void criarEquipa(String nome){
        this.equipas.put(nome,new EquipaFutebol(nome));
    }

    public void colocaJogador (Jogador j, String equipa) {
        if(!existeEquipa(equipa)){
            criarEquipa(equipa);
        }
        this.equipas.get(equipa).adicionaPlantel(j);
    }
}
