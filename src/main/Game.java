package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame implements Runnable {

    private GameScreen gameScreen;
    private BufferedImage image;
    private Thread gameThread;

    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;


    public Game() {
        importImg();

        setSize(640, 640); //20x20 grid 32x32px each chunk
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gameScreen = new GameScreen(image);
        add(gameScreen);
        setVisible(true);

    }

    private void importImg() {
        InputStream inputStream = getClass().getResourceAsStream("/spriteatlas.png");

        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void updateGame() {
        //update
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1_000_000_000.0 / FPS_SET;
        double timePerUpdate = 1_000_000_000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;



        while (true) {

            if (System.nanoTime() - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = System.nanoTime();
                frames++;
            }

            if (System.nanoTime() - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = System.nanoTime();
                updates++;
            }

            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }
}
