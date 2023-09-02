package scenes;

import main.Game;

import java.awt.image.BufferedImage;

public class GameScene {
    protected Game game;
    public GameScene(Game game) {
        this.game = game;
    }
    protected int ANIMATION_SPEED = 25;

    protected int animationIndex;
    protected int tick;

    protected boolean isAnimation(int spriteID) {
        return game.getTileManager().isSpriteAnimation(spriteID);
    }
    protected void updateTick() {
        tick++;
        if (tick >= ANIMATION_SPEED) {
            tick = 0;
            animationIndex++;
            if (animationIndex >= 4)
                animationIndex = 0;
        }
    }

    protected BufferedImage getSprite(int spriteID) {
        return game.getTileManager().getSprite(spriteID);
    }

    protected BufferedImage getSprite(int spriteID, int animationIndex) {
        return game.getTileManager().getAniSprite(spriteID,animationIndex);
    }

    public Game getGame() {
        return game;
    }
}
