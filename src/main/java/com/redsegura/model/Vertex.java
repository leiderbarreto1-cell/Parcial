package com.redsegura.model;

import java.util.Objects;

public class Vertex implements Comparable<Vertex> {
    private String id;
    private String label;
    private String type;

    public Vertex(String id, String label, String type) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del vértice no puede ser nulo o vacío.");
        }
        this.id = id;
        this.label = label;
        this.type = type;
    }

    public String getId() { return id; }
    public String getLabel() { return label; }
    public String getType() { return type; }

    @Override
    public int compareTo(Vertex other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id.equals(vertex.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", id, label, type);
    }
}
