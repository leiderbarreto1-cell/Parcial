package com.redsegura.app;

import com.redsegura.algorithms.DijkstraSolver;
import com.redsegura.graph.MixedGraph;
import com.redsegura.model.Edge;
import com.redsegura.model.Orientation;
import com.redsegura.model.Vertex;
import com.redsegura.trees.AvlTree;

import java.util.Scanner;

public class RedSeguraApplication {

    public static void main(String[] args) {
        MixedGraph graph = new MixedGraph();
        AvlTree avlTree = new AvlTree();

        Vertex[] verticesData = {
            new Vertex("S01", "UAJS Sede C", "Universidad"),
            new Vertex("S02", "UAJS Sede E", "Universidad"),
            new Vertex("S03", "Universidad de Sucre", "Universidad"),
            new Vertex("S04", "CECAR", "Universidad"),
            new Vertex("S05", "Hospital Universitario", "Salud"),
            new Vertex("S06", "Clínica Las Américas", "Salud"),
            new Vertex("S07", "Estación Central SETP", "Transporte"),
            new Vertex("S08", "Terminal de Transporte", "Transporte"),
            new Vertex("S09", "Parque Santander", "Punto de encuentro"),
            new Vertex("S10", "Mercado de Sincelejo", "Comercio"),
            new Vertex("S11", "PEP Argelia", "Paradero"),
            new Vertex("S12", "Glorieta Sincelejito", "Movilidad")
        };

        for (Vertex v : verticesData) {
            graph.addVertex(v);
            avlTree.insert(v);
        }

        graph.addEdge("R01", "S01", "S07", 4, "Corredor universitario", Orientation.DIRECTED);
        graph.addEdge("R02", "S07", "S01", 6, "Retorno por vía alterna", Orientation.DIRECTED);
        graph.addEdge("R03", "S01", "S09", 5, "Corredor peatonal", Orientation.UNDIRECTED);
        graph.addEdge("R04", "S02", "S01", 8, "Conexión entre sedes", Orientation.UNDIRECTED);
        graph.addEdge("R05", "S02", "S06", 10, "Ruta de atención", Orientation.DIRECTED);
        graph.addEdge("R06", "S06", "S05", 4, "Corredor clínico", Orientation.DIRECTED);
        graph.addEdge("R07", "S05", "S09", 6, "Vía hospitalaria", Orientation.UNDIRECTED);
        graph.addEdge("R08", "S09", "S10", 3, "Centro histórico", Orientation.UNDIRECTED);
        graph.addEdge("R09", "S10", "S08", 8, "Salida al terminal", Orientation.DIRECTED);
        graph.addEdge("R10", "S08", "S10", 9, "Ingreso desde terminal", Orientation.DIRECTED);
        graph.addEdge("R11", "S03", "S12", 4, "Corredor Puerta Roja", Orientation.UNDIRECTED);
        graph.addEdge("R12", "S12", "S09", 7, "Troncal al centro", Orientation.UNDIRECTED);
        graph.addEdge("R13", "S04", "S11", 6, "Conexión universitaria", Orientation.UNDIRECTED);
        graph.addEdge("R14", "S11", "S07", 8, "Ruta SETP al centro", Orientation.DIRECTED);
        graph.addEdge("R15", "S07", "S09", 3, "Transferencia central", Orientation.UNDIRECTED);
        graph.addEdge("R16", "S07", "S08", 12, "Ruta SETP al terminal", Orientation.DIRECTED);
        graph.addEdge("R17", "S09", "S03", 7, "Ruta hacia universidad", Orientation.DIRECTED);
        graph.addEdge("R18", "S12", "S04", 9, "Ruta hacia CECAR", Orientation.DIRECTED);

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        System.out.println("=========================================");
        System.out.println("    REDSEGURA SINCELEJO - PARCIAL 2      ");
        System.out.println("=========================================");

        do {
            System.out.println("\nSeleccione una consulta obligatoria (Q1-Q7) o 0 para salir:");
            System.out.println("1. Q1 - Buscar 'Hospital Universitario' en AVL y ver pasos");
            System.out.println("2. Q3 - Imprimir Lista y Matriz de Adyacencia");
            System.out.println("3. Q5 - Calcular Dijkstra de S02 a S08 (Resultado esperado: 24 min)");
            System.out.println("4. Q6 - Bloquear R16 y recalcular Dijkstra");
            System.out.println("5. Q7 - Consultar vértice inexistente sin romper programa");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        Vertex found = avlTree.search("S05");
                        System.out.println("Resultado: " + found);
                        System.out.println("Pasos realizados en el AVL: " + avlTree.getStepsCounter());
                        break;
                    case 2:
                        graph.printAdjacencyList();
                        graph.printAdjacencyMatrix();
                        break;
                    case 3:
                        DijkstraSolver.Result res = DijkstraSolver.findShortestPath(graph, "S02", "S08");
                        System.out.println("=== DIJKSTRA S02 -> S08 ===");
                        System.out.println("Costo total: " + res.totalCost + " minutos.");
                        System.out.println("Camino: " + res.path);
                        break;
                    case 4:
                        for (Edge e : graph.getEdges()) {
                            if (e.getId().equals("R16")) {
                                e.setActive(false);
                                System.out.println("Conexión R16 bloqueada con éxito.");
                            }
                        }
                        DijkstraSolver.Result resBlocked = DijkstraSolver.findShortestPath(graph, "S02", "S08");
                        System.out.println("=== DIJKSTRA TRAS BLOQUEO DE R16 ===");
                        System.out.println("Nuevo costo: " + resBlocked.totalCost + " minutos.");
                        System.out.println("Nuevo camino: " + resBlocked.path);
                        break;
                    case 5:
                        System.out.print("Ingrese ID del vértice a buscar: ");
                        String badId = scanner.next();
                        Vertex missing = avlTree.search(badId);
                        if (missing == null) {
                            System.out.println("Aviso: El vértice '" + badId + "' no existe en el sistema, pero el programa sigue funcionando con normalidad.");
                        } else {
                            System.out.println("Encontrado: " + missing);
                        }
                        break;
                    case 0:
                        System.out.println("Saliendo de RedSegura...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } else {
                scanner.next();
            }
        } while (option != 0);
        
        scanner.close();
    }
}
