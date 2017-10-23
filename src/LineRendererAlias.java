import java.awt.image.BufferedImage;

public class LineRendererAlias extends Renderer {
    private int color = 0xffff00;

    public LineRendererAlias(BufferedImage img) {
        super(img);
    }

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
        drawAA(x1, x2, y1, y2);
    }

    private void drawPixel(int x, int y) {
        if (x > 0 && x < img.getWidth() && y > 0 && y < img.getHeight()) {
            img.setRGB(x, y, color);
        }
    }

    private void drawAA(int x1, int x2, int y1, int y2) {
        int x = x1;
        int y = y1;

        double dx = (x2 - x1);
        double dy = (y2 - y1);

        double p = -dx;
        img.setRGB(x, y, color);
        while(x < x2) {
            p = p + dy;
            x = x + 1;
            if (p >= 0) {
                p = p - dx;
                y = y + 1;
            }
            double w = -p/dx;

        }
    }

}
