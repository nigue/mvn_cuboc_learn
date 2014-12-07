package com.learn.org.core.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.learn.org.core.data.value.RocketValues;
import java.util.ArrayList;
import java.util.List;

public class Rocket extends RocketValues {

    private Map map;
    private float stateTime;
    private int state;
    private Vector2 startPos;
    private Vector2 pos;
    private Vector2 vel;
    private Rectangle bounds;
    private List<Rectangle> er;

    public Rocket(Map map, float x, float y) {

        this.map = map;
        this.stateTime = 0;
        this.state = getFLYING();
        this.startPos = new Vector2();
        this.pos = new Vector2();
        this.vel = new Vector2();
        this.bounds = new Rectangle();

        this.startPos.set(x, y);
        this.pos.set(x, y);
        this.bounds.x = x + 0.2f;
        this.bounds.y = y + 0.2f;
        this.bounds.width = 0.6f;
        this.bounds.height = 0.6f;
        this.vel.set(-getVELOCITY(), 0);
        this.er = new ArrayList<Rectangle>();
        addRectagle();
    }

    public final void addRectagle() {
        getEr().add(new Rectangle());
        getEr().add(new Rectangle());
        getEr().add(new Rectangle());
        getEr().add(new Rectangle());
    }

    public void update(float deltaTime) {
        if (getState() == getFLYING()) {
            getVel().set(getMap().getBob().getPos());
            getVel().sub(getPos()).nor().scl(getVELOCITY());
            getPos().add(getVel().x * deltaTime, getVel().y * deltaTime);
            getBounds().x = getPos().x + 0.2f;
            getBounds().y = getPos().y + 0.2f;
            if (checkHit()) {
                setState(getEXPLODING());
                setStateTime(0);
            }
        }
        if (getState() == getEXPLODING()) {
            if (getStateTime() > 0.6f) {
                setState(getFLYING());
                setStateTime(0);
                getPos().set(getStartPos());
                getBounds().setX(getPos().x + 0.2f);
                getBounds().setY(getPos().y + 0.2f);
            }
        }

        setStateTime(getStateTime() + deltaTime);
    }

    private boolean checkHit() {
        fetchCollidableRects();
        for (Rectangle r : getEr()) {
            if (getBounds().overlaps(r)) {
                return true;
            }
        }

        if (getBounds().overlaps(getMap().getBob().getBounds())) {
            if (getMap().getBob().getState() != Bob.getDYING()) {
                getMap().getBob().setState(Bob.getDYING());
                getMap().getBob().setStateTime(0);
            }
            return true;
        }

        return getBounds().overlaps(getMap().getCube().getBounds());
    }

    private void fetchCollidableRects() {
        int p1x = (int) getBounds().x;
        int p1y = (int) Math.floor(getBounds().y);
        int p2x = (int) (getBounds().x + getBounds().width);
        int p2y = (int) Math.floor(getBounds().y);
        int p3x = (int) (getBounds().x + getBounds().width);
        int p3y = (int) (getBounds().y + getBounds().height);
        int p4x = (int) getBounds().x;
        int p4y = (int) (getBounds().y + getBounds().height);

        int[][] tiles = getMap().getTiles();
        int tile1 = tiles[p1x][getMap().getTiles()[0].length - 1 - p1y];
        int tile2 = tiles[p2x][getMap().getTiles()[0].length - 1 - p2y];
        int tile3 = tiles[p3x][getMap().getTiles()[0].length - 1 - p3y];
        int tile4 = tiles[p4x][getMap().getTiles()[0].length - 1 - p4y];

        if (tile1 != Map.getEMPTY()) {
            getEr().get(0).set(p1x, p1y, 1, 1);
        } else {
            getEr().get(0).set(-1, -1, 0, 0);
        }
        if (tile2 != Map.getEMPTY()) {
            getEr().get(1).set(p2x, p2y, 1, 1);
        } else {
            getEr().get(1).set(-1, -1, 0, 0);
        }
        if (tile3 != Map.getEMPTY()) {
            getEr().get(2).set(p3x, p3y, 1, 1);
        } else {
            getEr().get(2).set(-1, -1, 0, 0);
        }
        if (tile4 != Map.getEMPTY()) {
            getEr().get(3).set(p4x, p4y, 1, 1);
        } else {
            getEr().get(3).set(-1, -1, 0, 0);
        }
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Vector2 getStartPos() {
        return startPos;
    }

    public void setStartPos(Vector2 startPos) {
        this.startPos = startPos;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getVel() {
        return vel;
    }

    public void setVel(Vector2 vel) {
        this.vel = vel;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public List<Rectangle> getEr() {
        return er;
    }

    public void setEr(List<Rectangle> er) {
        this.er = er;
    }

}
