package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Frames {
   	private TextureRegion[] animationFrames;
    private Texture img;
    private int linhaInicial;
    private int xGrid;
    private int yGrid;
    private int tileWidth;
    private int tileHeigth;
    
    public Frames(int linhaInicial, int xGrid, int yGrid, int tileWidth, int tileHeigth, String path) {
        this.img = new Texture(path);
        this.linhaInicial = linhaInicial;
        this.xGrid = xGrid;
        this.yGrid = yGrid;
        this.tileWidth = tileWidth;
        this.tileHeigth = tileHeigth;   
    }


    public void createFrames(){
        TextureRegion[][] tmpFrames = TextureRegion.split(img, tileWidth,tileHeigth);
        animationFrames = new TextureRegion[xGrid * yGrid];
        int index = 0;
        for (int i = linhaInicial; i < linhaInicial + yGrid; i++){
            for (int j = 0; j < xGrid; j++) {
                animationFrames[index++] = tmpFrames[i][j];
            }
        }
    }

    public TextureRegion[] getAnimationFrames(){
        return animationFrames;
    }
}
