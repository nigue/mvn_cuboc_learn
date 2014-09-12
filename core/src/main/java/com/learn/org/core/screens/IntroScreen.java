package com.learn.org.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IntroScreen extends CubocScreen {

    private TextureRegion intro;
    private SpriteBatch batch;
    private float time;

    public IntroScreen(Game Game) {
        super(Game);
        this.time = 0;
    }

    @Override
    public void show() {
        Gdx.app.debug("Cuboc", "Intro Screen - show");
        setIntro(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal("intro.png")), 0, 0, 480, 320));
        setBatch(new SpriteBatch());
        Gdx.app.debug("Cuboc", "Intro Screen - seteando variables de intro");
        getBatch().getProjectionMatrix().setToOrtho2D(0, 0, 480, 320);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getBatch().begin();
        getBatch().draw(getIntro(), 0, 0);
        getBatch().end();
        setTime(getTime() + delta);
        if (getTime() > 1) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
                Gdx.app.debug("Cuboc", "Intro Screen - ha sido apretada la pantalla o tambien ha sido apretado un boton");
                getGame().setScreen(new GameScreen(getGame()));
            }
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Cuboc", "Intro Screen - hide");
        getBatch().dispose();
        getIntro().getTexture().dispose();
    }

    public TextureRegion getIntro() {
        return intro;
    }

    public void setIntro(TextureRegion intro) {
        this.intro = intro;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

}
