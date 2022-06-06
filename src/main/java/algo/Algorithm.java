package algo;

import model.Graph;
import java.util.ArrayList;
import java.util.List;

public abstract class Algorithm {
    protected final List<Graph> frames;

    public Algorithm(Graph graph) {
        frames = new ArrayList<>();
        final Graph frame = graph.duplicate();
        frames.add(frame);
        initFrame(frame);
    }

    protected abstract void initFrame(Graph frame);

    protected Graph makeFrame() {
        final Graph frame = frames.get(frames.size() - 1).duplicate();
        frames.add(frame);
        return frame;
    }

    public abstract List<Graph> run();
}
