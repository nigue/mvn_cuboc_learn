package com.learn.org.core.data.value;

public class BobValues {

    private static final int IDLE = 0;
    private static final int RUN = 1;
    private static final int JUMP = 2;
    private static final int SPAWN = 3;
    private static final int DYING = 4;
    private static final int DEAD = 5;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private static final float ACCELERATION = 20f;
    private static final float JUMP_VELOCITY = 10;
    private static final float GRAVITY = 20.0f;
    private static final float MAX_VEL = 6f;
    private static final float DAMP = 0.90f;

    public static int getIDLE() {
        return IDLE;
    }

    public static int getRUN() {
        return RUN;
    }

    public static int getJUMP() {
        return JUMP;
    }

    public static int getSPAWN() {
        return SPAWN;
    }

    public static int getDYING() {
        return DYING;
    }

    public static int getDEAD() {
        return DEAD;
    }

    public static int getLEFT() {
        return LEFT;
    }

    public static int getRIGHT() {
        return RIGHT;
    }

    public static float getACCELERATION() {
        return ACCELERATION;
    }

    public static float getJUMP_VELOCITY() {
        return JUMP_VELOCITY;
    }

    public static float getGRAVITY() {
        return GRAVITY;
    }

    public static float getMAX_VEL() {
        return MAX_VEL;
    }

    public static float getDAMP() {
        return DAMP;
    }

}
