package com.learn.org.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class RectangleTouch extends ApplicationAdapter {

    private OrthographicCamera camera;
    private Rectangle optionsMenu;
    private SpriteBatch spriteBatch;
    private Texture buttonImage;
    private BitmapFont font;
    private String text;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 800, 480);
        buttonImage = new Texture(Gdx.files.internal("tile.png"));
        optionsMenu = new Rectangle(100 + (120 * 2), 100, 100, 100);
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLUE);
        text = "";
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(Gdx.graphics.getDeltaTime());
        spriteBatch.begin();
        spriteBatch.draw(buttonImage, optionsMenu.getX(),
                optionsMenu.getY(), optionsMenu.getWidth(),
                optionsMenu.getHeight(), 0, 0, buttonImage.getWidth(),
                buttonImage.getHeight(), false, true);
        font.draw(spriteBatch, text, 100, 100);
        spriteBatch.end();
    }

    private void update(double delta) {
        Input i = Gdx.input;
        if (i.isTouched()) {
            tap(i.getX(), i.getY());
        }
    }

    public boolean tap(float x, float y) {
        Vector3 temp = new Vector3(x, y, 0);
        camera.unproject(temp);
        float scalePos = ((float) Gdx.graphics.getWidth() / 800.0f);
        temp.x = temp.x * scalePos;
        temp.y = temp.y * scalePos;
        text = optionsMenu.contains(temp.x, temp.y) ? "DENTRO" : "FUERA";
        return false;
    }
}
