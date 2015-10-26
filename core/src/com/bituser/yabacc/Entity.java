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

    public Entity (Vector2 position, int width, int height) {
        _position = position;
        _width = width;
        _height = height;
    }

    public Entity (int x, int y, int width, int height) {
        this(new Vector2(x, y), width, height);
    }

    public abstract void update (float deltaTime);

    public abstract void render (ShapeRenderer shapeRenderer);
}
