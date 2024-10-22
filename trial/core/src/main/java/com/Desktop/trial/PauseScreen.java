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
    private final Screen previousScreen; // The screen that was active before pausing
    private final SpriteBatch spriteBatch;
    private final FitViewport viewport;
    private Texture backgroundTexture;
    private Texture resumeTexture; // Texture for Resume button
    private Texture exitTexture;   // Texture for Exit button
    private Rectangle resumeButtonRectangle; // Rectangle to detect resume button click
    private Rectangle exitButtonRectangle;
    private Vector2 touchPos; // To store the touch coordinates

    public PauseScreen(Main game, Screen previousScreen) {
        this.game = game; // Reference to the game instance
        this.previousScreen = previousScreen; // Store the previous screen (level screen)
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 480); // Adjust to your screen size
        backgroundTexture = new Texture("pause_background.png"); // Load pause screen background
        resumeTexture = new Texture("resume.png"); // Load Resume button texture
        exitTexture = new Texture("exit.png");     // Load Exit button texture
        resumeButtonRectangle = new Rectangle(325, 250, 150, 60); // Rectangle for Resume button
        exitButtonRectangle = new Rectangle(325, 170, 140, 57); // Rectangle for Exit button
        touchPos = new Vector2();
    }

    @Override
    public void show() {
        // Code to run when the screen is shown
    }

    @Override
    public void render(float delta) {
        handleInput(); // Check for user input before rendering
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // Draw the background
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw the "Resume" and "Exit" buttons at the center
        float buttonYStart = 250; // Starting Y position for the buttons
        float buttonSpacing = 30; // Space between buttons
        float buttonWidth = 160;  // Width of the buttons
        float buttonHeight = 60;  // Height of the buttons

        // Draw Resume button
        spriteBatch.draw(resumeTexture, 325, buttonYStart, buttonWidth, buttonHeight);
        // Draw Exit button with spacing
        spriteBatch.draw(exitTexture, 325, buttonYStart - (buttonHeight + buttonSpacing), buttonWidth, 57);

        spriteBatch.end();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (resumeButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(previousScreen); // Resume the previously active level screen
            } else if (exitButtonRectangle.contains(touchPos.x, touchPos.y)) {
                Gdx.app.exit(); // Exit the application
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
        resumeTexture.dispose(); // Dispose of Resume button texture
        exitTexture.dispose();   // Dispose of Exit button texture
    }
}
