package com.learn.org.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainMenu extends CubocScreen {

    private TextureRegion title;
    private SpriteBatch batch;
    private float time;

    public MainMenu(Game game) {
        super(game);
        this.time = 0;
    }

    @Override
    public void show() {
        setTitle(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal("title.png")), 0, 0, 480, 320));
        setBatch(new SpriteBatch());
        getBatch().getProjectionMatrix().setToOrtho2D(0, 0, 480, 320);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getBatch().begin();
        getBatch().draw(getTitle(), 0, 0);
        getBatch().end();
        setTime(getTime() + delta);
        if (getTime() > 1) {
            Gdx.app.debug("Cubocy", "time is: " + delta);
            if (Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.justTouched()) {

                Gdx.app.debug("Cubocy", "Generate a new main menu");
                getGame().setScreen(new MainMenu(getGame()));
            }
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Cubocy", "dispose main menu");
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
