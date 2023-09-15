package managers;

import enemies.Enemy;
import objects.Tower;
import scenes.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.Towers.*;

public class TowerManager {

    private Playing playing;
    private BufferedImage[] towerImages;
    private ArrayList<Tower> towers = new ArrayList<>();
    private int towerAmount = 0;

    public TowerManager(Playing playing) {
        this.playing = playing;

        loadTowerImages();
    }


    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImages = new BufferedImage[3];

        for (int i = 0; i < 3; i++) {
            towerImages[i] = atlas.getSubimage((i + 4) * 32, 32, 32, 32);
        }

    }

    public void draw(Graphics g) {
        for (Tower t : towers) {
            g.drawImage(towerImages[t.getTowerType()], t.getX(), t.getY(), null);
        }
    }

    public void update() {
        for (Tower t : towers) {
            t.update();
            attackEnemyIfClose(t);
        }
    }

    private void attackEnemyIfClose(Tower t) {
            for (Enemy e : playing.getEnemyManager().getEnemies()) {
                if (e.isAlive()) {
                    if (isEnemyInRange(t, e)) {
                        if(t.isCooldownOver()) {
                            playing.shootEnemy(t, e);
                            t.resetCooldown();
                        }
                    }
                }
            }
        }


    private boolean isEnemyInRange(Tower t, Enemy e) {
        int range = LoadSave.GetHypoDistance(t.getX(), t.getY(), e.getX(), e.getY());
        return range < t.getRange();
    }

    public BufferedImage[] getTowerImages() {
        return towerImages;
    }

    public void addTower(Tower selectedTower, int xPos, int yPos) {
        towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
    }

    public Tower getTowerAt(int x, int y) {
        for (Tower t : towers) {
            if (t.getX() == x && t.getY() == y) return t;
        }

        return null;
    }

    public void removeTower(Tower displayedTower) {
        for (int i = 0; i < towers.size(); i++)
            if (towers.get(i).getId() == displayedTower.getId())
                towers.remove(i);
    }

    public void upgradeTower(Tower displayedTower) {
        for(Tower t : towers)
            if(t.getId() == displayedTower.getId())
                t.upgradeTower();
    }
}
