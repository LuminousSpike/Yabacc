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

public class Hand extends Entity {
    List<Card> _heldCards = new ArrayList<Card>();

    Card _currentCard = null, _selectedCard = null;

    boolean _mouseLeft = false, _repositionCards = false;

    final int CARD_SPACING = 10;

    public Hand (float x, float y) {
        super(x, y);
    }

    public int getCardCount () {
        return _heldCards.size();
    }

    public void addCard (Card card) {
        _heldCards.add(card);
        _repositionCards = true;
    }

    public void addCards (Card[] cards) {
        for (Card card : cards) {
            addCard(card);
        }
    }

    public Card playCard () { 
        Card card = _selectedCard;
        _heldCards.remove(card);
        _selectedCard = null;
        _repositionCards = true;
        
        return card;
    }

    public void touchDown (float x, float y, int pointer, int button) {
        for (Card card : _heldCards) {
            card.touchDown(x, y, pointer, button);
            if (card.isSelected()) {
                _selectedCard = card;
                break;
            }
        }
   }

     public void touchUp (float x, float y, int pointer, int button) {
        for (Card card : _heldCards) {
            card.touchUp(x, y, pointer, button);
        }
        _repositionCards = true;
        _selectedCard = null;
     }

     public void touchDragged (float x, float y, int pointer) {
        for (Card card : _heldCards) {
            card.touchDragged(x, y, pointer);
        }
     }

     public void mouseMoved (float x, float y) {
        for (Card card : _heldCards) {
            card.mouseMoved(x, y);
        }
     }

    private Vector2 calculateCardPosition (Card card, int placeInHand) {
        float xPos = _position.x + (card.getWidth() + CARD_SPACING) * placeInHand;
        float yPos = _position.y;
        return new Vector2(xPos, yPos);
    }

    private void positionCard (Card card, int placeInHand) {
        card.moveToPosition(calculateCardPosition(card, placeInHand));
    }

    private boolean positionCard (Card card, int placeInHand, float deltaTime) {
        return card.moveToPosition(calculateCardPosition(card, placeInHand), deltaTime);
    }

    private boolean repositionCards (float deltaTime) {
        boolean cardsMoved = false;
        int index = 0;
        for (Card card : _heldCards) {
            if (!card.equals(_selectedCard)) {
                if (positionCard(card, index, deltaTime)) {
                    cardsMoved = true;
                }
            }
            index++;
        }
        return cardsMoved;
    }

    @Override
    public void update (float deltaTime) {
        if(_repositionCards) {
            _repositionCards = repositionCards(deltaTime);
        }
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        for (Card card : _heldCards) {
            card.render(shapeRenderer);
        }

        if (_selectedCard != null) {
            _selectedCard.render(shapeRenderer);
        }
    }

    @Override
    public void render (SpriteBatch batch) {
        for (Card card : _heldCards) {
            card.render(batch);
        }

        if (_selectedCard != null) {
            _selectedCard.render(batch);
        }
    }
}
