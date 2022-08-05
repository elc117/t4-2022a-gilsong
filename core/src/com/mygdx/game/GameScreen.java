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
	Array<Enemy> enemies;
	
	OrthographicCamera camera;
	Vector3 touchPos;

	Texture background;
	Texture dragon;
	Texture fireball;
	Texture enemy;

    public GameScreen(final ThroneInvaders passed_game) {
		game = passed_game; 
		
		background = new Texture("background.png");
		dragon = new Texture("dragon.gif");
		fireball = new Texture("fireball1.gif");
		enemy = new Texture("teste1.png");
		
		// Init the camera objects.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		touchPos = new Vector3();
		
		batch = new SpriteBatch();
		dragonRef = new ImageReference(0f, 0f, 197f, 152f);
		fireballRef = new Fireball(dragonRef.x, dragonRef.y, 15f, 15f);
		enemies = new Array<Enemy>();
		spawnEnemies();
	}

    @Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, .2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
        game.batch.draw(background, 0, 0);
		game.batch.draw(fireball, fireballRef.x, fireballRef.y);
		game.batch.draw(dragon, dragonRef.x, dragonRef.y);
		for (Enemy enemyRef: enemies) {
			if (enemyRef.isVisible()) game.batch.draw(enemy, enemyRef.x, enemyRef.y);
		}
		game.batch.end();
		
		// controla movimento do dragão
		if (Gdx.input.isKeyPressed(Keys.LEFT)) dragonRef.x -= 300 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) dragonRef.x += 300 * Gdx.graphics.getDeltaTime();	
		// controla movimento da bola de fogo
		if (Gdx.input.isKeyPressed(Keys.LEFT) && !fireballRef.isMoving()) fireballRef.x -= 300 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT) && !fireballRef.isMoving()) fireballRef.x += 300 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) fireballRef.startMoving() ;
		
		if (fireballRef.isMoving() && fireballRef.y <= 722) fireballRef.y += 15;
		if (fireballRef.y > 722) {
			fireballRef.stopMoving();
			fireballRef.resetPosition(dragonRef.x, dragonRef.y);
		}

		if (enemies.isEmpty()) spawnEnemies();
		Iterator<Enemy> iter = enemies.iterator();
		while (iter.hasNext()) {
			Enemy enemyRef = iter.next();
			//controla movimento do inimigo
			if (enemyRef.isMoving() && enemyRef.y >= 130) enemyRef.y -= 0.2;
			// se tocar na muralha
			if (enemyRef.y < 130) {
				enemyRef.resetPosition(enemyRef.x, enemyRef.y + 520f);
			}

			if(fireballRef.overlaps(enemyRef)) {
				//som de morrendo
				//dá pra aumentar pontos
				iter.remove();
				fireballRef.stopMoving();
				fireballRef.resetPosition(dragonRef.x, dragonRef.y);
			}
		}

	}

	private void spawnEnemies() {

		float inicio = MathUtils.random(20, 960);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				Enemy enemyRef = new Enemy(inicio + 32 *j , 650f + 36*i, 32f, 36f);
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
	