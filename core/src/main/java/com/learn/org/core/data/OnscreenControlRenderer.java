package com.learn.org.core.data;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class OnscreenControlRenderer {

    private Map map;
    private SpriteBatch batch;
    private TextureRegion dpad;
    private TextureRegion left;
    private TextureRegion right;
    private TextureRegion jump;
    private TextureRegion cubeControl;
    private TextureRegion cubeFollow;

    public OnscreenControlRenderer(Map map) {
        this.map = map;
        loadAssets();
    }

    private void loadAssets() {
        Texture texture = new Texture(Gdx.files.internal("controls.png"));
        TextureRegion[] buttons = TextureRegion.split(texture, 64, 64)[0];
        setLeft(buttons[0]);
        setRight(buttons[1]);
        setJump(buttons[2]);
        setCubeControl(buttons[3]);
        setCubeFollow(TextureRegion.split(texture, 64, 64)[1][2]);
        setDpad(new TextureRegion(texture, 0, 64, 128, 128));
        setBatch(new SpriteBatch());
        getBatch().getProjectionMatrix().setToOrtho2D(0, 0, 480, 320);
    }

    public void render() {
        if (Gdx.app.getType() != Application.ApplicationType.Android) {
            return;
        }
        if (getMap().getCube().getState() != Cube.getCONTROLLED()) {
            getBatch().begin();
            getBatch().draw(getLeft(), 0, 0);
            getBatch().draw(getRight(), 70, 0);
            getBatch().draw(getCubeControl(), 480 - 64, 320 - 64);
            getBatch().draw(getCubeFollow(), 480 - 64, 320 - 138);
            getBatch().draw(getJump(), 480 - 64, 0);
            getBatch().end();
        } else {
            getBatch().begin();
            getBatch().draw(getDpad(), 0, 0);
            getBatch().draw(getCubeFollow(), 480 - 64, 320 - 138);
            getBatch().draw(getCubeControl(), 480 - 64, 320 - 64);
            getBatch().end();
        }
    }

    public void dispose() {
        getDpad().getTexture().dispose();
        getBatch().dispose();
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public TextureRegion getDpad() {
        return dpad;
    }

    public void setDpad(TextureRegion dpad) {
        this.dpad = dpad;
    }

    public TextureRegion getLeft() {
        return left;
    }

    public void setLeft(TextureRegion left) {
        this.left = left;
    }

    public TextureRegion getRight() {
        return right;
    }

    public void setRight(TextureRegion right) {
        this.right = right;
    }

    public TextureRegion getJump() {
        return jump;
    }

    public void setJump(TextureRegion jump) {
        this.jump = jump;
    }

    public TextureRegion getCubeControl() {
        return cubeControl;
    }

    public void setCubeControl(TextureRegion cubeControl) {
        this.cubeControl = cubeControl;
    }

    public TextureRegion getCubeFollow() {
        return cubeFollow;
    }

    public void setCubeFollow(TextureRegion cubeFollow) {
        this.cubeFollow = cubeFollow;
    }

}
