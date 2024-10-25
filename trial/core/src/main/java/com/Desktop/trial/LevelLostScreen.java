package com.Desktop.trial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelLostScreen implements Screen {
    private final Main game;
    private final SpriteBatch spriteBatch;
    private final FitViewport viewport;
    private Texture backgroundTexture;
    private Texture levelLostTexture;
    private Texture retryIconTexture;
    private Texture menuIconTexture;

    private Rectangle menuButtonRectangle;
    private Rectangle retryButtonRectangle;
    private Vector2 touchPos;

    public LevelLostScreen(Main game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 480);
        backgroundTexture = new Texture("level_lost_background.png");
        levelLostTexture = new Texture("level_lost.png");
        retryIconTexture = new Texture("retry_icon.png");
        menuIconTexture = new Texture("menu_icon.png");

        // Define the clickable areas
        menuButtonRectangle = new Rectangle(335, 140, 57, 31);
        retryButtonRectangle = new Rectangle(412, 140, 57, 30);
        touchPos = new Vector2();
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        // Check for button clicks
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (menuButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new LevelSelectionScreen(game));
            } else if (retryButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new EasyLevelScreen(game));
            }
        }

        // Draw the textures
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        spriteBatch.draw(levelLostTexture, 200, 180, 400, 260);
        spriteBatch.draw(menuIconTexture, 335, 140, 57, 31);
        spriteBatch.draw(retryIconTexture, 412, 140, 57, 30);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        levelLostTexture.dispose();
        retryIconTexture.dispose();
        menuIconTexture.dispose();
    }
}
