package com.learn.org.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.learn.org.core.data.Map;
import com.learn.org.core.data.MapRenderer;
import com.learn.org.core.data.OnscreenControlRenderer;

public class GameScreen extends CubocScreen {

    private Map map;
    private MapRenderer renderer;
    private OnscreenControlRenderer controlRenderer;

    public GameScreen(Game game) {
        
        super(game);
        
        Gdx.app.debug("Cuboc", "GameScreen - CONTRUCTOR");
    }

    @Override
    public void show() {
        Gdx.app.debug("Cuboc", "GameScreen - SHOW");
        setMap(new Map());
        setRenderer(new MapRenderer(getMap()));
        setControlRenderer(new OnscreenControlRenderer(getMap()));
    }

    @Override
    public void render(float delta) {
        delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        getMap().update(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getRenderer().render(delta);
        getControlRenderer().render();

        if (getMap().getBob().getBounds().overlaps(getMap().getEndDoor().getBounds())) {
            getGame().setScreen(new GameOverScreen(getGame()));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            getGame().setScreen(new MainMenu(getGame()));
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Cubocy", "dispose game screen");
        getRenderer().dispose();
        getControlRenderer().dispose();
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public MapRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(MapRenderer renderer) {
        this.renderer = renderer;
    }

    public OnscreenControlRenderer getControlRenderer() {
        return controlRenderer;
    }

    public void setControlRenderer(OnscreenControlRenderer controlRenderer) {
        this.controlRenderer = controlRenderer;
    }

}
