package com.learn.org.core.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.learn.org.core.data.value.CubeValues;
import java.util.List;

public class Cube extends CubeValues {

    private Map map;
    private Vector2 pos;
    private Vector2 accel;
    private Vector2 vel;
    private Rectangle bounds;
    private int state;
    private float stateTime;
    private Rectangle controllButtonRect;
    private Rectangle followButtonRect;
    private Rectangle dpadRect;
    private Vector2 target;
    private List<Rectangle> er;

    public Cube(Map map, float x, float y) {
        this.map = map;
        this.pos = new Vector2();
        this.pos.x = x;
        this.pos.y = y;
        this.accel = new Vector2();
        this.vel = new Vector2();
        this.bounds = new Rectangle();
        this.bounds.x = pos.x + 0.2f;
        this.bounds.y = pos.y + 0.2f;
        this.bounds.width = this.bounds.height = 1.0f;
        this.state = getFOLLOW();
        this.stateTime = 0;
        this.controllButtonRect = new Rectangle(480 - 64, 320 - 64, 64, 64);
        this.followButtonRect = new Rectangle(480 - 64, 320 - 138, 64, 64);
        this.dpadRect = new Rectangle(0, 0, 128, 128);
        this.target = new Vector2();
        this.er.add(new Rectangle());
        this.er.add(new Rectangle());
        this.er.add(new Rectangle());
        this.er.add(new Rectangle());
    }

    public void update(float deltaTime) {
        processKeys();

        if (getState() == getFOLLOW()) {
            getTarget().set(getMap().getBob().getPos());
            if (getMap().getBob().getDir() == Bob.getRIGHT()) {
                getTarget().x--;
            }
            if (getMap().getBob().getDir() == Bob.getLEFT()) {
                getTarget().x++;
            }
            getTarget().y += 0.2f;

            getVel().set(getTarget()).sub(getPos()).scl(Math.min(4, getPos().dst(getTarget())) * deltaTime);
            if (getVel().len() > getMAX_VELOCITY()) {
                getVel().nor().scl(getMAX_VELOCITY());
            }
            tryMove();
        }

        if (getState() == getCONTROLLED()) {
            getAccel().scl(deltaTime);
            getVel().add(getAccel().x, getAccel().y);
            if (getAccel().x == 0) {
                getVel().x *= getDAMP();
            }
            if (getAccel().y == 0) {
                getVel().y *= getDAMP();
            }
            if (getVel().x > getMAX_VELOCITY()) {
                getVel().x = getMAX_VELOCITY();
            }
            if (getVel().x < -getMAX_VELOCITY()) {
                getVel().x = -getMAX_VELOCITY();
            }
            if (getVel().y > getMAX_VELOCITY()) {
                getVel().y = getMAX_VELOCITY();
            }
            if (getVel().y < -getMAX_VELOCITY()) {
                getVel().y = -getMAX_VELOCITY();
            }
            getVel().scl(deltaTime);
            tryMove();
            getVel().scl(1.0f / deltaTime);
        }

        if (getState() == getFIXED()) {
            if (getStateTime() > 5.0f) {
                setStateTime(0);
                setState(getFOLLOW());
            }
        }

        setStateTime(getStateTime() + deltaTime);
    }

