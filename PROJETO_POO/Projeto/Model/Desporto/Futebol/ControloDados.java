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
import java.util.stream.Collectors;

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
        Jogador j2 = new Jogador(2,"JogadorB","Defesa",historialA,atrDF);eA.adicionaPlantel(j2);
        Jogador j3 = new Jogador(3,"JogadorC","Defesa",historialA,atrDF);eA.adicionaPlantel(j3);
        Jogador j4 = new Jogador(4,"JogadorD","Lateral",historialA,atrLT);eA.adicionaPlantel(j4);
        Jogador j5 = new Jogador(5,"JogadorE","Lateral",historialA,atrLT);eA.adicionaPlantel(j5);
        Jogador j6 = new Jogador(6,"JogadorF","Medio",historialA,atrMD);eA.adicionaPlantel(j6);
        Jogador j7 = new Jogador(7,"JogadorG","Medio",historialA,atrMD);eA.adicionaPlantel(j7);
        Jogador j8 = new Jogador(8,"JogadorH","Medio",historialA,atrMD);eA.adicionaPlantel(j8);
        Jogador j9 = new Jogador(9,"JogadorI","Medio",historialA,atrMD);eA.adicionaPlantel(j9);
        Jogador j10 = new Jogador(10,"JogadorJ","Avancado",historialA,atrAV);eA.adicionaPlantel(j10);
        Jogador j11 = new Jogador(11,"JogadorK","Avancado",historialA,atrAV);eA.adicionaPlantel(j11);
        Jogador j12 = new Jogador(1,"JogadorL","Guarda-Redes",historialB,atrGR);eB.adicionaPlantel(j12);
        Jogador j13 = new Jogador(2,"JogadorM","Defesa",historialB,atrDF);eB.adicionaPlantel(j13);
        Jogador j14 = new Jogador(3,"JogadorN","Defesa",historialB,atrDF);eB.adicionaPlantel(j14);
        Jogador j15 = new Jogador(4,"JogadorO","Lateral",historialB,atrLT);eB.adicionaPlantel(j15);
        Jogador j16 = new Jogador(5,"JogadorP","Lateral",historialB,atrLT);eB.adicionaPlantel(j16);
        Jogador j17 = new Jogador(6,"JogadorQ","Medio",historialB,atrMD);eB.adicionaPlantel(j17);
        Jogador j18 = new Jogador(7,"JogadorR","Medio",historialB,atrMD);eB.adicionaPlantel(j18);
        Jogador j19 = new Jogador(8,"JogadorS","Medio",historialB,atrMD);eB.adicionaPlantel(j19);
        Jogador j20 = new Jogador(9,"JogadorT","Medio",historialB,atrMD);eB.adicionaPlantel(j20);
        Jogador j21 = new Jogador(10,"JogadorU","Avancado",historialB,atrAV);eB.adicionaPlantel(j21);
        Jogador j22 = new Jogador(11,"JogadorV","Avancado",historialB,atrAV);eB.adicionaPlantel(j22);

        Jogador j23 = new Jogador(12,"SupelenteA","Guarda-Redes",historialA,atrGR);eA.adicionaPlantel(j23);
        Jogador j24 = new Jogador(13,"SupelenteB","Defesa",historialA,atrDF);eA.adicionaPlantel(j24);
        Jogador j25 = new Jogador(14,"SupelenteC","Lateral",historialA,atrLT);eA.adicionaPlantel(j25);
        Jogador j26 = new Jogador(15,"SupelenteD","Medio",historialA,atrMD);eA.adicionaPlantel(j26);
        Jogador j27 = new Jogador(16,"SupelenteE","Avancado",historialA,atrAV);eA.adicionaPlantel(j27);

        this.equipas.put("EquipaA",eA);
        this.equipas.put("EquipaB",eB);

    }

    public boolean simulacaoPossivel (){
        return equipasProntas() >= 2;
    }

    public int equipasProntas(){
        int x = 0;
        for(EquipaFutebol ef : equipas.values()){
            if(ef.equipaPronta()) {
                //System.out.println(ef.getNome()+" Pronta!");
                x++;
            }
        }
        return x;
    }

    public boolean existeEquipas (){
        return (this.equipas.size()>0);
    }

    public boolean existeEquipa(String equipa){
        return this.equipas.containsKey(equipa);
    }

    public EquipaFutebol getEquipaFutebol (String equipa){
        if(!existeEquipa(equipa)){
            criarEquipa(equipa);
        }
        return this.equipas.get(equipa).clone();
    }

    public void criarEquipa(EquipaFutebol e){
        this.equipas.put(e.getNome(),e);
    }

    public void criarEquipa(String nome){
        this.equipas.put(nome,new EquipaFutebol(nome));
    }

    public void removeEquipa (String nome){
        if(existeEquipa(nome)){
            this.equipas.remove(nome);
        }
    }

    public void colocaJogador (Jogador j, String equipa) {
        if(!existeEquipa(equipa)){
            criarEquipa(equipa);
        }
        this.equipas.get(equipa).adicionaPlantel(j);
    }

    public String[] nomesEquipas (){
        return this.equipas.keySet().toArray(new String[0]);
    }

}
