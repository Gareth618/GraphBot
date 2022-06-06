package gui;

import model.Graph;

import javax.swing.*;

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
        controlPanel = new ControlPanel(this);
        setLayout(null);
        add(drawingPanel);
        add(controlPanel);
        pack();
        graph = new Graph();
    }

    public Graph getGraph() {
        return graph;
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public void launch() {
        setLocation(0, 0);
        setSize(600, 436);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
    }
}
