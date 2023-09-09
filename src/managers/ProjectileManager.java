package managers;

import enemies.Enemy;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.Towers.*;
import static utils.Constants.Projectiles.*;

public class ProjectileManager {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] projectileImages;
    private int projectileID = 0;

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImages();
    }

    private void importImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        projectileImages = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            projectileImages[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
        }
    }

    public void newProjectile(Tower t, Enemy e) {
        int type = getProjectileType(t);

        int xDist = (int) Math.abs(t.getX() - e.getX());
        int yDist = (int) Math.abs(t.getY() - e.getY());
        int totalDistance = xDist + yDist;

        float xPercent = (float) xDist / totalDistance;


        float xSpeed = xPercent * Constants.Projectiles.GetSpeed(type);
        float ySpeed = Constants.Projectiles.GetSpeed(type) - xSpeed;

        if (t.getX() > e.getX()) xSpeed *= -1;
        if (t.getY() > e.getY()) ySpeed *= -1;

        projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), projectileID++, type));
    }

    private int getProjectileType(Tower t) {
        switch (t.getTowerType()) {
            case CANNON -> {
                return BOMB;
            }
            case ARCHER -> {
                return ARROW;
            }
            case WIZARD -> {
                return CHAINS;
            }
        }
        return 0;
    }

    public void update() {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.move();
                if (isProjectileHittingEnemy(p)) {
                    p.setActive(false);
                }
            }
        }
    }

    private boolean isProjectileHittingEnemy(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.getBounds().contains(p.getPos())) {
                e.hurt(p.getDmg());
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                g.drawImage(projectileImages[p.getProjectileType()], (int) p.getPos().x, (int) p.getPos().y, null);
            }
        }
    }
}
