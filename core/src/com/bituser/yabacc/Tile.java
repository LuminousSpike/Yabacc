package com.bituser.yabacc;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Tile extends EntityCollection {
    int _tileNumber;
    boolean _isFlipped, _isActive = true;
    Color _color1, _color2;

    TileSide _leftSide, _rightSide;

    Bag _bag;

    BitmapFont _font;

    ArrayList<Token> _tokens = new ArrayList<Token>(), _newTokens = new ArrayList<Token>();

    public Tile (float x, float y, Color color1, Color color2, int tileNumber, Bag bagOfTokens, BitmapFont font) {
        super(x, y, 100, 100);
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

    public TileSide getLeftSide () { return _leftSide; }

    public TileSide getRightSide () { return _rightSide; }

    public boolean getFlipped () { return _isFlipped; }

    public boolean getActive () { return _isActive; }

    public boolean cardMatchesColor (Card card) {
        for(Token token : _tokens) {
            if (card.getColor() == token.getColor()) {
                return true;
            }
        }
        return false;
    }

    public int getTotalTokensOfColor (Color color) {
        int amount = 0;
        for(Token token : _tokens) {
            if (token.getColor() == color) {
                amount++;
            }
        }
        return amount;
    }

    public ArrayList getDiscardedCards () {
        ArrayList<Card> _discardedCards = new ArrayList<Card>(); _discardedCards.addAll(_leftSide.getDiscardedCards());
        _discardedCards.addAll(_rightSide.getDiscardedCards());
        return _discardedCards;
    }

    public boolean isFull() {
        for(Token token : _tokens) {
            if (_leftSide.haveCardsOfWantedColor(token.getColor()) && _rightSide.haveCardsOfWantedColor(token.getColor())) {
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }

    public ArrayList claimTokens() {
        ArrayList<Token> _tokensToSend = new ArrayList<Token>();
        _tokensToSend.addAll(_tokens);
        flipTile();
        return _tokensToSend;
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);

        _leftSide.update(deltaTime);
        _rightSide.update(deltaTime);
        placeNewTokens(deltaTime);

        for (Token token : _tokens) {
            token.update(deltaTime);
        }
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
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

        for(Token token : _tokens) {
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
                _tokens.add(token);
                _newTokens.add(token);
            }
            return true;
        }
        return false;
    }

    private void placeNewTokens (float deltaTime) {
        Token tokenPlaced = null;

        for (Token token : _newTokens) {
            int tokenPostition = _tokens.indexOf(token);
            int nextX = (int)_position.x - (int)(_width / 2) + 10 + (int)((token.getWidth() + 5) * tokenPostition);
            int nextY = (int)_position.y;

            if (!token.moveToPosition(new Vector2(nextX, nextY), deltaTime)) {
                tokenPlaced = token;
            }
        }

        if (tokenPlaced != null) {
            _newTokens.remove(tokenPlaced);
        }
    }

    private void flipTile () {
        _tokens.clear();
        _leftSide.discardCards();
        _rightSide.discardCards();
        _isFlipped = !_isFlipped;

        if (!getTokensFromBag(_bag)) {
            _isActive = false;
        }
    }
}
