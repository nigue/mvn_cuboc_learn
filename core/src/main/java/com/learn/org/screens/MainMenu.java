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
//    private String imageName;

    public MainMenu(Game game) {
        super(game);
        this.time = 0;
//        this.imageName = imageName;
    }

    @Override
    public void show() {
        Gdx.app.debug("cuboc", "iniciando funcion show");
        setTitle(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal("title.png")), 0, 0, 480, 320));
        setBatch(new SpriteBatch());
        getBatch().getProjectionMatrix().setToOrtho2D(0, 0, 480, 320);
    }

    @Override
    public void render(float delta) {
//        Gdx.app.debug("cuboc", "iniciando funcion show con fps: " + Float.toString(1/delta));
//        Gdx.app.debug("cuboc", "iniciando funcion show con fps: " + getTime());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getBatch().begin();
//        getBatch().setColor(1.0f, 1.0f, 1.0f, getOpacity(getTime()));
        getBatch().draw(getTitle(), 0, 0);
        getBatch().end();
        setTime(getTime() + delta);
        if (getTime() > 1) {
            if (Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.justTouched()) {

                Gdx.app.debug("cuboc", "Generate a new main menu");
                getGame().setScreen(new IntroScreen(getGame()));
            }
        }
        if (getTime() > 5){
            setTime(0f);
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("cuboc", "dispose main menu");
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

    public float getOpacity(float x) {
        float y;

//        y = x * (10 - x) / 25;
        y = 4 * x * (5 - x) / 25;

        return y;
    }
    
    public boolean getRandomBoolean() {
       return Math.random() < 0.5;
   }
    
    public String getPaku(){
        String result;
        if (getRandomBoolean()){
            result = "title3.png";
        } else {
            result = "pakupaku480.png";
        }
        
        return result;
    }

//    public String getImageName() {
//        return imageName;
//    }
//
//    public void setImageName(String imageName) {
//        this.imageName = imageName;
//    }

}
