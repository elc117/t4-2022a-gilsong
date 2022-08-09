package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    final ThroneInvaders game;

    SpriteBatch batch;
	ImageReference dragonRef;
	ImageReference fireballRef;
	Array<ImageReference> enemies;

	Frames enemyFrames;
   	Animation<TextureRegion> enemyAnimation;

	Frames dragonFrames;
   	Animation<TextureRegion> dragonAnimation;
   	
	OrthographicCamera camera;
	Vector3 touchPos;

	Texture background;
	Texture fireball;

    Sound fireballSound;
    Sound damageSound;

	float elapsedTime;
    int score;

    public GameScreen(final ThroneInvaders passed_game, Music musica) {
		game = passed_game; 
		
		background = new Texture("background.png");
		fireball = new Texture("fireball1.gif");
		dragonFrames = new Frames(0, 4, 2, 205, 155, "dragon.png");
		enemyFrames = new Frames(10, 7, 1, 64, 64, "enemy.png");
		dragonFrames.createFrames();
		enemyFrames.createFrames();
		
		// Init the camera objects.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		touchPos = new Vector3();
		
		batch = new SpriteBatch();
		dragonRef = new ImageReference(0f, 0f, 205f, 155f);
		fireballRef = new Fireball(dragonRef.x, dragonRef.y, 15f, 15f);
		enemies = new Array<ImageReference>();

		dragonAnimation = new Animation<TextureRegion>(1f/6f, dragonFrames.getAnimationFrames());
      	enemyAnimation = new Animation<TextureRegion>(1f/4f, enemyFrames.getAnimationFrames());
		spawnEnemies();

        fireballSound = Gdx.audio.newSound(Gdx.files.internal("fireballSound.mp3"));
        damageSound = Gdx.audio.newSound(Gdx.files.internal("DamageSound.mp3"));

        score = 0;
	}

    @Override
	public void render(float delta) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, .2f, 1);
        game.font.setColor(Color.BLACK);
        game.font.getData().setScale(3);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
        game.batch.draw(background, 0, 0);
		if (fireballRef.isVisible()) game.batch.draw(fireball, fireballRef.x, fireballRef.y);
		game.batch.draw(dragonAnimation.getKeyFrame(elapsedTime, true), dragonRef.x, dragonRef.y);
		for (ImageReference enemyRef: enemies) {
			game.batch.draw(enemyAnimation.getKeyFrame(elapsedTime,true), enemyRef.x, enemyRef.y);
		}
        game.font.draw(game.batch, Integer.toString(score) , 30,650);
		game.batch.end();
		
		// controla movimento do dragão
		if (Gdx.input.isKeyPressed(Keys.LEFT)) dragonRef.x -= 300 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) dragonRef.x += 300 * Gdx.graphics.getDeltaTime();	
		// controla movimento da bola de fogo
		if (Gdx.input.isKeyPressed(Keys.LEFT) && !fireballRef.isMoving()) fireballRef.x -= 300 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT) && !fireballRef.isMoving()) fireballRef.x += 300 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !fireballRef.isMoving()){
			fireballSound.play(0.5f);
			fireballRef.startMoving(); 
			fireballRef.setVisible();
		}
		if (fireballRef.isMoving() && fireballRef.y <= 722) fireballRef.y += 15;
		if (fireballRef.y > 722) {
			fireballRef.stopMoving();
			fireballRef.setUnvisible();
			fireballRef.resetPosition(dragonRef.x, dragonRef.y);
		}

		if (enemies.isEmpty()) spawnEnemies();
		Iterator<ImageReference> iter = enemies.iterator();
		while (iter.hasNext()) {
			ImageReference enemyRef = iter.next();
			//controla movimento do inimigo
			if (enemyRef.isMoving() && enemyRef.y >= 130) enemyRef.y -= 0.2;
			// se tocar na muralha
			if (enemyRef.y < 130) {
				enemyRef.resetPosition(enemyRef.x, enemyRef.y + 520f);
                //end-game
			}

			if(fireballRef.overlaps(enemyRef)) {
				damageSound.play(0.5f);
				score += 10;
				iter.remove();
				fireballRef.stopMoving();
				fireballRef.setUnvisible();
				fireballRef.resetPosition(dragonRef.x, dragonRef.y);
			}
		}

	}

	private void spawnEnemies() {
		float inicio = MathUtils.random(20, 900);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				ImageReference enemyRef = new ImageReference(inicio + 45 *j , 650f + 50*i, 64f, 64f);
				enemyRef.startMoving();
				enemyRef.setVisible();
				enemies.add(enemyRef);
			}
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
	