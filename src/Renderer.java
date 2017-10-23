import java.awt.image.BufferedImage;

/**
 * Abstraktní třída Renderer
 *
 * @author adamk
 * @version 2017
 */
public abstract class Renderer {
    protected final BufferedImage img;

    public Renderer(BufferedImage img) {
        this.img = img;
    }
}
