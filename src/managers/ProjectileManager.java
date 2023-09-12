package managers;

import enemies.Enemy;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.Towers.*;
import static utils.Constants.Projectiles.*;

public class ProjectileManager {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private BufferedImage[] projectileImages, explosionImages;
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
        importExplosion(atlas);
    }

    private void importExplosion(BufferedImage atlas) {
        explosionImages = new BufferedImage[7];
        for (int i = 0; i < 7; i++) {
            explosionImages[i] = atlas.getSubimage(i * 32, 32 * 2, 32, 32);
        }
    }

    public void newProjectile(Tower t, Enemy e) {
        int type = getProjectileType(t);

        int xDist = (int) (t.getX() - e.getX());
        int yDist = (int) (t.getY() - e.getY());
        int totalDistance = Math.abs(xDist) + Math.abs(yDist);

        float xPercent = (float) Math.abs(xDist) / totalDistance;


        float xSpeed = xPercent * Constants.Projectiles.GetSpeed(type);
        float ySpeed = Constants.Projectiles.GetSpeed(type) - xSpeed;

        if (t.getX() > e.getX()) xSpeed *= -1;
        if (t.getY() > e.getY()) ySpeed *= -1;

        float rotate = 0;

        if (type == ARROW) {
            float arcValue = (float) Math.atan(yDist / (float) xDist);
            rotate = (float) Math.toDegrees(arcValue);

            if (xDist < 0) {
                rotate += 180;
            }
        }

        projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16,
                xSpeed, ySpeed, t.getDmg(), rotate, projectileID++, type));
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
                    if (p.getProjectileType() == BOMB) {
                        explosions.add(new Explosion(p.getPos()));
                        explodeOnEnemies(p);
                    }
                }
            }
        }
        for (Explosion e : explosions) {
            if(e.getIndex() < explosionImages.length){
                e.update();
            }
        }
    }

    private void explodeOnEnemies(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive()) {
                float radius = 40.0f;
                float xDist = Math.abs(p.getPos().x - e.getX());
                float yDist = Math.abs(p.getPos().y - e.getY());

                float realDistance = (float) Math.hypot(xDist, yDist);
                if (realDistance <= radius) {
                    e.hurt(p.getDmg());
                }
            }
        }
    }

    private boolean isProjectileHittingEnemy(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if(e.isAlive()){
                if (e.getBounds().contains(p.getPos())) {
                    e.hurt(p.getDmg());
                    if(p.getProjectileType()==CHAINS){
                        e.slow();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        for (Projectile p : projectiles) {
            if (p.isActive()) {
                if (p.getProjectileType() == ARROW) {
                    g2d.translate(p.getPos().x, p.getPos().y);
                    g2d.rotate(Math.toRadians(p.getRotation()));

                    g2d.drawImage(projectileImages[p.getProjectileType()], -16, -16, null);

                    g2d.rotate(-Math.toRadians(p.getRotation()));
                    g2d.translate(-p.getPos().x, -p.getPos().y);
                } else {
                    g2d.drawImage(projectileImages[p.getProjectileType()], (int) p.getPos().x - 16, (int) p.getPos().y - 16, null);
                }
            }
        }
        drawExplosions(g2d);

    }

    private void drawExplosions(Graphics2D g2d) {
        for (Explosion e : explosions) {
            if (e.getIndex() < explosionImages.length) {
                g2d.drawImage(explosionImages[e.getIndex()], (int) (e.getPosition().x - 16), (int) (e.getPosition().y - 16), null);
            }
        }
    }

    public class Explosion {
        private Point2D.Float position;
        private int tick = 0, index = 0;

        public Explosion(Point2D.Float position) {
            this.position = position;
        }

        public void update() {
            tick++;
            if (tick >= 12) {
                tick = 0;
                index++;
            }

        }

        public int getIndex() {
            return index;
        }

        public Point2D.Float getPosition() {
            return position;
        }
    }
}
