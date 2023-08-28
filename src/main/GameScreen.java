package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {

    private Dimension size;

    private Game game;

    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;

    public GameScreen(Game game) {
        this.game = game;

        setPanelSize();
    }

    private void setPanelSize() {
        size = new Dimension(640, 740); //20x20 grid 32x32px each chunk
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void initInputs() {
        myMouseListener = new MyMouseListener(game);
        keyboardListener = new KeyboardListener();
        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        requestFocus();
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);

        game.getRender().render(graphics);
    }

}
