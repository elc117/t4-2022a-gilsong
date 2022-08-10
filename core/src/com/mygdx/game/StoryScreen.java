package com.mygdx.game;

import java.security.Key;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;



public class StoryScreen implements Screen {
    final ThroneInvaders game;
	static private int WIDTH = 1280;
	static private int HEIGHT = 720;

	Texture background;

	Music musica;

    OrthographicCamera camera;

    public StoryScreen(final ThroneInvaders passed_game, Music musica) {
		this.game = passed_game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		background = new Texture("historia.png");
		this.musica = musica;
	}

    @Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.batch.draw(background, 0, 0);
		game.batch.end();
		
		// If player activates the game, dispose of this menu.
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			game.setScreen(new GameScreen(game, musica));	
			dispose();
		}

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
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

}
