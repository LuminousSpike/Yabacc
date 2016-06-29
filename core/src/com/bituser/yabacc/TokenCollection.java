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

public class TokenCollection extends EntityCollection {
    ArrayList<Token> _tokens = new ArrayList<Token>(), _newTokens = new ArrayList<Token>();

    static final int TOKEN_SPACING = 10;

    boolean _repositionTokens = false;

    public TokenCollection (float x, float y, Color color) {
        super(x, y, 200, 100);
        _color = color;
    }

    public int getTokenCount () { return _tokens.size(); }

    public boolean hasTokensOfColor (int amount, Color color) {
        int have = amount;
        ArrayList<Token> tokens = new ArrayList<Token>();

        for (Token token : _tokens) {
            if (token.getColor() == color) {
                have--;
            }
        }

        if (have <= 0) { return true; }
        else if (have < amount) {
            if (threeForOneTokens(have, color, tokens)) {
                for (Token token : tokens) {
                    _tokens.remove(token);
                }
                return true;
            }
        }
        return false;
    }

    public boolean threeForOneTokens (int amount, Color excludedColor, ArrayList<Token> tokens) {
        ArrayList<Color> colors = new ArrayList<Color>();

        for (Token token : _tokens) {
            Color tokenColor = token.getColor();
            if (tokenColor != excludedColor && !colors.contains(tokenColor)) {
                colors.add(tokenColor);
            }
        }

        for (Color color : colors) {
            ArrayList<Token> tokenSet = new ArrayList<Token>();
            if (amount > 0) {
                for (Token token : _tokens) {
                    if (token.getColor() == color) {
                        if (tokenSet.size() < 3) {
                            tokenSet.add(token);
                        }
                        if (tokenSet.size() == 3) {
                            amount--;
                            tokens.addAll(tokenSet);
                            tokenSet.clear();
                        }
                    }
                }
            }
        }
        return amount == 0;
    }

    public void addToken (Token token) {
        _tokens.add(token);
        _newTokens.add(token);
    }

    public void addTokens (ArrayList<Token> tokens) {
        _tokens.addAll(tokens);
        _newTokens.addAll(tokens);
    }

    public void removeTokens (int amount, Color color) {
        ArrayList<Token> tokens = new ArrayList<Token>();

        for (int i = 0; i < _tokens.size(); i++) {
            Token token = _tokens.get(i);
            if (token.getColor() == color) {
                tokens.add(token);
                amount--;
            }
            if (amount <= 0) {
                break;
            }
        }

        _tokens.removeAll(tokens);
        _repositionTokens = true;
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);

        if (!_newTokens.isEmpty()) {
            positionNewTokens(deltaTime);
        }

        if (_repositionTokens) {
            repositionTokens(deltaTime);
        }
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        super.render(shapeRenderer);
        for (Token token : _tokens) {
            token.render(shapeRenderer);
        }
    }

    @Override
    public void render (SpriteBatch batch) {

    }

    private void positionNewTokens (float deltaTime) {
        Token tokenToRemove = null;

        for (Token token : _newTokens) {
            if (positionToken(token, _tokens.indexOf(token), deltaTime)) {
                tokenToRemove = token;
            }
        }

        if (tokenToRemove != null) {
            _newTokens.remove(tokenToRemove);
        }
    }

    private boolean positionToken (Token token, int index, float deltaTime) {
        int row = 0;
        row = index / 5;

        //int positionY = (int)(_position.y + (token.getHeight() + TOKEN_SPACING) * (row - 1));
        //int positionX = (int)(_position.x + ((token.getWidth() + TOKEN_SPACING) * (index - (row + 1) * 5)));
        int positionX = (int)(_position.x + (token.getWidth() + TOKEN_SPACING) * index);
        int positionY = (int)(_position.y);
        return !token.moveToPosition (new Vector2(positionX, positionY), deltaTime);
    }

    private boolean repositionTokens (float deltaTime) {
        boolean finished = true;

        for (int i = 0; i < _tokens.size(); i++) {
            Token token = _tokens.get(i);
            if (!positionToken(token, i, deltaTime)) {
                finished = false;
            }
        }
        return finished;
    }
}
