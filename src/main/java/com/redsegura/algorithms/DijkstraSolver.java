package com.redsegura.algorithms;

import com.redsegura.model.Edge;
import com.redsegura.model.Orientation;
import com.redsegura.model.Vertex;
import com.redsegura.graph.MixedGraph;

import java.util.*;

public class DijkstraSolver {

    public static class Result {
        public final int totalCost;
        public final List<Vertex> path;

        public Result(int totalCost, List<Vertex> path) {
            this.totalCost = totalCost;
            this.path = path;
        }
    }

    public static Result findShortestPath(MixedGraph graph, String startId, String endId) {
        Vertex start = graph.getVertex(startId);
        Vertex end = graph.getVertex(endId);

        if (start == null || end == null) {
            throw new IllegalArgumentException("Vértice inicial o final inválido.");
        }

        Map<Vertex, Integer> distances = new HashMap<>();
        Map<Vertex, Vertex> predecessors = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingInt(nd -> nd.distance));

        for (Vertex v : graph.getVertices()) {
            distances.put(v, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
        pq.add(new NodeDistance(start, 0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            Vertex u = current.vertex;

            if (u.equals(end)) break;

            if (current.distance > distances.get(u)) continue;

            for (Edge e : graph.getEdges()) {
                if (!e.isActive()) continue;

                Vertex v = null;
                if (e.getSource().equals(u)) {
                    v = e.getDestination();
                } else if (e.getOrientation() == Orientation.UNDIRECTED && e.getDestination().equals(u)) {
                    v = e.getSource();
                }

                if (v != null) {
                    int newDist = distances.get(u) + e.getWeight();
                    if (newDist < distances.get(v)) {
                        distances.put(v, newDist);
                        predecessors.put(v, u);
                        pq.add(new NodeDistance(v, newDist));
                    }
                }
            }
        }

        List<Vertex> path = new ArrayList<>();
        Vertex curr = end;
        while (curr != null) {
            path.add(0, curr);
            curr = predecessors.get(curr);
        }

        if (path.size() == 1 && !path.get(0).equals(start)) {
            return new Result(-1, Collections.emptyList());
        }

        return new Result(distances.get(end), path);
    }

    private static class NodeDistance {
        Vertex vertex;
        int distance;

        NodeDistance(Vertex vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}
