package com.learn.org.core.values;

public class BobValues {

    private int idle = 0;
    private int run = 1;
    private int jump = 2;
    private int spawn = 3;
    private int dying = 4;
    private int dead = 5;
    private int left = -1;
    private int right = 1;
    private float acceleration = 20f;
    private float jumpVelocity = 10;
    private float gravity = 20.0f;
    private float maxVel = 6f;
    private float damp = 0.90f;

    public BobValues() {

        this.idle = 0;
        this.run = 1;
        this.jump = 2;
        this.spawn = 3;
        this.dying = 4;
        this.dead = 5;
        this.left = -1;
        this.right = 1;
        this.acceleration = 20f;
        this.jumpVelocity = 10;
        this.gravity = 20.0f;
        this.maxVel = 6f;
        this.damp = 0.90f;

    }

    public int getIdle() {
        return idle;
    }

    public int getRun() {
        return run;
    }

    public int getJump() {
        return jump;
    }

    public int getSpawn() {
        return spawn;
    }

    public int getDying() {
        return dying;
    }

    public int getDead() {
        return dead;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getJumpVelocity() {
        return jumpVelocity;
    }

    public float getGravity() {
        return gravity;
    }

    public float getMaxVel() {
        return maxVel;
    }

    public float getDamp() {
        return damp;
    }

}
