package scenes;

import main.Game;
import objects.Tile;
import ui.ActionBar;
import ui.ToolBar;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Editing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private ToolBar toolBar;
    private Tile selectedTile;
    private int mouseX, mouseY;
    private int lastTileX, lastTileY, lastTileId;
    private boolean drawSelect = false;

    public Editing(Game game) {
        super(game);
        loadDefaultLevel();
        toolBar = new ToolBar(0, 640, 640, 100, this);

    }

    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new_level");
    }

    @Override
    public void render(Graphics graphics) {

        drawLevel(graphics);
        toolBar.draw(graphics);
        drawSelectedTile(graphics);

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

    private void drawSelectedTile(Graphics graphics) {
        if (selectedTile != null && drawSelect) {
            graphics.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    public void saveLevel() {

        LoadSave.saveLevel("new_level", lvl);
        game.getPlaying().setLevel(lvl);
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
        drawSelect = true;
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
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            toolBar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            toolBar.mouseMoved(x, y);
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

    }

    @Override
    public void mouseReleased(int x, int y) {
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (y >= 640) {
        } else {
            changeTile(x, y);
        }
    }
}
