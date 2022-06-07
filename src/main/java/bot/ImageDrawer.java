package bot;

import db.Converter;
import model.Edge;
import model.Graph;
import model.Node;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageDrawer {
    private Graph graph;
    private static final int R = 16;

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
                paintGraph(g2d);
            }

            public void paintGraph(Graphics2D g2d) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(model.Color.WHITE.getColor());
                g2d.fillRect(0, 0, 400, 400);
                g2d.setStroke(new BasicStroke(3));

                for (Edge edge : graph.getEdges()) {
                    g2d.setColor(edge.getColor().getColor());
                    int x1 = graph.getNodes().get(edge.getNode1()).getX();
                    int y1 = graph.getNodes().get(edge.getNode1()).getY();
                    int x2 = graph.getNodes().get(edge.getNode2()).getX();
                    int y2 = graph.getNodes().get(edge.getNode2()).getY();
                    g2d.drawLine(x1, y1, x2, y2);
                }
                for (int i = 0; i < graph.getNodes().size(); i++) {
                    Node node = graph.getNodes().get(i);
                    int x = node.getX();
                    int y = node.getY();
                    g2d.setColor(node.getColor().getColor());
                    g2d.fillOval(x - R, y - R, 2 * R, 2 * R);
                    g2d.setColor(model.Color.BLACK.getColor());
                    g2d.drawOval(x - R, y - R, 2 * R, 2 * R);
                    g2d.setColor(model.Color.BLACK.getColor());
                    g2d.setFont(new Font("Monospaced", Font.BOLD, 20));
                    int w = g2d.getFontMetrics().stringWidth(String.valueOf(i + 1));
                    g2d.drawString(String.valueOf(i + 1), x - w / 2, y + 7);
                }
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
