package com.learn.org.core.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.learn.org.core.data.value.CubeValues;

public class Cube extends CubeValues {

    private Map map;
    private Vector2 pos;
    private Vector2 accel;
    private Vector2 vel;
    private Rectangle bounds;
    private int state;
    private float stateTime;
    private Rectangle controllButtonRect;
    private Rectangle followButtonRect;
    private Rectangle dpadRect;
    private Vector2 target;
    private Rectangle[] r = {new Rectangle(), new Rectangle(), new Rectangle(), new Rectangle()};

    public Cube(Map map, float x, float y) {
        this.map = map;
        this.pos = new Vector2();
        this.pos.x = x;
        this.pos.y = y;
        this.accel = new Vector2();
        this.vel = new Vector2();
        this.bounds = new Rectangle();
        this.bounds.x = pos.x + 0.2f;
        this.bounds.y = pos.y + 0.2f;
        this.bounds.width = this.bounds.height = 1.0f;
        this.state = getFOLLOW();
        this.stateTime = 0;
        this.controllButtonRect = new Rectangle(480 - 64, 320 - 64, 64, 64);
        this.followButtonRect = new Rectangle(480 - 64, 320 - 138, 64, 64);
        this.dpadRect = new Rectangle(0, 0, 128, 128);
        this.target = new Vector2();
    }

    private void processKeys() {
        float x0 = (Gdx.input.getX(0) / (float) Gdx.graphics.getWidth()) * 480;
        float x1 = (Gdx.input.getX(1) / (float) Gdx.graphics.getWidth()) * 480;
        float y0 = 320 - (Gdx.input.getY(0) / (float) Gdx.graphics.getHeight()) * 320;
        float y1 = 320 - (Gdx.input.getY(1) / (float) Gdx.graphics.getHeight()) * 320;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
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

    public Rectangle getControllButtonRect() {
        return controllButtonRect;
    }

    public void setControllButtonRect(Rectangle controllButtonRect) {
        this.controllButtonRect = controllButtonRect;
    }

    public Rectangle getFollowButtonRect() {
        return followButtonRect;
    }

    public void setFollowButtonRect(Rectangle followButtonRect) {
        this.followButtonRect = followButtonRect;
    }

    public Rectangle getDpadRect() {
        return dpadRect;
    }

    public void setDpadRect(Rectangle dpadRect) {
        this.dpadRect = dpadRect;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

}
