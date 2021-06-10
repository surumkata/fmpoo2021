package Desporto.Futebol;

import Desporto.Futebol.Equipa.EquipaFutebol;
import Desporto.Futebol.Equipa.Jogador.*;
import Desporto.Futebol.Equipa.Plantel;
import Desporto.Futebol.Equipa.Tatica;
import Desporto.Futebol.Partida.PartidaFutebol;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe de controlo para a execução de partidas de futebol, que incluem map de equipas de futebol que são identificadas pelo seu nome e
 * um map de partidas de futebol onde cada é possivel associar o nome de uma equipa à lista de todos os seus jogos efetuados
 */
public class ControloDados implements Serializable{
    private Map<String, EquipaFutebol> equipas;
    private Map<String, List<PartidaFutebol>> partidas;

    /**
     * Construtor vazio para a execução de partidas, onde existem 2 equipas criadas por defeito
     */
    public ControloDados() {
        this.equipas = new HashMap<>();
        this.partidas = new HashMap<>();

        EquipaFutebol eA = new EquipaFutebol("TeamA");
        EquipaFutebol eB = new EquipaFutebol("TeamB");

        Tatica t422 = new Tatica(1,2,2,4,2);

        this.fillEquipa(eA);
        this.fillEquipa(eB);
        eA.setTatica(t422); eB.setTatica(t422);
        while(equipasProntas()<2){
            removeEquipa(eA.getNome());
            removeEquipa(eB.getNome());
            this.fillEquipa(eA);
            this.fillEquipa(eB);
            eA.setTatica(t422); eB.setTatica(t422);
        }
    }

    /**
     * Verifica se é possível simular um jogo (tem de existir pelo menos 2 equipas prontas a jogar)
     * @return true se for possível, false caso contrário
     */
    public boolean simulacaoPossivel (){
        return equipasProntas() >= 2;
    }

    /**
     * Devolve o número de equipas prontas a jogar 
     * @return Número de equipas
     */
    public int equipasProntas(){
        int x = 0;
        for(EquipaFutebol ef : equipas.values())
            if(ef.equipaPronta()) 
                x++;
        return x;
    }

    /**
     * Verifica se existem equipas no map de equipas
     * @return true se existem, false caso contrário
     */
    public boolean existeEquipas (){
        return (this.equipas.size()>0);
    }

    /**
     * Verifica se existe uma dada equipa no map de equipas
     * @param equipa Nome da equipa a verificar
     * @return true se existe, false caso contrário
     */
    public boolean existeEquipa(String equipa){
        return this.equipas.containsKey(equipa);
    }

    /**
     * Devolve a equipa de futebol com um dado nome
     * @param equipa Nome da equipa de futebol a obter
     * @return Equipa de futebol pretendida
     */
    public EquipaFutebol getEquipaFutebol (String equipa){
        if(!existeEquipa(equipa)){
            System.out.println("Não está criada o "+equipa);
            criarEquipa(equipa);
        }
        return this.equipas.get(equipa).clone();
    }

    /**
     * Adiciona uma equipa de futebol ao map de equipas
     * @param e Equipa de futebol
     */
    public void criarEquipa(EquipaFutebol e){
        this.equipas.put(e.getNome(),e);
    }

    /**
     * Cria uma equipa de futebol com um dado nome
     * @param nome Nome da equipa a criar
     */
    public void criarEquipa(String nome){
        this.equipas.put(nome,new EquipaFutebol(nome));
    }

    /**
     * Remove uma equipa de futebol
     * @param nome Nome da equipa a remover
     */
    public void removeEquipa (String nome){
        if(existeEquipa(nome)){
            this.equipas.remove(nome);
        }
    }

    /**
     * Coloca um jogador numa dada equipa, criando uma nova equipa caso não exista
     * @param j Jogador a mover
     * @param equipa Nome da equipa para onde se quer tranferir o jogador
     */
    public void colocaJogador (Jogador j, String equipa) {
        if(!existeEquipa(equipa)){
            criarEquipa(equipa);
        }
        j.addEquipaHistorial(equipa);
        this.equipas.get(equipa).adicionaPlantel(j);
    }

