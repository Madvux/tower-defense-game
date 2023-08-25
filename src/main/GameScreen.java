package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {

    private Random random;

    private BufferedImage image;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();



    public GameScreen(BufferedImage image) {
        this.image = image;

        loadSprites();
        random = new Random();

    }

    private void loadSprites() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                sprites.add(image.getSubimage(x * 32, y * 32, 32, 32));
            }
        }
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                graphics.drawImage(sprites.get(getRandomInt()), x * 32, y * 32, null);
            }
        }
    }

    public Color getRandomColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    private int getRandomInt() {
        return random.nextInt(100);
    }
}
