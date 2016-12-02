package com.bituser.yabacc.droplets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

class ComputerPlayer extends Player {
    private Array<Tile> _tiles;
    private Array<TileSide> _myTileSides, _opponentTileSides;

    ComputerPlayer (float x, float y, Color color) {
        super(x, y, color);
        _hand.setCardsHidden(true);
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
        // TODO: AI code goes here
        if (isCurrentTurn()) {
            computerLogic();
        }
    }

    void computerLogic() {
        Array<Card> cards = _hand.getCards();
        //Array<Tile> orderedTiles = getTileOrder();
        if (!isAbleToPlay()) {
            for (int fuzz = 0; fuzz < 13; fuzz++) {
                for (Card card : cards) {
                    if (fuzzyCardMatch(card, 6, fuzz)) {
                        card.toggleDiscard();
                        _hand.setReadyToDiscardCards(true);
                        return;
                    }
                }
            }
        }
        for (int fuzz = 0; fuzz < 13; fuzz++) {
            for (Tile tile : _tiles) {
                TileSide mySide = tile.getRightSide();
                TileSide opponentSide = tile.getLeftSide();

                for (Card card : cards) {
                    if (fuzzyCardMatch(card, mySide, mySide.getBestCardValueToPlay(), fuzz)) return;
                    if (fuzzyCardMatch(card, opponentSide, opponentSide.getWorstCardValueToPlay(), fuzz)) return;
                }
            }
        }
    }

    private boolean fuzzyCardMatch(Card card, TileSide side, int valueToMatch, int fuzz) {
        if (fuzzyCardMatch(card, valueToMatch, fuzz)) {
            if (side.addCard(card)) {
                _hand.setSelectedCard(card);
                playCard();
                return true;
            }
        }
        return false;
    }

    private boolean fuzzyCardMatch (Card card, int valueToMatch, int fuzz) {
        int fuzzyValue;
        if (valueToMatch > 1) fuzzyValue = -fuzz;
        else fuzzyValue = fuzz;

        if (valueToMatch + fuzzyValue == card.getValue()) {
            return true;
        }
        return false;
    }

    // Ordered using a absolute point based system
    private Array<Tile> getTileOrder() {
        for (int i = 0; i < _tiles.size; i++) {
            for (int j = 0; j < _tiles.size; j++) {
                Tile tile1 = _tiles.get(i);
                Tile tile2 = _tiles.get(j);
            }
        }
        return null;
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
