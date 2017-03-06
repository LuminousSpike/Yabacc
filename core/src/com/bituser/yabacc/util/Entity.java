package com.bituser.yabacc.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public abstract class Entity {
    protected Vector2 _position;
    protected Rectangle _rect;
    protected Color _color = Color.BLACK;
    protected float _width, _height;
    protected static final Random _rand = new Random();

    public Entity() {
        _position = new Vector2(0, 0);
    }

    public Entity(Vector2 position, float width, float height) {
        _position = position;
        _width = width;
        _height = height;
        _rect = new Rectangle(_position.x, _position.y, _width, _height);
    }

    public Entity(float x, float y, float width, float height) {
        this(new Vector2(x, y), width, height);
    }

    public float getWidth() {
        return _width;
    }

    public float getHeight() {
        return _height;
    }

    public Vector2 getPosition () {
        return _position;
    }

    public Color getColor() {
       return _color;
   }

    public boolean compareColor (Color color) {
       return _color == color;
   }

    public boolean moveToPosition(Vector2 position, float deltaTime) {
        _position.lerp(position, 8f *  deltaTime);

        return !_position.equals(position);
    }

    public void moveToPosition(float x, float y) {
        _position.set(x, y);
    }

    public void moveToPosition(Vector2 position)
    {
        _position.set(position);
    }

    public abstract void update(float deltaTime);

    public abstract void render(ShapeRenderer shapeRenderer);

    public abstract void render(SpriteBatch batch);
}
