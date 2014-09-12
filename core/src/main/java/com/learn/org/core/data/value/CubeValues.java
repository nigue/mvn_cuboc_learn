package com.learn.org.core.data.value;

public class CubeValues {

    private static final int FOLLOW = 0;
    private static final int FIXED = 1;
    private static final int CONTROLLED = 2;
    private static final int DEAD = 3;
    private static final float ACCELERATION = 20;
    private static final float MAX_VELOCITY = 4;
    private static final float DAMP = 0.80f;

    public static int getFOLLOW() {
        return FOLLOW;
    }

    public static int getFIXED() {
        return FIXED;
    }

    public static int getCONTROLLED() {
        return CONTROLLED;
    }

    public static int getDEAD() {
        return DEAD;
    }

    public static float getACCELERATION() {
        return ACCELERATION;
    }

    public static float getMAX_VELOCITY() {
        return MAX_VELOCITY;
    }

    public static float getDAMP() {
        return DAMP;
    }

}
