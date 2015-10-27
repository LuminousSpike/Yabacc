package com.bituser.yabacc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2; 
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import java.util.Random;

public abstract class Entity {
    Vector2 _position;
    Rectangle _rect;
    Color _color = Color.BLACK;
    int _width, _height;
    static Random _rand;

    public Entity () {
        _position = new Vector2(0, 0);
    }

    public Entity (Vector2 position, int width, int height) {
        _position = position;
        _width = width;
        _height = height;
        _rect = new Rectangle(_position.x, _position.y, _width, _height);
    }

    public Entity (int x, int y, int width, int height) {
        this(new Vector2(x, y), width, height);
    }

    public int getWidth () {
        return _width;
    }

    public int getHeight () {
        return _height;
    }

    public Vector2 getPosition () {
        return _position;
    }

    public boolean moveToPosition (Vector2 position, float deltaTime) {
        _position.lerp(position, 0.1f * deltaTime);
        if (_position.equals(position)){
            return false;
        }
        return true;
    }

    public void moveToPosition (float x, float y) {
        _position.set(x, y);
    }

    public void moveToPosition (Vector2 position)
    {
        _position.set(position);
    }

    public abstract void update (float deltaTime);

    public abstract void render (ShapeRenderer shapeRenderer);

    public abstract void render (SpriteBatch batch);
}
