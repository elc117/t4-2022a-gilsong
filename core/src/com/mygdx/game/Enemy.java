package com.mygdx.game;
import com.mygdx.game.ImageReference;

public class Enemy extends ImageReference {
    public Enemy(){
        super();
    }

    public Enemy(Float x, Float y, Float width, Float height) {
        super(x, y, width, height);
    }

    @Override
    public void resetPosition(Float x, Float y) {
        this.x = x;
        this.y = y;
    }
}
