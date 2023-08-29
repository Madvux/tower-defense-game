package scenes;

import main.Game;
import ui.ActionBar;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private ActionBar actionBar;

    private int mouseX, mouseY;


    public Playing(Game game) {
        super(game);
        loadDefaultLevel();
        actionBar = new ActionBar(0, 640, 640, 100, this);


    }

    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new_level");
    }


    @Override
    public void render(Graphics graphics) {
        drawLevel(graphics);
        actionBar.draw(graphics);
    }

    private void drawLevel(Graphics graphics) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                graphics.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
    }

    private BufferedImage getSprite(int spriteId) {
        return game.getTileManager().getSprite(spriteId);
    }


    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            actionBar.mouseClicked(x, y);
        }
    }


    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            actionBar.mouseMoved(x, y);
        } else {
            //divide and multiply by size of the tile to make it snap in right position
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640) {
            actionBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        actionBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }
}
