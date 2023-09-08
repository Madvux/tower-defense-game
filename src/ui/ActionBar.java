package ui;

import main.GameStates;
import objects.Tower;
import scenes.Playing;
import utils.Constants;

import java.awt.*;

public class ActionBar extends Bar {
    private MyButton bMenu;
    private Playing playing;

    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayTower;

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
        drawDisplayTower(graphics);
    }

    private void drawDisplayTower(Graphics g) {
        if(displayTower != null){
            g.setColor(Color.gray);
            g.fillRect(410,645,220,85);
            g.setColor(Color.BLACK);
            g.drawRect(410,645,220,85);
            g.drawRect(420,650,50,50);
            g.drawImage(playing.getTowerManager().getTowerImages()[displayTower.getTowerType()], 420,650,50,50,null);
            g.setFont(new Font("LucidaSans",Font.BOLD,15));
            g.drawString("" + Constants.Towers.getName(displayTower.getTowerType()),490,660);
            g.drawString("ID: " + displayTower.getId(),490,675);
            
            drawDisplayedTowerBorder(g);
            drawDisplayTowerRange(g);
        }
    }

    private void drawDisplayTowerRange(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawOval(displayTower.getX()+16-(int)displayTower.getRange()/2,  displayTower.getY()+16-(int)displayTower.getRange()/2, (int)displayTower.getRange(),(int)displayTower.getRange());
    }

    private void drawDisplayedTowerBorder(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(displayTower.getX(), displayTower.getY(), 32,32);
    }


    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.MENU);
        } else {
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    selectedTower = new Tower(0, 0, -1, b.getId());
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
        for (MyButton b : towerButtons) {
            b.resetBooleans();
        }
    }

    public void displayTower(Tower t) {
        displayTower = t;
    }
}
