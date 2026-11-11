package com.redsegura.model;

public class Edge {
    private String id;
    private Vertex source;
    private Vertex destination;
    private int weight; // Minutos
    private String label;
    private Orientation orientation;
    private boolean active; // Para simular bloqueos (Q6)

    public Edge(String id, Vertex source, Vertex destination, int weight, String label, Orientation orientation) {
        if (weight < 0) {
            throw new IllegalArgumentException("El peso (minutos) no puede ser negativo.");
        }
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.label = label;
        this.orientation = orientation;
        this.active = true;
    }

    public String getId() { return id; }
    public Vertex getSource() { return source; }
    public Vertex getDestination() { return destination; }
    public int getWeight() { return weight; }
    public String getLabel() { return label; }
    public Orientation getOrientation() { return orientation; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        String arrow = (orientation == Orientation.DIRECTED) ? " -> " : " -- ";
        return String.format("%s: %s%s%s [%d min] (%s)", id, source.getId(), arrow, destination.getId(), weight, label);
    }
}
