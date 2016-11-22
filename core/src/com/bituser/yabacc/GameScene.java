package com.bituser.yabacc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * This class is inherited by all games for easy swapping
**/
abstract class GameScene {
    // Required methods
    abstract public void update (float deltaTime);
    abstract public void render (SpriteBatch batch);

    // Useful but not requried
    void render (ShapeRenderer ShapeRenderer) { }
    void touchDown(float x, float y, int pointer, int button) { }
    void touchUp(float x, float y, int pointer, int button) { }
    void touchDragged(int x, int y, int pointer) { }
    void mouseMoved(int x, int y) { }
    // TODO: Add more methods for input and such
}
