package Desporto.Futebol;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.*;
import Desporto.Futebol.Equipa.Plantel;
import Desporto.Futebol.Equipa.Tatica;
import Desporto.Futebol.Partida.PartidaFutebol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControloDados {
    private Map<String, EquipaFutebol> equipas;
    private Map<String, List<PartidaFutebol>> partidas;

    public ControloDados() {
        this.equipas = new HashMap<>();
        this.partidas = new HashMap<>();

        EquipaFutebol eA = new EquipaFutebol("Braga");
        EquipaFutebol eB = new EquipaFutebol("Benfica");

        Tatica t = new Tatica(1,3,2,3,2);

        eA.setTatica(t);

        List<String> historialA = new ArrayList<>();
        historialA.add("Tondela");
        historialA.add("Benfica");
        historialA.add("Braga");
        List<String> historialB = new ArrayList<>();
        historialB.add("Benfica");

        Atributos atrGRa = new AtributosGR(99,99,99,99,99,99,99,99,99);
        Atributos atrDFa = new AtributosDefesa(99,99,99,99,99,99,99,99,99);
        Atributos atrLTa = new AtributosLateral(99,99,99,99,99,99,99,99,99);
        Atributos atrMDa = new AtributosMedio(99,99,99,99,99,99,99,99,99);
        Atributos atrAVa = new AtributosAvancado(99,99,99,99,99,99,99,99,99);

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

        Jogador j23 = new Jogador(12,"Tiago Sá","Guarda-Redes",historialA,atrGRa);eA.adicionaPlantel(j23);
        Jogador j24 = new Jogador(13,"Rolando","Defesa",historialA,atrDFa);eA.adicionaPlantel(j24);
        Jogador j25 = new Jogador(14,"João Novais","Medio",historialA,atrLTa);eA.adicionaPlantel(j25);
        Jogador j26 = new Jogador(15,"André Horta","Medio",historialA,atrMDa);eA.adicionaPlantel(j26);
        Jogador j27 = new Jogador(16,"Galeno","Avancado",historialA,atrAVa);eA.adicionaPlantel(j27);

        this.equipas.put("Braga",eA);
        this.equipas.put("Benfica",eB);

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
            System.out.println("Não está criada o "+equipa);
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
        j.addEquipaHistorial(equipa);
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

    public void adicionaPartida (PartidaFutebol p){
        String equipa1 = p.getNomeEquipa(true);
        String equipa2 = p.getNomeEquipa(false);
        List<PartidaFutebol> l1 = new ArrayList<>();
        List<PartidaFutebol> l2 = new ArrayList<>();
        if(this.partidas.containsKey(equipa1)){
            l1 = this.partidas.get(equipa1);
            this.partidas.remove(equipa1);
        }
        l1.add(p);
        this.partidas.put(equipa1,l1);
        if(this.partidas.containsKey(equipa2)){
            l2 = this.partidas.get(equipa2);
            this.partidas.remove(equipa2);
        }
        l2.add(p);
        this.partidas.put(equipa2,l2);
    }

    public String historicoString2 (String equipa){
        List<PartidaFutebol> l = historico(equipa);
        StringBuilder sb = new StringBuilder();
        sb.append("  **Partidas**\n");
        for(PartidaFutebol p : l){
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }

    public String[] historicoString (String equipa){
        List<PartidaFutebol> l = historico(equipa);
        String[] s = new String[l.size()+1];
        s[0] = "Partidas";
        int x = 1;
        for(PartidaFutebol p : l){
            s[x] = p.resultado();
            x++;
        }
        return s;
    }

    public List<PartidaFutebol> historico(String equipa){
        List<PartidaFutebol> l = new ArrayList<>();
        if(this.partidas.containsKey(equipa)){
            l = this.partidas.get(equipa);
        }
        return l;
    }

    public List<PartidaFutebol> todasPartidas(){
        List<PartidaFutebol> l = new ArrayList<>();
        for(List<PartidaFutebol> laux : this.partidas.values()){
            for(PartidaFutebol p : laux){
                if(!l.contains(p)){
                    l.add(p);
                }
            }
        }
        return l;
    }

    public void lerFicheiro(String ficheiro) throws IOException, PosicaoInvalidaException {
        BufferedReader reader = new BufferedReader(new FileReader("inputFiles\\"+ficheiro));
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
                if(!primeiraEquipa){
                    criarEquipa(e);
                }
                else primeiraEquipa = true;
                System.out.println(l);
                // Jogo:<EquipaCasa>,<EquipaFora>,<ScoreCasa>,<ScoreFora>,<Data>,<JogadoresCasa>,<SubstituicoesCasa>,<JogadoresFora>,<SubstituicoesFora>
                // Jogo:Sporting Club Shostakovich,Mendelssohn F. C.,0,0,2021-03-30,43,30,1,22,33,11,38,31,39,6,12,22->37,43->25,25->3,2,1,40,16,25,49,41,17,14,33,36,1->42,49->31,14->45
                String[] parametros = l.split(",");
                String equipaA = parametros[0].split(":")[1];
                String equipaB = parametros[1];
                int golosVisitado = Integer.parseInt(parametros[2]);
                int golosVisitante = Integer.parseInt(parametros[3]);
                LocalDate data = stringToLocalDateTime(parametros[4]);
                EquipaFutebol eA = getEquipaFutebol(equipaA);
                EquipaFutebol eB = getEquipaFutebol(equipaB);
                Plantel pA = eA.getPlantel(); pA.limpaTitulares();
                Plantel pB = eB.getPlantel(); pB.limpaTitulares();
                System.out.println(eA.toString());
                System.out.println(eB.toString());

                for(int x = 5; x <= 15; x++){
                    int numero = Integer.parseInt(parametros[x]);
                    pA.removeSuplente(numero);
                    pA.adicionaTitular(eA.getJogador(numero));
                }
                for(int x = 19; x <= 29; x++){
                    int numero = Integer.parseInt(parametros[x]);
                    pB.removeSuplente(numero);
                    pB.adicionaTitular(eB.getJogador(numero));
                }
                eA.setPlantel(pA);
                eB.setPlantel(pB);
                PartidaFutebol partida = new PartidaFutebol(eA,eB,data,golosVisitado,golosVisitante);
                for(int x = 16; x <= 18; x++){
                    if(!parametros[x].equals("")){
                        int n1 = Integer.parseInt(parametros[x].split("->")[0]);
                        int n2 = Integer.parseInt(parametros[x].split("->")[1]);
                        partida.decSubstituicoes(true,n1,n2);
                    }
                }
                for(int x = 30; x <= 32; x++){
                    if(!parametros[x].equals("")){
                        int n1 = Integer.parseInt(parametros[x].split("->")[0]);
                        int n2 = Integer.parseInt(parametros[x].split("->")[1]);
                        partida.decSubstituicoes(false,n1,n2);
                    }
                }
                adicionaPartida(partida);


            }
            else{
                System.out.println(l);
                Jogador j = new Jogador(l);
                j.addEquipaHistorial(e.getNome());
                e.adicionaPlantel(j);
            }
        }
        if (!primeiraEquipa)
            criarEquipa(e);
        reader.close();
    }

    public LocalDate stringToLocalDateTime(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(s, formatter);
    }


}
