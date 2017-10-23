import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Třída pro vykreslení polygonu za pomoci spojování bodů
 *
 * @author Adam Kvasnička
 * @version 2017
 * @see LineRenderer
 */
public class PolygonRenderer extends Renderer {
    private LineRenderer lr;

    public PolygonRenderer(BufferedImage img) {
        super(img);
        lr = new LineRenderer(img);
    }

    /**
     * Vykreslení polygonu pomocí bodů obsažených ve vstupním parametru
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
