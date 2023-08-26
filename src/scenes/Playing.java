package scenes;

import main.Game;
import managers.TileManager;
import utils.LevelBuild;

import java.awt.*;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private TileManager tileManager;

    public Playing(Game game) {
        super(game);

        lvl = LevelBuild.getLevelBeta();
        tileManager = new TileManager();
    }

    @Override
    public void render(Graphics graphics) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                graphics.drawImage(tileManager.getSprite(id),x*32,y*32,null);
            }
        }
    }
}
