package model;

import java.io.Serializable;

public class Edge implements Serializable {
    private int node1;
    private int node2;
    private String text = "";
    private Color color = Color.BLACK;

    public Edge() { }

    public Edge(int node1, int node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public int getNode1() {
        return node1;
    }

    public void setNode1(int node1) {
        this.node1 = node1;
    }

    public int getNode2() {
        return node2;
    }

    public void setNode2(int node2) {
        this.node2 = node2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge)) return false;
        final Edge edge = (Edge) obj;
        return node1 == edge.node1 && node2 == edge.node2;
    }
}
