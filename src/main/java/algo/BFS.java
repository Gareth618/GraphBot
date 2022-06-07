package algo;

import model.Color;
import model.Edge;
import model.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BFS extends Algorithm {
    private final int source;
    private final boolean[] visited;
    private final List<List<Integer>> adj;

    public BFS(Graph graph, int source) {
        super(graph);
        this.source = source;
        final int n = graph.getNodes().size();
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<>());
        for (final Edge edge : graph.getEdges()) {
            adj.get(edge.getNode1()).add(edge.getNode2());
            adj.get(edge.getNode2()).add(edge.getNode1());
        }
        visited = new boolean[n];
    }

    @Override
    protected void initFrame(Graph frame) {
        for (final Edge edge : frame.getEdges())
            edge.setText("");
    }

    private void bfs(int s) {
        Deque<Integer> queue = new ArrayDeque<>();
        Graph frame;
        frame = makeFrame();
        frame.getNodes().get(s).setColor(Color.RED);
        visited[s] = true;
        queue.add(s);
        while (!queue.isEmpty()) {
            final int node = queue.poll();
            frame = makeFrame();
            frame.getNodes().get(node).setColor(Color.RED);
            for (final int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    final int node1 = Math.min(node, neighbor);
                    final int node2 = Math.max(node, neighbor);
                    frame.getEdges().get(frame.getEdges().indexOf(new Edge(node1, node2))).setColor(Color.YELLOW);
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

    @Override
    public List<Graph> run() {
        bfs(source);
        return frames;
    }
}
