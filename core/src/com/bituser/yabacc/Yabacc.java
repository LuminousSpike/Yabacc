package com.bituser.yabacc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Yabacc extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture img;
	Card card;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		img = new Texture("badlogic.jpg");
		card = new Card(0, 0);
	}

	@Override
	public void render () {
		// Fills the screen with corn flour blue
		Gdx.gl.glClearColor(0.392156863f, 0.584313725f, 0.929411765f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		card.render(shapeRenderer);
		shapeRenderer.end();
	}
}
