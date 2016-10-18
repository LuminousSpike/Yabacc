package com.bituser.yabacc;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

class TokenCollection extends GenericCollection<Token> {
    TokenCollection(float x, float y, Color color) {
        super(new Vector2(x, y), 190, 80);
        _color = color;
        setColumns(5);
        setSpacing(5);
    }

    public int getTokenCount () { return _entities.size; }

    boolean hasTokensOfColor(int amount, Color color) {
        int have = amount;
        Array<Token> tokens = new Array<Token>();

        for (Token token : _entities) {
            if (token.getColor() == color) {
                have--;
            }
        }

        if (have <= 0) { return true; }
        else if (have < amount) {
            if (threeForOneTokens(have, color, tokens)) {
                for (Token token : tokens) {
                    remove(token);
                }
                return true;
            }
        }
        return false;
    }

    private boolean threeForOneTokens(int amount, Color excludedColor, Array<Token> tokens) {
        Array<Color> colors = new Array<Color>();

        for (Token token : _entities) {
            Color tokenColor = token.getColor();
            if (tokenColor != excludedColor && !colors.contains(tokenColor, true)) {
                colors.add(tokenColor);
            }
        }

        for (Color color : colors) {
            Array<Token> tokenSet = new Array<Token>();
            if (amount > 0) {
                for (Token token : _entities) {
                    if (token.getColor() == color) {
                        if (tokenSet.size < 3) {
                            tokenSet.add(token);
                        }
                        if (tokenSet.size == 3) {
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
        add(token);
    }

    void addTokens(Array<Token> tokens) {
        addAll(tokens);
    }

    void removeTokens(int amount, Color color) {
        Array<Token> tokens = new Array<Token>();

        for (int i = 0; i < _entities.size; i++) {
            Token token = _entities.get(i);
            if (token.getColor() == color) {
                tokens.add(token);
                amount--;
            }
            if (amount <= 0) {
                break;
            }
        }

        removeAll(tokens);
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        super.render(shapeRenderer);
        for (Token token : _entities) {
            token.render(shapeRenderer);
        }
    }

    @Override
    public void render (SpriteBatch batch) {

    }
}
