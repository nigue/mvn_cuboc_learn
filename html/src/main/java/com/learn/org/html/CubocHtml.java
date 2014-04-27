package com.learn.org.html;

import com.learn.org.core.Cuboc;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class CubocHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new Cuboc();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
