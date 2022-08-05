package com.mygdx.game;
import com.badlogic.gdx.maps.ImageResolver;
import com.mygdx.game.ImageReference;

public class Fireball extends ImageReference {
    
    public Fireball(){
        super();
    }

    public Fireball(Float x, Float y, Float width, Float height) {
        super(x + 91, y + 145, width, height);
    }

    @Override
    public void resetPosition(Float x, Float y) {
        this.x = x + 91;
        this.y = y + 145;
    }

    
}
