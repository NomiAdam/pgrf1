import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Potomek třídy Renderer pro vykreslení čar
 *
 * @author adamk
 * @version 2017
 * @see Renderer
 */
public class LineRendererTrivial extends Renderer {
    private int color;

    public LineRendererTrivial(BufferedImage img) {
        super(img);
        color = 0xffff00;
    }

    /**
     * Modifikátor barvy
     *
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Metoda pro vykreslení čáry
     *
     * @param x1 počáteční bod X
     * @param x2 koncový bod X
     * @param y1 počáteční bod Y
     * @param y2 koncový bod Y
     */
    public void draw(int x1, int x2, int y1, int y2) {
        double k, q;

        //Delta X a Y pro výpočet směrnice K
        double dx = x2 - x1;
        double dy = y2 - y1;

        if (Math.abs((int) dy) < Math.abs((int) dx)) {

            //Výměna koncových bodů
            if (x2 < x1) {
                int slave = x1;
                x1 = x2;
                x2 = slave;

                slave = y1;
                y1 = y2;
                y2 = slave;
            }

            //Výpočet podle řidící osy X
            k = dy / dx;
            q = y1 - (k * x1);

            for (int i = x1; i <= x2; i++) {
                double y = (k * i) + q;
                int int_y = (int) Math.round(y);
                img.setRGB(i, int_y, color);
            }

        } else {

            if (y2 < y1) {
                int slave = x1;
                x1 = x2;
                x2 = slave;

                slave = y1;
                y1 = y2;
                y2 = slave;
            }

            //Výpočet podle řidící osy Y
            k = dy / dx;
            q = y1 - (k * x1);

            for (int i = y1; i <= y2; i++) {
                double x = (i - q) / k;
                int int_x = (int) Math.round(x);
                img.setRGB(int_x, i, color);
            }
        }
    }

    /**
     * Metoda pro překreslení čar pomocí bodů
     *
     * @param points
     */
    public void reDraw(List<Point> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            int x1 = (int) points.get(i).getX();
            int x2 = (int) points.get(i + 1).getX();
            int y1 = (int) points.get(i).getY();
            int y2 = (int) points.get(i + 1).getY();
            draw(x1, x2, y1, y2);
        }
    }
}