    /**
     * Devolve um array de strings com o nome de todas as equipas prontas a jogar
     * @return Array de strings
     */
    public String[] nomesEquipas (){
        return this.equipas.keySet().toArray(new String[0]);
    }

    /**
     * Lista com todas as equipas prontas a jogar
     * @return Lista com as equipas
     */
    public List<String> nomesEquipasProntas (){
        return this.equipas.values().stream()
                .filter(EquipaFutebol::equipaPronta)
                .map(EquipaFutebol::getNome)
                .collect(Collectors.toList());
    }

    /**
     * Adiciona uma partida de futebol ao map de partidas de futebol
     * @param p Partida a adicionar
     */
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

    /**
     * Transforma uma lista de todos os jogos de uma equipa num array de strings
     * @param equipa Nome da equipa
     * @return Array de strings
     */
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

    /**
     * Devolve uma lista de todos os jogos de uma equipa
     * @param equipa Nome da equipa
     * @return Lista de partidas de uma equipa
     */
    public List<PartidaFutebol> historico(String equipa){
        List<PartidaFutebol> l = new ArrayList<>();
        if(this.partidas.containsKey(equipa)){
            l = this.partidas.get(equipa);
        }
        return l;
    }

    /**
     * Devolve uma lista de todas as partidas de futebol
     * @return Lista de partidas
     */
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

