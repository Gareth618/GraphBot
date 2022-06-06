package server;

import model.Edge;
import model.Graph;
import model.Node;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/graphs")
public class GraphController {
    private List<Graph> graphs;

    public GraphController() {
        graphs = new ArrayList<>();
        final Graph graph = new Graph();
        graph.getNodes().add(new Node(314, 618));
        graph.getNodes().add(new Node(618, 314));
        graph.getEdges().add(new Edge(0, 1));
        graphs.add(graph);
    }

    @GetMapping
    public List<Graph> getGraphs() {
        return graphs;
    }
}
