import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Potomek třídy Renderer pro kreslení kružnice
 *
 * @author adamk
 * @version 2017
 * @see Renderer
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

    private int angleAlfa;  //Calculated angle from mouse position
    private double angleBeta;  //Final starting angle point

    private int angle;  //Final výseč angle

    private Boolean vysec = false;

    public CircleRenderer(BufferedImage img) {
        super(img);
        lr = new LineRenderer(img);
    }

    public void drawCircle() {

        if (!vysec) {
            angle = 360;
            angleBeta = 0;
            borderX = borderX - centerX;
            borderY = borderY - centerY;
        } else if (vysec) {
            angleBeta = getAngle();
            angle = angleAlfa + (int) Math.toDegrees(getAngle());
        }

        int r = (int) Math.sqrt((borderX * borderX) + (borderY * borderY));

        //TODO somehow change this to r/10 or something like that so it wont be number like this
        double alfa = 0.025;

        for (double fi = angleBeta; fi <= Math.PI / 180 * angle; fi += alfa) {  // Pí/180 * 160 Převod stupňu na radiány se kterými počítá sin a cos

            double x = r * Math.cos(fi);
            double y = r * Math.sin(fi);

            int int_x = (int) Math.round(x) + centerX;
            int int_y = (int) Math.round(y) + centerY;

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

    public void setStartingAngle(int x, int y) {
        this.startingAngleX = x;
        this.startingAngleY = y;
        vysec = true;
    }

    public void setStartingAngle() {
        this.startingAngleX = 0;
        this.startingAngleY = 0;
        vysec = false;
    }

    //TODO I DONT WANT TO HAVE FI AS ANGLE BUT AS RADIAN
    private double getAngle() {
        return Math.atan2(startingAngleY - centerY, startingAngleX - centerX);
    }

    public void setAngle(int x, int y) {
        int point1X = x;
        int point1Y = y;

        int point2X = startingAngleX;
        int point2Y = startingAngleY;

        if ((point2X > point1X)) {
            angleAlfa = (int) (Math.atan2((point2X - point1X), (point1Y - point2Y)) * 180 / Math.PI);
        } else if ((point2X < point1X)) {
            angleAlfa = (int) (360 - (Math.atan2((point1X - point2X), (point1Y - point2Y)) * 180 / Math.PI));
        }
    }
}
