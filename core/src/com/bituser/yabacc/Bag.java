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
import java.util.*;

public class Bag extends EntityCollection {
    BitmapFont _font;

    public Bag (float x, float y, 
            Color token1, Color token2, Color token3, Color token4,
            Color token5, BitmapFont font) {
        super(x, y, 100, 100);
        _font = font;
        
        createTokens(13, token1);
        createTokens(11, token2);
        createTokens(9, token3);
        createTokens(7, token4);
        createTokens(5, token5);
    }

    public Bag (float x, float y, BitmapFont font) {
        this(x, y, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE,
                Color.GRAY, font);
    }

    public void add (Token token) {
        super.add(token);
    }

    public void add (Token[] tokens) {
        super.add(tokens);
    }

    public Token get () {
        return (Token) super.get();
    }

    private void createTokens (int amount, Color tokenColor) {
        for (int i = 0; i < amount; i++) {
            add(new Token(_position, tokenColor));
        }
    }

    @Override
    public void render (SpriteBatch batch) {
        super.render(batch);
        _font.draw(batch, String.valueOf("Tokens:\n   " + size()), _position.x - 40, _position.y + 30);
    }
}
