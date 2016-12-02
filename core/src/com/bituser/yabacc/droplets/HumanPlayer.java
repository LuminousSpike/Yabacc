package com.bituser.yabacc.droplets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

class HumanPlayer extends Player {
    private Vector2 _mousePos = new Vector2();

    private Card _currentCard, _selectedCard;

    HumanPlayer(float x, float y, Color color) {
        super(x, y, color);
    }

    void setReadyToDiscardCards (boolean value) {
        _hand.setReadyToDiscardCards(value);
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
    }

    void touchDown(float x, float y, int pointer, int button) {
        if (isAbleToPlay()) {
            _selectedCard = _hand.touchDown(x, y, pointer, button);
        }
   }

   void touchUp(float x, float y, int pointer, int button) {
       if (isAbleToPlay()) {
           _selectedCard = _hand.touchUp(x, y, pointer, button);
       }
       else {
           _hand.toggleDiscard(x, y, pointer, button);
       }
   }

   void mouseMoved(float x, float y) {
       if (isAbleToPlay()) {
           _mousePos.set(x, y);
           _hand.mouseMoved(x, y);
       }
     }
    void touchDragged(int x, int y, int pointer) {
        _hand.touchDragged(x, y, pointer);
    }
}
