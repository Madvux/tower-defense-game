package ui;

import main.GameStates;
import objects.Tile;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BottomBar {
    private int x, y, width, height;
    private MyButton bMenu;
    private Playing playing;

    private ArrayList<MyButton> tileButtons = new ArrayList<>();

    public BottomBar(int x, int y, int width, int height, Playing playing) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.playing = playing;
        initButtons();
    }

    private void drawButtons(Graphics graphics) {
        bMenu.draw(graphics);

        drawTileButtons(graphics);

    }

    private void drawTileButtons(Graphics graphics) {
        for (MyButton myButton : tileButtons) {
            graphics.drawImage(getButtonImage(myButton.getId()), myButton.x, myButton.y, myButton.width, myButton.height, null);
        }
    }

    public BufferedImage getButtonImage(int id) {
        return playing.getTileManager().getSprite(id);
    }

    private void initButtons() {
        bMenu = new MyButton(2, 642, 100, 30, "Menu");

        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1);
        int i = 0;
        for (Tile tile : playing.getTileManager().tiles) {
            tileButtons.add(new MyButton(xStart + xOffset * i, yStart, w, h, tile.getName(), i));
            i++;
        }
    }

    public void draw(Graphics graphics) {

        //background
        graphics.setColor(new Color(220, 123, 15));
        graphics.fillRect(x, y, width, height);

        //buttons
        drawButtons(graphics);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) GameStates.setGameState(GameStates.MENU);
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
    }

}
