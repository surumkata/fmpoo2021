package Desporto.Futebol;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.*;
import Desporto.Futebol.Equipa.Tatica;
import Desporto.Futebol.Partida.ExecutaPartida;
import Desporto.Futebol.Partida.PartidaFutebol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControloDados {
    private Map<String, EquipaFutebol> equipas;
    private List<PartidaFutebol> partidas;

    public void lerFicheiro() throws IOException, PosicaoInvalidaException {
        BufferedReader reader = new BufferedReader(new FileReader("../inputFiles/logs.txt"));
        EquipaFutebol e = new EquipaFutebol();
        boolean primeiraEquipa = true;
        while (reader.ready()) {
            String l = reader.readLine();
            if (l.startsWith("Equipa:")){
                if (!primeiraEquipa)
                    criarEquipa(e);     
                else
                    primeiraEquipa = false;
                String[] eq = l.split(":");
                e = new EquipaFutebol(eq[1]);
            }
            else if (l.contains("Jogo:")){
                //a fazer parse
            }
            else{
                System.out.println(l);
                Jogador j = new Jogador(l);
                e.adicionaPlantel(j);
            }
        }
        if (!primeiraEquipa)
            criarEquipa(e);
        reader.close();
    }

    public ControloDados() {
        this.equipas = new HashMap<>();
        this.partidas = new ArrayList<>();

        EquipaFutebol eA = new EquipaFutebol("Braga");
        EquipaFutebol eB = new EquipaFutebol("Benfica");

        Tatica t = new Tatica(1,3,2,3,2);

        eA.setTatica(t);

        List<String> historialA = new ArrayList<>();
        historialA.add("EquipaA");
        List<String> historialB = new ArrayList<>();
        historialB.add("EquipaB");

        Atributos atrGRa = new AtributosGR(50,50,50,50,50,50,50,50,50);
        Atributos atrDFa = new AtributosDefesa(50,50,50,50,50,50,50,50,50);
        Atributos atrLTa = new AtributosLateral(50,50,50,50,50,50,50,50,50);
        Atributos atrMDa = new AtributosMedio(50,50,50,50,50,50,50,50,50);
        Atributos atrAVa = new AtributosAvancado(50,50,50,50,50,50,50,50,50);

        Atributos atrGRb =  new AtributosGR(50,50,50,50,50,50,50,50,50);
        Atributos atrDFb =  new AtributosDefesa(50,50,50,50,50,50,50,50,50);
        Atributos atrLTb =  new AtributosLateral(50,50,50,50,50,50,50,50,50);
        Atributos atrMDb =  new AtributosMedio(50,50,50,50,50,50,50,50,50);
        Atributos atrAVb =  new AtributosAvancado(50,50,50,50,50,50,50,50,50);

        Jogador j1 = new Jogador(1,"Matheus","Guarda-Redes",historialA,atrGRa);eA.adicionaPlantel(j1);
        Jogador j2 = new Jogador(2,"David Carmo","Defesa",historialA,atrDFa);eA.adicionaPlantel(j2);
        Jogador j3 = new Jogador(3,"Raúl Silva","Defesa",historialA,atrDFa);eA.adicionaPlantel(j3);
        Jogador j4 = new Jogador(4,"Bruno Viana","Defesa",historialA,atrDFa);eA.adicionaPlantel(j4);
        Jogador j5 = new Jogador(5,"Esgaio","Lateral",historialA,atrLTa);eA.adicionaPlantel(j5);
        Jogador j6 = new Jogador(6,"Sequeira","Lateral",historialA,atrLTa);eA.adicionaPlantel(j6);
        Jogador j7 = new Jogador(7,"Castro","Medio",historialA,atrMDa);eA.adicionaPlantel(j7);
        Jogador j8 = new Jogador(8,"Musrati","Medio",historialA,atrMDa);eA.adicionaPlantel(j8);
        Jogador j9 = new Jogador(9,"Fransérgio","Medio",historialA,atrMDa);eA.adicionaPlantel(j9);
        Jogador j10 = new Jogador(10,"Abél Ruiz","Avancado",historialA,atrAVa);eA.adicionaPlantel(j10);
        Jogador j11 = new Jogador(11,"Paulinho","Avancado",historialA,atrAVa);eA.adicionaPlantel(j11);
        Jogador j12 = new Jogador(1,"Hugo Leite","Guarda-Redes",historialB,atrGRb);eB.adicionaPlantel(j12);
        Jogador j13 = new Jogador(2,"Otamendi","Defesa",historialB,atrDFb);eB.adicionaPlantel(j13);
        Jogador j14 = new Jogador(3,"Vertoghen","Defesa",historialB,atrDFb);eB.adicionaPlantel(j14);
        Jogador j15 = new Jogador(4,"Diogo Gonçalves","Lateral",historialB,atrLTb);eB.adicionaPlantel(j15);
        Jogador j16 = new Jogador(5,"Grimaldo","Lateral",historialB,atrLTb);eB.adicionaPlantel(j16);
        Jogador j17 = new Jogador(6,"Taraabt","Medio",historialB,atrMDb);eB.adicionaPlantel(j17);
        Jogador j18 = new Jogador(7,"Weigl","Medio",historialB,atrMDb);eB.adicionaPlantel(j18);
        Jogador j19 = new Jogador(8,"Everton","Medio",historialB,atrMDb);eB.adicionaPlantel(j19);
        Jogador j20 = new Jogador(9,"Pizzi","Medio",historialB,atrMDb);eB.adicionaPlantel(j20);
        Jogador j21 = new Jogador(10,"Rafa Silva","Avancado",historialB,atrAVb);eB.adicionaPlantel(j21);
        Jogador j22 = new Jogador(11,"Seferovic","Avancado",historialB,atrAVb);eB.adicionaPlantel(j22);

        Jogador j23 = new Jogador(12,"SupelenteA","Guarda-Redes",historialA,atrGRa);eA.adicionaPlantel(j23);
        Jogador j24 = new Jogador(13,"SupelenteB","Defesa",historialA,atrDFa);eA.adicionaPlantel(j24);
        Jogador j25 = new Jogador(14,"SupelenteC","Lateral",historialA,atrLTa);eA.adicionaPlantel(j25);
        Jogador j26 = new Jogador(15,"SupelenteD","Medio",historialA,atrMDa);eA.adicionaPlantel(j26);
        Jogador j27 = new Jogador(16,"SupelenteE","Avancado",historialA,atrAVa);eA.adicionaPlantel(j27);

        this.equipas.put("Braga",eA);
        this.equipas.put("Benfica",eB);

    }

    public void simulacao(String eVisitada, String eVisitante){
        ExecutaPartida exp = new ExecutaPartida(this.equipas.get(eVisitada),this.equipas.get(eVisitante));

        exp.run();
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

    public List<String> nomesEquipasProntas (){
        return this.equipas.values().stream()
                .filter(EquipaFutebol::equipaPronta)
                .map(EquipaFutebol::getNome)
                .collect(Collectors.toList());
    }


}
