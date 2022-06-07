package bot;

import db.Converter;
import gui.DrawingPanel;
import model.Graph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageDrawer {
    private Graph graph;
    private final JFrame frame;
    private final JPanel panel;

    public ImageDrawer() {
        frame = new JFrame();
        frame.setSize(400, 400);
        frame.setResizable(false);
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                DrawingPanel.paintGraph(g2d, graph, -1);
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(400, 400));
        frame.add(panel);
    }

    public void save(Graph graph) {
        this.graph = graph;
        System.out.println(Converter.toJSON(graph));
        frame.pack();
        final BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g2d = image.createGraphics();
        panel.paint(g2d);
        g2d.dispose();
        try { ImageIO.write(image, "png", new File("image.png")); }
        catch (final Exception ignored) { }
        frame.dispose();
    }
}
