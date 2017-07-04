package com.bituser.yabacc.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * This class is inherited by all games for easy swapping
**/
abstract public class GameScene {
    // TODO: Replace this with a dynamic solution for screen resize support
    private final int _screenHeight;
    private final int _screenWidth;
    private final BitmapFont _font;

    public GameScene(int screenWidth, int screenHeight, BitmapFont cyrillicFont) {
        _screenWidth = screenWidth;
        _screenHeight = screenHeight;
        _font = cyrillicFont;
    }
    // Required methods
    abstract public void update (float deltaTime);
    abstract public void render (SpriteBatch batch);
    abstract public boolean isGameOver ();

    // Useful but not requried
    public void render (ShapeRenderer ShapeRenderer) { }
    public void touchDown(float x, float y, int pointer, int button) { }
    public void touchUp(float x, float y, int pointer, int button) { }
    public void touchDragged(int x, int y, int pointer) { }
    public void mouseMoved(int x, int y) { }
    // TODO: Add more methods for input and such
}
