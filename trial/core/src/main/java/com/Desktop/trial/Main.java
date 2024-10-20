package com.Desktop.trial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    private BitmapFont font;
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        // Set the initial screen to the HomeScreen when the game starts
        setScreen(new HomeScreen(this));
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void render() {
        // Delegate rendering to the current screen
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        // Handle resizing for the current screen
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        // Dispose of the current screen resources
        if (getScreen() != null) {
            getScreen().dispose();
        }
        font.dispose();
        batch.dispose();
    }
}
