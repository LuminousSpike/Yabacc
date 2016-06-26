package com.bituser.yabacc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.Color;

public class Yabacc extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	Texture img;
	Hand hand;
	DropletDeck deck;
	Bag bag;
	Tile tile;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		camera= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		img = new Texture("badlogic.jpg");

		// font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Folks-Normal.ttf"));

		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 30;

		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
		BitmapFont cyrillicFont = generator.generateFont(parameter);
		generator.dispose();

		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		hand = new Hand(width / 2 - (50 * 8), 85);
		deck = new DropletDeck(width - 60, height / 2, cyrillicFont);
		bag = new Bag(width - 200, height / 2, cyrillicFont);
		tile = new Tile(width / 2, height / 2, Color.WHITE, 3, bag, cyrillicFont);

		for (int i = 0; i < 8; i++) {
		    hand.addCard(deck.getCard());
        }

		Gdx.input.setInputProcessor(new InputAdapter () {
		    @Override
		    public boolean touchDown (int x, int y, int pointer, int button) {
		        Vector3 input = new Vector3(x, y, 0);
		        camera.unproject(input);
		        hand.touchDown(input.x, input.y, pointer, button);
		        return true;
            }

            @Override
            public boolean touchUp (int x, int y, int pointer, int button) {
		        Vector3 input = new Vector3(x, y, 0);
		        camera.unproject(input);
		        hand.touchUp(input.x, input.y, pointer, button);
		        return true;
            }
            @Override
            public boolean touchDragged (int x, int y, int pointer) {
		        Vector3 input = new Vector3(x, y, 0);
		        camera.unproject(input);
                hand.touchDragged(input.x, input.y, pointer);
                return true;
            }

		    @Override
		    public boolean mouseMoved (int x, int y) {
		        Vector3 input = new Vector3(x, y, 0);
		        camera.unproject(input);
		        hand.mouseMoved(input.x, input.y);
		        return true;
            }
    	} );
    }

	@Override
	public void render () {
	    deck.update(Gdx.graphics.getDeltaTime());
	    tile.update(Gdx.graphics.getDeltaTime());
	    hand.update(Gdx.graphics.getDeltaTime());
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// Fills the screen with corn flour blue
		Gdx.gl.glClearColor(0.392156863f, 0.584313725f, 0.929411765f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		deck.render(shapeRenderer);
		bag.render(shapeRenderer);
		tile.render(shapeRenderer);
		hand.render(shapeRenderer);
		shapeRenderer.end();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		deck.render(batch);
		bag.render(batch);
		tile.render(batch);
		hand.render(batch);
		batch.end();
	}
}
