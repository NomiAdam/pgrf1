package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Trida pro vykresleni usecky
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class LineRenderer extends Renderer {
    private int color;

    public LineRenderer(BufferedImage img) {
        super(img);
        color = 0xffff00;
    }

    /**
     * Modifikator barvy
     *
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Metoda pro vykresleni usecky
     *
     * @param x1 pocatecni bod X
     * @param x2 koncovy bod X
     * @param y1 pocatecni bod Y
     * @param y2 koncovy bod Y
     */
    public void draw(int x1, int x2, int y1, int y2) {

        //Delta X a Y pro vypocet smernice K
        double dx = x2 - x1;
        double dy = y2 - y1;

        if (Math.abs((int) dy) <= Math.abs((int) dx)) {

            //Vymena koncovych bodu
            if (x2 < x1) {
                int slave = x1;
                x1 = x2;
                x2 = slave;

                slave = y1;
                y1 = y2;
                y2 = slave;
            }

            //Vypočet podle ridici osy X
            double y = y1;
            double k = dy / dx;

            for (int i = x1; i <= x2; i++) {
                int intY = (int) Math.round(y + 0.5);
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

            //Vypocet podle ridici osy Y
            double x = x1;
            double k = dx / dy;

            for (int i = y1; i <= y2; i++) {
                int intX = (int) Math.round(x + 0.5);
                drawPixel(intX, i);
                x += k;
            }
        }
    }

    /**
     * Kontrola zdali se bod nachazi na platne
     *
     * @param x
     * @param y
     */
    private void drawPixel(int x, int y) {
        if (x > 0 && x < img.getWidth() && y > 0 && y < img.getHeight()) {
            img.setRGB(x, y, color);
        }
    }

    /**
     * Metoda pro prekresleni usecek pomoci kolekce bodu
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
