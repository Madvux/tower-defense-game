package managers;

import enemies.*;
import scenes.Playing;
import utils.Constants;
import utils.LoadSave;

import static utils.Constants.Directions.*;
import static utils.Constants.Tiles.*;
import static utils.Constants.Enemies.*;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[] enemyImages;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float speed = 0.5f;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        addEnemy(1 * 32, 14 * 32, ORC);
        addEnemy(3 * 32, 9 * 32, BAT);
        addEnemy(3 * 32, 9 * 32, KNIGHT);
        addEnemy(3 * 32, 9 * 32, WOLF);
        loadEnemyImages();
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();

        for (int i = 0; i < 4; i++) {
            enemyImages[i] = atlas.getSubimage(i * 32, 32, 32, 32);
        }

    }

    public void addEnemy(int x, int y, int enemyType) {
        switch (enemyType) {
            case ORC -> enemies.add(new Orc(x, y, 0));

            case BAT -> enemies.add(new Bat(x, y, 0));

            case KNIGHT -> enemies.add(new Knight(x, y, 0));

            case WOLF -> enemies.add(new Wolf(x, y, 0));

        }

    }

    public void update() {

        for (Enemy e : enemies) {
            updateEnemyMove(e);
        }
    }

    public void updateEnemyMove(Enemy e) {

        if (e.getLastDir() == -1) setNewDirectionAndMove(e);

        int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir()));
        int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir()));

        if (getTileType(newX, newY) == ROAD_TILE) {
            e.move(speed, e.getLastDir());
        } else if (isAtEnd(e)) {
            //reached end of path
        } else {
            setNewDirectionAndMove(e);
        }
    }

    private void setNewDirectionAndMove(Enemy e) {
        int dir = e.getLastDir();
        int xCord = (int) (e.getX() / 32);
        int yCord = (int) (e.getY() / 32);

        fixEnemyOffsetTile(e, dir, xCord, yCord);

        if (dir == LEFT || dir == RIGHT) {
            int newY = (int) (e.getY() + getSpeedAndHeight(UP));
            if (getTileType((int) e.getX(), newY) == ROAD_TILE) e.move(speed, UP);
            else e.move(speed, DOWN);

        } else {
            int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir()));
            if (getTileType(newX, (int) e.getY()) == ROAD_TILE) e.move(speed, RIGHT);
            else e.move(speed, LEFT);
        }
    }

    private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
        switch (dir) {
            case RIGHT -> {
                if (xCord < 19) xCord++;
            }
            case DOWN -> {
                if (yCord < 19) yCord++;
            }
        }
        e.setPosition(xCord * 32, yCord * 32);
    }

    private boolean isAtEnd(Enemy e) {
        return false;
    }

    private int getTileType(int newX, int newY) {
        return playing.getTileType(newX, newY);
    }

    private float getSpeedAndWidth(int direction) {
        if (direction == LEFT) return -speed;
        if (direction == RIGHT) return speed + 32;
        return 0;
    }

    private float getSpeedAndHeight(int direction) {
        if (direction == UP) return -speed;
        if (direction == DOWN) return speed + 32;
        return 0;
    }

    public void draw(Graphics g) {

        for (Enemy e : enemies) {
            drawEnemy(e, g);
        }

    }

    private void drawEnemy(Enemy e, Graphics g) {
        g.drawImage(enemyImages[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
    }
}
