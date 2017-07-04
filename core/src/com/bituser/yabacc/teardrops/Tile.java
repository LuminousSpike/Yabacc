package com.bituser.yabacc.teardrops;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bituser.yabacc.util.Entity;

/**
 * Created by nathan on 4/07/17.
 */

public class Tile extends Entity {
    private static final float WIDTH = 50, HEIGHT = 50;

    public Tile (Vector2 position, float width, float height) {
        _position = position;
        _width = width;
        _height = height;
    }

    public Tile (float x, float y) {
        this(new Vector2(x, y), WIDTH, HEIGHT);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(_color);
        shapeRenderer.rect(_position.x, _position.y, _width, _height);
    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
