package com.bituser.yabacc.droplets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * This class is inherited by all games for easy swapping
**/
abstract public class GameScene {
    // Required methods
    abstract public void update (float deltaTime);
    abstract public void render (SpriteBatch batch);

    // Useful but not requried
    public void render (ShapeRenderer ShapeRenderer) { }
    public void touchDown(float x, float y, int pointer, int button) { }
    public void touchUp(float x, float y, int pointer, int button) { }
    public void touchDragged(int x, int y, int pointer) { }
    public void mouseMoved(int x, int y) { }
    // TODO: Add more methods for input and such
}
