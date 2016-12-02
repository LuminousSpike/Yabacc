package com.bituser.yabacc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bag extends GenericCollection<Token> {
    private final BitmapFont _font;

    public Bag(float x, float y,
               BitmapFont font) {
        super(new Vector2(x, y), 80, 80);
        _font = font;

        createTokens(13, Color.RED);
        createTokens(11, Color.YELLOW);
        createTokens(9, Color.GREEN);
        createTokens(7, Color.BLUE);
        createTokens(5, Color.GRAY);
    }

    void add(Token token) {
        super.add(token);
    }

    Token get() { return getRandom(); }

    private void createTokens (int amount, Color tokenColor) {
        for (int i = 0; i < amount; i++) {
            add(new Token(_position, tokenColor));
        }
    }

    @Override
    public void update (float deltaTime) {
        _rect.setX(_position.x - (_width / 2));
        _rect.setY(_position.y - (_height / 2));
    }

    @Override
    public void render (SpriteBatch batch) {
        super.render(batch);
        _font.draw(batch, String.valueOf("Tokens:\n   " + size()), _position.x - (_width / 2.5f), _position.y + (_height / 4));
    }
}
