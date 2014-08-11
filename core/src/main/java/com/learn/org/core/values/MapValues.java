package com.learn.org.core.values;

public class MapValues {

    private int empty = 0;
    private int tile = 0xffffff;
    private int start = 0xff0000;
    private int end = 0xff00ff;
    private int dispenser = 0xff0100;
    private int spikes = 0x00ff00;
    private int rocket = 0x0000ff;
    private int movingSpikes = 0xffff00;
    private int laser = 0x00ffff;

    public MapValues() {
        this.empty = 0;
        this.tile = 0xffffff;
        this.start = 0xff0000;
        this.end = 0xff00ff;
        this.dispenser = 0xff0100;
        this.spikes = 0x00ff00;
        this.rocket = 0x0000ff;
        this.movingSpikes = 0xffff00;
        this.laser = 0x00ffff;
    }

    public int getEmpty() {
        return empty;
    }

    public int getTile() {
        return tile;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getDispenser() {
        return dispenser;
    }

    public int getSpikes() {
        return spikes;
    }

    public int getRocket() {
        return rocket;
    }

    public int getMovingSpikes() {
        return movingSpikes;
    }

    public int getLaser() {
        return laser;
    }
    
    
}
