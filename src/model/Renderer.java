package model;

import java.awt.image.BufferedImage;

/**
 * Abstraktni trida Renderer
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public abstract class Renderer {
    protected final BufferedImage img;

    public Renderer(BufferedImage img) {
        this.img = img;
    }
}
