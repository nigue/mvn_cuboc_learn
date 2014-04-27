package com.learn.org.core;

import com.badlogic.gdx.Game;
import com.learn.org.screens.MainMenu;

public class Cuboc extends Game {

    @Override
    public void create() {
        setScreen(new MainMenu(this));
    }

}
