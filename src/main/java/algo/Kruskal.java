package algo;

import model.Color;
import model.Edge;
import model.Graph;

import java.util.Comparator;
import java.util.List;

public class Kruskal extends Algorithm {
    public Kruskal(Graph graph) {
        super(graph);
    }

    @Override
    protected void initFrame(Graph frame) {
        for (final Edge edge : frame.getEdges())
            if (!edge.getText().matches(".*\\d.*"))
                edge.setText("0");
    }

    private void mst() {
        final UnionFind<Integer> forest = new UnionFind<>();
        frames.get(0).getEdges().sort(Comparator.comparingInt(edge -> Integer.parseInt(edge.getText())));
        for (final Edge edge : frames.get(0).getEdges())
            if (forest.union(edge.getNode1(), edge.getNode2())) {
                final Graph frame = makeFrame();
                frame.getEdges().get(frame.getEdges().indexOf(edge)).setColor(Color.RED);
            }
    }

    @Override
    public List<Graph> run() {
        mst();
        return frames;
    }
}
