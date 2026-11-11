package com.redsegura.graph;

import com.redsegura.model.Edge;
import com.redsegura.model.Orientation;
import com.redsegura.model.Vertex;

import java.util.*;

public class MixedGraph {
    private final Map<String, Vertex> vertices = new LinkedHashMap<>();
    private final List<Edge> edges = new ArrayList<>();

    public void addVertex(Vertex vertex) {
        if (vertices.containsKey(vertex.getId())) {
            throw new IllegalArgumentException("Vértice duplicado: " + vertex.getId());
        }
        vertices.put(vertex.getId(), vertex);
    }

    public void addEdge(String id, String sourceId, String destId, int weight, String label, Orientation orientation) {
        Vertex source = vertices.get(sourceId);
        Vertex dest = vertices.get(destId);
        if (source == null || dest == null) {
            throw new IllegalArgumentException("Vértice de origen o destino no existe.");
        }
        Edge edge = new Edge(id, source, dest, weight, label, orientation);
        edges.add(edge);
    }

    public Vertex getVertex(String id) {
        return vertices.get(id);
    }

    public Collection<Vertex> getVertices() {
        return vertices.values();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void printAdjacencyList() {
        System.out.println("=== LISTA DE ADYACENCIA ===");
        for (Vertex v : vertices.values()) {
            System.out.print(v.getId() + " -> ");
            for (Edge e : edges) {
                if (e.isActive() && e.getSource().equals(v)) {
                    System.out.print(e.getDestination().getId() + "(" + e.getWeight() + "m) ");
                } else if (e.isActive() && e.getOrientation() == Orientation.UNDIRECTED && e.getDestination().equals(v)) {
                    System.out.print(e.getSource().getId() + "(" + e.getWeight() + "m) ");
                }
            }
            System.out.println();
        }
    }

    public void printAdjacencyMatrix() {
        System.out.println("\n=== MATRIZ DE ADYACENCIA PONDERADA (Minutos) ===");
        List<Vertex> vList = new ArrayList<>(vertices.values());
        int n = vList.size();
        
        System.out.print("     ");
        for (Vertex v : vList) System.out.printf("%-5s", v.getId());
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.printf("%-5s", vList.get(i).getId());
            for (int j = 0; j < n; j++) {
                Vertex u = vList.get(i);
                Vertex w = vList.get(j);
                int weight = getDirectWeight(u, w);
                if (weight == -1) {
                    System.out.printf("%-5s", "INF");
                } else {
                    System.out.printf("%-5d", weight);
                }
            }
            System.out.println();
        }
    }

    private int getDirectWeight(Vertex u, Vertex v) {
        if (u.equals(v)) return 0;
        for (Edge e : edges) {
            if (!e.isActive()) continue;
            if (e.getSource().equals(u) && e.getDestination().equals(v)) return e.getWeight();
            if (e.getOrientation() == Orientation.UNDIRECTED && e.getSource().equals(v) && e.getDestination().equals(u)) return e.getWeight();
        }
        return -1;
    }
}
