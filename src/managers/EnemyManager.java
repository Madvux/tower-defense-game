package managers;

import enemies.Enemy;
import scenes.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[] enemyImages;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        addEnemy(3*32,9*32);
        loadEnemyImages();
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImages[0] = atlas.getSubimage(0,32,32,32);
        enemyImages[1] = atlas.getSubimage(32,32,32,32);
        enemyImages[2] = atlas.getSubimage(2*32,32,32,32);
        enemyImages[3] = atlas.getSubimage(3*32,32,32,32);
    }
    public void addEnemy(int x, int y){
        enemies.add(new Enemy(x,y,0,0));

    }

    public void update(){

        for(Enemy e : enemies){
            e.move(0.5f,0);
        }
    }
    public void draw(Graphics g){

        for(Enemy e : enemies){
            drawEnemy(e,g);
        }

    }

    private void drawEnemy(Enemy testEnemy, Graphics g) {
        g.drawImage(enemyImages[0], (int) testEnemy.getX(), (int) testEnemy.getY(),null);
    }
}
