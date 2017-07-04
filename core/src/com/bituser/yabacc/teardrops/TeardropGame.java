package com.bituser.yabacc.teardrops;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bituser.yabacc.util.GameScene;

/**
 * Created by nathan on 3/07/17.
 */

public class TeardropGame extends GameScene {
    // Test objects
    Tile _tile;
    Grid _grid;

    public TeardropGame(int screenWidth, int screenHeight, BitmapFont cyrillicFont) {
        super(screenWidth, screenHeight, cyrillicFont);
        _tile = new Tile(10, 10);
        _grid = new Grid(new Vector2(200, 200), 200, 200);
    }

    @Override
    public void update(float deltaTime) {
        _tile.update(deltaTime);
        _grid.update(deltaTime);
    }

    @Override
    public void render(ShapeRenderer ShapeRenderer) {
        _tile.render(ShapeRenderer);
        _grid.render(ShapeRenderer);
    }

    @Override
    public void render(SpriteBatch batch) {
        _tile.render(batch);
        _grid.render(batch);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