    /**
     * Escreve uma partida de futebol num ficheiro de texto
     * @param ficheiro Nome do ficheiro
     * @throws IOException Exceção que é atirada caso não encontre o ficheiro
     */
    public void escreverFicheiro(String ficheiro) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("inputFiles/"+ficheiro));
        for(EquipaFutebol e : this.equipas.values()){
            writer.append("Equipa:");
            writer.append(e.toFicheiro());
        }
        List<PartidaFutebol> l = todasPartidas();
        for(PartidaFutebol p : l){
            writer.append("Jogo:");
            writer.append(p.toFicheiro());
        }
        writer.close();
    }

    /**
     * Lê um ficheiro de texto e faz os respetivos parses nas estruturas de dados
     * @param ficheiro Nome do ficheiro de texto
     * @throws IOException Exceção que é atirada caso não encontre o ficheiro
     * @throws PosicaoInvalidaException Exceção que é atirada caso encontre uma posição inválida
     */
    public void lerFicheiro(String ficheiro) throws IOException, PosicaoInvalidaException {
        BufferedReader reader = new BufferedReader(new FileReader("inputFiles/"+ficheiro));
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
                    primeiraEquipa = true;
                }
                boolean gravar = true;
                String[] parametros = l.split(",",-1);
                String equipaA = parametros[0].split(":")[1];
                String equipaB = parametros[1];
                int golosVisitado = Integer.parseInt(parametros[2]);
                int golosVisitante = Integer.parseInt(parametros[3]);
                LocalDate data = stringToLocalDateTime(parametros[4]);
                EquipaFutebol eA = getEquipaFutebol(equipaA);
                EquipaFutebol eB = getEquipaFutebol(equipaB);
                Plantel pA = eA.getPlantel(); pA.limpaTitulares();
                Plantel pB = eB.getPlantel(); pB.limpaTitulares();

                for(int x = 5; x <= 15 && gravar; x++){
                    int numero = Integer.parseInt(parametros[x]);
                    if(eA.getJogador(numero)!=null) {
                        pA.removeSuplente(numero);
                        pA.adicionaTitular(eA.getJogador(numero));
                    }
                    else gravar = false;
                }
                for(int x = 19; x <= 29 && gravar; x++){
                    int numero = Integer.parseInt(parametros[x]);
                    if(eB.getJogador(numero)!=null) {
                        pB.removeSuplente(numero);
                        pB.adicionaTitular(eB.getJogador(numero));
                    }
                    else gravar = false;
                }
                eA.setPlantel(pA);
                eB.setPlantel(pB);
                PartidaFutebol partida = new PartidaFutebol(eA,eB,data,golosVisitado,golosVisitante);
                int [][] subsC = new int[][] {new int[]{0,0,0},new int[]{0,0,0}};
                int [][] subsF = new int[][] {new int[]{0,0,0},new int[]{0,0,0}};
                for(int x = 16; x <= 18 && gravar; x++){
                    if(parametros[x].contains("->")){
                        int n1 = Integer.parseInt(parametros[x].split("->")[0]);
                        int n2 = Integer.parseInt(parametros[x].split("->")[1]);
                        boolean jaSub = false;
                        for(int i = 0; i < (x-16); i++){
                            if (n1 == subsC[0][i] || n2 == subsC[1][i]) {
                                jaSub = true;
                                break;
                            }
                        }
                        if(!jaSub && pA.getTitular(n1) != null && pA.getSuplente(n2) != null) {
                            partida.decSubstituicoes(true, n1, n2);
                            subsC[0][x - 16] = n1;
                            subsC[1][x - 16] = n2;
                        }
                        else
                            gravar = false;
                    }
                }
                for(int x = 30; x <= 32 && gravar; x++){
                    if(parametros[x].contains("->")){
                        int n1 = Integer.parseInt(parametros[x].split("->")[0]);
                        int n2 = Integer.parseInt(parametros[x].split("->")[1]);
                        boolean jaSub = false;
                        for(int i = 0; i < (x-30); i++){
                            if (n1 == subsF[0][i] || n2 == subsF[1][i]) {
                                jaSub = true;
                                break;
                            }
                        }
                        if(!jaSub && pB.getTitular(n1) != null && pB.getSuplente(n2) != null) {
                            partida.decSubstituicoes(false, n1, n2);
                            subsF[0][x - 30] = n1;
                            subsF[1][x - 30] = n2;
                        }
                        else
                            gravar = false;
                    }
                }
                if(gravar) adicionaPartida(partida);


            }
            else{
                Jogador j = new Jogador(l);
                if(j.getNumero() != 0 && !j.getNome().equals("") && !j.getPosicao().equals("")) {
                    j.addEquipaHistorial(e.getNome());
                    e.adicionaPlantel(j);
                }
            }
        }
        if (!primeiraEquipa)
            criarEquipa(e);
        reader.close();
    }

    /**
     * Tranforma uma string com uma data para um LocalDate
     * @param s Linha com a string data
     * @return LocalDate
     */
    public LocalDate stringToLocalDateTime(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(s, formatter);
    }

    /**
     * Lê o ficheiro objeto com um jogo de futebol
     * @param filename Nome do ficheiro
     * @return Execução de um jogo
     * @throws IOException Exceção que é atirada caso não encontre o ficheiro
     * @throws ClassNotFoundException Exceção que é atirada caso não encontre a classe pretendida
     */
    public ControloDados lerFicheiroObjeto(String filename) throws IOException, ClassNotFoundException {
        File f = new File("inputFiles/"+filename);
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ControloDados cd;
        if((cd = (ControloDados) ois.readObject())!=null){
            ois.close();
            return cd;
        }
        ois.close();
        return null;
    }

    /**
     * Grava num ficheiro objeto uma partida de futebol
     * @param filename Nome do ficheiro objeto
     * @throws IOException Exceção que é atirada caso não encontre o ficheiro
     */
    public void gravarFicheiroObjeto (String filename) throws IOException {
        File f = new File("inputFiles/"+filename);
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public void fillEquipa(EquipaFutebol e){
//
        while(e.getPlantel().getnJogadoresNoPlantel() < 22){
            Jogador j = new Jogador();
            j.random();
            e.adicionaPlantel(j);
        }

        removeEquipa(e.getNome());
        criarEquipa(e);
    }
}