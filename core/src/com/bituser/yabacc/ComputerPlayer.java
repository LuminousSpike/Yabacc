package com.bituser.yabacc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

class ComputerPlayer extends Player {
    private Array<Tile> _tiles;
    private Array<TileSide> _myTileSides, _opponentTileSides;

    ComputerPlayer (float x, float y, Color color) {
        super(x, y, color);
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
        // TODO: AI code goes here
        if (isCurrentTurn()) {
            Array<Card> cards = _hand._entities;
            for (Card card : cards) {
                for (TileSide side : _myTileSides) {
                    if (side.addCard(card)) {
                        _hand.setSelectedCard(card);
                        playCard();
                        return;
                    }
                }
            }
        }
    }

    void setTiles (Array<Tile> tiles) {
        _tiles = tiles;
        _myTileSides = new Array<TileSide>();
        _opponentTileSides = new Array<TileSide>();

        for (Tile tile : _tiles) {
            _opponentTileSides.add(tile.getLeftSide());
            _myTileSides.add(tile.getRightSide());
        }
    }
}
