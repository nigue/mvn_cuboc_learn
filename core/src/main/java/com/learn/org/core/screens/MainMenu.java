package com.learn.org.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainMenu extends CubocScreen {

    private TextureRegion title;
    private SpriteBatch batch;
    private float time;

    public MainMenu(Game Game) {
        super(Game);
        this.time = 0f;
    }

    @Override
    public void show() {
        setTitle(new TextureRegion(new Texture(Gdx.files.internal("title.png")), 0, 0, 480, 320));
        setBatch(new SpriteBatch());
        getBatch().getProjectionMatrix().setToOrtho2D(0, 0, 480, 320);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getBatch().begin();
        getBatch().draw(title, 0, 0);
        getBatch().end();

        setTime(getTime() + delta);
        if (getTime() > 1) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
                getGame().setScreen(new IntroScreen(getGame()));
            }
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Cuboc", "MainMenu - dispose main menu");
        getBatch().dispose();
        getTitle().getTexture().dispose();
    }

    public TextureRegion getTitle() {
        return title;
    }

    public void setTitle(TextureRegion title) {
        this.title = title;
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
