package scenes;

import main.Game;
import main.GameStates;
import ui.MyButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Menu extends GameScene implements SceneMethods {


    private Random random;

    private BufferedImage image;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private MyButton bPlaying, bSettings, bQuit;

    public Menu(Game game) {
        super(game);
        random = new Random();
        importImg();
        loadSprites();
        initButtons();
    }

    private void initButtons() {
        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;
        bPlaying = new MyButton(x, y, w, h, "Play");
        bSettings = new MyButton(x, y + yOffset, w, h, "Settings");
        bQuit = new MyButton(x, y + yOffset * 2, w, h, "Quit");
    }

    @Override
    public void render(Graphics graphics) {
        drawButtons(graphics);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bPlaying.getBounds().contains(x, y)) GameStates.setGameState(GameStates.PLAYING);
        if (bSettings.getBounds().contains(x, y)) GameStates.setGameState(GameStates.SETTINGS);
        if (bQuit.getBounds().contains(x, y)) System.exit(0);
    }

    @Override
    public void mouseMoved(int x, int y) {
        bPlaying.setMouseOver(false);
        if (bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMouseOver(true);
        }

        bSettings.setMouseOver(false);
        if (bSettings.getBounds().contains(x, y)) {
            bSettings.setMouseOver(true);
        }

        bQuit.setMouseOver(false);
        if (bQuit.getBounds().contains(x, y)) {
            bQuit.setMouseOver(true);
        }

    }

    @Override
    public void mousePressed(int x, int y) {
        if (bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMousePressed(true);
        }
        if (bSettings.getBounds().contains(x, y)) {
            bSettings.setMousePressed(true);
        }
        if (bQuit.getBounds().contains(x, y)) {
            bQuit.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void resetButtons() {
        bPlaying.resetBooleans();
        bSettings.resetBooleans();
        bQuit.resetBooleans();
    }

    private void drawButtons(Graphics graphics) {
        bPlaying.draw(graphics);
        bSettings.draw(graphics);
        bQuit.draw(graphics);
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
