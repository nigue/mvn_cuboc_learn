package com.learn.org.core.data;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.learn.org.core.data.value.BobValues;

public class Bob extends BobValues {

    private Vector2 pos;
    private Vector2 accel;
    private Vector2 vel;
    private Rectangle bounds;
    private int state;
    private float stateTime;
    private int dir;
    private Map map;
    private boolean grounded;

    public Bob(Map map, float x, float y) {
        this.pos = new Vector2();
        this.accel = new Vector2();
        this.accel = new Vector2();
        this.bounds = new Rectangle();
        this.state = getSPAWN();
        this.stateTime = 0;
        this.dir = getLEFT();
        this.map = map;
        this.grounded = false;
        this.pos.x = x;
        this.pos.y = y;
        this.bounds.width = 0.6f;
        this.bounds.height = 0.8f;
        this.bounds.x = pos.x + 0.2f;
        this.bounds.y = pos.y;
    }
    
    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getAccel() {
        return accel;
    }

    public void setAccel(Vector2 accel) {
        this.accel = accel;
    }

    public Vector2 getVel() {
        return vel;
    }

    public void setVel(Vector2 vel) {
        this.vel = vel;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

}
