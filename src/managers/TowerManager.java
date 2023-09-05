package managers;

import objects.Tower;
import scenes.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Towers.*;

public class TowerManager {
    private Playing playing;
    private BufferedImage[] towerImages;
    private Tower tower;

    public TowerManager(Playing playing) {
        this.playing = playing;

        loadTowerImages();
        initTowers();
    }

    private void initTowers() {
        tower = new Tower(3 * 23, 6 * 32, 0, ARCHER);
    }

    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImages = new BufferedImage[3];

        for (int i = 0; i < 3; i++) {
            towerImages[i] = atlas.getSubimage((i + 4) * 32, 32, 32, 32);
        }

    }

    public void draw(Graphics g) {
        g.drawImage(towerImages[ARCHER], tower.getX(), tower.getY(), null);
    }

    public void update() {

    }
}
