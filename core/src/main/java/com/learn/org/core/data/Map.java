package com.learn.org.core.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.learn.org.core.data.value.MapValues;
import java.util.ArrayList;
import java.util.List;

public class Map extends MapValues {

    private int[][] tiles;
    private Bob bob;
    private Cube cube;
    private List<Dispenser> dispensers;
    private Dispenser activeDispenser;
    private List<Rocket> rockets;
    private List<MovingSpikes> movingSpikes;
    private List<Laser> lasers;
    private EndDoor endDoor;

    public Map() {

        this.dispensers = new ArrayList<Dispenser>();
        this.activeDispenser = null;
        this.rockets = new ArrayList<Rocket>();
        this.movingSpikes = new ArrayList<MovingSpikes>();
        this.lasers = new ArrayList<Laser>();

        loadBinary();
    }

    private void loadBinary() {

        Pixmap pixmap = new Pixmap(Gdx.files.internal("levels.png"));
        setTiles(new int[pixmap.getWidth()][pixmap.getHeight()]);

        Gdx.app.debug("Cuboc", "MAP - tiles: " + pixmap.getWidth() + ", " + pixmap.getHeight());
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
        for (MovingSpikes movingSpike : getMovingSpikes()) {
            movingSpike.init();
        }
        for (Laser laser : getLasers()) {
            laser.init();
        }
    }

    public void update(float deltaTime) {

        getBob().update(deltaTime);

        //Gdx.app.debug("Cuboc", "Map - getDispensers(): " + getDispensers().size());
        if (getBob().getState() == Bob.getDEAD()) {
            setBob(new Bob(this, getActiveDispenser().getBounds().x, getActiveDispenser().getBounds().y));
        }
        getCube().update(deltaTime);
        if (getCube().getState() == Cube.getDEAD()) {
            setCube(new Cube(this, getBob().getBounds().x, getBob().getBounds().y));
        }
        for (Dispenser dispenser : getDispensers()) {
            Gdx.app.debug("Cuboc", "Map - overlaps: " + getBob().getBounds().overlaps(dispenser.getBounds()));
            if (getBob().getBounds().overlaps(dispenser.getBounds())) {
                setActiveDispenser(dispenser);
            }
        }
        for (Rocket rocket : getRockets()) {
            rocket.update(deltaTime);
        }
        for (MovingSpikes movingSpike : getMovingSpikes()) {
            movingSpike.update(deltaTime);
        }
        for (Laser laser : getLasers()) {
            laser.update();
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

    public Dispenser getActiveDispenser() {
        return activeDispenser;
    }

    public void setActiveDispenser(Dispenser activeDispenser) {
        this.activeDispenser = activeDispenser;
    }

    public List<Laser> getLasers() {
        return lasers;
    }

    public void setLasers(List<Laser> lasers) {
        this.lasers = lasers;
    }

    public EndDoor getEndDoor() {
        return endDoor;
    }

    public void setEndDoor(EndDoor endDoor) {
        this.endDoor = endDoor;
    }

    public List<Dispenser> getDispensers() {
        return dispensers;
    }

    public void setDispensers(List<Dispenser> dispensers) {
        this.dispensers = dispensers;
    }

    public List<Rocket> getRockets() {
        return rockets;
    }

    public void setRockets(List<Rocket> rockets) {
        this.rockets = rockets;
    }

    public List<MovingSpikes> getMovingSpikes() {
        return movingSpikes;
    }

    public void setMovingSpikes(List<MovingSpikes> movingSpikes) {
        this.movingSpikes = movingSpikes;
    }
    
}
