package com.learn.org.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class CubocScreen implements Screen {

    private Game Game;

    public CubocScreen(Game Game) {
        this.Game = Game;
    }
    
    @Override
    public void render(float delta) {
        
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public Game getGame() {
        return Game;
    }

    public void setGame(Game Game) {
        this.Game = Game;
    }
    
}
