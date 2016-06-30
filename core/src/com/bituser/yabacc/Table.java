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
import java.util.*;

public class Table extends EntityCollection {
    ArrayList<Player> _players = new ArrayList<Player>();
    ArrayList<TrophyCard> _trophyCards = new ArrayList<TrophyCard>();
    ArrayList<Card> _discardedCards = new ArrayList<Card>();

    Hand _trophyHand;
    Bag _bag;
    Deck _deck;

    Player _player1, _player2, _activePlayer, _winnerPlayer;
    Random _rand = new Random();

    BitmapFont _font;

    // Does this need to exist?
    public Table (float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public Table (int tableWidth, int tableHeight, Bag bagOfTokens, Deck deckOfCards, ArrayList<Player> players, BitmapFont font) {
        this (0, 0, tableWidth, tableHeight);
        _bag = bagOfTokens;
        _deck = deckOfCards;
        _font = font;

        _entities.add(_bag);
        _entities.add(_deck);

        createTrophyCards(tableHeight);
        createTiles(tableWidth, tableHeight, bagOfTokens);

        _player1 = players.get(0);
        _player2 = players.get(1);
        _players.addAll(players);
        _entities.addAll(players);

        if (_rand.nextInt(100) < 50) {
            _activePlayer = _player1;
        }
        else {
            _activePlayer = _player2;
        }

        drawStartingHandForEachPlayer();
    }

    // Need to refactor this out
    public ArrayList<Player> getPlayers () { return _players; }

    public Player getWinner () { return _winnerPlayer; }

    // Need to refactor this out
    public ArrayList<TileSide> getTileSides () {
        ArrayList<TileSide> sides = new ArrayList<TileSide>();
        for (Entity entity : _entities) {
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
            _entities.remove(tileToRemove);
        }

        playerLogic();
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        for (Entity entity : _entities) {
            entity.render(shapeRenderer);
        }
    }

    @Override
    public void render (SpriteBatch batch) {
        for (Entity entity : _entities) {
            entity.render(batch);
        }
    }

    private void createTrophyCards (int height) {
        _trophyCards.add(new TrophyCard(new Vector2(0, 0), Color.GRAY, 3, _font));
        _trophyCards.add(new TrophyCard(new Vector2(0, 0), Color.BLUE, 4, _font));
        _trophyCards.add(new TrophyCard(new Vector2(0, 0), Color.GREEN, 5, _font));
        _trophyCards.add(new TrophyCard(new Vector2(0, 0), Color.YELLOW, 6, _font));
        _trophyCards.add(new TrophyCard(new Vector2(0, 0), Color.RED, 7, _font));

        _trophyHand = new Hand(120, height / 1.75f, 200, 200, 3);
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.addAll(_trophyCards);
        _trophyHand.addCards(cards);
        _entities.add(_trophyHand);
    }

    private void drawStartingHandForEachPlayer () {
        for (Entity entity : _entities) {
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

        _entities.add(new Tile(centre, tableSixth + offset, Color.ORANGE, Color.BLUE, 1, bagOfTokens, _font));
        _entities.add(new Tile(centre, tableSixth * 2 + offset, Color.ORANGE, Color.BLUE, 2, bagOfTokens, _font));
        _entities.add(new Tile(centre, tableSixth * 3 + offset, Color.ORANGE, Color.BLUE, 3, bagOfTokens, _font));
        _entities.add(new Tile(centre, tableSixth * 4 + offset, Color.ORANGE, Color.BLUE, 4, bagOfTokens, _font));
    }

    private void playerLogic () {
        for (Player player : _players) {
            checkIfTrophyCardWon(player);
            checkIfPlayerWon(player);
            checkIfPlayerPicksUpCard(player);

            if (_activePlayer.isCurrentTurn() == false && _activePlayer != player) {
                _activePlayer = player;
                _activePlayer.startTurn();
            }
        }
    }

    private void checkIfPlayerWon (Player player) {
        if (player.getTropyCardCount() >= 3) {
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

        for (Entity entity : _entities) {
            entity.update(deltaTime);
            if (entity instanceof Tile) {
                Tile tile = (Tile)entity;

                checkIfTileIsFull(tile);

                _discardedCards.addAll(tile.getDiscardedCards());

                if (tile.getActive() == false) {
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
            _trophyCards.remove(cardToRemove);
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

    public void touchDown (float x, float y, int pointer, int button, HumanPlayer player) {
        if (player.isCurrentTurn()) {
            player.touchDown(x, y, pointer, button);
        }
    }

    public void touchUp (float x, float y, int pointer, int button, HumanPlayer player) {
        if (player.isCurrentTurn()) {
            Card card = player.getSelectedCard();

            // Might need refactoring to make it faster (i.e cache all sides)
            for (TileSide side : getTileSides()) {
                if (card != null && card.overlaps(side.getRect()) && card.isPlayed() == false) {
                    if (side.addCard(card)) {
                        player.playCard(_deck.getCard());
                        break;
                    }
                }
            }
            player.touchUp(x, y, pointer, button);
        }
    }

    public void touchDragged (int x, int y, int pointer, HumanPlayer player) {
        if (player.isCurrentTurn()) {
            player.touchDragged(x, y, pointer);
        }
    }

    public void mouseMoved (int x, int y, HumanPlayer player) {
        if (player.isCurrentTurn()) {
            player.mouseMoved(x, y);
        }
    }
}
