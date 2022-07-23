package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class ImageReference extends Rectangle{
    private Boolean visible;
    private Boolean moving;

    public ImageReference() {
        visible = false;
        moving = false;
    }

    public ImageReference(Float x, Float y, Float width, Float height) {
        visible = false;
        moving = false;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setVisible() {
        this.visible = true;
    }

    public void setUnvisible() {
        this.visible = false;
    }

    public void startMoving() {
        moving = true;
    }

    public void stopMoving() {
        moving = false;
    }

    public Boolean isMoving() {
        return moving;
    }

    public void resetPosition(Float x, Float y) {
        this.x = x + 91;
        this.y = y + 145;
    }

}
