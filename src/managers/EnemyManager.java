package managers;

import enemies.Enemy;
import scenes.Playing;
import utils.LoadSave;

import static utils.Constants.Direction.*;
import static utils.Constants.Tiles.*;


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
        addEnemy(3 * 32, 9 * 32);
        loadEnemyImages();
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImages[0] = atlas.getSubimage(0, 32, 32, 32);
        enemyImages[1] = atlas.getSubimage(32, 32, 32, 32);
        enemyImages[2] = atlas.getSubimage(2 * 32, 32, 32, 32);
        enemyImages[3] = atlas.getSubimage(3 * 32, 32, 32, 32);
    }

    public void addEnemy(int x, int y) {
        enemies.add(new Enemy(x, y, 0, 0));

    }

    public void update() {

        for (Enemy e : enemies) {
            if (isNextTileRoad(e)) {

            }
        }
    }

    public boolean isNextTileRoad(Enemy e) {
        int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir()));
        int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir()));

        if (getTileType(newX, newY) == ROAD_TILE) {
            e.move(speed, e.getLastDir());
        } else if (isAtEnd(e)) {
            //reached end of path
        } else {
            setNewDirectionAndMove(e);
        }
        return false;
    }

    private void setNewDirectionAndMove(Enemy e) {
        int dir = e.getLastDir();
        int xCord = (int) (e.getX()/32);
        int yCord = (int) (e.getY()/32);

        fixEnemyOffsetTile(e,dir,xCord,yCord);

        if (dir == LEFT || dir == RIGHT) {
            int newY = (int) (e.getY() + getSpeedAndHeight(UP));
            if (getTileType((int) e.getX(), newY) == ROAD_TILE) e.move(speed,UP);
            else e.move(speed,DOWN);

        }else{
            int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir()));
            if (getTileType( newX,(int) e.getY()) == ROAD_TILE) e.move(speed,RIGHT);
            else e.move(speed,LEFT);
        }
    }

    private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
    switch (dir){
//        case LEFT -> {
//            if(xCord>0) xCord--;
//        }
//        case UP -> {
//            if(yCord>0) yCord--;
//        }
        case RIGHT -> {
            if(xCord<19) xCord++;
        }
        case DOWN -> {
            if(yCord<19) yCord++;
        }
    }
        e.setPosition(xCord*32,yCord*32);
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

    private void drawEnemy(Enemy testEnemy, Graphics g) {
        g.drawImage(enemyImages[0], (int) testEnemy.getX(), (int) testEnemy.getY(), null);
    }
}
