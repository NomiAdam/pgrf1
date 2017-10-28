package app;

import view.Canvas;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(640, 480).start());
    }
}
