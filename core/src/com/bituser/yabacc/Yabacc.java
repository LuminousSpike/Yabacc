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

public class Yabacc extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	Texture img;
	Hand hand;
	
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
		hand = new Hand(200, 200);
		hand.addCard(new Card(1, cyrillicFont));
		hand.addCard(new Card(5, cyrillicFont));
		hand.addCard(new Card(13, cyrillicFont));
		

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
	    hand.update(Gdx.graphics.getDeltaTime());
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// Fills the screen with corn flour blue
		Gdx.gl.glClearColor(0.392156863f, 0.584313725f, 0.929411765f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		hand.render(shapeRenderer);
		shapeRenderer.end();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		hand.render(batch);
		batch.end();
	}
}
