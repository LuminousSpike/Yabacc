package com.bituser.yabacc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class HumanPlayer extends Player {
    Vector2 _mousePos = new Vector2();

    Card _currentCard, _selectedCard;

    public HumanPlayer (float x, float y, Color color) {
        super(x, y, color);
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
        _hand.update(deltaTime);
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        super.render(shapeRenderer);
    }

    @Override
    public void render (SpriteBatch batch) {
        super.render(batch);
    }

   public void touchDown (float x, float y, int pointer, int button) {
       _selectedCard = _hand.touchDown(x, y, pointer, button);
   }

   public void touchUp (float x, float y, int pointer, int button) {
       _selectedCard = _hand.touchUp(x, y, pointer, button);
   }

   public void mouseMoved (float x, float y) {
        _mousePos.set(x, y);
        _hand.mouseMoved(x, y);
     }
    public void touchDragged (int x, int y, int pointer) {
        _hand.touchDragged(x, y, pointer);
    }
}
