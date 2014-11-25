package com.learn.org.core.data;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.learn.org.core.data.value.LaserValues;

public class Laser extends LaserValues {

    private int state;

    private Map map;
    private Vector2 pos;
    private Vector2 endPoint;
    private Vector2 cappedEndPoint;
    private float angle;
    private Vector2 startPoint;

    public Laser(Map map, float x, float y) {

        this.state = getFORWARD();
        this.pos = new Vector2();
        this.endPoint = new Vector2();
        this.cappedEndPoint = new Vector2();
        this.angle = 0;

        this.map = map;
        this.pos.x = x;
        this.pos.y = y;

        this.startPoint = new Vector2();

    }

    public void init() {

        int ix = (int) getPos().x;
        int iy = getMap().getTiles()[0].length - 1 - (int) getPos().y;

        int left = getMap().getTiles()[ix - 1][iy];
        int right = getMap().getTiles()[ix + 1][iy];
        int top = getMap().getTiles()[ix][iy - 1];
        int bottom = getMap().getTiles()[ix][iy + 1];

        if (left == Map.getTILE()) {
            setAngle(-90);
            for (int x = ix; x < getMap().getTiles().length; x++) {
                if (getMap().getTiles()[x][iy] == Map.getTILE()) {
                    getEndPoint().set(x, getPos().y);
                    break;
                }
            }
        }
        if (right == Map.getTILE()) {
            setAngle(90);
            for (int x = ix; x >= 0; x--) {
                if (getMap().getTiles()[x][iy] == Map.getTILE()) {
                    getEndPoint().set(x, getPos().y);
                    break;
                }
            }
        }
        if (top == Map.getTILE()) {
            setAngle(180);
            for (int y = iy; y < getMap().getTiles()[0].length; y++) {
                if (getMap().getTiles()[ix][y] == Map.getTILE()) {
                    getEndPoint().set(getPos().x, getMap().getTiles()[0].length - y - 1);
                    break;
                }
            }
        }
        if (bottom == Map.getTILE()) {
            setAngle(0);
            for (int y = iy; y >= 0; y--) {
                if (getMap().getTiles()[ix][y] == Map.getTILE()) {
                    getEndPoint().set(getPos().x, getMap().getTiles()[0].length - y - 1);
                    break;
                }
            }
        }
    }

    public void update() {

        getStartPoint().set(getPos()).add(0.5f, 0.5f);
        getCappedEndPoint().set(getEndPoint()).add(0.5f, 0.5f);

        Rectangle cbounds = getMap().getCube().getBounds();
        Rectangle bbounds = getMap().getBob().getBounds();

        boolean kill = false;

        if (getAngle() == -90) {
            if (getStartPoint().x < cbounds.x && getEndPoint().x > cbounds.x) {
                if (cbounds.y < getStartPoint().y && cbounds.y + cbounds.height > getStartPoint().y) {
                    getCappedEndPoint().x = cbounds.x;
                }
            }
        }
        if (getAngle() == 90) {
            if (getStartPoint().x > cbounds.x && getEndPoint().x < cbounds.x) {
                if (cbounds.y < getStartPoint().y && cbounds.y + cbounds.height > getStartPoint().y) {
                    getCappedEndPoint().x = cbounds.x + cbounds.width;
                }
            }
        }

        if (getAngle() == 0) {
            if (getStartPoint().y < cbounds.y && getEndPoint().y > cbounds.y) {
                if (cbounds.x < getStartPoint().x && cbounds.x + cbounds.width > getStartPoint().x) {
                    getCappedEndPoint().y = cbounds.y;
                }
            }
        }

        if (getAngle() == 180) {
            if (getStartPoint().y > cbounds.y && getEndPoint().y < cbounds.y) {
                if (cbounds.x < getStartPoint().x && cbounds.x + cbounds.width > getStartPoint().x) {
                    getCappedEndPoint().y = cbounds.y + cbounds.height;
                }
            }
        }

        if (getAngle() == -90) {
            if (getStartPoint().x < bbounds.x) {
                if (bbounds.y < getStartPoint().y && bbounds.y + bbounds.height > getStartPoint().y) {
                    if (getCappedEndPoint().x > bbounds.x) {
                        kill = true;
                    }
                }
            }
        }
        if (getAngle() == 90) {
            if (getStartPoint().x > bbounds.x) {
                if (bbounds.y < getStartPoint().y && bbounds.y + bbounds.height > getStartPoint().y) {
                    if (getCappedEndPoint().x < bbounds.x + bbounds.width) {
                        kill = true;
                    }
                }
            }
        }

        if (getAngle() == 0) {
            if (getPos().y < bbounds.y) {
                if (bbounds.x < getStartPoint().x && bbounds.x + bbounds.width > getStartPoint().x) {
                    if (getCappedEndPoint().y > bbounds.y) {
                        kill = true;
                    }
                }
            }
        }

        if (getAngle() == 180) {
            if (getPos().y > bbounds.y) {
                if (bbounds.x < getStartPoint().x && bbounds.x + bbounds.width > getStartPoint().x) {
                    if (getCappedEndPoint().y < bbounds.y + bbounds.height) {
                        kill = true;
                    }
                }
            }
        }

        if (kill && getMap().getBob().getState() != Bob.getDYING()) {
            getMap().getBob().setState(Bob.getDYING());
            getMap().getBob().setStateTime(0);
        }

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public Vector2 getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Vector2 endPoint) {
        this.endPoint = endPoint;
    }

    public Vector2 getCappedEndPoint() {
        return cappedEndPoint;
    }

    public void setCappedEndPoint(Vector2 cappedEndPoint) {
        this.cappedEndPoint = cappedEndPoint;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Vector2 getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Vector2 startPoint) {
        this.startPoint = startPoint;
    }

}
