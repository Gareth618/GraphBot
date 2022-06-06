package server;

import algo.DFS;
import model.Edge;
import model.Graph;
import model.Node;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GraphController {
    private final GraphRepo graphRepo;

    public GraphController() {
        graphRepo = new GraphRepo();
        final Graph graph = new Graph();
        graph.getNodes().add(new Node(314, 618));
        graph.getNodes().add(new Node(618, 314));
        graph.getEdges().add(new Edge(0, 1));
        graphRepo.create(graph);
    }

    @GetMapping("/graphs")
    public List<Graph> getGraphs() {
        return graphRepo.findAll();
    }

    @PostMapping(value = "/graphs", consumes="application/json")
    public ResponseEntity<String> addGraph(@RequestBody Graph graph) {
        graphRepo.create(graph);
        return new ResponseEntity<>("graph created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/algo/dfs")
    public List<Graph> runDFS(@RequestParam int id, @RequestParam int source) {
        final Graph graph = graphRepo.findById(id);
        return new DFS(graph, source).run();
    }
}
