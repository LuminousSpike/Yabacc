package com.bituser.yabacc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

class Tile extends GenericCollection<Token> {
    private int _tileNumber;
    private boolean _isFlipped, _isActive = true;
    private Color _color1, _color2;

    private TileSide _leftSide, _rightSide;

    private Bag _bag;

    private BitmapFont _font;

    Tile(float x, float y, Color color1, Color color2, int tileNumber, Bag bagOfTokens, BitmapFont font) {
        super(new Vector2(x, y), 100, 100);
        _color1 = color1;
        _color2 = color2;
        _color = _color1;

        _tileNumber = tileNumber;
        _isFlipped = (tileNumber % 2) != 0;

        _leftSide = new TileSide(_position.x - (_width / 2 + 150), _position.y, Color.GREEN, this, TileSide.TileSide_Side.Left);
        _rightSide = new TileSide(_position.x + (_width / 2 + 150), _position.y, Color.RED, this, TileSide.TileSide_Side.Right);

        _bag = bagOfTokens;

        _font = font;

        getTokensFromBag(_bag);
    }

    TileSide getLeftSide() { return _leftSide; }

    TileSide getRightSide() { return _rightSide; }

    boolean getFlipped() { return _isFlipped; }

    boolean getActive() { return _isActive; }

    boolean cardMatchesColor(Card card) {
        for(Token token : _entities) {
            if (card.getColor() == token.getColor()) {
                return true;
            }
        }
        return false;
    }

    int getTotalTokensOfColor(Color color) {
        int amount = 0;
        for(Token token : _entities) {
            if (token.getColor() == color) {
                amount++;
            }
        }
        return amount;
    }

    Array getDiscardedCards() {
        Array<Card> _discardedCards = new Array<Card>();
        _discardedCards.addAll(_leftSide.getDiscardedCards());
        _discardedCards.addAll(_rightSide.getDiscardedCards());
        return _discardedCards;
    }

    boolean isFull() {
        for(Token token : _entities) {
            if (_leftSide.haveCardsOfWantedColor(token.getColor()) && _rightSide.haveCardsOfWantedColor(token.getColor())) {
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }

    Array claimTokens() {
        Array<Token> _tokensToSend = new Array<Token>();
        _tokensToSend.addAll(_entities);
        flipTile();
        return _tokensToSend;
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);

        _leftSide.update(deltaTime);
        _rightSide.update(deltaTime);

        for (Token token : _entities) {
            token.update(deltaTime);
        }
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        super.render(shapeRenderer);
        if (_isFlipped) {
            _color = _color2;
        }
        else {
            _color = _color1;
        }

        _color.a = 0.5f;

        shapeRenderer.setColor(_color);
        shapeRenderer.rect(_rect.x, _rect.y, _width, _height);

        _leftSide.render(shapeRenderer);
        _rightSide.render(shapeRenderer);

        for(Token token : _entities) {
            token.render(shapeRenderer);
        }
    }

    @Override
    public void render (SpriteBatch batch) {
        if(_font != null)
        {
            float textPosX = 0;

            if (_tileNumber % 2 == 0) {
                textPosX = _position.x - (_width / 2) + 10;
            }
            else {
                textPosX = _position.x + (_width / 2) - 15;
            }
            _leftSide.render(batch);
            _rightSide.render(batch);
            _font.draw(batch, String.valueOf(_tileNumber), textPosX - 5, _position.y - 25);
        }
    }

    private boolean getTokensFromBag(Bag bag) {
        if (_bag.size() >= _tileNumber) {
            for (int i = 0; i < _tileNumber; i++) {
                Token token = bag.get();
                add(token);
            }
            return true;
        }
        return false;
    }

    private void flipTile () {
        _entities.clear();
        _leftSide.discardCards();
        _rightSide.discardCards();
        _isFlipped = !_isFlipped;

        if (!getTokensFromBag(_bag)) {
            _isActive = false;
        }
    }
}
