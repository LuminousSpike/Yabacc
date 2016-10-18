package com.bituser.yabacc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

class Player extends Entity {
    final Hand _hand;
    private final Hand _trophyHand;

    private final TokenCollection _tokens;

    private boolean _isCurrentTurn = false;

    boolean isCurrentTurn() { return _isCurrentTurn; }

    Player(float x, float y, Color color) {
        super(new Vector2(x, y), 600, 120);
        _hand = new Hand(x, y, _width, _height, 8);
        _trophyHand = new Hand(x - _width / 2f, y, _width, _height, 3);
        _tokens = new TokenCollection(x + _width / 1.20f, y);
        _color = color;
        _color.a = 0.5f;
    }

    boolean hasTokensOfColor(int amount, Color color) {
        return _tokens.hasTokensOfColor(amount, color);
    }

    int getHeldCards() { return _hand.getCardCount(); }

    int getTrophyCardCount() { return _trophyHand.getCardCount(); }

    Card getSelectedCard() { return _hand.getSelectedCard(); }

    public void add (Card card) {
        _hand.add(card);
    }

    public void add (TrophyCard card) {
        _trophyHand.add(card);
    }

    public void add (Array<Token> tokens) {
        _tokens.addTokens(tokens);
    }

    void removeTokens(int amount, Color color) {
        _tokens.removeTokens(amount, color);
    }

    void startTurn() {
        _isCurrentTurn = true;
    }

    private void endTurn(Card card) {
        add(card);
        _isCurrentTurn = false;
    }

    void playCard(Card cardFromDeck) {
        _hand.playCard();
        endTurn(cardFromDeck);
    }

    @Override
    public String toString () {
        // Method stub
        return "Player (number)";
    }

    @Override
    protected void update(float deltaTime) {
        _rect.setX(_position.x - (_width / 2));
        _rect.setY(_position.y - (_height / 2));
        _tokens.update(deltaTime);
        _trophyHand.update(deltaTime);
        _tokens.update(deltaTime);
    }

    @Override
    protected void render(ShapeRenderer shapeRenderer) {
        if (_isCurrentTurn) {
            shapeRenderer.setColor(_color);
            shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
        }
        _tokens.render(shapeRenderer);
        _trophyHand.render(shapeRenderer);
        _hand.render(shapeRenderer);
    }

    @Override
    protected void render(SpriteBatch batch) {
        _tokens.render(batch);
        _trophyHand.render(batch);
        _hand.render(batch);
    }
}
