package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {

    private Dimension size;

    private Game game;

    public GameScreen(Game game) {
        this.game = game;

        setPanelSize();
    }

    private void setPanelSize() {
        size = new Dimension(640, 640); //20x20 grid 32x32px each chunk
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }


    public void paint(Graphics graphics) {
        super.paint(graphics);

        game.getRender().render(graphics);
    }

}
