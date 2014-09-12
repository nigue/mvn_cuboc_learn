package com.learn.org.core.data.value;

public class MapValues {

    private static final int EMPTY = 0;
    private static final int TILE = 0xffffff;
    private static final int START = 0xff0000;
    private static final int END = 0xff00ff;
    private static final int DISPENSER = 0xff0100;
    private static final int SPIKES = 0x00ff00;
    private static final int ROCKET = 0x0000ff;
    private static final int MOVING_SPIKES = 0xffff00;
    private static final int LASER = 0x00ffff;

    public static int getEMPTY() {
        return EMPTY;
    }

    public static int getTILE() {
        return TILE;
    }

    public static int getSTART() {
        return START;
    }

    public static int getEND() {
        return END;
    }

    public static int getDISPENSER() {
        return DISPENSER;
    }

    public static int getSPIKES() {
        return SPIKES;
    }

    public static int getROCKET() {
        return ROCKET;
    }

    public static int getMOVING_SPIKES() {
        return MOVING_SPIKES;
    }

    public static int getLASER() {
        return LASER;
    }

}
