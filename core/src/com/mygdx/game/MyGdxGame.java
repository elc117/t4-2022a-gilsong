package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Music gotMusic;
	Rectangle image;
	Texture dragon;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		dragon = new Texture("dragon.gif");
		image = new Rectangle();
		image.x = 256/2;
		image.y = 20;
		image.width = 256;
		image.height = 256;	
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(dragon, image.x, image.y);
		batch.end();

		if(Gdx.input.isKeyPressed(Keys.LEFT)) image.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) image.x += 200 * Gdx.graphics.getDeltaTime();
		if
		
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		dragon.dispose();
		
	}
}
