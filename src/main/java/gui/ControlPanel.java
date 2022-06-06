package gui;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ControlPanel extends JPanel {
    private final App app;

    public ControlPanel(App app) {
        this.app = app;
        setSize(200, 400);
        setLocation(400, 0);
        setLayout(new GridLayout(5, 1));

        JButton exportPNG = new JButton("Export as PNG");
        JButton exportSVG = new JButton("Export as SVG");
        JButton exportTIKZ = new JButton("Export as TIKZ");
        JButton saveGraph = new JButton("Save");
        JButton newGraph = new JButton("New");

        add(exportPNG);
        add(exportSVG);
        add(exportTIKZ);
        add(saveGraph);
        add(newGraph);
        app.pack();

        exportPNG.addActionListener(this::onExportPNGClicked);
        exportSVG.addActionListener(this::onExportSVGClicked);
        exportTIKZ.addActionListener(this::onExportTIKZClicked);
        saveGraph.addActionListener(this::onSaveGraphClicked);
        newGraph.addActionListener(this::onNewGraphClicked);
    }

    private void onExportPNGClicked(ActionEvent e) {
        try {
            String path = System.getProperty("user.home") + "/Downloads/graph_" + app.getGraph().getId() + ".png";
            Util.exportPNG(app.getDrawingPanel(), path);
            System.out.println("Graph " + app.getGraph().getId() + " exported as PNG");
        } catch (IOException ex) {
            System.err.println("Wrong path");
        }
    }

    private void onExportSVGClicked(ActionEvent e) {
        try {
            String path = System.getProperty("user.home") + "/Downloads/graph_" + app.getGraph().getId() + ".svg";
            Util.exportSVG(app.getDrawingPanel(), path);
            System.out.println("Graph " + app.getGraph().getId() + " exported as SVG");
        } catch (Exception ex) {
            System.err.println("Wrong path");
        }
    }

    private void onExportTIKZClicked(ActionEvent e) {

    }

    private void onSaveGraphClicked(ActionEvent e) {
        try {
            Util.saveGraph(app.getGraph(), "http://localhost:8000/api/graphs");
            System.out.println("Graph " + app.getGraph().getId() + " saved in the database");
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }

    private void onNewGraphClicked(ActionEvent e) {

    }
}
