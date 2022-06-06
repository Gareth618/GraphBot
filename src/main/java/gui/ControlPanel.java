package gui;

import spring.SpringClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ControlPanel extends JPanel {
    private final App app;
    private final SpringClient client;

    public ControlPanel(App app) {
        this.app = app;
        client = new SpringClient();

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
            String path = System.getProperty("user.home") + "/Downloads/graph-" + app.getGraph().getId() + ".png";
            ExportUtil.exportPNG(app.getDrawingPanel(), path);
            System.out.println("Graph " + app.getGraph().getId() + " exported as PNG");
        } catch (IOException ex) {
            System.err.println("Wrong path");
        }
    }

    private void onExportSVGClicked(ActionEvent e) {
        try {
            String path = System.getProperty("user.home") + "/Downloads/graph-" + app.getGraph().getId() + ".svg";
            ExportUtil.exportSVG(app.getDrawingPanel(), path);
            System.out.println("Graph " + app.getGraph().getId() + " exported as SVG");
        } catch (Exception ex) {
            System.err.println("Wrong path");
        }
    }

    private void onExportTIKZClicked(ActionEvent e) {

    }

    private void onSaveGraphClicked(ActionEvent e) {
        client.saveGraph(app.getGraph());
        System.out.println("Graph " + app.getGraph().getId() + " saved in the database");
    }

    private void onNewGraphClicked(ActionEvent e) {

    }
}
