package com.learn.org.core.data;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.learn.org.core.data.value.MovingSpikesValues;

public class MovingSpikes extends MovingSpikesValues {

    private int state;
    private Map map;
    private Rectangle bounds;
    private Vector2 vel;
    private Vector2 pos;
    private float angle;
    private int fx;
    private int fy;
    private int bx;
    private int by;

    public MovingSpikes(Map map, float x, float y) {
        this.state = getFORWARD();
        this.bounds = new Rectangle();
        this.vel = new Vector2();
        this.pos = new Vector2();
        this.angle = 0;
        this.fx = 0;
        this.fy = 0;
        this.bx = 0;
        this.by = 0;

        this.map = map;
        this.pos.x = x;
        this.pos.y = y;
        this.bounds.x = x;
        this.bounds.y = y;
        this.bounds.width = bounds.height = 1;
    }

    	public void init () {
		int ix = (int)getPos().x;
		int iy = (int)getPos().y;

		int left = getMap().getTiles()[ix - 1][getMap().getTiles()[0].length - 1 - iy];
		int right = getMap().getTiles()[ix + 1][getMap().getTiles()[0].length - 1 - iy];
		int top = getMap().getTiles()[ix][getMap().getTiles()[0].length - 1 - iy - 1];
		int bottom = getMap().getTiles()[ix][getMap().getTiles()[0].length - 1 - iy + 1];

		if (left == Map.getTILE()) {
			getVel().x = getFORWARD_VEL();
			setAngle(-90);
			setFx(1);
		}
		if (right == Map.getTILE()) {
			getVel().x = -getFORWARD_VEL();
			setAngle(90);
			setBx(1);
		}
		if (top == Map.getTILE()) {
			getVel().y = -getFORWARD_VEL();
			setAngle(180);
			setBy(-1);
		}
		if (bottom == Map.getTILE()) {
			getVel().y = getFORWARD_VEL();
			setAngle(0);
			setFy(-1);
		}
	}
        
        public void update (float deltaTime) {
		getPos().add(getVel().x * deltaTime, getVel().y * deltaTime);
		boolean change = false;
		if (getState() == getFORWARD()) {
			change = getMap().getTiles()[(int)getPos().x + getFx()][getMap().getTiles()[0].length - 1 - (int)getPos().y + getFy()] == Map.getTILE();
		} else {
			change = getMap().getTiles()[(int)getPos().x + getBx()][getMap().getTiles()[0].length - 1 - (int)getPos().y + getBy()] == Map.getTILE();
		}
		if (change) {
			getPos().x -= getVel().x * deltaTime;
			getPos().y -= getVel().y * deltaTime;
			setState(-getState());
			getVel().scl(-1);
			if (getState() == getFORWARD()) getVel().nor().scl(getFORWARD_VEL());
			if (getState() == getBACKWARD()) getVel().nor().scl(getBACKWARD_VEL());
		}

		getBounds().x = getPos().x;
		getBounds().y = getPos().y;

		if (getMap().getBob().getBounds().overlaps(getBounds())) {
			if (getMap().getBob().getState() != Bob.getDYING()) {
				getMap().getBob().setState(Bob.getDYING());
				getMap().getBob().setStateTime(0);
			}
		}

		if (getMap().getCube().getBounds().overlaps(getBounds())) {
			getMap().getCube().setState(Cube.getDEAD());
			getMap().getCube().setStateTime(0);
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

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Vector2 getVel() {
        return vel;
    }

    public void setVel(Vector2 vel) {
        this.vel = vel;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getFx() {
        return fx;
    }

    public void setFx(int fx) {
        this.fx = fx;
    }

    public int getFy() {
        return fy;
    }

    public void setFy(int fy) {
        this.fy = fy;
    }

    public int getBx() {
        return bx;
    }

    public void setBx(int bx) {
        this.bx = bx;
    }

    public int getBy() {
        return by;
    }

    public void setBy(int by) {
        this.by = by;
    }
}
