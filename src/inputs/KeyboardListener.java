package inputs;

import main.Game;
import main.GameStates;
import scenes.Menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static main.GameStates.*;

public class KeyboardListener implements KeyListener {

    private Game game;

    public KeyboardListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == EDIT) game.getEditing().keyPressed(e);
        else if(gameState == PLAYING) game.getPlaying().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
