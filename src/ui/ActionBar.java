package ui;

import main.GameStates;
import objects.Tower;
import scenes.Playing;
import utils.Constants;

import java.awt.*;
import java.text.DecimalFormat;

public class ActionBar extends Bar {

    private Playing playing;

    private MyButton bMenu, bPause;
    private MyButton[] towerButtons;
    private MyButton sellTower, upgradeTower;
    private Tower selectedTower;
    private Tower displayTower;
    private int gold = 100;
    private boolean showTowerCost;
    private int towerCostType;

    private DecimalFormat formatter;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");
        initButtons();
    }

    private void drawButtons(Graphics graphics) {
        bMenu.draw(graphics);
        bPause.draw(graphics);
        for (MyButton b : towerButtons) {
            graphics.setColor(Color.gray);
            graphics.fillRect(b.x, b.y, b.width, b.height);
            graphics.drawImage(playing.getTowerManager().getTowerImages()[b.getId()], b.x, b.y, b.width, b.height, null);
            drawButtonFeedback(graphics, b);
        }

    }


    private void initButtons() {
        bMenu = new MyButton(2, 642, 100, 30, "Menu");
        bPause = new MyButton(2, 682, 100, 30, "Pause");
        towerButtons = new MyButton[3];

        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);

        for (int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton(xStart + xOffset * i, yStart, w, h, "", i);
        }
        sellTower = new MyButton(420, 702, 80, 25, "Sell");
        upgradeTower = new MyButton(545, 702, 80, 25, "Upgrade");
    }

    public void draw(Graphics graphics) {

        graphics.setColor(new Color(220, 123, 15));
        graphics.fillRect(x, y, width, height);

        drawButtons(graphics);
        drawDisplayTower(graphics);

        drawWaveInfo(graphics);
        drawGoldAmount(graphics);

        if (showTowerCost)
            drawTowerCost(graphics);

        if(playing.isGamePaused()){
            graphics.setColor(Color.BLACK);
            graphics.drawString("Game is Paused!",110,790);
        }
    }

    private void drawTowerCost(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(280, 650, 120, 50);
        g.setColor(Color.black);
        g.drawRect(280, 650, 120, 50);

        g.drawString("" + getTowerCostName(), 285, 670);
        g.drawString("Cost: " + getTowerCostCost() + "g", 285, 695);

        if (isTowerCostMoreThanCurrentGold()) {
            g.setColor(Color.RED);
            g.drawString("Can't Afford", 270, 725);

        }

    }

    private boolean isTowerCostMoreThanCurrentGold() {
        return getTowerCostCost() > gold;
    }

    private String getTowerCostName() {
        return utils.Constants.Towers.GetName(towerCostType);
    }

    private int getTowerCostCost() {
        return utils.Constants.Towers.GetTowerCost(towerCostType);
    }

    private void drawGoldAmount(Graphics g) {
        g.drawString("Gold: " + gold + "g", 110, 725);

    }


    private void drawWaveInfo(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("LucidaSans", Font.BOLD, 20));

        drawWaveTimerInfo(g);
        drawEnemiesLeftInfo(g);
        drawWavesLeftInfo(g);
    }

    private void drawWavesLeftInfo(Graphics g) {
        int current = playing.getWaveManager().getWaveIndex();
        int size = playing.getWaveManager().getWaves().size();
        g.drawString("Wave " + (current + 1) + " / " + size, 425, 770);
    }

    private void drawEnemiesLeftInfo(Graphics g) {
        int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
        g.drawString("Enemies Left: " + remaining, 425, 790);
    }

    private void drawWaveTimerInfo(Graphics g) {
        if (playing.getWaveManager().isWaveTimerStarted()) {
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formattedText = formatter.format(timeLeft);
            g.drawString("Time Left: " + formattedText, 425, 750);
        }
    }


    private void drawDisplayTower(Graphics g) {
        if (displayTower != null) {

            g.setColor(Color.gray);
            g.fillRect(410, 645, 220, 85);
            g.setColor(Color.BLACK);
            g.drawRect(410, 645, 220, 85);
            g.drawRect(420, 650, 50, 50);
            g.drawImage(playing.getTowerManager().getTowerImages()[displayTower.getTowerType()], 420, 650, 50, 50, null);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString("" + Constants.Towers.GetName(displayTower.getTowerType()), 480, 660);
            g.drawString("ID: " + displayTower.getId(), 480, 675);
            g.drawString("Tier: " + displayTower.getTier(), 560, 660);

            drawDisplayedTowerBorder(g);
            drawDisplayTowerRange(g);

            // Sell button
            sellTower.draw(g);
            drawButtonFeedback(g, sellTower);

            // Upgrade Button
            if (displayTower.getTier() < 3 && gold >= getUpgradeAmount(displayTower)) {
                upgradeTower.draw(g);
                drawButtonFeedback(g, upgradeTower);
            }

            if (sellTower.isMouseOver()) {
                g.setColor(Color.red);
                g.drawString("Sell for: " + getSellAmount(displayTower) + "g", 480, 695);
            } else if (upgradeTower.isMouseOver() && gold >= getUpgradeAmount(displayTower)) {
                g.setColor(Color.blue);
                g.drawString("Upgrade for: " + getUpgradeAmount(displayTower) + "g", 480, 695);
            }
        }
    }

    private int getUpgradeAmount(Tower displayedTower) {
        return (int) (Constants.Towers.GetTowerCost(displayedTower.getTowerType()) * 0.3f);
    }

    private int getSellAmount(Tower displayedTower) {
        int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
        upgradeCost *= 0.5f;

        return Constants.Towers.GetTowerCost(displayedTower.getTowerType()) / 2 + upgradeCost;
    }

    private void drawDisplayTowerRange(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawOval(displayTower.getX() + 16 - (int) displayTower.getRange(), displayTower.getY() + 16 - (int) displayTower.getRange(), (int) displayTower.getRange() * 2, (int) displayTower.getRange() * 2);
    }

    private void drawDisplayedTowerBorder(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(displayTower.getX(), displayTower.getY(), 32, 32);
    }

    private void sellTowerClicked() {
        playing.removeTower(displayTower);
        gold += Constants.Towers.GetTowerCost(displayTower.getTowerType()) / 2;

        int upgradeCost = (displayTower.getTier() - 1) * getUpgradeAmount(displayTower);
        upgradeCost *= 0.5f;
        gold += upgradeCost;

        displayTower = null;

    }

    private void upgradeTowerClicked() {
        playing.upgradeTower(displayTower);
        gold -= getUpgradeAmount(displayTower);

    }
    private void togglePause() {
        playing.setGamePaused(!playing.isGamePaused());

        if (playing.isGamePaused()) {
            bPause.setText("Resume");
        } else {
            bPause.setText("Pause");
        }

    }
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.MENU);
        } if (bPause.getBounds().contains(x, y)) {
            togglePause();
        } else {
            if (displayTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTowerClicked();

                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayTower.getTier() < 3 && gold >= getUpgradeAmount(displayTower)) {
                    upgradeTowerClicked();
                    return;
                }
            }

            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    if (!isGoldEnoughForTower(b.getId())) return;

                    selectedTower = new Tower(0, 0, -1, b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }



    private boolean isGoldEnoughForTower(int towerType) {

        return gold >= utils.Constants.Towers.GetTowerCost(towerType);
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        showTowerCost = false;
        sellTower.setMouseOver(false);
        upgradeTower.setMouseOver(false);
        for (MyButton b : towerButtons) {
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bPause.getBounds().contains(x, y)) {
            bPause.setMouseOver(true);
        } else {
            if (displayTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTower.setMouseOver(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayTower.getTier() < 3) {
                    upgradeTower.setMouseOver(true);
                    return;
                }
            }

            for (MyButton b : towerButtons)
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    showTowerCost = true;
                    towerCostType = b.getId();
                    return;
                }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else if (bPause.getBounds().contains(x, y)) {
            bPause.setMousePressed(true);
        } else {
            if (displayTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTower.setMousePressed(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayTower.getTier() < 3) {
                    upgradeTower.setMousePressed(true);
                    return;
                }
            }

            for (MyButton b : towerButtons)
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bPause.resetBooleans();
        sellTower.resetBooleans();
        upgradeTower.resetBooleans();
        for (MyButton b : towerButtons) {
            b.resetBooleans();
        }
    }

    public void payForTower(int towerType) {
        this.gold -= utils.Constants.Towers.GetTowerCost(towerType);

    }

    public void addGold(int getReward) {
        this.gold += getReward;

    }

    public void displayTower(Tower t) {
        displayTower = t;
    }
}
