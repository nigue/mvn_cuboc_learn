package com.learn.org.core.data;

import com.badlogic.gdx.utils.Array;
import com.learn.org.core.data.value.MapValues;

public class Map extends MapValues {

    private int[][] tiles;
    private Bob bob;
    private Cube cube;
    private Array<Dispenser> dispensers;
    private Dispenser activeDispenser;

    public Map() {
        this.dispensers = new Array<Dispenser>();
        this.activeDispenser = null;
    }

    public boolean isDeadly(int tileId) {
        return tileId == getSPIKES();
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
}
