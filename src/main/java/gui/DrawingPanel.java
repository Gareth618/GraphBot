package gui;

import model.Color;
import model.Edge;
import model.Graph;
import model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingPanel extends JPanel {
    private final App app;
    private static final int R = 16;
    private int selectedNode = -1;

    public DrawingPanel(App app) {
        this.app = app;
        setSize(400, 400);
        setLocation(0, 0);

        addMouseListener(new MouseAdapter() {
            private void addNode(int x1, int y1) {
                if (x1 <= R) return;
                if (y1 <= R) return;
                if (x1 >= 400 - R) return;
                if (y1 >= 400 - R) return;
                for (Node node : app.getGraph().getNodes()) {
                    int x2 = node.getX();
                    int y2 = node.getY();
                    if ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) <= 9 * R * R)
                        return;
                }
                app.getGraph().getNodes().add(new Node(x1, y1));
            }

            private void selectNode(int x1, int y1) {
                for (int i = 0; i < app.getGraph().getNodes().size(); i++) {
                    Node node = app.getGraph().getNodes().get(i);
                    int x2 = node.getX();
                    int y2 = node.getY();
                    if ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) <= R * R) {
                        if (selectedNode == -1) {
                            selectedNode = i;
                        }
                        else if (selectedNode == i) {
                            selectedNode = -1;
                        }
                        else {
                            int node1 = Math.min(selectedNode, i);
                            int node2 = Math.max(selectedNode, i);
                            Edge edge = new Edge(node1, node2);
                            if (!app.getGraph().getEdges().contains(edge)) {
                                app.getGraph().getEdges().add(edge);
                            }
                            else {
                                showTextField(edge);
                            }
                            selectedNode = -1;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x1 = e.getX();
                int y1 = e.getY();
                selectNode(x1, y1);
                addNode(x1, y1);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintGraph(g2d);
    }

    public void paintGraph(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE.getColor());
        g2d.fillRect(0, 0, 400, 400);
        g2d.setStroke(new BasicStroke(3));

        final Graph graph = app.getGraph();
        for (Edge edge : graph.getEdges()) {
            g2d.setColor(edge.getColor().getColor());
            int x1 = graph.getNodes().get(edge.getNode1()).getX();
            int y1 = graph.getNodes().get(edge.getNode1()).getY();
            int x2 = graph.getNodes().get(edge.getNode2()).getX();
            int y2 = graph.getNodes().get(edge.getNode2()).getY();
            g2d.drawLine(x1, y1, x2, y2);
            if (!edge.getText().equals("")) {
                g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
                int x = (x1 + x2 - g2d.getFontMetrics().stringWidth(edge.getText())) / 2;
                int y = (y1 + y2 + g2d.getFontMetrics().getHeight() / 2) / 2;
                Rectangle rectangle = new Rectangle(
                        x - 3,
                        y - g2d.getFontMetrics().getHeight() + 6,
                        g2d.getFontMetrics().stringWidth(edge.getText()) + 6,
                        g2d.getFontMetrics().getHeight()
                );
                g2d.setColor(Color.WHITE.getColor());
                g2d.fill(rectangle);
                g2d.setColor(edge.getColor().getColor());
                g2d.draw(rectangle);
                g2d.drawString(edge.getText(), x, y);
            }
        }
        for (int i = 0; i < graph.getNodes().size(); i++) {
            Node node = graph.getNodes().get(i);
            int x = node.getX();
            int y = node.getY();
            g2d.setColor(node.getColor().getColor());
            g2d.fillOval(x - R, y - R, 2 * R, 2 * R);
            g2d.setColor(selectedNode == i ? Color.RED.getColor() : Color.BLACK.getColor());
            g2d.drawOval(x - R, y - R, 2 * R, 2 * R);
            g2d.setColor(Color.BLACK.getColor());
            g2d.setFont(new Font("Monospaced", Font.BOLD, 20));
            int w = g2d.getFontMetrics().stringWidth(String.valueOf(i + 1));
            g2d.drawString(String.valueOf(i + 1), x - w / 2, y + 7);
        }
    }

    private void showTextField(Edge edge) {
        JFrame frame = new JFrame("Edge value");
        JTextField textField = new JTextField();
        JButton submit = new JButton("Submit");

        frame.add(textField, BorderLayout.NORTH);
        frame.add(submit, BorderLayout.SOUTH);
        frame.pack();

        submit.addActionListener((ActionEvent e) -> {
            String text = textField.getText();
            if (!text.equals("")) {
                int i = app.getGraph().getEdges().indexOf(edge);
                app.getGraph().getEdges().get(i).setText(text);
            }
            frame.dispose();
            repaint();
        });

        frame.setSize(260, 100);
        frame.setLayout(new GridLayout(2, 1));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
