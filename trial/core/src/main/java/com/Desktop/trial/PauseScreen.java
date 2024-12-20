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

public class PauseScreen implements Screen {
    private final Main game;
    private final Screen previousScreen;
    private final SpriteBatch spriteBatch;
    private final FitViewport viewport;
    private Texture backgroundTexture;
    private Texture resumeTexture;
    private Texture exitTexture;
    private Rectangle resumeButtonRectangle;
    private Rectangle exitButtonRectangle;
    private Vector2 touchPos;

    public PauseScreen(Main game, Screen previousScreen) {
        this.game = game;
        this.previousScreen = previousScreen;
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 480);
        backgroundTexture = new Texture("pause_background.png");
        resumeTexture = new Texture("resume.png");
        exitTexture = new Texture("exit.png");
        resumeButtonRectangle = new Rectangle(325, 250, 150, 60);
        exitButtonRectangle = new Rectangle(325, 170, 140, 57);
        touchPos = new Vector2();
    }

    @Override
    public void show() {
        // Code to run when the screen is shown
    }

    @Override
    public void render(float delta) {
        handleInput();
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // Draw the background
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw the "Resume" and "Exit" buttons at the center
        float buttonYStart = 250;
        float buttonSpacing = 30;
        float buttonWidth = 160;
        float buttonHeight = 60;


        spriteBatch.draw(resumeTexture, 325, buttonYStart, buttonWidth, buttonHeight);

        spriteBatch.draw(exitTexture, 325, buttonYStart - (buttonHeight + buttonSpacing), buttonWidth, 57);

        spriteBatch.end();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (resumeButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(previousScreen);
            } else if (exitButtonRectangle.contains(touchPos.x, touchPos.y)) {
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
        resumeTexture.dispose();
        exitTexture.dispose();
    }
}
