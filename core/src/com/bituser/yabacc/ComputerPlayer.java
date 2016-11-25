package com.bituser.yabacc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

class ComputerPlayer extends Player {
    private Table _table;
    private Array<Tile> _tiles;
    private Array<TileSide> _myTileSides, _opponentTileSides;
    // Dirty hack
    private Deck _deck;

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
                        // Nasty and needs to be handled way better
                        playCard(_deck.getCard());
                        return;
                    }
                }
            }
        }
    }

    void setTable (Table table) {
        _table = table;
        _tiles = _table.getTiles();
        _myTileSides = new Array<TileSide>();
        _opponentTileSides = new Array<TileSide>();

        for (Tile tile : _tiles) {
            _opponentTileSides.add(tile.getLeftSide());
            _myTileSides.add(tile.getRightSide());
        }
    }

    // Remove ASAP
    void setDeck (Deck deck) { _deck = deck; }
}
