package scenes;

import main.Game;
import main.GameStates;
import managers.TileManager;
import objects.Tile;
import ui.BottomBar;
import ui.MyButton;
import utils.LevelBuild;
import utils.LoadSave;

import java.awt.*;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private TileManager tileManager;
    private BottomBar bottomBar;
    private Tile selectedTile;
    private int mouseX, mouseY;
    private int lastTileX, lastTileY, lastTileId;
    private boolean drawSelect = false;

    public Playing(Game game) {
        super(game);

        lvl = LevelBuild.getLevelBeta();
        tileManager = new TileManager();
        bottomBar = new BottomBar(0, 640, 640, 100, this);

//        LoadSave.createFile();
        createDefaultLevel();
        loadDefaultLevel();
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new_level");
    }


    private void createDefaultLevel() {

        int[] array = new int[400];
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
        LoadSave.createLevel("new_level", array);
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    @Override
    public void render(Graphics graphics) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                graphics.drawImage(tileManager.getSprite(id), x * 32, y * 32, null);
            }
        }
        bottomBar.draw(graphics);
        drawSelectedTile(graphics);
    }

    private void drawSelectedTile(Graphics graphics) {
        if (selectedTile != null && drawSelect) {
            graphics.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
        drawSelect = true;
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            bottomBar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }
    }

    private void changeTile(int x, int y) {
        if (selectedTile != null) {
            int tileX = x / 32;
            int tileY = y / 32;
            if (lastTileX == tileX && lastTileY == tileY
                    && lastTileId == selectedTile.getId())
                return;
            lastTileX = tileX;
            lastTileY = tileY;
            lastTileId = selectedTile.getId();

            lvl[tileY][tileX] = selectedTile.getId();
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            bottomBar.mouseMoved(x, y);
            drawSelect = false;
        } else {
            drawSelect = true;
            //divide and multiply by size of the tile to make it snap in right position
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640) {
            bottomBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        bottomBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (y >= 640) {
        } else {
            changeTile(x, y);
        }
    }

    public void saveLevel() {

        LoadSave.saveLevel("new_level", lvl);
    }
}
