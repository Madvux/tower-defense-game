package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame {

    private GameScreen gameScreen;

    private BufferedImage image;

    public Game() {
        importImg();

        setSize(640, 640); //20x20 grid 32x32px each chunk
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gameScreen = new GameScreen(image);
        add(gameScreen);
    }

    private void importImg() {
        InputStream inputStream = getClass().getResourceAsStream("/spriteatlas.png");

        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
