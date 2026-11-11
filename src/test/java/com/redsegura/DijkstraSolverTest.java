package com.redsegura;

import com.redsegura.algorithms.DijkstraSolver;
import com.redsegura.graph.MixedGraph;
import com.redsegura.model.Orientation;
import com.redsegura.model.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraSolverTest {

    @Test
    public void testShortestPathS02ToS08() {
        MixedGraph graph = new MixedGraph();
        
        Vertex s01 = new Vertex("S01", "UAJS Sede C", "Universidad");
        Vertex s02 = new Vertex("S02", "UAJS Sede E", "Universidad");
        Vertex s07 = new Vertex("S07", "Estación Central SETP", "Transporte");
        Vertex s08 = new Vertex("S08", "Terminal de Transporte", "Transporte");

        graph.addVertex(s01);
        graph.addVertex(s02);
        graph.addVertex(s07);
        graph.addVertex(s08);

        graph.addEdge("R04", "S02", "S01", 8, "Conexión entre sedes", Orientation.UNDIRECTED);
        graph.addEdge("R01", "S01", "S07", 4, "Corredor universitario", Orientation.DIRECTED);
        graph.addEdge("R16", "S07", "S08", 12, "Ruta SETP al terminal", Orientation.DIRECTED);

        DijkstraSolver.Result result = DijkstraSolver.findShortestPath(graph, "S02", "S08");

        assertEquals(24, result.totalCost, "La ruta de S02 a S08 debe costar exactamente 24 minutos");
        assertEquals(4, result.path.size());
        assertEquals("S02", result.path.get(0).getId());
        assertEquals("S01", result.path.get(1).getId());
        assertEquals("S07", result.path.get(2).getId());
        assertEquals("S08", result.path.get(3).getId());
    }
}
