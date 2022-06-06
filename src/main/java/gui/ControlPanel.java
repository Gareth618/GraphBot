package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ControlPanel extends JPanel {
    private final App app;
    private final JButton exportSVG = new JButton("Export as SVG");
    private final JButton exportPNG = new JButton("Export as PNG");
    private final JButton exportTIKZ = new JButton("Export as TIKZ");
    private final JButton saveGraph = new JButton("Save");
    private final JButton newGraph = new JButton("New");

    public ControlPanel(App app) {
        this.app = app;
        setSize(200, 400);
        setLocation(400, 0);
        setLayout(new GridLayout(5, 1));

        add(exportSVG);
        add(exportPNG);
        add(exportTIKZ);
        add(saveGraph);
        add(newGraph);
        app.pack();

        exportSVG.addActionListener(this::onExportSVGClicked);
        exportPNG.addActionListener(this::onExportPNGClicked);
        exportTIKZ.addActionListener(this::onExportTIKZClicked);
        saveGraph.addActionListener(this::onSaveGraphClicked);
        newGraph.addActionListener(this::onNewGraphClicked);
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

    private void onExportPNGClicked(ActionEvent e) {
        try {
            String path = System.getProperty("user.home") + "/Downloads/graph_" + app.getGraph().getId() + ".png";
            Util.exportPNG(app.getDrawingPanel(), path);
            System.out.println("Graph " + app.getGraph().getId() + " exported as PNG");
        } catch (IOException ex) {
            System.err.println("Wrong path");
        }
    }

    private void onExportTIKZClicked(ActionEvent e) {

    }

    private void onSaveGraphClicked(ActionEvent e) {

    }

    private void onNewGraphClicked(ActionEvent e) {

    }
}
