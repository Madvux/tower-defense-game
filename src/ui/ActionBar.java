package ui;

import main.GameStates;
import objects.Tile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ActionBar extends Bar {
    private MyButton bMenu;
    private Playing playing;

    private MyButton[] towerButtons;
    private Tower selectedTower;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);

        this.playing = playing;
        initButtons();
    }

    private void drawButtons(Graphics graphics) {
        bMenu.draw(graphics);
        for (MyButton b : towerButtons) {
            graphics.setColor(Color.gray);
            graphics.fillRect(b.x, b.y, b.width, b.height);
            graphics.drawImage(playing.getTowerManager().getTowerImages()[b.getId()], b.x, b.y, b.width, b.height, null);
            drawButtonFeedback(graphics, b);
        }

    }


    private void initButtons() {
        bMenu = new MyButton(2, 642, 100, 30, "Menu");
        towerButtons = new MyButton[3];

        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);

        for (int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton(xStart + xOffset * i, yStart, w, h, "", i);
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
        if (bMenu.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.MENU);
        }else{
            for (MyButton b: towerButtons){
                if (b.getBounds().contains(x,y)){
                    selectedTower = new Tower(0,0,-1,b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        for (MyButton b : towerButtons) {
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else {
            for (MyButton b : towerButtons)
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    return;
                }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else {
            for (MyButton b : towerButtons)
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        for(MyButton b: towerButtons){
            b.resetBooleans();
        }
    }

}
