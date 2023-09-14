package enemies;

import managers.EnemyManager;
import objects.Tower;
import utils.Constants;

import java.awt.*;

import static utils.Constants.Directions.*;

public abstract class Enemy {

    protected EnemyManager enemyManager;
    protected float x, y;
    protected Rectangle bounds;
    protected int health, maxHealth;
    protected int id;
    protected int enemyType;
    protected int lastDir;
    protected boolean alive = true;
    protected int slowTickLimit =120;
    protected int slowTick = slowTickLimit;


    public Enemy(float x, float y, int id, int enemyType, EnemyManager enemyManager) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.enemyType = enemyType;
        this.enemyManager = enemyManager;
        bounds = new Rectangle((int) x, (int) y, 32, 32);
        lastDir = -1;
        setStartHealth();
    }

    private void setStartHealth() {
        health = Constants.Enemies.GetStartHealth(enemyType);
        maxHealth = health;
    }

    public float getHealthBarFloat() {
        return health / (float) maxHealth;
    }

    public void move(float speed, int direction) {
        lastDir = direction;

        if(isSlowed()){
            slowTick++;
            speed *= 0.5f;
        }

        switch (direction) {
            case LEFT -> this.x -= speed;
            case UP -> this.y -= speed;
            case RIGHT -> this.x += speed;
            case DOWN -> this.y += speed;
        }
        updateHitbox();
    }

    private void updateHitbox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    public void setPosition(int x, int y) {
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

    public void hurt(int dmg) {
        this.health -= dmg;
        if (health <= 0) {
            alive = false;
            enemyManager.rewardPlayer(enemyType);
        }
    }
    public void slow() {
        slowTick=0;

    }
    public void kill() {
        //kills enemy, when reaches the end;
        alive = false;
        health =0;

    }
    public boolean isAlive() {
        return alive;
    }
    public boolean isSlowed() {
        return slowTick < slowTickLimit;
    }

}
