package scenes;

import main.Game;
import main.GameStates;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.PLAYING;
import static main.GameStates.MENU;


public class GameOver extends GameScene implements SceneMethods {

    private MyButton bReplay, bMenu;

    public GameOver(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons() {

        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 300;
        int yOffset = 100;

        bMenu = new MyButton(x, y, w, h, "Menu");
        bReplay = new MyButton(x, y + yOffset, w, h, "Replay");

    }

    @Override
    public void render(Graphics g) {
        // game over text
        g.setFont(new Font("LucidaSans", Font.BOLD, 50));
        g.setColor(Color.red);
        g.drawString("Game Over!", 160, 80);

        // buttons
        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        bMenu.draw(g);
        bReplay.draw(g);
    }

    private void replayGame() {
        // reset everything
        resetAll();

        // change state to playing
        GameStates.setGameState(PLAYING);

    }

    private void resetAll() {
        game.getPlaying().resetEverything();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            GameStates.setGameState(MENU);
            resetAll();
        } else if (bReplay.getBounds().contains(x, y))
            replayGame();
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bReplay.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if (bReplay.getBounds().contains(x, y))
            bReplay.setMouseOver(true);
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else if (bReplay.getBounds().contains(x, y))
            bReplay.setMousePressed(true);

    }

    @Override
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bReplay.resetBooleans();

    }

    @Override
    public void mouseDragged(int x, int y) {

    }

}
