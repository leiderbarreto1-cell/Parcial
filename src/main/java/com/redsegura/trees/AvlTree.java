package com.redsegura.trees;

import com.redsegura.model.Vertex;

public class AvlTree {

    public static class Node {
        public Vertex vertex;
        public int height;
        public Node left;
        public Node right;

        public Node(Vertex vertex) {
            this.vertex = vertex;
            this.height = 1;
        }
    }

    private Node root;
    private int stepsCounter = 0;

    public int getStepsCounter() { return stepsCounter; }
    public void resetStepsCounter() { this.stepsCounter = 0; }

    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    private int getBalance(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    // Rotación Simple a la Derecha (LL)
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Rotación Simple a la Izquierda (RR)
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(Vertex vertex) {
        root = insertRec(root, vertex);
    }

    private Node insertRec(Node node, Vertex vertex) {
        if (node == null) return new Node(vertex);

        int cmp = vertex.compareTo(node.vertex);
        if (cmp < 0) {
            node.left = insertRec(node.left, vertex);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, vertex);
        } else {
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        // Rotación LL
        if (balance > 1 && vertex.compareTo(node.left.vertex) < 0) {
            return rotateRight(node);
        }
        // Rotación RR
        if (balance < -1 && vertex.compareTo(node.right.vertex) > 0) {
            return rotateLeft(node);
        }
        // Rotación LR
        if (balance > 1 && vertex.compareTo(node.left.vertex) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // Rotación RL
        if (balance < -1 && vertex.compareTo(node.right.vertex) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public Vertex search(String id) {
        resetStepsCounter();
        Node current = root;
        while (current != null) {
            stepsCounter++;
            int cmp = id.compareTo(current.vertex.getId());
            if (cmp == 0) return current.vertex;
            else if (cmp < 0) current = current.left;
            else current = current.right;
        }
        return null;
    }

    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(Node node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.print(node.vertex.getId() + " ");
            inOrderRec(node.right);
        }
    }
}
