package com.bituser.yabacc.droplets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class DropletDeck extends Deck {

    private final BitmapFont _font;

    DropletDeck(float x, float y, BitmapFont font) {
        super(x, y, 60, 90);
        _font = font;

        createCards(13, Color.RED);
        createCards(11, Color.YELLOW);
        createCards(9, Color.GREEN);
        createCards(7, Color.BLUE);
        createCards(5, Color.GRAY);
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
    public void render (SpriteBatch batch) {
        super.render(batch);
        _font.draw(batch, String.valueOf("Cards:\n  " + _cards.size), _position.x - (_width / 2.5f), _position.y + (_height / 4));
    }
}
