package main;

import java.awt.*;

public class Render {

    private Game game;

    public Render(Game game) {
        this.game = game;

    }
    public void render(Graphics graphics){

        switch (GameStates.gameState){
            case MENU -> game.getMenu().render(graphics);
            case PLAYING -> game.getPlaying().render(graphics);
            case SETTINGS -> game.getSettings().render(graphics);
        }

    }



}
