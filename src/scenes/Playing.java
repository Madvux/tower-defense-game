package scenes;

import main.Game;
import managers.EnemyManager;
import managers.TowerManager;
import objects.PathPoint;
import objects.Tower;
import ui.ActionBar;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.Tiles.GRASS_TILE;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private ActionBar actionBar;

    private int mouseX, mouseY;
    private EnemyManager enemyManager;
    private TowerManager towerManager;

    private PathPoint start, end;
    private Tower selectedTower;

    public Playing(Game game) {
        super(game);
        loadDefaultLevel();
        actionBar = new ActionBar(0, 640, 640, 160, this);
        enemyManager = new EnemyManager(this, start, end);
        towerManager = new TowerManager(this);
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new_level");
        ArrayList<PathPoint> points = LoadSave.getLevelPathPoint("new_level");
        start = points.get(0);
        end = points.get(1);
    }


    @Override
    public void render(Graphics graphics) {
        drawLevel(graphics);
        actionBar.draw(graphics);
        enemyManager.draw(graphics);
        towerManager.draw(graphics);
        drawSelectedTower(graphics);
        drawHighlight(graphics);
    }

    private void drawHighlight(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(mouseX,mouseY,32,32);
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }


    private void drawSelectedTower(Graphics graphics) {
        if (selectedTower == null) return;
        graphics.drawImage(towerManager.getTowerImages()[selectedTower.getTowerType()], mouseX, mouseY, null);
    }

    public void update() {
        updateTick();
        enemyManager.update();
        towerManager.update();
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
        } else {
            if (selectedTower != null) {
                if (isTileGrass(mouseX, mouseY)) {
                    if (getTowerAt(mouseX, mouseY) == null) {
                        towerManager.addTower(selectedTower, mouseX, mouseY);
                        selectedTower = null;
                    }
                }
            } else {
                Tower t = getTowerAt(mouseX, mouseY);
                actionBar.displayTower(t);
            }
        }
    }


    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x,y);
    }

    private boolean isTileGrass(int x, int y) {
        int id = lvl[y / 32][x / 32];
        int tileType = game.getTileManager().getTile(id).getTileType();

        return tileType == GRASS_TILE;
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
        int xCord = x / 32;
        int yCord = y / 32;
        if (xCord < 0 || xCord > 19) return 0;
        if (yCord < 0 || yCord > 19) return 0;

        int id = lvl[y / 32][x / 32];
        return game.getTileManager().getTile(id).getTileType();
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            setSelectedTower(null);
        }
    }
}
