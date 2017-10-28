package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Trida pro vykresleni polygonu
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class PolygonRenderer extends Renderer {
    private LineRenderer lr;

    public PolygonRenderer(BufferedImage img) {
        super(img);
        lr = new LineRenderer(img);
    }

    /**
     * Vykresleni polygonu pomoci kolekce bodu
     *
     * @param points
     */
    public void drawPolygon(List<Point> points) {
        int x1, x2, y1, y2;
        for (int i = 0; i < points.size() - 1; i++) {
            x1 = (int) points.get(i).getX();
            x2 = (int) points.get(i + 1).getX();
            y1 = (int) points.get(i).getY();
            y2 = (int) points.get(i + 1).getY();
            lr.draw(x1, x2, y1, y2);
            if (points.size() > 1) {
                x1 = (int) points.get(0).getX();
                x2 = (int) points.get(points.size() - 1).getX();
                y1 = (int) points.get(0).getY();
                y2 = (int) points.get(points.size() - 1).getY();
                lr.draw(x1, x2, y1, y2);
            }
        }
    }
}
