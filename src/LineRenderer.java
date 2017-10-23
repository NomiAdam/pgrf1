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
public class LineRenderer extends Renderer {
    private int color;

    public LineRenderer(BufferedImage img) {
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

        //Delta X a Y pro výpočet směrnice K
        double dx = x2 - x1;
        double dy = y2 - y1;

        if (Math.abs((int) dy) <= Math.abs((int) dx)) {

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
            double y = y1;
            double k = dy / dx;

            for (int i = x1; i <= x2; i++) {
                int intY = (int) Math.round(y);
                drawPixel(i, intY);
                y += k;
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
            double x = x1;
            double k = dx / dy;

            for (int i = y1; i <= y2; i++) {
                int intX = (int) Math.round(x + 0.5);
                drawPixel(intX, i);
                x += k;
            }
        }
    }

    private void drawPixel(int x, int y) {
        if (x > 0 && x < img.getWidth() && y > 0 && y < img.getHeight()) {
            img.setRGB(x, y, color);
        }
    }

    /**
     * Metoda pro překreslení čar pomocí bodů
     * Iterujeme o 2 protože chceme vykreslit dvojici bodů
     * V případě iterace o jednu se budou body spojovat
     *
     * @param points
     */
    public void reDraw(List<Point> points) {
        for (int i = 0; i < points.size() - 1; i += 2) {
            int x1 = (int) points.get(i).getX();
            int x2 = (int) points.get(i + 1).getX();
            int y1 = (int) points.get(i).getY();
            int y2 = (int) points.get(i + 1).getY();
            draw(x1, x2, y1, y2);
        }
    }
}
