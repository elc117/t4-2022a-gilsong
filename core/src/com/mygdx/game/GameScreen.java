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
	Array<EnemyReference> enemies;
	Array<ImageReference> spires;

	Frames enemyFrames;
   	Animation<TextureRegion> enemyAnimation;

	Frames dragonFrames;
   	Animation<TextureRegion> dragonAnimation;

   	
	OrthographicCamera camera;
	Vector3 touchPos;

	Texture background;
	Texture fireball;
    Texture spire;

	Music musica;

    Sound fireballSound;
    Sound damageSound;

	float elapsedTime;
    int score;
	float timeSeconds;

    public GameScreen(final ThroneInvaders passed_game, Music musica) {
		game = passed_game; 
		
		background = new Texture("backgroundB.png");
		fireball = new Texture("fireball1.gif");
		dragonFrames = new Frames(0, 4, 2, 205, 155, "dragon.png");
		enemyFrames = new Frames(10, 7, 1, 64, 64, "enemy.png");
		spire = new Texture("lance14x69.png");
		dragonFrames.createFrames();
		enemyFrames.createFrames();
		
		// Init the camera objects.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		touchPos = new Vector3();
		
		batch = new SpriteBatch();
		dragonRef = new ImageReference(0f, 0f, 180f, 100f);
		fireballRef = new Fireball(dragonRef.x, dragonRef.y, 15f, 15f);
		enemies = new Array<EnemyReference>();
		spires = new Array<ImageReference>();

		dragonAnimation = new Animation<TextureRegion>(1f/6f, dragonFrames.getAnimationFrames());
      	enemyAnimation = new Animation<TextureRegion>(1f/4f, enemyFrames.getAnimationFrames());
		spawnEnemies(10, 5);

        fireballSound = Gdx.audio.newSound(Gdx.files.internal("fireballSound.mp3"));
        damageSound = Gdx.audio.newSound(Gdx.files.internal("DamageSound.mp3"));

		this.musica = musica;
		timeSeconds = 5;
        score = 0;
	}

    @Override
	public void render(float delta) {
		timeSeconds += Gdx.graphics.getDeltaTime();
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
		for (EnemyReference enemyRef: enemies) {
			game.batch.draw(enemyAnimation.getKeyFrame(elapsedTime,true), enemyRef.x, enemyRef.y);
		}
		for (ImageReference spireRef: spires) {
			if (spireRef.isVisible()) game.batch.draw(spire, spireRef.x, spireRef.y);
		}
        game.font.draw(game.batch, Integer.toString(score) , 30,650);
		game.batch.draw(dragonAnimation.getKeyFrame(elapsedTime, true), dragonRef.x, dragonRef.y);
		game.batch.end();
		
		// controla movimento do dragão
		if (Gdx.input.isKeyPressed(Keys.LEFT) && dragonRef.x >= 0) dragonRef.x -= 300 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT)&& dragonRef.x <= 1075) dragonRef.x += 300 * Gdx.graphics.getDeltaTime();	
		// controla movimento da bola de fogo
		if (Gdx.input.isKeyPressed(Keys.LEFT) && !fireballRef.isMoving() && dragonRef.x >= 0) fireballRef.x -= 300 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT) && !fireballRef.isMoving() && dragonRef.x <= 1075) fireballRef.x += 300 * Gdx.graphics.getDeltaTime();
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

		if (enemies.isEmpty()) spawnEnemies(10,5);
		Iterator<EnemyReference> iter = enemies.iterator();
		while (iter.hasNext()) {
			EnemyReference enemyRef = iter.next();
			//controla movimento do inimigo
			if (enemyRef.isMoving() && enemyRef.y >= 130) enemyRef.y -= 0.2;
			// se tocar na muralha
			if (enemyRef.y < 130) {
				musica.stop();
                game.setScreen(new EndGameScreen(game, score));	
				break;
			}

			if (fireballRef.overlaps(enemyRef)) {
				damageSound.play(0.5f);
				score += 10;
				iter.remove();
				fireballRef.stopMoving();
				fireballRef.setUnvisible();
				fireballRef.resetPosition(dragonRef.x, dragonRef.y);
			}
		}

		if (spires.isEmpty()) spawnSpires();
		Iterator<ImageReference> iter1 = spires.iterator();
		while (iter1.hasNext()) {
			ImageReference spireRef = iter1.next();

			if (spireRef.isMoving()) spireRef.y -= 3;

			if(spireRef.y < -100) iter1.remove();

			if (spireRef.overlaps(dragonRef)){
				musica.stop();
				game.setScreen(new EndGameScreen(game, score));	
			}
		}
		if (timeSeconds >= 10) {
			setShots(7);
			timeSeconds = 0;
		}
	}

	private void spawnEnemies(int weight, int height) {
		float inicio = MathUtils.random(20, 700);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < weight; j++) {
				EnemyReference enemyRef = new EnemyReference(inicio + 45 *j , 720f + 50*i, 64f, 64f);
				enemyRef.startMoving();
				enemyRef.setVisible();
				this.enemies.add(enemyRef);
			}
		}
	}

	private void spawnSpires() {
		for (EnemyReference enemyRef: enemies) {
			ImageReference spireRef = new ImageReference(enemyRef.x, enemyRef.y, 14f, 69f);
			spires.add(spireRef);
		}
	}

	private void setShots(int numOfShots) {
		if (enemies.size < numOfShots) {
			numOfShots = enemies.size;
		}
		Iterator<ImageReference> iter = spires.iterator();

		for (int i = 0; i < numOfShots; i++) {
			int pos = MathUtils.random(0, enemies.size - 1);
			EnemyReference enemyRef = enemies.get(pos);

			if (iter.hasNext()) {
				ImageReference spireRef = iter.next();
				spireRef.x = enemyRef.x;
				spireRef.y = enemyRef.y;
				spireRef.setVisible();
				spireRef.startMoving();
			}
		}
	}

    @Override
	public void dispose() {
		
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
	