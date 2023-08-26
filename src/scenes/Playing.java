package scenes;

import main.Game;
import main.GameStates;
import managers.TileManager;
import ui.MyButton;
import utils.LevelBuild;

import java.awt.*;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private TileManager tileManager;
    private MyButton bMenu;

    public Playing(Game game) {
        super(game);

        initButtons();
        lvl = LevelBuild.getLevelBeta();
        tileManager = new TileManager();
    }

    private void initButtons() {
        bMenu = new MyButton(2, 2, 100, 30,"Menu");

    }

    @Override
    public void render(Graphics graphics) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                graphics.drawImage(tileManager.getSprite(id),x*32,y*32,null);
            }
        }
        drawButtons(graphics);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);

    }
    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) GameStates.setGameState(GameStates.MENU);
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
    }

    @Override
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
    }
}
