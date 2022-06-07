package spring;

import algo.DFS;
import db.GraphRepo;
import model.Graph;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SpringController {
    private final GraphRepo graphRepo = new GraphRepo();

    @GetMapping("/graphs")
    public List<Graph> getGraphs() {
        return graphRepo.findAll();
    }

    @PostMapping(value = "/graphs", consumes="application/json")
    public ResponseEntity<String> addGraph(@RequestBody Graph graph) {
        graphRepo.create(graph);
        return new ResponseEntity<>("graph created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/algo/dfs/{id}")
    public List<Graph> runDFS(@PathVariable int id, @RequestParam int source) {
        final Graph graph = graphRepo.findById(id);
        return new DFS(graph, source).run();
    }
}
