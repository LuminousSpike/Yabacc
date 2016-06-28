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
import java.util.*;

public class Player extends Entity {
    protected Hand _hand, _trophyHand;

    protected TokenCollection _tokens;

    boolean _isCurrentTurn = false;

    public boolean isCurrentTurn () { return _isCurrentTurn; }

    public Player (float x, float y, Color color) {
        super(new Vector2(x, y), 550, 120);
        _hand = new Hand(x, y);
        _trophyHand = new Hand(x, y);
        _tokens = new TokenCollection(x, y, Color.GRAY);
    }

    public boolean hasTokensOfColor (int amount, Color color) {
        return _tokens.hasTokensOfColor(amount, color);
    }

    public int getHeldCards () { return _hand.getCardCount(); }

    public int getTropyCardCount () { return _trophyHand.getCardCount(); }

    public Card getSelectedCard () { return _hand.getSelectedCard(); }

    public void add (Card card) {
        _hand.addCard(card);
    }

    public void add (TrophyCard card) {
        _hand.addCard(card);
    }

    public void add (ArrayList<Token> tokens) {
        _tokens.addTokens(tokens);
    }

    public void removeTokens (int amount, Color color) {
        _tokens.removeTokens(amount, color);
    }

    public void startTurn () {
        _isCurrentTurn = true;
    }

    public void endTurn (Card card) {
        add(card);
        _isCurrentTurn = false;
    }

    public void playCard (Card card, Card cardFromDeck) {
        card.placeDown();
        endTurn(cardFromDeck);
    }

    @Override
    public String toString () {
        // Method stub
        return "Player (number)";
    }

    @Override
    public void update (float deltaTime) {
        _tokens.update(deltaTime);
        _trophyHand.update(deltaTime);
        _tokens.update(deltaTime);
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        if (_isCurrentTurn) {
            _rect.setX(_position.x - (_width / 2));
            _rect.setY(_position.y - (_height / 2));

            shapeRenderer.setColor(_color);
            shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
        }
        _tokens.render(shapeRenderer);
        _trophyHand.render(shapeRenderer);
        _hand.render(shapeRenderer);
    }

    @Override
    public void render (SpriteBatch batch) {
    }
}
