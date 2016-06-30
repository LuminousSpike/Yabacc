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
    DropletGame dropletGame;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		camera= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		img = new Texture("badlogic.jpg");

		// font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Folks-Normal.ttf"));

		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 20;

		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
		BitmapFont cyrillicFont = generator.generateFont(parameter);
		generator.dispose();

		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

        dropletGame = new DropletGame ((int)width, (int)height, cyrillicFont);

		Gdx.input.setInputProcessor(new InputAdapter () {
		    @Override
		    public boolean touchDown (int x, int y, int pointer, int button) {
		        Vector3 input = new Vector3(x, y, 0);
		        camera.unproject(input);
		        dropletGame.touchDown((int)input.x, (int)input.y, pointer, button);
		        return true;
            }

            @Override
            public boolean touchUp (int x, int y, int pointer, int button) {
		        Vector3 input = new Vector3(x, y, 0);
		        camera.unproject(input);
		        dropletGame.touchUp((int)input.x, (int)input.y, pointer, button);
		        return true;
            }
            @Override
            public boolean touchDragged (int x, int y, int pointer) {
		        Vector3 input = new Vector3(x, y, 0);
		        camera.unproject(input);
                dropletGame.touchDragged((int)input.x, (int)input.y, pointer);
                return true;
            }

		    @Override
		    public boolean mouseMoved (int x, int y) {
		        Vector3 input = new Vector3(x, y, 0);
		        camera.unproject(input);
		        dropletGame.mouseMoved((int)input.x, (int)input.y);
		        return true;
            }
    	} );
    }

	@Override
	public void render () {
	    dropletGame.update(Gdx.graphics.getDeltaTime());
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// Fills the screen with corn flour blue
		Gdx.gl.glClearColor(0.392156863f, 0.584313725f, 0.929411765f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		dropletGame.render(shapeRenderer);
		shapeRenderer.end();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		dropletGame.render(batch);
		batch.end();
	}
}
