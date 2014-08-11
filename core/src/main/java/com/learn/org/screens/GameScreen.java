package com.learn.org.screens;

import com.badlogic.gdx.Game;
import com.learn.org.core.Map;

public class GameScreen extends CubocScreen {

    private Map map;
    
    public GameScreen(Game Game) {
        super(Game);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

}
