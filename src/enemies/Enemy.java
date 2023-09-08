package enemies;

import utils.Constants;

import java.awt.*;

import static utils.Constants.Directions.*;

public abstract class Enemy {
    private float x, y;
    private Rectangle bounds;
    private int health;
    private int id;
    private int enemyType;
    private int lastDir;


    public Enemy(float x, float y, int id, int enemyType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.enemyType = enemyType;
        bounds = new Rectangle((int) x, (int) y, 32, 32);
        lastDir = -1;
    }

    protected void setStartHealth(){
        health = Constants.Enemies.GetStartHealth(enemyType);
    }

    public void move(float speed, int direction) {
        lastDir = direction;
        switch (direction) {
            case LEFT -> this.x -= speed;
            case UP -> this.y -= speed;
            case RIGHT -> this.x += speed;
            case DOWN -> this.y += speed;
        }
    }
    public void setPosition(int x,int y){
        //this is for fixing position of enemies
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }


    public int getId() {
        return id;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getLastDir() {
        return lastDir;
    }
}
