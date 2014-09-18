package com.learn.org.core.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.learn.org.core.data.value.BobValues;
import java.util.List;

public class Bob extends BobValues {

    private Vector2 pos;
    private Vector2 accel;
    private Vector2 vel;
    private Rectangle bounds;
    private int state;
    private float stateTime;
    private int dir;
    private Map map;
    private boolean grounded;
    private List<Rectangle> er;

    public Bob(Map map, float x, float y) {
        this.pos = new Vector2();
        this.accel = new Vector2();
        this.accel = new Vector2();
        this.bounds = new Rectangle();
        this.state = getSPAWN();
        this.stateTime = 0;
        this.dir = getLEFT();
        this.map = map;
        this.grounded = false;
        this.pos.x = x;
        this.pos.y = y;
        this.bounds.width = 0.6f;
        this.bounds.height = 0.8f;
        this.bounds.x = pos.x + 0.2f;
        this.bounds.y = pos.y;
        this.er.add(new Rectangle());
        this.er.add(new Rectangle());
        this.er.add(new Rectangle());
        this.er.add(new Rectangle());
        this.er.add(new Rectangle());
    }

    public void update(float deltaTime) {
        processKeys();
        getAccel().y = -getGRAVITY();
        getAccel().scl(deltaTime);
        getVel().add(getAccel().x, getAccel().y);
        if (getAccel().x == 0) {
            getVel().x *= getDAMP();
        }
        if (getVel().x > getMAX_VEL()) {
            getVel().x = getMAX_VEL();
        }
        if (getVel().x < -getMAX_VEL()) {
            getVel().x = -getMAX_VEL();
        }
        getVel().scl(deltaTime);
        tryMove();
        getVel().scl(1.0f / deltaTime);

        if (getState() == getSPAWN()) {
            if (getStateTime() > 0.4f) {
                setState(getIDLE());
            }
        }

        if (getState() == getDYING()) {
            if (getStateTime() > 0.4f) {
                setState(getDEAD());
            }
        }

        setStateTime(+deltaTime);
    }

    private void processKeys() {
        if (getMap().getCube().getState() == Cube.getCONTROLLED() || getState() == getSPAWN() || getState() == getDYING()) {
            return;
        }

        float x0 = (Gdx.input.getX(0) / (float) Gdx.graphics.getWidth()) * 480;
        float x1 = (Gdx.input.getX(1) / (float) Gdx.graphics.getWidth()) * 480;
        float y0 = 320 - (Gdx.input.getY(0) / (float) Gdx.graphics.getHeight()) * 320;

        boolean leftButton = (Gdx.input.isTouched(0) && x0 < 70) || (Gdx.input.isTouched(1) && x1 < 70);
        boolean rightButton = (Gdx.input.isTouched(0) && x0 > 70 && x0 < 134) || (Gdx.input.isTouched(1) && x1 > 70 && x1 < 134);
        boolean jumpButton = (Gdx.input.isTouched(0) && x0 > 416 && x0 < 480 && y0 < 64)
                || (Gdx.input.isTouched(1) && x1 > 416 && x1 < 480 && y0 < 64);

        if ((Gdx.input.isKeyPressed(Input.Keys.W) || jumpButton) && getState() != getJUMP()) {
            setState(getJUMP());
            getVel().y = getJUMP_VELOCITY();
            setGrounded(false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || leftButton) {
            if (getState() != getJUMP()) {
                setState(getRUN());
            }
            setDir(getLEFT());
            getAccel().x = getACCELERATION() * getDir();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || rightButton) {
            if (getState() != getJUMP()) {
                setState(getRUN());
            }
            setDir(getRIGHT());
            getAccel().x = getACCELERATION() * getDir();
        } else {
            if (getState() != getJUMP()) {
                setState(getIDLE());
            }
            getAccel().x = 0;
        }
    }

    private void tryMove() {
        getBounds().setX(1 + getVel().x);
        fetchCollidableRects();
        for (Rectangle rect : getEr()) {
            if (getBounds().overlaps(rect)) {
                if (getVel().x < 0) {
                    getBounds().setX(rect.x + rect.width + 0.01f);
                } else {
                    getBounds().setX(rect.x - bounds.width - 0.01f);
                }
                getVel().x = 0;
            }
        }
        getBounds().setY(1 + getVel().y);
        fetchCollidableRects();

        for (Rectangle rect : getEr()) {
            if (getBounds().overlaps(rect)) {
                if (vel.y < 0) {
                    getBounds().setY(rect.y + rect.height + 0.01f);
                    setGrounded(true);
                    if (getState() != getDYING() && getState() != getSPAWN()) {
                        setState(Math.abs(getAccel().x) > 0.1f ? getRUN() : getIDLE());
                    }
                } else {
                    getBounds().setY(rect.y - getBounds().getHeight() - 0.01f);
                }
                getVel().y = 0;
            }
        }

        getPos().x = getBounds().getX() - 0.2f;
        getPos().y = getBounds().getY();
    }

    private void fetchCollidableRects() {
        int p1x = (int) getBounds().getX();
        int p1y = (int) Math.floor(getBounds().getY());
        int p2x = (int) (getBounds().getX() + getBounds().getWidth());
        int p2y = (int) Math.floor(getBounds().getY());
        int p3x = (int) (getBounds().getX() + getBounds().getWidth());
        int p3y = (int) (getBounds().getY() + getBounds().getHeight());
        int p4x = (int) getBounds().getX();
        int p4y = (int) (getBounds().getY() + getBounds().getHeight());

        int[][] tiles = getMap().getTiles();
        int tile1 = tiles[p1x][getMap().getTiles()[0].length - 1 - p1y];
        int tile2 = tiles[p2x][getMap().getTiles()[0].length - 1 - p2y];
        int tile3 = tiles[p3x][getMap().getTiles()[0].length - 1 - p3y];
        int tile4 = tiles[p4x][getMap().getTiles()[0].length - 1 - p4y];

        if (getState() != getDYING() && (getMap().isDeadly(tile1) || getMap().isDeadly(tile2)
                || getMap().isDeadly(tile3) || getMap().isDeadly(tile4))) {
            setState(getDYING());
            setStateTime(0);
        }

        if (tile1 == Map.getTILE()) {
            getEr().get(0).set(p1x, p1y, 1, 1);
        } else {
            getEr().get(0).set(-1, -1, 0, 0);
        }
        if (tile2 == Map.getTILE()) {
            getEr().get(1).set(p2x, p2y, 1, 1);
        } else {
            getEr().get(1).set(-1, -1, 0, 0);
        }
        if (tile3 == Map.getTILE()) {
            getEr().get(2).set(p3x, p3y, 1, 1);
        } else {
            getEr().get(2).set(-1, -1, 0, 0);
        }
        if (tile4 == Map.getTILE()) {
            getEr().get(3).set(p4x, p4y, 1, 1);
        } else {
            getEr().get(3).set(-1, -1, 0, 0);
        }

        if (getMap().getCube().getState() == Cube.getFIXED()) {
            getEr().get(4).setX(getMap().getCube().getBounds().getX());
            getEr().get(4).setY(getMap().getCube().getBounds().getY());
            getEr().get(4).setWidth(getMap().getCube().getBounds().getWidth());
            getEr().get(4).setHeight(getMap().getCube().getBounds().getHeight());
        } else {
            getEr().get(4).set(-1, -1, 0, 0);
        }
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getAccel() {
        return accel;
    }

    public void setAccel(Vector2 accel) {
        this.accel = accel;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public List<Rectangle> getEr() {
        return er;
    }

    public void setEr(List<Rectangle> er) {
        this.er = er;
    }
}
