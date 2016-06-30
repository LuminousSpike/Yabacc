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
import java.util.*;

public class TileSide extends EntityCollection {
    Tile _parent;
    TileSide_Side _sideOfTile;

    List<Card> _cards = new ArrayList<Card>();
    List<Card> _discardedCards = new ArrayList<Card>();
    List<Card> _movingCards = new ArrayList<Card>();

    public enum TileSide_Side {
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

    public TileSide (float x, float y, Color color, Tile parent, TileSide_Side sideOfTile) {
        super(x, y, 400, 100);
        _color = color;
        _parent = parent;
        _sideOfTile = sideOfTile;
    }

    public int getValue () {
        int total = 0;
        for (Card card : _cards) {
            total += card.getValue();
        }
        return total;
    }

    public Tile getParent () { return _parent; }

    public ArrayList getDiscardedCards () {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.addAll(_discardedCards);
        _discardedCards.clear();
        return cards;
    }

    public Rectangle getRect () { return _rect; }

    public boolean addCard (Card card) {
        if (_parent.cardMatchesColor(card) && !haveCardsOfWantedColor(card.getColor())) {
            _cards.add(card);
            _movingCards.add(card);
            return true;
        }
        return false;
    }

    public boolean haveCardsOfWantedColor (Color color) {
        int needed = _parent.getTotalTokensOfColor(color);
        int have = 0;

        for (Card card : _cards) {
            if (card.getColor() == color) {
                have++;
                if (have == needed) {
                    return true;
                }
            }
        }
        return false;
    }

    public void discardCards () {
        _discardedCards.addAll(_cards);
        _cards.clear();
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);

        positionCards(deltaTime);

        for (Card card : _cards) {
            card.update(deltaTime);
        }
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(_color);
        shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
        for (Card card : _cards) {
            card.render(shapeRenderer);
        }
    }

    @Override
    public void render (SpriteBatch batch) {
        for (Card card : _cards) {
            card.render(batch);
        }
    }

    private void positionCards (float deltaTime) {
        Card positionedCard = null;

        for (Card card : _movingCards) {
            if (positionCard(card, _cards.indexOf(card), deltaTime)) {
                positionedCard = card;
            }
        }

        if (positionedCard != null) {
            _movingCards.remove(positionedCard);
        }
    }

    private boolean positionCard (Card card, int cardPosition, float deltaTime) {
        float nextX, nextY;
        float offset = (card.getWidth() - 10) - ((10 + card.getWidth()) * cardPosition) + 10;

        if (_sideOfTile == TileSide_Side.Right) {
            nextX = _position.x - offset;
        }
        else {
            nextX = _position.x + offset;
        }
        nextY = _position.y;

        return !card.moveToPosition(new Vector2(nextX, nextY), deltaTime);
    }
}
