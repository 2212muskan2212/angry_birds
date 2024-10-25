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

public class LevelCompletedScreen implements Screen {
    private final Main game;
    private final SpriteBatch spriteBatch;
    private final FitViewport viewport;
    private final Texture backgroundTexture;
    private final Texture levelCompletedTexture;
    private final Texture nextIconTexture;
    private final Texture menuIconTexture;
    private final Rectangle nextButtonRectangle;
    private final Rectangle menuButtonRectangle;
    private final Vector2 touchPos;

    public LevelCompletedScreen(Main game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 480);
        backgroundTexture = new Texture("level_completed_background.png");
        levelCompletedTexture = new Texture("level_completed.png");
        nextIconTexture = new Texture("next_icon.png");
        menuIconTexture = new Texture("menu_icon.png");

        nextButtonRectangle = new Rectangle(412, 130, 57, 30);
        menuButtonRectangle = new Rectangle(335, 130, 57, 31);

        touchPos = new Vector2();
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // Draw the textures
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        spriteBatch.draw(levelCompletedTexture, 202, 100, 400, 285);
        spriteBatch.draw(menuIconTexture, 335, 130, 57, 31);
        spriteBatch.draw(nextIconTexture, 412, 130, 57, 30);

        spriteBatch.end();

        // Handle touch input
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (nextButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new MediumLevelScreen(game));
            } else if (menuButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new LevelSelectionScreen(game));
            }
        }
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
        levelCompletedTexture.dispose();
        nextIconTexture.dispose();
        menuIconTexture.dispose();
    }
}
