package gui;

import model.Graph;

import javax.swing.*;
import java.awt.*;

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
        add(drawingPanel, BorderLayout.WEST);
        add(controlPanel, BorderLayout.EAST);
        pack();
        graph = new Graph();
    }

    public Graph getGraph() {
        return graph;
    }

    public void launch() {
        setSize(600, 436);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
    }
}
