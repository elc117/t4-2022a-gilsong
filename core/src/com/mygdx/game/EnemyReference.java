package com.mygdx.game;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.ImageReference;

public class EnemyReference extends ImageReference {
    Boolean right;
    Boolean left;
    Boolean up;
    Boolean down;
    
    public EnemyReference() {
        super();
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;
    }

    public EnemyReference(Float x, Float y, Float width, Float height) {
        super(x, y, width, height);
    }

    public void setRight() {
        this.right = true;
        this.left = false;
        this.up = false;
        this.down = false;
    }

    public void setLeft() {
        this.right = false;
        this.left = true;
        this.up = false;
        this.down = false;
    }

    public void setUp() {
        this.right = false;
        this.left = false;
        this.up = true;
        this.down = false;
    }

    public void setDown() {
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = true;
    }

    public Boolean isRight() {
        return this.right;
    }

    public Boolean isLeft() {
        return this.left;
    }

    public Boolean isUp() {
        return this.up;
    }

    public Boolean isDown() {
        return this.down;
    }
}
