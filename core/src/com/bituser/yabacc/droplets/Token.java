package com.bituser.yabacc.droplets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

class Token extends com.bituser.yabacc.util.Entity {

    private Token(float x, float y, Color color) {
        super(x, y, 15, 15);
        _color = color;
    }

    Token(Vector2 position, Color color) {
        this(position.x, position.y, color);
    }

    @Override
    public void update (float deltaTime) {
        _rect.setX(_position.x - (_width / 2));
        _rect.setY(_position.y - (_height / 2));
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        _color.a = 1f;
        shapeRenderer.setColor(_color);
        shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
    }

    @Override
    public void render (SpriteBatch batch) {

    }
}
