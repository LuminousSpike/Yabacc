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

public class Card extends Entity {

    public Card(Vector2 position) {
        super(position, 50, 100);
    }

    public Card (int x, int y) {
        this(new Vector2(x, y));
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
       shapeRenderer.setColor(_color);
       shapeRenderer.rect(_position.x, _position.y, _width, _height);
    }
}
