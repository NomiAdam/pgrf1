import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Potomek třídy Renderer pro kreslení kružnice
 *
 * @author adamk
 * @version 2017
 * @see LineRenderer
 */
public class CircleRenderer extends Renderer {
    private List<Point> points = new ArrayList<>();
    private LineRenderer lr;

    private int centerX;
    private int borderX;
    private int centerY;
    private int borderY;

    private int startingAngleX;
    private int startingAngleY;

    private int alfa;  //Calculated angle from mouse position
    private double beta;  //Final starting angle point

    private int finalAngle;  //Final výseč angle

    private Boolean vysec = false;

    public CircleRenderer(BufferedImage img) {
        super(img);
        lr = new LineRenderer(img);
    }

    /**
     * Metoda pro výkreslení kružnice
     */
    public void drawCircle() {

        if (!vysec) {
            finalAngle = 360;
            beta = 0;
            borderX = borderX - centerX;
            borderY = borderY - centerY;
        } else if (vysec) {
            beta = getStartingAngle();
            finalAngle = alfa + (int) Math.toDegrees(getStartingAngle());
        }

        int r = (int) Math.sqrt((borderX * borderX) + (borderY * borderY));

        // Pí/180 * 160 Převod stupňu na radiány se kterými počítá sin a cos
        for (double fi = beta; fi <= Math.PI / 180 * finalAngle; fi += 0.025) {

            double x = r * Math.cos(fi);
            double y = r * Math.sin(fi);

            int int_x = (int) Math.round(x + centerX);
            int int_y = (int) Math.round(y + centerY);

            points.add(new Point(int_x, int_y));
        }

        int x1, x2, y1, y2;
        for (int i = 0; i < points.size() - 1; i++) {
            x1 = (int) points.get(i).getX();
            x2 = (int) points.get(i + 1).getX();
            y1 = (int) points.get(i).getY();
            y2 = (int) points.get(i + 1).getY();
            lr.draw(x1, x2, y1, y2);
        }
        points.clear();
    }

    public void setBorder(int x, int y) {
        this.borderX = x;
        this.borderY = y;
    }

    public void setCenter(int x, int y) {
        this.centerX = x;
        this.centerY = y;
        vysec = false;
    }

    /**
     * Metoda pro nastavení počátečního úhlu pří kreslení výseče
     *
     * @param x
     * @param y
     */
    public void setStartingAngle(int x, int y) {
        this.startingAngleX = x;
        this.startingAngleY = y;
        vysec = true;
    }

    /**
     * Přetížená metoda pro vynulování počátečního úhlu pří ukončení kresby výseče
     */
    public void setStartingAngle() {
        this.startingAngleX = 0;
        this.startingAngleY = 0;
        vysec = false;
    }

    /**
     * Výpočet počátečního úhlu vůči středu kružnice
     *
     * @return radians
     */
    private double getStartingAngle() {
        return Math.atan2(startingAngleY - centerY, startingAngleX - centerX);
    }

    /**
     * Nastavení koncového úhlu vůči počátečnímu úhlu
     *
     * @param x
     * @param y
     */
    public void setAngle(int x, int y) {
        int point1X = x;
        int point1Y = y;

        int point2X = startingAngleX;
        int point2Y = startingAngleY;

        if ((point2X > point1X)) {
            alfa = (int) (Math.atan2((point2X - point1X), (point1Y - point2Y)) * 180 / Math.PI);
        } else if ((point2X < point1X)) {
            alfa = (int) (360 - (Math.atan2((point1X - point2X), (point1Y - point2Y)) * 180 / Math.PI));
        }
    }
}
