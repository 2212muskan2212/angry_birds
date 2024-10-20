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

public class LevelSelectionScreen implements Screen {
    private final Main game;
    private final SpriteBatch spriteBatch;
    private final FitViewport viewport;
    private Texture backgroundTexture;
    private Texture chooseLevelTexture;
    private Texture easyTexture;
    private Texture mediumTexture;
    private Texture hardTexture;
    private Rectangle easyButtonRectangle;
    private Rectangle mediumButtonRectangle;
    private Vector2 touchPos;

    public LevelSelectionScreen(Main game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 480); // Adjust to your screen size
        backgroundTexture = new Texture("choose_level_background.png");
        chooseLevelTexture = new Texture("choose_level.png");
        easyTexture = new Texture("easy.png");
        mediumTexture = new Texture("medium.png");
        hardTexture = new Texture("hard.png");

        mediumButtonRectangle = new Rectangle(340, 195, 120, 35); // Position and size of Medium button
        touchPos = new Vector2();
    }

    @Override
    public void show() {
        // Code to run when the screen is shown
    }

    @Override
    public void render(float delta) {
        handleInput(); // Check for button clicks

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // Draw the background
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw "Choose Level" title, adjusted position
        spriteBatch.draw(chooseLevelTexture, 270, 320, 250, 50);

        // Draw level options
        float buttonYStart = 250; // Starting Y position for the buttons
        float buttonSpacing = 20; // Space between buttons

        spriteBatch.draw(easyTexture, 340, buttonYStart, 120, 35);
        spriteBatch.draw(mediumTexture, 340, buttonYStart - (35 + buttonSpacing), 120, 35);
        spriteBatch.draw(hardTexture, 340, buttonYStart - 2 * (35 + buttonSpacing), 120, 35);

        spriteBatch.end();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            if (mediumButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new MediumLevelScreen(game)); // Switch to MediumLevelScreen
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
        chooseLevelTexture.dispose();
        easyTexture.dispose();
        mediumTexture.dispose();
        hardTexture.dispose();
    }
}
