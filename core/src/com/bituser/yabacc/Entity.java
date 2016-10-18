package com.bituser.yabacc;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

abstract class Entity {
    Vector2 _position;
    Rectangle _rect;
    Color _color = Color.BLACK;
    float _width, _height;
    static final Random _rand = new Random();

    Entity() {
        _position = new Vector2(0, 0);
    }

    Entity(Vector2 position, float width, float height) {
        _position = position;
        _width = width;
        _height = height;
        _rect = new Rectangle(_position.x, _position.y, _width, _height);
    }

    Entity(float x, float y, float width, float height) {
        this(new Vector2(x, y), width, height);
    }

    float getWidth() {
        return _width;
    }

    float getHeight() {
        return _height;
    }

    public Vector2 getPosition () {
        return _position;
    }

   Color getColor() {
       return _color;
   }

   public boolean compareColor (Color color) {
       return _color == color;
   }

    boolean moveToPosition(Vector2 position, float deltaTime) {
        _position.lerp(position, 8f *  deltaTime);

        return !_position.equals(position);
    }

    void moveToPosition(float x, float y) {
        _position.set(x, y);
    }

    void moveToPosition(Vector2 position)
    {
        _position.set(position);
    }

    protected abstract void update(float deltaTime);

    protected abstract void render(ShapeRenderer shapeRenderer);

    protected abstract void render(SpriteBatch batch);
}
