package com.bituser.yabacc.droplets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public abstract class Deck extends Entity {
    final Array<Card> _cards = new Array<Card>();

    Deck(float x, float y, float width, float height) {
        super(x, y, width, height);
        _color = Color.GREEN;
    }

    public int size () {
        return _cards.size;
    }

    public Card getCard () {
        Card card = null;

        int cardCount = _cards.size;

        if (cardCount > 0) {
            card = _cards.get(_rand.nextInt(cardCount));
            _cards.removeValue(card, true);
        }

        return card;
    }

    private void addCard(Card card) {
        card.moveToPosition(_position.x, _position.y);
        _cards.add(card);
    }

    public void addCards (Array<Card> cards) {
        for(Card card : cards) {
            addCard(card);
        }
    }

    @Override
    protected void update(float deltaTime) {
        _rect.setX(_position.x - (_width / 2));
        _rect.setY(_position.y - (_height / 2));
    }

    @Override
    protected void render(ShapeRenderer shapeRenderer) {
        _color.a = 0.5f;
        shapeRenderer.setColor(_color);
        shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
    }

    @Override
    protected void render(SpriteBatch batch) {
    }
}
