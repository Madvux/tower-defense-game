package ui;

import main.GameStates;
import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ToolBar extends Bar {
    private MyButton bMenu, bSave;

    private Tile selectedTile;
    private ArrayList<MyButton> tileButtons = new ArrayList<>();
    private Editing editing;

    public ToolBar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);

        this.editing = editing;
        initButtons();
    }

    public void draw(Graphics graphics) {

        //background
        graphics.setColor(new Color(220, 123, 15));
        graphics.fillRect(x, y, width, height);

        //buttons
        drawButtons(graphics);
    }

    private void initButtons() {
        bMenu = new MyButton(2, 642, 100, 30, "Menu");
        bSave = new MyButton(2, 674, 100, 30, "Save");

        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1);
        int i = 0;
        for (Tile tile : editing.getGame().getTileManager().tiles) {
            tileButtons.add(new MyButton(xStart + xOffset * i, yStart, w, h, tile.getName(), i));
            i++;
        }
    }

    private void saveLevel() {
        editing.saveLevel();
    }

    private void drawButtons(Graphics graphics) {
        bMenu.draw(graphics);
        bSave.draw(graphics);

        drawTileButtons(graphics);

    }

    private void drawTileButtons(Graphics graphics) {

        for (MyButton myButton : tileButtons) {

            //sprite
            graphics.drawImage(getButtonImage(myButton.getId()), myButton.x, myButton.y, myButton.width, myButton.height, null);

            ///mouseOver
            if (myButton.isMouseOver()) {
                graphics.setColor(Color.WHITE);
            } else {
                graphics.setColor(Color.BLACK);
            }
            //border
            graphics.drawRect(myButton.x, myButton.y, myButton.width, myButton.height);


            //mousePressed
            if (myButton.isMousePressed()) {

                graphics.drawRect(myButton.x + 1, myButton.y + 1, myButton.width - 2, myButton.height - 2);
                graphics.drawRect(myButton.x + 2, myButton.y + 2, myButton.width - 4, myButton.height - 4);
            }
        }
        drawSelectedTile(graphics);
    }

    private void drawSelectedTile(Graphics graphics) {
        if (selectedTile != null) {
            graphics.drawImage(selectedTile.getSprite(), 550, 650, 50, 50, null);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(550, 650, 50, 50);
        }
    }

    public BufferedImage getButtonImage(int id) {
        return editing.getGame().getTileManager().getSprite(id);
    }



    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.MENU);
        } else if (bSave.getBounds().contains(x, y)) {
            saveLevel();
        } else {
            for (MyButton b : tileButtons) {
                if (b.getBounds().contains(x, y)) {
                    selectedTile = editing.getGame().getTileManager().getTile(b.getId());
                    editing.setSelectedTile(selectedTile);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bSave.setMouseOver(false);
        for (MyButton b : tileButtons) {
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bSave.getBounds().contains(x, y)) {
            bSave.setMouseOver(true);
        } else {
            for (MyButton b : tileButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else if (bSave.getBounds().contains(x, y)) {
            bSave.setMousePressed(true);
        } else {
            for (MyButton b : tileButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bSave.resetBooleans();
        for (MyButton b : tileButtons) {
            b.resetBooleans();
        }
    }

}
