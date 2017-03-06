package com.bituser.yabacc.droplets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;
import java.util.Random;

class Table extends GenericCollection<com.bituser.yabacc.util.Entity> {
    private final Array<Player> _players = new Array<Player>();
    private final Array<com.bituser.yabacc.droplets.TrophyCard> _trophyCards = new Array<com.bituser.yabacc.droplets.TrophyCard>();
    private final Array<Card> _discardedCards = new Array<Card>();
    private final Array<com.bituser.yabacc.droplets.Tile> _tiles = new Array<com.bituser.yabacc.droplets.Tile>();

    private Hand _trophyHand;
    private Bag _bag;
    private Deck _deck;
    private DiscardButton _discardButton;

    private Player _player1, _player2, _activePlayer, _winnerPlayer;
    private final Random _rand = new Random();

    private BitmapFont _font;

    // Does this need to exist?
    private Table(float width, float height) {
        super(new Vector2((float) 0, (float) 0), width, height);
    }

    Table(int tableWidth, int tableHeight, Bag bagOfTokens, Deck deckOfCards, Array<Player> players, BitmapFont font) {
        this (tableWidth, tableHeight);
        _bag = bagOfTokens;
        _deck = deckOfCards;
        _font = font;

        _discardButton = new DiscardButton(new Vector2(950, 10), 80, 80, _font);

        add(_bag);
        add(_deck);

        createTrophyCards(tableHeight);
        createTiles(tableWidth, tableHeight, bagOfTokens);

        _player1 = players.get(0);
        _player2 = players.get(1);
        // TODO: Get rid of this casting
        ((ComputerPlayer) _player2).setTiles(_tiles);
        _players.addAll(players);

        addAll(players);

        if (_rand.nextInt(100) < 50) {
            _activePlayer = _player1;
        }
        else {
            _activePlayer = _player2;
        }

        drawStartingHandForEachPlayer();
    }

    // TODO: Need to refactor this out
    Array<Player> getPlayers() { return _players; }

    Player getWinner() { return _winnerPlayer; }

    // TODO: Need to refactor this out
    private Array<com.bituser.yabacc.droplets.TileSide> getTileSides() {
        Array<com.bituser.yabacc.droplets.TileSide> sides = new Array<com.bituser.yabacc.droplets.TileSide>();
        for (Iterator<com.bituser.yabacc.droplets.Tile> it = _tiles.iterator(); it.hasNext();) {
            com.bituser.yabacc.droplets.Tile tile = it.next();
            sides.add(tile.getLeftSide());
            sides.add(tile.getRightSide());
        }
        return sides;
    }

    @Override
    public void update (float deltaTime) {
        if (_winnerPlayer != null) {
            // Display game overview
            // Toggle game over
            return;
        }
        for (Iterator<com.bituser.yabacc.util.Entity> it = iterator(); it.hasNext();) {
            (it.next()).update(deltaTime);
        }
        com.bituser.yabacc.droplets.Tile tileToRemove = tileLogic(deltaTime);

        if (tileToRemove != null) {
            remove(tileToRemove);
            _tiles.removeValue(tileToRemove, true);
        }

        playerLogic();
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
    	for (Iterator<com.bituser.yabacc.util.Entity> it = iterator(); it.hasNext();) {
    		com.bituser.yabacc.util.Entity entity = it.next();
            entity.render(shapeRenderer);
        }
        if (!_player1.isAbleToPlay()) {
            _discardButton.render(shapeRenderer);
        }
    }

    @Override
    public void render (SpriteBatch batch) {
    	for (Iterator<com.bituser.yabacc.util.Entity> it = iterator(); it.hasNext();) {
    		com.bituser.yabacc.util.Entity entity = it.next();
            entity.render(batch);
        }
        if (!_player1.isAbleToPlay()) {
            _discardButton.render(batch);
        }
    }

    private void createTrophyCards (int height) {
        _trophyCards.add(new com.bituser.yabacc.droplets.TrophyCard(new Vector2(0, 0), Color.GRAY, 3, _font));
        _trophyCards.add(new com.bituser.yabacc.droplets.TrophyCard(new Vector2(0, 0), Color.BLUE, 4, _font));
        _trophyCards.add(new com.bituser.yabacc.droplets.TrophyCard(new Vector2(0, 0), Color.GREEN, 5, _font));
        _trophyCards.add(new com.bituser.yabacc.droplets.TrophyCard(new Vector2(0, 0), Color.YELLOW, 6, _font));
        _trophyCards.add(new com.bituser.yabacc.droplets.TrophyCard(new Vector2(0, 0), Color.RED, 7, _font));

        _trophyHand = new Hand<com.bituser.yabacc.droplets.TrophyCard>(120, height / 1.75f, 200, 200, 3);
        Array<com.bituser.yabacc.droplets.TrophyCard> cards = new Array<com.bituser.yabacc.droplets.TrophyCard>();
        cards.addAll(_trophyCards);
        _trophyHand.addAll(cards);
        _trophyHand.setColumns(3);
        add(_trophyHand);
    }

    private void drawStartingHandForEachPlayer () {
        for (Iterator<Player> it = _players.iterator(); it.hasNext();) {
            Player player = it.next();
            for (int i = 0; i < 8; i++) {
                player.add(getCardFromDeck());
            }
        }
    }

