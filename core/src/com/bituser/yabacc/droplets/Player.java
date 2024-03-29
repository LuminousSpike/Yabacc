package com.bituser.yabacc.droplets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

class Player extends com.bituser.yabacc.util.Entity {
    final Hand<Card> _hand;
    private final Hand<com.bituser.yabacc.droplets.TrophyCard> _trophyHand;

    private final TokenCollection _tokens;

    private boolean _isCurrentTurn = false, _isAbleToPlay = true;

    boolean isCurrentTurn() { return _isCurrentTurn; }

    boolean isAbleToPlay () { return _isAbleToPlay; }

    boolean isReadyToDiscardCards () { return _hand.isReadyToDiscardCards(); }

    void setReadyToDiscardCards (boolean value) { _hand.setReadyToDiscardCards(value); }

    void setAbleToPlay (boolean value) { _isAbleToPlay = value; }

    Player(float x, float y, Color color) {
        super(new Vector2(x, y), 600, 120);
        _hand = new Hand<Card>(x, y, _width, _height, 8);
        _trophyHand = new Hand<com.bituser.yabacc.droplets.TrophyCard>(x - _width / 2f, y, _width, _height, 3);
        _tokens = new TokenCollection(x + _width / 1.20f, y);
        _color = color;
        _color.a = 0.5f;
    }

    boolean hasTokensOfColor(int amount, Color color) {
        return _tokens.hasTokensOfColor(amount, color);
    }

    int getHeldCards() { return _hand.getCardCount(); }

    int getTrophyCardCount() { return _trophyHand.getCardCount(); }

    public Array<Card> getDiscardedCards() {
        return _hand.getDiscardedCards();
    }

    Card getSelectedCard() { return _hand.getSelectedCard(); }

    public void add (Card card) {
        _hand.add(card);
    }

    public void add (com.bituser.yabacc.droplets.TrophyCard card) {
        _trophyHand.add(card);
    }

    public void add (Array<com.bituser.yabacc.droplets.Token> tokens) {
        _tokens.addTokens(tokens);
    }

    void removeTokens(int amount, Color color) {
        _tokens.removeTokens(amount, color);
    }

    void startTurn() {
        _isCurrentTurn = true;
    }

    private void endTurn() {
        _isCurrentTurn = false;
    }

    boolean hasPlayableHand (Array<com.bituser.yabacc.droplets.TileSide> sides){
        _isAbleToPlay = false;
        for (com.bituser.yabacc.droplets.TileSide side : sides) {
            for (Card card : (Array<Card>)_hand.getCards()) {
                if (side.canAdd(card)) _isAbleToPlay = true;
            }
        }
        return _isAbleToPlay;
    }

    void playCard() {
        _hand.playCard();
        endTurn();
    }

    @Override
    public String toString () {
        // Method stub
        return "Player (number)";
    }

    @Override
    public void update(float deltaTime) {
        _rect.setX(_position.x - (_width / 2));
        _rect.setY(_position.y - (_height / 2));
        _tokens.update(deltaTime);
        _trophyHand.update(deltaTime);
        _tokens.update(deltaTime);
        _hand.update(deltaTime);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (_isCurrentTurn) {
            shapeRenderer.setColor(_color);
            shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
        }
        _tokens.render(shapeRenderer);
        _trophyHand.render(shapeRenderer);
        _hand.render(shapeRenderer);
    }

    @Override
    public void render(SpriteBatch batch) {
        _tokens.render(batch);
        _trophyHand.render(batch);
        _hand.render(batch);
    }
}
