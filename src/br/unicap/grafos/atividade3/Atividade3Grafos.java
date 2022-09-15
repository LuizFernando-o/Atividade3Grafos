package br.unicap.grafos.atividade3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Atividade3Grafos {

    static LinkedList<LinkedList<Integer>> grafo = new LinkedList<>();
    static Queue<LinkedList<Integer>> fila = new LinkedList<>();
    static String cor[];
    static String linha;
    static int ordem, tamanho;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //indicar caminho do txt para realizar leitura e criar o grafo
        leitorTxt("C:\\Users\\luizn\\Documents\\NetBeansProjects\\Atividade3Grafos\\src\\br\\unicap\\grafos\\atividade3\\grafo.txt");

        //escolher vértice inicial
        int inicial = 0;

        //executar busca em largura
        buscaEmLargura(inicial);

        //escolher inicio e destino
        int inicio = 5;
        int destino = 6;

        System.out.print("Menor caminho entre " + inicio + " e " + destino + " => ");
        //executar a busca do menor caminho
        encontraMenorCaminho(inicio, destino);

    }

    public static void leitorTxt(String caminho) throws FileNotFoundException, IOException {
        BufferedReader buffRead
                = new BufferedReader(new FileReader(caminho));

        linha = buffRead.readLine();
        ordem = Integer.parseInt(linha);   //capturar ordem (nº de vértices)

        linha = buffRead.readLine();
        tamanho = Integer.parseInt(linha); //capturar tamanho (nº de arestas)

        for (int i = 0; i < ordem; ++i) {  //criando lista de adjacência
            grafo.add(new LinkedList<>());
        }

        //lendo txt e adicionando arestas
        linha = buffRead.readLine();
        while (linha != null) {
            String vu[] = linha.split(" ");
            int v = Integer.parseInt(vu[0]);
            int u = Integer.parseInt(vu[1]);
            grafo.get(v).add(u);
            grafo.get(u).add(v);
            linha = buffRead.readLine();
        }
        buffRead.close();
    }

    public static void buscaEmLargura(int inicial) {

        cor = new String[tamanho];

        for (int i = 0; i < cor.length; i++) {
            cor[i] = "BRANCO";
        }

        cor[inicial] = "CINZA";
        LinkedList atual = grafo.get(inicial);

        System.out.println("Vértice Atual: " + inicial);
        fila.add(atual);

        exibirPassoCores();

        while (!fila.isEmpty()) {
            for (int i = 0; i < grafo.size(); i++) {
                if (grafo.get(i) == fila.element()) {
                    System.out.println("Vértice Atual: " + i);
                }
            }

            for (int i = 0; i < fila.element().size(); i++) {

                if ("BRANCO".equals(cor[fila.element().get(i)])) {
                    cor[fila.element().get(i)] = "CINZA"; //foi visitado
                    System.out.println("Visitando: " + fila.element().get(i));
                    fila.add(grafo.get(fila.element().get(i)));
                } else {
                    System.out.println("Pula Visita: " + fila.element().get(i));
                }
                exibirPassoCores();
            }

            for (int i = 0; i < grafo.size(); i++) {
                if (grafo.get(i) == fila.element()) {
                    System.out.println("Vértice Preto: " + i);
                    cor[i] = "PRETO";
                }
            }
            exibirPassoCores();
            fila.remove();
        }
    }

    //exibir o passo a passo das cores ficando branca, cinza e preta
    //por fins de execução, comentar as chamadas no main para não exibir
    static void exibirPassoCores() {
        System.out.println("************");
        for (int j = 0; j < cor.length; j++) {
            System.out.println(j + " = " + cor[j]);
        }
        System.out.println("************");
    }

    static void encontraMenorCaminho(int inicio, int destino) {

        // Criando Queue que guarda os caminhos
        Queue<LinkedList<Integer>> queue = new LinkedList<>();

        // Vetor caminho para guardar o caminho atual
        LinkedList<Integer> caminho = new LinkedList<>();

        caminho.add(inicio);
        queue.add(caminho);

        while (!queue.isEmpty()) {
            caminho = queue.poll();
            int ultimo = caminho.get(caminho.size() - 1);
            if (ultimo == destino) {
                exibirCaminho(caminho);
                break; //retirar o 'break' caso queira exibir todos os caminhos possíveis
            }
            LinkedList<Integer> verticePai = grafo.get(ultimo);

            for (int i = 0; i < verticePai.size(); i++) {
                if (naoVisitado(verticePai.get(i), caminho)) {
                    LinkedList<Integer> novoCaminho = new LinkedList<>(caminho);
                    novoCaminho.add(verticePai.get(i));
                    queue.add(novoCaminho);
                }
            }
        }
    }

    //metodos auxiliares encontraMenorCaminho()
    private static boolean naoVisitado(int x, LinkedList<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i) == x) {
                return false;
            }
        }
        return true;
    }

    static void exibirCaminho(LinkedList<Integer> caminho) {
        for (Integer v : caminho) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
