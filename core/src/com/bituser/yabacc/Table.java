package com.bituser.yabacc;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

class Table extends GenericCollection<Entity> {
    private final Array<Player> _players = new Array<Player>();
    private final Array<TrophyCard> _trophyCards = new Array<TrophyCard>();
    private final Array<Card> _discardedCards = new Array<Card>();

    private Hand _trophyHand;
    private Bag _bag;
    private Deck _deck;

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

        add(_bag);
        add(_deck);

        createTrophyCards(tableHeight);
        createTiles(tableWidth, tableHeight, bagOfTokens);

        _player1 = players.get(0);
        _player2 = players.get(1);
        _players.addAll(players);
        
        // quick and dirty way of casting to parent class
        Array<Entity> entities = new Array<Entity>();
        entities.addAll(players);
        
        addAll(entities);

        if (_rand.nextInt(100) < 50) {
            _activePlayer = _player1;
        }
        else {
            _activePlayer = _player2;
        }

        drawStartingHandForEachPlayer();
    }

    // Need to refactor this out
    Array<Player> getPlayers() { return _players; }

    Player getWinner() { return _winnerPlayer; }

    // Need to refactor this out
    private Array<TileSide> getTileSides() {
        Array<TileSide> sides = new Array<TileSide>();
        for (Iterator<Entity> it = iterator(); it.hasNext();) {
    		Entity entity = it.next();
            if (entity instanceof Tile) {
                Tile tile = (Tile)entity;
                sides.add(tile.getLeftSide());
                sides.add(tile.getRightSide());
            }
        }
        return sides;
    }

    @Override
    public void update (float deltaTime) {
        Tile tileToRemove = tileLogic(deltaTime);
        deckLogic();

        if (tileToRemove != null) {
            remove(tileToRemove);
        }

        playerLogic();
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
    	for (Iterator<Entity> it = iterator(); it.hasNext();) {
    		Entity entity = it.next();
            entity.render(shapeRenderer);
        }
    }

    @Override
    public void render (SpriteBatch batch) {
    	for (Iterator<Entity> it = iterator(); it.hasNext();) {
    		Entity entity = it.next();
            entity.render(batch);
        }
    }

    private void createTrophyCards (int height) {
        _trophyCards.add(new TrophyCard(new Vector2(0, 0), Color.GRAY, 3, _font));
        _trophyCards.add(new TrophyCard(new Vector2(0, 0), Color.BLUE, 4, _font));
        _trophyCards.add(new TrophyCard(new Vector2(0, 0), Color.GREEN, 5, _font));
        _trophyCards.add(new TrophyCard(new Vector2(0, 0), Color.YELLOW, 6, _font));
        _trophyCards.add(new TrophyCard(new Vector2(0, 0), Color.RED, 7, _font));

        _trophyHand = new Hand<TrophyCard>(120, height / 1.75f, 200, 200, 3);
        Array<TrophyCard> cards = new Array<TrophyCard>();
        cards.addAll(_trophyCards);
        _trophyHand.addAll(cards);
        _trophyHand.setColumns(3);
        add(_trophyHand);
    }

    private void drawStartingHandForEachPlayer () {
    	for (Iterator<Entity> it = iterator(); it.hasNext();) {
    		Entity entity = it.next();
            if (entity instanceof Player) {
                Player player = (Player)entity;
                for (int i = 0; i < 8; i++) {
                    player.add(_deck.getCard());
                }
            }
        }
    }

    private void createTiles (int width, int height, Bag bagOfTokens) {
        int tableSixth = height / 6 + 4;
        int centre = width / 2;
        int offset = height / 14;

        add(new Tile(centre, tableSixth + offset, 1, bagOfTokens, _font));
        add(new Tile(centre, tableSixth * 2 + offset, 2, bagOfTokens, _font));
        add(new Tile(centre, tableSixth * 3 + offset, 3, bagOfTokens, _font));
        add(new Tile(centre, tableSixth * 4 + offset, 4, bagOfTokens, _font));
    }

    private void playerLogic () {
        for (Player player : _players) {
            checkIfTrophyCardWon(player);
            checkIfPlayerWon(player);
            checkIfPlayerPicksUpCard(player);

            if (!_activePlayer.isCurrentTurn() && _activePlayer != player) {
                _activePlayer = player;
                _activePlayer.startTurn();
            }
        }
    }

    private void checkIfPlayerWon (Player player) {
        if (player.getTrophyCardCount() >= 3) {
            _winnerPlayer = player;
        }
    }

    private void deckLogic () {
        if (_deck.size() == 0) {
            _deck.addCards(_discardedCards);
            _discardedCards.clear();
        }
    }

    private Tile tileLogic (float deltaTime) {
        Tile tileToRemove = null;

        for (Iterator<Entity> it = iterator(); it.hasNext();) {
    		Entity entity = it.next();
            entity.update(deltaTime);
            if (entity instanceof Tile) {
                Tile tile = (Tile)entity;

                checkIfTileIsFull(tile);

                _discardedCards.addAll(tile.getDiscardedCards());

                if (!tile.getActive()) {
                    tileToRemove = tile;
                }
            }
        }
        return tileToRemove;
    }

    private void checkIfPlayerPicksUpCard (Player player) {
        if (player.getHeldCards() < 8 && player.isCurrentTurn()) {
            player.add(_deck.getCard());
        }
    }

    private void checkIfTrophyCardWon (Player player) {
        TrophyCard cardToRemove = null;

        for (TrophyCard card : _trophyCards) {
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

    private void checkIfTileIsFull (Tile tile) {
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

    void touchUp(float x, float y, int pointer, int button, HumanPlayer player) {
        if (player.isCurrentTurn()) {
            Card card = player.getSelectedCard();

            // Might need refactoring to make it faster (i.e cache all sides)
            for (TileSide side : getTileSides()) {
                if (card != null && card.overlaps(side.getRect()) && !card.isPlayed()) {
                    if (side.addCard(card)) {
                        player.playCard(_deck.getCard());
                        break;
                    }
                }
            }
            player.touchUp(x, y, pointer, button);
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
        }
    }
}
