package com.learn.org.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.learn.org.screens.MainMenu;

public class Cuboc extends Game {

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.app.debug("Cuboc", "inicio de programa");
        setScreen(new MainMenu(this));
    }

}