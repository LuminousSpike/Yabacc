package com.bituser.yabacc;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

// TODO refactor out repositionCards and instead use the GenericCollection's method.
// TODO refactor out all collection logic to use GenericCollection's.
public class GenericHand extends GenericCollection<Card> {
    Card _activeCard = null, _selectedCard = null;

    Vector2 _mousePos = new Vector2(0,0);

    final int CARD_SPACING = 10;

    int _columns;

    public GenericHand (float x, float y, float width, float height, int columns) {
        super(new Vector2(x, y), width, height);
        _columns = columns;
    }

    public int getCardCount () {
        return _entities.size;
    }

    public Card getSelectedCard () { return _selectedCard; }

    @Override
    public void remove (Card card) {
        super.remove(card);
        _selectedCard = null;
        _activeCard = null;
    }

    public void playCard () {
        _selectedCard.placeDown();
        remove(_selectedCard);
    }

    public Card touchDown (float x, float y, int pointer, int button) {
         setActiveCard(x, y);
        if (_activeCard != null) {
            _selectedCard = _activeCard;
            setSelectedEntity(_selectedCard);
            _selectedCard.touchDown(x, y, pointer, button);
        }
        return _activeCard;
   }

     public Card touchUp (float x, float y, int pointer, int button) {
         setActiveCard(x, y);
        for (Card card : _entities) {
            card.touchUp(x, y, pointer, button);
        }
        _selectedCard = null;
        setSelectedEntity(_selectedCard);
        repositionEntities();
        return _selectedCard;
     }

     public void touchDragged (float x, float y, int pointer) {
        _mousePos.set(x, y);
        if (_selectedCard != null) {
            _selectedCard.touchDragged(x, y, pointer);
        }
     }

     public void mouseMoved (float x, float y) {
        _mousePos.set(x, y);
     }

     // TODO adapt this for use with the GenericCollection's way of repositioning entities.
    private Vector2 calculateCardPosition (Card card, int placeInHand) {
        int row = placeInHand / _columns;
        float xPos = _position.x + (card.getWidth() + CARD_SPACING) * (placeInHand % _columns);
        xPos -= _width / 2.5f;
        float yPos = _position.y - (card.getHeight() + CARD_SPACING) * row;
        return new Vector2(xPos, yPos);
    }

    private void positionCard (Card card, int placeInHand) {
        card.moveToPosition(calculateCardPosition(card, placeInHand));
    }

    private boolean positionCard (Card card, int placeInHand, float deltaTime) {
        return card.moveToPosition(calculateCardPosition(card, placeInHand), deltaTime);
    }

    // TODO remove this method after replacing functionality with the GenericCollection's.
    private boolean repositionCards (float deltaTime) {
        boolean cardsMoved = false;
        int index = 0;
        for (Card card : _entities) {
            if (!card.equals(_selectedCard)) {
                if (positionCard(card, index, deltaTime)) {
                    cardsMoved = true;
                }
            }
            index++;
        }
        return cardsMoved;
    }

    private void setActiveCard (float x, float y) {
        for (Card card : _entities) {
            if (_selectedCard == null) {
                if (_activeCard == null && card.mouseMoved(x, y)) {
                    _activeCard = card;
                }
                else if (_activeCard != null && _activeCard.mouseMoved(x, y) == false) {
                    _activeCard = null;
                }
            }
        }
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
        setActiveCard(_mousePos.x, _mousePos.y);

        for (Card card : _entities) {
            card.update(deltaTime);
        }
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        for (Card card : _entities) {
            card.render(shapeRenderer);
        }

        if (_selectedCard != null) {
            _selectedCard.render(shapeRenderer);
        }
    }

    @Override
    public void render (SpriteBatch batch) {
        for (Card card : _entities) {
            card.render(batch);
        }

        if (_selectedCard != null) {
            _selectedCard.render(batch);
        }
    }
}
