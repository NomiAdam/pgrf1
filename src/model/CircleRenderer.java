package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Trida pro vykresleni kruznice
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class CircleRenderer extends Renderer {
    private List<Point> points;
    private LineRenderer lr;

    private int centerX;
    private int borderX;
    private int centerY;
    private int borderY;

    private int startingAngleX;
    private int startingAngleY;
    private int computedAngle;  //Uhel vypocteny z pozice mysi
    private double initialAngle;  //Pocatecni uhel
    private int finalAngle;  //Koncovy uhel

    private boolean circleSector;

    public CircleRenderer(BufferedImage img) {
        super(img);
        points = new ArrayList<>();
        lr = new LineRenderer(img);
    }

    /**
     * Metoda pro vykresleni kruznice
     */
    public void drawCircle() {

        if (!circleSector) {
            //Nastaveni koncoveho uhlu a vypocet hranicnich pri kresleni cele kruznice
            finalAngle = 360;
            initialAngle = 0;
            borderX = borderX - centerX;
            borderY = borderY - centerY;
        } else {
            //Nastaveni pocatecniho uhlu a koncoveho uhlu pri kresleni vysece
            initialAngle = getStartingAngle();
            finalAngle = computedAngle + (int) Math.toDegrees(getStartingAngle());
        }

        int r = (int) Math.sqrt((borderX * borderX) + (borderY * borderY));
        // Pi/180 * stupne - Prevod stupnu na radiany
        double angle = Math.PI / 180 * finalAngle;

        for (double fi = initialAngle; fi <= angle; fi += 0.025) {

            double x = r * Math.cos(fi);
            double y = r * Math.sin(fi);

            int int_x = (int) Math.round(x + centerX + 0.5);
            int int_y = (int) Math.round(y + centerY + 0.5);

            points.add(new Point(int_x, int_y));
        }
        linkPoints();
    }

    /**
     * Metoda pro spojeni kazdeho bodu kruznice useckami
     */
    public void linkPoints() {
        int x1, x2, y1, y2;
        for (int i = 0; i < points.size() - 1; i++) {
            x1 = (int) points.get(i).getX();
            x2 = (int) points.get(i + 1).getX();
            y1 = (int) points.get(i).getY();
            y2 = (int) points.get(i + 1).getY();
            lr.draw(x1, x2, y1, y2);
        }
        if (!circleSector) {
            x1 = (int) points.get(0).getX();
            y1 = (int) points.get(0).getY();
            x2 = (int) points.get(points.size() - 1).getX();
            y2 = (int) points.get(points.size() - 1).getY();
            lr.draw(x1, x2, y1, y2);
        }
        points.clear();
    }

    /**
     * Nastaveni hranicnich bodu
     *
     * @param x
     * @param y
     */
    public void setBorder(int x, int y) {
        this.borderX = x;
        this.borderY = y;
    }

    /**
     * Nastaveni stredoveho bodu
     *
     * @param x
     * @param y
     */
    public void setCenter(int x, int y) {
        this.centerX = x;
        this.centerY = y;
        circleSector = false;
    }

    /**
     * Metoda pro nastaveni pocatecnich bodu pri vykresleni vysece
     *
     * @param x
     * @param y
     */
    public void setStartingAnglePoints(int x, int y) {
        this.startingAngleX = x;
        this.startingAngleY = y;
        circleSector = true;
    }

    /**
     * Pretizena metoda pro vynulovani pocatecnich bodu pri ukonceni kresby vysece
     */
    public void setStartingAnglePoints() {
        this.startingAngleX = 0;
        this.startingAngleY = 0;
        circleSector = false;
    }

    /**
     * Vypocet pocatecniho uhlu vuci stredu kruznice
     *
     * @return radians
     */
    private double getStartingAngle() {
        return Math.atan2(startingAngleY - centerY, startingAngleX - centerX);
    }

    /**
     * Vypocet koncoveho uhlu vuci pocatecnim bodum pri vykresleni vysece
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
            computedAngle = (int) (Math.atan2((point2X - point1X), (point1Y - point2Y)) * 180 / Math.PI);
        } else if ((point2X < point1X)) {
            computedAngle = (int) (360 - (Math.atan2((point1X - point2X), (point1Y - point2Y)) * 180 / Math.PI));
        }
    }
}
