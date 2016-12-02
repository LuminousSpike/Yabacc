package com.bituser.yabacc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Hand<T extends Card> extends GenericCollection<Card> {
    private Card _activeCard = null;
    private Card _selectedCard = null;

    private Vector2 _mousePos = new Vector2(0,0);

    private int _columns;
    private int _cardsToDiscard = 0;

    private boolean _isReadyToDiscardCards = false;
    private boolean _cardsHidden = false;

    public Hand(float x, float y, float width, float height, int columns) {
        super(new Vector2(x, y), width, height);
        _columns = columns;
    }

    public int getCardCount () {
        return _entities.size;
    }

    Array<Card> getCards () { return _entities; }

    Array<Card> getDiscardedCards () {
        Array<Card> discardedCards = new Array<Card>();
        for (Card card : getCards()) {
            if (card.isMarkedForDiscard()) {
                card.toggleDiscard();
                _cardsToDiscard = 0;
                discardedCards.add(card);
            }
        }
        removeAll(discardedCards);
        setReadyToDiscardCards(false);
        return discardedCards;
    }

    public Card getSelectedCard () { return _selectedCard; }

    public void setSelectedCard (Card card) { _selectedCard = card; }

    public boolean isReadyToDiscardCards () { return _isReadyToDiscardCards; }

    public void setReadyToDiscardCards (boolean value) { _isReadyToDiscardCards = value; }

    public void setCardsHidden (boolean value) { _cardsHidden = value; }

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
        setSelectedEntity(null);
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

    private void setActiveCard (float x, float y) {
        for (Card card : _entities) {
            if (_selectedCard == null) {
                if (_activeCard == null && card.mouseMoved(x, y)) {
                    _activeCard = card;
                }
                else if (_activeCard != null && !_activeCard.mouseMoved(x, y)) {
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
        if (_cardsHidden) {
            for (Card card : _entities ) {
                card.hiddenRender(shapeRenderer);
            }
            return;
        }

        for (Card card : _entities) {
            card.render(shapeRenderer);
        }

        if (_selectedCard != null) {
            _selectedCard.render(shapeRenderer);
        }
    }

    @Override
    public void render (SpriteBatch batch) {
        if (_cardsHidden) {
            return;
        }
        for (Card card : _entities) {
            card.render(batch);
        }

        if (_selectedCard != null) {
            _selectedCard.render(batch);
        }
    }

    void toggleDiscard(float x, float y, int pointer, int button) {
        for (Card card : _entities) {
            if (card.hasBeenClicked(x, y, pointer, button)) {
                if (card.isMarkedForDiscard()){
                    _cardsToDiscard--;
                    card.toggleDiscard();
                    return;
                }
                else if (_cardsToDiscard < 3) {
                    _cardsToDiscard++;
                    card.toggleDiscard();
                }
            }
        }
    }
}