    private void createTiles (int width, int height, Bag bagOfTokens) {
        int tableSixth = height / 6 + 4;
        int centre = width / 2;
        int offset = height / 14;

        _tiles.add(new com.bituser.yabacc.droplets.Tile(centre, tableSixth + offset, 1, bagOfTokens, _font));
        _tiles.add(new com.bituser.yabacc.droplets.Tile(centre, tableSixth * 2 + offset, 2, bagOfTokens, _font));
        _tiles.add(new com.bituser.yabacc.droplets.Tile(centre, tableSixth * 3 + offset, 3, bagOfTokens, _font));
        _tiles.add(new com.bituser.yabacc.droplets.Tile(centre, tableSixth * 4 + offset, 4, bagOfTokens, _font));
        addAll(_tiles);
    }

    private void playerLogic () {
        for (Player player : _players) {
            checkIfTrophyCardWon(player);
            checkIfPlayerWon(player);
            checkIfPlayerHasCardsToDiscard(player);
            checkIfPlayerPicksUpCard(player);

            if (!_activePlayer.isCurrentTurn() && _activePlayer != player) {
                // Player that has just had their turn will pick up a card
                if (_activePlayer.getHeldCards() < 8) {
                    _activePlayer.add(getCardFromDeck());
                }

                // Swap active players
                _activePlayer = player;
                _activePlayer.startTurn();

                // check if player can actually make a move
                if (!checkIfPlayerCanMakeAMove(_activePlayer)) {
                    System.out.println("Player cannot make a move!");
                }
            }
        }
    }

    private void checkIfPlayerHasCardsToDiscard(Player player) {
        if (player.isReadyToDiscardCards()) {
            Array<Card> cardsToDiscard = player.getDiscardedCards();
            _discardedCards.addAll(cardsToDiscard);
            for (int i = 0; i < cardsToDiscard.size; i++) {
                player.add(getCardFromDeck());
            }
            player.setAbleToPlay(checkIfPlayerCanMakeAMove(player));
        }
    }

    private boolean checkIfPlayerCanMakeAMove(Player activePlayer) {
        return activePlayer.hasPlayableHand(getTileSides());
    }

    private void checkIfPlayerWon (Player player) {
        if (player.getTrophyCardCount() >= 3) {
            _winnerPlayer = player;
        }
    }

    private Card getCardFromDeck() {
        if (_deck.size() == 0) {
            _deck.addCards(_discardedCards);
            _discardedCards.clear();
        }
        return _deck.getCard();
    }

    private com.bituser.yabacc.droplets.Tile tileLogic (float deltaTime) {
        com.bituser.yabacc.droplets.Tile tileToRemove = null;

        for (Iterator<com.bituser.yabacc.droplets.Tile> it = _tiles.iterator(); it.hasNext();) {
            com.bituser.yabacc.droplets.Tile tile = it.next();
            checkIfTileIsFull(tile);

            _discardedCards.addAll(tile.getDiscardedCards());

            if (!tile.getActive()) {
                tileToRemove = tile;
            }
        }
        return tileToRemove;
    }

    private void checkIfPlayerPicksUpCard (Player player) {
        if (player.getHeldCards() < 8 && player.isCurrentTurn()) {
            player.add(getCardFromDeck());
        }
    }

    private void checkIfTrophyCardWon (Player player) {
        com.bituser.yabacc.droplets.TrophyCard cardToRemove = null;

        for (com.bituser.yabacc.droplets.TrophyCard card : _trophyCards) {
            if (player.hasTokensOfColor(card.getValue(), card.getColor())) {
                cardToRemove = card;
                player.removeTokens(card.getValue(), card.getColor());
                player.add(card);
                break;
            }
        }

        if (cardToRemove != null) {
            _trophyCards.removeValue(cardToRemove, true);
            _trophyHand.remove(cardToRemove);
        }
    }

    private void checkIfTileIsFull (com.bituser.yabacc.droplets.Tile tile) {
        if (tile.isFull()) {
            if (tile.getLeftSide().getValue() == tile.getRightSide().getValue()) {
                _activePlayer.add(tile.claimTokens());
                return;
            }

            if (tile.getFlipped()) {
                if (tile.getLeftSide().getValue() > tile.getRightSide().getValue()) {
                    _player1.add(tile.claimTokens());
                }
                else {
                    _player2.add(tile.claimTokens());
                }
            }
            else {
                if (tile.getLeftSide().getValue() < tile.getRightSide().getValue()) {
                    _player1.add(tile.claimTokens());
                }
                else {
                    _player2.add(tile.claimTokens());
                }
            }
        }
    }

    void touchDown(float x, float y, int pointer, int button, HumanPlayer player) {
        if (player.isCurrentTurn()) {
            player.touchDown(x, y, pointer, button);
        }
    }

    // Human player logic
    void touchUp(float x, float y, int pointer, int button, HumanPlayer player) {
        if (player.isCurrentTurn()) {
            Card card = player.getSelectedCard();

            // Might need refactoring to make it faster (i.e cache all sides)
            for (com.bituser.yabacc.droplets.TileSide side : getTileSides()) {
                if (card != null && card.overlaps(side.getRect()) && !card.isPlayed()) {
                    if (side.addCard(card)) {
                        player.playCard();
                        break;
                    }
                }
            }
            player.touchUp(x, y, pointer, button);

            if (!player.isAbleToPlay()) {
                _discardButton.touchUp(x, y, pointer, button, player);
            }
        }
    }

    void touchDragged(int x, int y, int pointer, HumanPlayer player) {
        if (player.isCurrentTurn()) {
            player.touchDragged(x, y, pointer);
        }
    }

    void mouseMoved(int x, int y, HumanPlayer player) {
        if (player.isCurrentTurn()) {
            player.mouseMoved(x, y);
            if (!player.isAbleToPlay()) {
                _discardButton.mouseMoved(x, y);
            }
        }
    }
}
