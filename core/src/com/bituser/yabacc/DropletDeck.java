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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import java.util.*;

public class DropletDeck extends Deck {

    BitmapFont _font;

    public DropletDeck (float x, float y, BitmapFont font) {
        super(x, y, 100, 150); 
        _font = font;

        createCards(13, Color.RED);
        createCards(11, Color.YELLOW);
        createCards(9, Color.GREEN);
        createCards(7, Color.BLUE);
        createCards(5, Color.GRAY);

        for (Card card : _cards) {
            System.out.println(card.toString());
        }
    }

    private void createCards (int amount, Color suite) {
        for (int i = 1; i <= amount; i++)
        {
            _cards.add(new Card(_position.x, _position.y, suite, valueOfCard(i, amount), _font));
        }
    }

    private int stepValue (int value, int limit) {
        if (value > limit) {
            return 1;
        }

        return 0;
    }

    private int valueOfCard (int card, int cardsInSuite) {
        int value = card;
        switch (cardsInSuite) {
            case 11:
                value += stepValue(card, 3);
                value += stepValue(card, 8);
                break;
            case 9:
                value += stepValue(card, 2);
                value += stepValue(card, 3);
                value += stepValue(card, 6);
                value += stepValue(card, 7);
                break;
            case 7:
                if (card > 1) { return 2 * card - 1; }
                break;
            case 5:
                return 3 * card - 2;
        }

        return value;
    }
    
    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        super.render(shapeRenderer);
    }

    @Override
    public void render (SpriteBatch batch) {
        super.render(batch);
    }
}
