package com.learn.org.core.screens;

import com.badlogic.gdx.Game;
import com.learn.org.core.data.Map;

public class GameScreen extends CubocScreen {

    private Map map;
    
    
    public GameScreen(Game game) {
		super(game);
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    
}
