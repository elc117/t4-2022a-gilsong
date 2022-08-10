package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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



public class MainMenuScreen implements Screen {
    final ThroneInvaders game;
	static private int WIDTH = 1280;
	static private int HEIGHT = 720;

	Texture background;
	Texture logo;

	Music musica;

    OrthographicCamera camera;

	/*private Stage stage;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;*/


    public MainMenuScreen(final ThroneInvaders passed_game) {
		game = passed_game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		background = new Texture("blurredbackground.png");
		logo = new Texture("THRONE logo.png");
		musica = Gdx.audio.newMusic(Gdx.files.internal("they_are_coming.mp3"));


	}

    @Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.batch.draw(background, 0, 0);
		game.batch.draw(logo, 380, 150);
		game.batch.end();
		
		// If player activates the game, dispose of this menu.
		if (Gdx.input.isTouched()) {
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
		musica.setLooping(true);
		musica.setVolume(0.1f);
		musica.play();
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

}
