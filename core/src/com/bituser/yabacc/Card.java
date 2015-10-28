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

public class Card extends Entity {
    int _value = 1;

    boolean _isActive = false, _isSelected = false, _isPlayed = false;

    Color _colorActive = Color.YELLOW;

    BitmapFont _font = null;

    public Card (int value, BitmapFont font) {
        this(new Vector2(0, 0), value, font);
    }

    // Main constructor
    public Card (Vector2 position, int value, BitmapFont font) {
        super(position, 100, 150);
        _color.a = 0.5f;
        _colorActive.a = 0.5f;
        _value = value;
        _font = font;
    }

    public Card (float x, float y, int value, BitmapFont font) {
        this(new Vector2(x, y), value, font);
    }

    public int getValue () {
        return _value;
    }

    public boolean isSelected () {
        return _isSelected;
    }

    public void setFont (BitmapFont font) {
        _font = font;
    }

   public boolean compareColor (Color color) {
       return _color == color;
   }

   public void placeDown () {
       _isSelected = false;
       _isActive = false;
       _isPlayed = true;
   }

   public void discard () {
       _isPlayed = false;
   }

   public boolean overlaps (Rectangle rectangle) {
       _isActive = _rect.overlaps(rectangle);
       return _isActive;
   }

   public boolean contains (float x, float y) {
        return _rect.contains(x, y);
   }

   public void touchDown (float x, float y, int pointer, int button) {
       _isSelected = (contains(x, y) && button == 0);
       if (_isSelected) { 
           moveToPosition(x, y); 
       }
   }

   public void touchUp (float x, float y, int pointer, int button) {
       if(button == 0) {
           _isSelected = false;
       }
   }

   public void touchDragged (float x, float y, int pointer) {
       if(_isSelected) {
           moveToPosition(x, y);
       }
   }

   public void mouseMoved (float x, float y) {
       _isActive = contains(x, y);
   }

    @Override
    public void update (float deltaTime) {

    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        _rect.setX(_position.x - (_width / 2));
        _rect.setY(_position.y - (_height / 2));

        if(_isPlayed) {
            _color.a = 1f;
        }

        shapeRenderer.setColor(_color);
        shapeRenderer.rect(_rect.x, _rect.y, _width, _height);

        if(_isActive || _isSelected) {
            shapeRenderer.setColor(_colorActive);
            shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
        }

    }

    @Override
    public void render (SpriteBatch batch) {
        if(_font != null)
        {
            _font.draw(batch, String.valueOf(_value), _position.x - 6, _position.y + 9);
        }
    }
}
