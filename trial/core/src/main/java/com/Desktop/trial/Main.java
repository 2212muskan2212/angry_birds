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
        SoundManager.getInstance().playMusic();
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
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }
        SoundManager.getInstance().dispose();
        font.dispose();
        batch.dispose();
    }
}
