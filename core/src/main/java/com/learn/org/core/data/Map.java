package com.learn.org.core.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
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
    private EndDoor endDoor;

    public Map() {
        this.dispensers = new Array<Dispenser>();
        this.activeDispenser = null;
        this.rockets = new Array<Rocket>();
        this.movingSpikes = new Array<MovingSpikes>();
        this.lasers = new Array<Laser>();

        loadBinary();
    }

    private void loadBinary() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("levels.png"));
        setTiles(new int[pixmap.getWidth()][pixmap.getHeight()]);
        for (int y = 0; y < 35; y++) {
            for (int x = 0; x < 150; x++) {
                int pix = (pixmap.getPixel(x, y) >>> 8) & 0xffffff;
                if (match(pix, getSTART())) {
                    Dispenser dispenser = new Dispenser(x, pixmap.getHeight() - 1 - y);
                    getDispensers().add(dispenser);
                    setActiveDispenser(dispenser);
                    setBob(new Bob(this, getActiveDispenser().getBounds().x, getActiveDispenser().getBounds().y));
                    getBob().setState(Bob.getSPAWN());
                    setCube(new Cube(this, getActiveDispenser().getBounds().x, getActiveDispenser().getBounds().y));
                    getCube().setState(Cube.getDEAD());
                } else if (match(pix, getDISPENSER())) {
                    Dispenser dispenser = new Dispenser(x, pixmap.getHeight() - 1 - y);
                    getDispensers().add(dispenser);
                } else if (match(pix, getROCKET())) {
                    Rocket rocket = new Rocket(this, x, pixmap.getHeight() - 1 - y);
                    getRockets().add(rocket);
                } else if (match(pix, getMOVING_SPIKES())) {
                    getMovingSpikes().add(new MovingSpikes(this, x, pixmap.getHeight() - 1 - y));
                } else if (match(pix, getLASER())) {
                    getLasers().add(new Laser(this, x, pixmap.getHeight() - 1 - y));
                } else if (match(pix, getEND())) {
                    setEndDoor(new EndDoor(x, pixmap.getHeight() - 1 - y));
                } else {
                    getTiles()[x][y] = pix;
                }
            }
        }

        for (int i = 0; i < getMovingSpikes().size; i++) {
            getMovingSpikes().get(i).init();
        }
        for (int i = 0; i < getLasers().size; i++) {
            getLasers().get(i).init();
        }
    }

    public void update(float deltaTime) {
        getBob().update(deltaTime);
        if (getBob().getState() == Bob.getDEAD()) {
            setBob(new Bob(this, getActiveDispenser().getBounds().x, getActiveDispenser().getBounds().y));
        }
        getCube().update(deltaTime);
        if (getCube().getState() == Cube.getDEAD()) {
            setCube(new Cube(this, getBob().getBounds().x, getBob().getBounds().y));
        }
        for (int i = 0; i < getDispensers().size; i++) {
            if (getBob().getBounds().overlaps(getDispensers().get(i).getBounds())) {
                setActiveDispenser(getDispensers().get(i));
            }
        }
        for (int i = 0; i < getRockets().size; i++) {
            Rocket rocket = getRockets().get(i);
            rocket.update(deltaTime);
        }
        for (int i = 0; i < getMovingSpikes().size; i++) {
            MovingSpikes spikes = getMovingSpikes().get(i);
            spikes.update(deltaTime);
        }
        for (int i = 0; i < getLasers().size; i++) {
            getLasers().get(i).update();
        }
    }

    public boolean isDeadly(int tileId) {
        return tileId == getSPIKES();
    }

    boolean match(int src, int dst) {
        return src == dst;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }

    public Bob getBob() {
        return bob;
    }

    public void setBob(Bob bob) {
        this.bob = bob;
    }

    public Cube getCube() {
        return cube;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
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

    public EndDoor getEndDoor() {
        return endDoor;
    }

    public void setEndDoor(EndDoor endDoor) {
        this.endDoor = endDoor;
    }

}
