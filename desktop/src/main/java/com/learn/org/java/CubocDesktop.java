package com.learn.org.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.learn.org.core.Cuboc;

public class CubocDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = true;
                config.width = 480;
                config.height = 320;
                config.title = "Cubocy";
		new LwjglApplication(new Cuboc(), config);
	}
}
