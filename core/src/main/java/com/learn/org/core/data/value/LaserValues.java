package com.learn.org.core.data.value;

public class LaserValues {

    private static final int FORWARD = 1;
    private static final int BACKWARD = -1;
    private static final float FORWARD_VEL = 10;
    private static final float BACKWARD_VEL = 4;

    public static int getFORWARD() {
        return FORWARD;
    }

    public static int getBACKWARD() {
        return BACKWARD;
    }

    public static float getFORWARD_VEL() {
        return FORWARD_VEL;
    }

    public static float getBACKWARD_VEL() {
        return BACKWARD_VEL;
    }
}
