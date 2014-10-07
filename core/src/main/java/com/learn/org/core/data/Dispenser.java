package com.learn.org.core.data;

import com.badlogic.gdx.math.Rectangle;

public class Dispenser {

    private Rectangle bounds;
    private boolean active;

    public Dispenser(float x, float y) {
        this.bounds = new Rectangle();
        this.active = false;

        this.bounds.x = x;
        this.bounds.y = y;
        this.bounds.width = this.bounds.height = 1;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isActive() {
        return active;
    }

}
