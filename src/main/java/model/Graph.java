package model;

import db.Converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graph implements Serializable {
    private int id;
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    public Graph() { }

    public Graph duplicate() {
        return Converter.toGraph(Converter.toJSON(this));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
