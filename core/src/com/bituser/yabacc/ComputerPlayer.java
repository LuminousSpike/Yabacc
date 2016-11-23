package com.bituser.yabacc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

class ComputerPlayer extends Player {
    private Table _table;
    private Array<Tile> _tiles;

    ComputerPlayer (float x, float y, Color color) {
        super(x, y, color);
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
        // TODO: AI code goes here
    }

    void setTable (Table table) {
        _table = table;
        _tiles = _table.getTiles();
    }
}
