package com.bituser.yabacc.droplets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class DropletGame extends GameScene {
    private int _screenWidth, _screenHeight;
    private final com.bituser.yabacc.droplets.Table _table;

    public DropletGame(int screenWidth, int screenHeight, BitmapFont font) {
        _screenWidth = screenWidth;
        _screenHeight = screenHeight;

        com.bituser.yabacc.droplets.DropletDeck deck = new com.bituser.yabacc.droplets.DropletDeck(screenWidth - 40, screenHeight / 2, font);
        Bag bag = new Bag(screenWidth - 120, screenHeight / 2, font);
        Array<Player> players = new Array<Player>();
        players.add(new com.bituser.yabacc.droplets.HumanPlayer(screenWidth / 2, 50, Color.FOREST));
        players.add(new ComputerPlayer(screenWidth / 2, screenHeight - 50, Color.MAROON));
        _table = new com.bituser.yabacc.droplets.Table(screenWidth, screenHeight, bag, deck, players, font);
    }

    // TODO method stub
    public boolean isGameOver () { return false; }

    public Player getWinner () { return _table.getWinner(); }

    @Override
    public void update (float deltaTime) {
        _table.update(deltaTime);
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        _table.render(shapeRenderer);
    }

    @Override
    public void render (SpriteBatch batch) {
        _table.render(batch);
    }

    @Override
    public void touchDown(float x, float y, int pointer, int button) {
        for (Player player : _table.getPlayers()) {
            if (player instanceof com.bituser.yabacc.droplets.HumanPlayer) {
                _table.touchDown(x, y, pointer, button, (com.bituser.yabacc.droplets.HumanPlayer)player);
            }
        }
    }

    @Override
    public void touchUp(float x, float y, int pointer, int button) {
        for (Player player : _table.getPlayers()) {
            if (player instanceof com.bituser.yabacc.droplets.HumanPlayer) {
                _table.touchUp(x, y, pointer, button, (com.bituser.yabacc.droplets.HumanPlayer)player);
            }
        }
    }

    @Override
    public void touchDragged(int x, int y, int pointer) {
        for (Player player : _table.getPlayers()) {
            if (player instanceof com.bituser.yabacc.droplets.HumanPlayer) {
                _table.touchDragged(x, y, pointer, (com.bituser.yabacc.droplets.HumanPlayer)player);
            }
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        for (Player player : _table.getPlayers()) {
            if (player instanceof com.bituser.yabacc.droplets.HumanPlayer) {
                _table.mouseMoved(x, y, (com.bituser.yabacc.droplets.HumanPlayer)player);
            }
        }
    }
}
