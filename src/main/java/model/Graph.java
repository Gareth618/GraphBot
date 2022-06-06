package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graph implements Serializable {
    private static final ObjectMapper om = new ObjectMapper();

    private int id;
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    public Graph() { }

    public Graph duplicate() {
        String json;
        try { json = om.writeValueAsString(this); }
        catch (final Exception exc) { json = ""; }
        Graph cloned;
        try { cloned = om.readValue(json, Graph.class); }
        catch (final Exception exc) { cloned = null; }
        return cloned;
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
