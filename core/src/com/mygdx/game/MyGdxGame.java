package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Rectangle image1;
	Rectangle image2;
	
	Animation<TextureRegion> animation1;
	Animation<TextureRegion> animation2;
	float elapsed;

	@Override
	public void create () {
		batch = new SpriteBatch();
		animation1 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("dragon.gif").read());
		animation2 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("fireball1.gif").read());
		image1 = new Rectangle();
		image2 = new Rectangle();
		image1.x = 0;
		image1.y = 0;
		image2.x = image1.x + 91;
		image2.y = image1.y + 145;
		image1.width = 197;
		image1.height = 152;	
		image2.width = 15;
		image2.height = 15;
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		elapsed += Gdx.graphics.getDeltaTime();
		batch.begin();
		batch.draw(animation2.getKeyFrame(elapsed), image2.x, image2.y);
		batch.draw(animation1.getKeyFrame(elapsed), image1.x, image1.y);
		//batch.draw(fireball, image2.x, image2.y);
		batch.end();

		if(Gdx.input.isKeyPressed(Keys.LEFT)) image1.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) image1.x += 200 * Gdx.graphics.getDeltaTime();	
	
		if(Gdx.input.isKeyPressed(Keys.LEFT)) image2.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) image2.x += 200 * Gdx.graphics.getDeltaTime();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
