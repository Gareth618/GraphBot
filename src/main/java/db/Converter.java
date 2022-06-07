package db;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Graph;

public class Converter {
    private static final ObjectMapper om = new ObjectMapper();

    public static Graph toGraph(String json) {
        Graph graph;
        try { graph = om.readValue(json, Graph.class); }
        catch (final Exception exc) { graph = new Graph(); }
        return graph;
    }

    public static String toJSON(Graph graph) {
        String json;
        try { json = om.writeValueAsString(graph); }
        catch (final Exception exc) { json = ""; }
        return json;
    }
}
