package com.bituser.yabacc.droplets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class DiscardButton extends Entity {

    private BitmapFont _font;
    private boolean _active = false;

    public DiscardButton (Vector2 position, float width, float height, BitmapFont font) {
        super(position, width, height);
        _font = font;
    }

    private boolean contains(float x, float y) {
        return _rect.contains(x, y);
    }

    @Override
    protected void update(float deltaTime) {

    }

    @Override
    protected void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(_rect.x, _rect.y, _width, _height);

        if (_active) {
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
        }
    }

    @Override
    protected void render(SpriteBatch batch) {
        if (_font != null) {
            _font.draw(batch, "Discard\n Cards", _position.x + 10, _position.y + (_height / 1.25f));
        }
    }
    
    public boolean mouseMoved (float x, float y) {
        _active = contains(x, y);
        return _active;
    }

    void touchUp(float x, float y, int pointer, int button, HumanPlayer player) {
        if (_active) {
            player.setReadyToDiscardCards(true);
        }
    }
}
