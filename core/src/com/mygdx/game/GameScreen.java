package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    final ThroneInvaders game;

    SpriteBatch batch;
	ImageReference dragonRef;
	ImageReference fireballRef;
	ImageReference backgroundRef;
	OrthographicCamera camera;
	Vector3 touchPos;

	Texture background;
	Texture dragon;
	Texture fireball;
	float elapsed;

    public GameScreen(final ThroneInvaders passed_game) {
		game = passed_game; 
		
		background = new Texture("background.png");
		dragon = new Texture("dragon.gif");
		fireball = new Texture("fireball1.gif");
		
		// Init the camera objects.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		touchPos = new Vector3();
		
		batch = new SpriteBatch();
		
		backgroundRef = new ImageReference(0f, 0f, 1280f, 720f);
		dragonRef = new ImageReference(0f, 0f, 197f, 152f);
		fireballRef = new ImageReference(dragonRef.x + 91, dragonRef.y + 145, 15f, 15f);
	}

    @Override
	public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, .2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
        game.batch.draw(background, 0, 0);
		game.batch.draw(fireball, fireballRef.x, fireballRef.y);
		game.batch.draw(dragon, dragonRef.x, dragonRef.y);
		game.batch.end();
		
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
	public void dispose() {
		// Clear all the "native" resources
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
	