    private void processKeys() {
        float x0 = (Gdx.input.getX(0) / (float) Gdx.graphics.getWidth()) * 480;
        float x1 = (Gdx.input.getX(1) / (float) Gdx.graphics.getWidth()) * 480;
        float y0 = 320 - (Gdx.input.getY(0) / (float) Gdx.graphics.getHeight()) * 320;
        float y1 = 320 - (Gdx.input.getY(1) / (float) Gdx.graphics.getHeight()) * 320;
        boolean controlButton = (Gdx.input.isTouched(0) && controllButtonRect.contains(x0, y0))
                || (Gdx.input.isTouched(1) && controllButtonRect.contains(x1, y1));
        boolean followButton = (Gdx.input.isTouched(0) && followButtonRect.contains(x0, y0))
                || (Gdx.input.isTouched(1) && followButtonRect.contains(x1, y1));
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || controlButton) && getState() == getFOLLOW() && getStateTime() > 0.5f) {
            setStateTime(0);
            setState(getCONTROLLED());
            return;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || controlButton) && getState() == getCONTROLLED() && getStateTime() > 0.5f) {
            setStateTime(0);
            setState(getFIXED());
            return;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || controlButton) && getState() == getFIXED() && getStateTime() > 0.5f) {
            setStateTime(0);
            setState(getCONTROLLED());
            return;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.F) || followButton) && getStateTime() > 0.5f) {
            setStateTime(0);
            setState(getFOLLOW());
            return;
        }

        boolean touch0 = Gdx.input.isTouched(0);
        boolean touch1 = Gdx.input.isTouched(1);
        boolean right = (touch0 && (x0 > 80 && x0 < 128)) || (touch1 && (x1 > 80 && x1 < 128));
        boolean down = (touch0 && (y0 < 60)) || (touch1 && (y1 < 60));
        boolean up = (touch0 && (y0 > 80 && x0 < 128)) || (touch1 && (y1 > 80 && y1 < 128));

        if (getState() == getCONTROLLED()) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                getAccel().x = -getACCELERATION();
            } else if (Gdx.input.isKeyPressed(Input.Keys.D) || right) {
                getAccel().x = getACCELERATION();
            } else {
                getAccel().x = 0;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) || up) {
            getAccel().y = getACCELERATION();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) || down) {
            getAccel().y = -getACCELERATION();
        } else {
            getAccel().y = 0;
        }

        if (touch0) {
            if (getDpadRect().contains(x0, y0)) {
                float x = (x0 - 64) / 64;
                float y = (y0 - 64) / 64;
                float len = (float) Math.sqrt(x * x + y * y);
                if (len != 0) {
                    x /= len;
                    y /= len;
                } else {
                    x = 0;
                    y = 0;
                }
                getVel().x = x * getMAX_VELOCITY();
                getVel().y = y * getMAX_VELOCITY();
            } else {
                getAccel().x = 0;
                getAccel().y = 0;
            }
        }
    }

    private void tryMove() {
        getBounds().x += getVel().x;
        fetchCollidableRects();
        for (Rectangle rect : getEr()) {
            if (getBounds().overlaps(rect)) {
                if (getVel().x < 0) {
                    getBounds().x = rect.x + rect.width + 0.01f;
                } else {
                    getBounds().x = rect.x - getBounds().width - 0.01f;
                }
                getVel().x = 0;
            }
        }
        getBounds().y += getVel().y;
        fetchCollidableRects();
        for (Rectangle rect : getEr()) {
            if (getBounds().overlaps(rect)) {
                if (getVel().y < 0) {
                    getBounds().y = rect.y + rect.height + 0.01f;
                } else {
                    getBounds().y = rect.y - getBounds().height - 0.01f;
                }
                getVel().y = 0;
            }
        }
        getPos().x = getBounds().x - 0.2f;
        getPos().y = getBounds().y - 0.2f;
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

    public void setControlled() {
        if (getState() == getFOLLOW()) {
            setState(getCONTROLLED());
            setStateTime(0);
        }
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
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

    public Rectangle getControllButtonRect() {
        return controllButtonRect;
    }

    public void setControllButtonRect(Rectangle controllButtonRect) {
        this.controllButtonRect = controllButtonRect;
    }

    public Rectangle getFollowButtonRect() {
        return followButtonRect;
    }

    public void setFollowButtonRect(Rectangle followButtonRect) {
        this.followButtonRect = followButtonRect;
    }

    public Rectangle getDpadRect() {
        return dpadRect;
    }

    public void setDpadRect(Rectangle dpadRect) {
        this.dpadRect = dpadRect;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public List<Rectangle> getEr() {
        return er;
    }

    public void setEr(List<Rectangle> er) {
        this.er = er;
    }

}
