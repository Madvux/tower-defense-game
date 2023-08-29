package ui;

import main.GameStates;
import objects.Tile;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ActionBar extends Bar {
    private MyButton bMenu;
    private Playing playing;


    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);

        this.playing = playing;
        initButtons();
    }

    private void drawButtons(Graphics graphics) {
        bMenu.draw(graphics);
    }


    private void initButtons() {
        bMenu = new MyButton(2, 642, 100, 30, "Menu");

    }

    public void draw(Graphics graphics) {

        //background
        graphics.setColor(new Color(220, 123, 15));
        graphics.fillRect(x, y, width, height);

        //buttons
        drawButtons(graphics);
    }


    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.MENU);
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
    }

}
