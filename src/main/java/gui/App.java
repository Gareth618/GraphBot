package gui;

import model.Edge;
import model.Graph;
import model.Node;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.awt.BorderLayout.*;

public class App extends JFrame {
    private Graph graph;
    private final DrawingPanel drawingPanel;
    private final ControlPanel controlPanel;

    public static void main(String[] args) {
        new App().launch();
    }

    public App() {
        super("GraphBot");
        drawingPanel = new DrawingPanel(this);
        controlPanel = new ControlPanel();
        add(drawingPanel, WEST);
        add(controlPanel, EAST);
        pack();

        graph = new Graph();
//        Random random = new Random();
//        graph.getNodes().add(new Node(random.nextInt(400), random.nextInt(400)));
//        graph.getNodes().add(new Node(random.nextInt(400), random.nextInt(400)));
//        graph.getNodes().add(new Node(random.nextInt(400), random.nextInt(400)));
//        graph.getEdges().add(new Edge(0, 1));
//        graph.getEdges().add(new Edge(1, 2));
//        graph.getEdges().add(new Edge(2, 0));
    }

    public Graph getGraph() {
        return graph;
    }

    public void launch() {
        setSize(600, 436);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
    }
}
