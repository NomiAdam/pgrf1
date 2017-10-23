import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu
 *
 * @author PGRF FIM UHK
 * @version 2017
 */

public class Canvas {
    private JFrame frame;
    private JPanel panel;
    private BufferedImage img;

    private LineRenderer lr;
    private CircleRenderer cr;
    private PolygonRenderer pr;

    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int selectedImage;

    private Boolean vysec = false;

    private List<Point> points = new ArrayList<>();
    private List<Point> lines = new ArrayList<>();

    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        lr = new LineRenderer(img);
        cr = new CircleRenderer(img);
        pr = new PolygonRenderer(img);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        KeyAdapter key = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 97) {
                    selectedImage = 0;
                    frame.setTitle("Přímka");
                } else if (e.getKeyCode() == 98) {
                    selectedImage = 1;
                    frame.setTitle("Polygon");
                } else if (e.getKeyCode() == 99) {
                    selectedImage = 2;
                    frame.setTitle("Kružnice");
                }
                clear();
            }
        };

        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                switch (selectedImage) {
                    case 0:
                        lines.add(new Point(x1, y1));
                        break;
                    case 1:
                        if (points.size() < 1) {
                            points.add(new Point(x1, y1));
                        }
                        break;
                    case 2:
                        if (!vysec) {
                            cr.setCenter(x1, y1);
                        } else if (vysec) {
                            cr.setStartingAngle(x1, y1);
                        }
                        break;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                clear();
                x2 = e.getX();
                y2 = e.getY();

                switch (selectedImage) {
                    case 0:
                        lr.reDraw(lines);
                        lr.setColor(0xba1a1a);
                        lr.draw(x1, x2, y1, y2);
                        break;
                    case 1:
                        //Draw polygon
                        lr.setColor(0xba1a1a);
                        //First line
                        if (points.size() > 1) {
                            lr.draw((int) points.get(points.size() - 1).getX(), x2, (int) points.get(points.size() - 1).getY(), y2);
                        }
                        lr.setColor(0x00000ff);
                        lr.draw((int) points.get(0).getX(), x2, (int) points.get(0).getY(), y2);
                        pr.drawPolygon(points);
                        break;
                    case 2:
                        if (!vysec) {
                            cr.setBorder(x2, y2);
                            cr.drawCircle();
                            lr.setColor(0xba1a1a);
                            lr.draw(x1, x2, y1, y2);
                        } else if (vysec) {
                            cr.setAngle(x2, y2);
                            cr.drawCircle();
                            lr.setColor(0xba1a1a);
                            lr.draw(x1, x2, y1, y2);
                        }
                        break;
                }
                lr.setColor(0xffff00);
                panel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                clear();
                x2 = e.getX();
                y2 = e.getY();
                switch (selectedImage) {
                    case 0:
                        lines.add(new Point(x2, y2));
                        lr.reDraw(lines);
                        break;
                    case 1:
                        points.add(new Point(x2, y2));
                        points.add(new Point(x2, y2));
                        pr.drawPolygon(points);
                        break;
                    case 2:
                        if (!vysec) {
                            cr.setBorder(x2, y2);
                            cr.drawCircle();
                            vysec = true;
                        } else if (vysec) {
                            lr.setColor(0x00ffff);
                            cr.drawCircle();
                            cr.setStartingAngle();
                            vysec = false;
                        }
                        break;
                }
                panel.repaint();
            }
        };

        panel.addMouseListener(mouse);
        panel.addMouseMotionListener(mouse);

        frame.addKeyListener(key);

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
        gr.setColor(new Color(0xffff00));
        gr.drawString("Press: 1 - for lines, 2 - for polygon, 3 - for circle", 5, img.getHeight() - 5);
    }

    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void start() {
        clear();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(640, 480).start());
    }
}
