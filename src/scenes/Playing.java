package scenes;

import enemies.Enemy;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;
import objects.Tower;
import ui.ActionBar;
import utils.Constants;
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
    private ProjectileManager projectileManager;
    private WaveManager waveManager;

    private PathPoint start, end;
    private Tower selectedTower;
    private int goldTick;
    private boolean gamePaused;

    public Playing(Game game) {
        super(game);
        loadDefaultLevel();
        actionBar = new ActionBar(0, 640, 640, 160, this);
        enemyManager = new EnemyManager(this, start, end);
        towerManager = new TowerManager(this);
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
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
        projectileManager.draw(graphics);

        drawSelectedTower(graphics);
        drawHighlight(graphics);
    }

    private void drawHighlight(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(mouseX, mouseY, 32, 32);
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }


    private void drawSelectedTower(Graphics graphics) {
        if (selectedTower == null) return;
        graphics.drawImage(towerManager.getTowerImages()[selectedTower.getTowerType()], mouseX, mouseY, null);
    }

    public void update() {
        if (!gamePaused) {
            updateTick();
            waveManager.update();

            // Gold tick
            goldTick++;
            if (goldTick % (60 * 3) == 0)
                actionBar.addGold(1);

            if (isAllEnemiesDead()) {
                if (isThereMoreWaves()) {
                    waveManager.startWaveTimer();
                    if (isWaveTimerOver()) {
                        waveManager.increaseWaveIndex();
                        enemyManager.getEnemies().clear();
                        waveManager.resetEnemyIndex();
                    }
                }
            }

            if (isTimeForNewEnemy()) {
                spawnEnemy();
            }
            enemyManager.update();
            towerManager.update();
            projectileManager.update();
        }
    }

    private boolean isWaveTimerOver() {
        return waveManager.isWaveTimerOver();
    }

    private boolean isThereMoreWaves() {
        return waveManager.isThereMoreWaves();
    }

    private boolean isAllEnemiesDead() {
        if (waveManager.isThereMoreEnemiesInWave()) {
            return false;
        }

        for (Enemy e : enemyManager.getEnemies()) {
            if (e.isAlive()) {
                return false;
            }
        }

        return true;
    }

    private void spawnEnemy() {
        enemyManager.spawnEnemy(waveManager.getNextEnemy());
    }

    private boolean isTimeForNewEnemy() {
        if (waveManager.isTimeForNewEnemy()) {
            if (waveManager.isThereMoreEnemiesInWave()) {
                return true;
            }
        }
        return false;
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
                        removeGold(selectedTower.getTowerType());
                        selectedTower = null;
                    }
                }
            } else {
                Tower t = getTowerAt(mouseX, mouseY);
                actionBar.displayTower(t);
            }
        }
    }

    private void removeGold(int towerType) {
        actionBar.payForTower(towerType);
    }


    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x, y);
    }

    private boolean isTileGrass(int x, int y) {
        int id = lvl[y / 32][x / 32];
        int tileType = game.getTileManager().getTile(id).getTileType();

        return tileType == GRASS_TILE;
    }

    public void shootEnemy(Tower t, Enemy e) {
        projectileManager.newProjectile(t, e);
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
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            setSelectedTower(null);
        }
    }

    public void rewardPlayer(int enemyType) {
        actionBar.addGold(Constants.Enemies.GetReward(enemyType));
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public void upgradeTower(Tower displayedTower) {
        towerManager.upgradeTower(displayedTower);

    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public void removeTower(Tower displayedTower) {
        towerManager.removeTower(displayedTower);
    }

    public void removeOneLife() {
        actionBar.removeOneLife();
    }

    public void resetEverything() {

        actionBar.resetEverything();

        enemyManager.reset();
        towerManager.reset();
        projectileManager.reset();
        waveManager.reset();

        mouseX = 0;
        mouseY = 0;

        selectedTower = null;
        goldTick = 0;
        gamePaused = false;

    }
}
