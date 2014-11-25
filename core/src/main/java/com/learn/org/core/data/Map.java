package com.learn.org.core.data;

import com.badlogic.gdx.utils.Array;
import com.learn.org.core.data.value.MapValues;

public class Map extends MapValues {

    private int[][] tiles;
    private Bob bob;
    private Cube cube;
    private Array<Dispenser> dispensers;
    private Dispenser activeDispenser;
    private Array<Rocket> rockets;
    private Array<MovingSpikes> movingSpikes;
    private Array<Laser> lasers;

    public Map() {
        this.dispensers = new Array<Dispenser>();
        this.activeDispenser = null;
        this.rockets = new Array<Rocket>();
        this.movingSpikes = new Array<MovingSpikes>();
        this.lasers = new Array<Laser>();
    }

    public boolean isDeadly(int tileId) {
        return tileId == getSPIKES();
    }

    boolean match(int src, int dst) {
        return src == dst;
    }

    public Bob getBob() {
        return bob;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public Cube getCube() {
        return cube;
    }

    public Array<Dispenser> getDispensers() {
        return dispensers;
    }

    public void setDispensers(Array<Dispenser> dispensers) {
        this.dispensers = dispensers;
    }

    public Dispenser getActiveDispenser() {
        return activeDispenser;
    }

    public void setActiveDispenser(Dispenser activeDispenser) {
        this.activeDispenser = activeDispenser;
    }

    public Array<Rocket> getRockets() {
        return rockets;
    }

    public void setRockets(Array<Rocket> rockets) {
        this.rockets = rockets;
    }

    public Array<MovingSpikes> getMovingSpikes() {
        return movingSpikes;
    }

    public void setMovingSpikes(Array<MovingSpikes> movingSpikes) {
        this.movingSpikes = movingSpikes;
    }

    public Array<Laser> getLasers() {
        return lasers;
    }

    public void setLasers(Array<Laser> lasers) {
        this.lasers = lasers;
    }
}
