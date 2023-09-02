package scenes;

import main.Game;
import managers.EnemyManager;
import ui.ActionBar;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private ActionBar actionBar;

    private int mouseX, mouseY;
    private EnemyManager enemyManager;

    public Playing(Game game) {
        super(game);
        loadDefaultLevel();
        actionBar = new ActionBar(0, 640, 640, 100, this);
        enemyManager = new EnemyManager(this);

    }

    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new_level");
    }


    @Override
    public void render(Graphics graphics) {
        drawLevel(graphics);
        actionBar.draw(graphics);
        enemyManager.draw(graphics);
    }

    public void update() {
        updateTick();
        enemyManager.update();
    }

    private void drawLevel(Graphics graphics) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                if (isAnimation(id)) {
                    graphics.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
                } else
                    graphics.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            actionBar.mouseClicked(x, y);
        }else{
//            enemyManager.addEnemy(x,y);
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

    public int getTileType(int x, int y) {
        int xCord = x/32;
        int yCord = y/32;
        if(xCord<0 || xCord>19) return 0;
        if(yCord<0 || yCord>19) return 0;

        int id = lvl[y/32][x/32];
        return  game.getTileManager().getTile(id).getTileType();
    }
}
