package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	ImageReference dragonRef;
	ImageReference fireballRef;
	ImageReference backgroundRef;
	
	Animation<TextureRegion> dragonAnim;
	Animation<TextureRegion> fireballAnim;
	Texture background;
	float elapsed;

	@Override
	public void create () {
		batch = new SpriteBatch();
		dragonAnim = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("dragon.gif").read());
		fireballAnim = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("fireball1.gif").read());
		background = new Texture("background.png");

		backgroundRef = new ImageReference(0f, 0f, 1280f, 720f);
		dragonRef = new ImageReference(0f, 0f, 197f, 152f);
		fireballRef = new ImageReference(dragonRef.x + 91, dragonRef.y + 145, 15f, 15f);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		elapsed += Gdx.graphics.getDeltaTime();

		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(fireballAnim.getKeyFrame(elapsed), fireballRef.x, fireballRef.y);
		batch.draw(dragonAnim.getKeyFrame(elapsed), dragonRef.x, dragonRef.y);
		batch.end();

		if (Gdx.input.isKeyPressed(Keys.LEFT)) dragonRef.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) dragonRef.x += 200 * Gdx.graphics.getDeltaTime();	
	
		if (Gdx.input.isKeyPressed(Keys.LEFT) && !fireballRef.isMoving()) fireballRef.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT) && !fireballRef.isMoving()) fireballRef.x += 200 * Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) fireballRef.startMoving() ;
		if (fireballRef.isMoving() && fireballRef.y <= 722) fireballRef.y += 15;
		if (fireballRef.y > 722) {
			fireballRef.stopMoving();
			fireballRef.resetPosition(dragonRef.x, dragonRef.y);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
