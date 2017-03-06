package com.bituser.yabacc.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bituser.yabacc.util.Entity;

/**
 * Created by nathan on 12/12/16.
 */

public class Hud extends Entity {
    Hud () {
        super(new Vector2(0,0), 10, 10);
    }

    @Override
    public void update (float deltaTime) {

    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        return;
    }

    @Override
    public void render (SpriteBatch batch) {
        return;
    }
}
