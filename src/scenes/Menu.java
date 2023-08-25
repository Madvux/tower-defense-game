package scenes;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Menu extends GameScene implements SceneMethods{


    private Random random;

    private BufferedImage image;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();


    public Menu(Game game) {
        super(game);
        random = new Random();
        importImg();
        loadSprites();
    }

    @Override
    public void render(Graphics graphics) {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                graphics.drawImage(sprites.get(getRandomInt()), x * 32, y * 32, null);
            }
        }
    }
    private void loadSprites() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                sprites.add(image.getSubimage(x * 32, y * 32, 32, 32));
            }
        }
    }
    private int getRandomInt() {
        return random.nextInt(100);
    }
    private void importImg() {
        InputStream inputStream = getClass().getResourceAsStream("/spriteatlas.png");

        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
