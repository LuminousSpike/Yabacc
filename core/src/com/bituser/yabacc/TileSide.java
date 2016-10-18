package com.bituser.yabacc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

class TileSide extends GenericCollection<Card> {
    private Tile _parent;
    private TileSide_Side _sideOfTile;

    private Array<Card> _discardedCards = new Array<Card>();

    enum TileSide_Side {
        Left (-1),
        Right (1);

        private int index;

        public int getIndex () {
            return index;
        }

        private TileSide_Side (int index) {
            this.index = index;
        }
    }

    TileSide(float x, float y, Color color, Tile parent, TileSide_Side sideOfTile) {
        super(new Vector2(x,y), 300, 100);
        _color = color;
        _parent = parent;
        _sideOfTile = sideOfTile;
        setOffsetX(10);
    }

    int getValue() {
        int total = 0;
        for (Card card : _entities) {
            total += card.getValue();
        }
        return total;
    }

    public Tile getParent () { return _parent; }

    Array getDiscardedCards() {
        Array<Card> cards = new Array<Card>();
        cards.addAll(_discardedCards);
        _discardedCards.clear();
        return cards;
    }

    Rectangle getRect() { return _rect; }

    boolean addCard(Card card) {
        if (_parent.cardMatchesColor(card) && !haveCardsOfWantedColor(card.getColor())) {
            add(card);
            return true;
        }
        return false;
    }

    boolean haveCardsOfWantedColor(Color color) {
        int needed = _parent.getTotalTokensOfColor(color);
        int have = 0;

        for (Card card : _entities) {
            if (card.getColor() == color) {
                have++;
                if (have == needed) {
                    return true;
                }
            }
        }
        return false;
    }

    void discardCards() {
        for (Card card : _entities) {
            card.discard();
            _discardedCards.add(card);
        }

        _entities.clear();
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);

        for (Card card : _entities) {
            card.update(deltaTime);
        }
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        _color.a = 0.5f;
        shapeRenderer.setColor(_color);
        shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
        for (Card card : _entities) {
            card.render(shapeRenderer);
        }
    }

    @Override
    public void render (SpriteBatch batch) {
        for (Card card : _entities) {
            card.render(batch);
        }
    }
}
