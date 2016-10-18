package com.bituser.yabacc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

class DropletGame {
    private int _screenWidth, _screenHeight;
    private Table _table;

    DropletGame(int screenWidth, int screenHeight, BitmapFont font) {
        _screenWidth = screenWidth;
        _screenHeight = screenHeight;

        DropletDeck deck = new DropletDeck(screenWidth - 40, screenHeight / 2, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.GRAY, font);
        Bag bag = new Bag(screenWidth - 120, screenHeight / 2, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.GRAY, font);
        Array<Player> players = new Array<Player>();
        players.add(new HumanPlayer((int)(screenWidth / 2), screenHeight - 50, Color.FOREST));
        players.add(new HumanPlayer((int)(screenWidth / 2), 50, Color.MAROON));
        _table = new Table(screenWidth, screenHeight, bag, deck, players, font);
    }

    public Player getWinner () { return _table.getWinner(); }

    public void update (float deltaTime) {
        _table.update(deltaTime);
    }

    public void render (ShapeRenderer shapeRenderer) {
        _table.render(shapeRenderer);
    }

    public void render (SpriteBatch batch) {
        _table.render(batch);
    }

    void touchDown(float x, float y, int pointer, int button) {
        for (Player player : _table.getPlayers()) {
            if (player instanceof HumanPlayer) {
                _table.touchDown(x, y, pointer, button, (HumanPlayer)player);
            }
        }
    }

    void touchUp(float x, float y, int pointer, int button) {
        for (Player player : _table.getPlayers()) {
            if (player instanceof HumanPlayer) {
                _table.touchUp(x, y, pointer, button, (HumanPlayer)player);
            }
        }
    }

    void touchDragged(int x, int y, int pointer) {
        for (Player player : _table.getPlayers()) {
            if (player instanceof HumanPlayer) {
                _table.touchDragged(x, y, pointer, (HumanPlayer)player);
            }
        }
    }

    void mouseMoved(int x, int y) {
        for (Player player : _table.getPlayers()) {
            if (player instanceof HumanPlayer) {
                _table.mouseMoved(x, y, (HumanPlayer)player);
            }
        }
    }
}
