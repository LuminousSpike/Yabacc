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

public abstract class Deck extends Entity {
    List<Card> _cards = new ArrayList<Card>();

    public Deck (float x, float y, float width, float height) {
        super(x, y, width, height);
        _color = Color.GREEN;
    }

    public int size () {
        return _cards.size();
    }

    public Card getCard () {
        Card card = null;

        int cardCount = _cards.size();

        if (cardCount > 0) {
            card = _cards.get(_rand.nextInt(cardCount));
            _cards.remove(card);
        }

        return card;
    }

    public void addCard (Card card) {
        card.moveToPosition(_position.x, _position.y);
        _cards.add(card);
    }

    public void addCards (ArrayList<Card> cards) {
        for(Card card : cards) {
            addCard(card);
        }
    }

    @Override
    public void update (float deltaTime) {
        _rect.setX(_position.x - (_width / 2));
        _rect.setY(_position.y - (_height / 2));
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(_color);
        shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
    }

    @Override
    public void render (SpriteBatch batch) {
    }
}
