package com.learn.org.core.data;

import com.badlogic.gdx.math.Rectangle;

public class EndDoor {

    private Rectangle bounds;

    public EndDoor(float x, float y) {
        this.bounds = new Rectangle();
        this.bounds.x = x;
        this.bounds.y = y;
        this.bounds.width = this.bounds.height = 1;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
