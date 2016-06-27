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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class TrophyCard extends Card {
    public TrophyCard (int value, Color suite, BitmapFont font) {
        this(new Vector2(0, 0), suite, value, font);
    }

    // Main constructor
    public TrophyCard (Vector2 position, Color suite, int value, BitmapFont font) {
        super(position, suite, value, font);
    }

    public TrophyCard (float x, float y, Color suite, int value, BitmapFont font) {
        this(new Vector2(x, y), suite, value, font);
    }

   @Override
   public String toString () {
       return super.toString();
   }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        super.render(shapeRenderer);
    }

    @Override
    public void render (SpriteBatch batch) {
        super.render(batch);
    }
}
