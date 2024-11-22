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
    private boolean isTouched;
    private final Main game;
    private final SpriteBatch spriteBatch;
    private final FitViewport viewport;
    private Texture backgroundTexture;
    private Texture chooseLevelTexture;
    private Texture backTexture;
    private Texture easyTexture;
    private Texture mediumTexture;
    private Texture hardTexture;
    private Texture unmuteTexture;
    private Texture muteTexture; // Add mute texture
    private boolean isMuted = false; // Track mute state
    private Rectangle backButtonRectangle;
    private Rectangle easyButtonRectangle;
    private Rectangle mediumButtonRectangle;
    private Rectangle hardButtonRectangle;
    private Rectangle unmuteButtonRectangle;
    private Vector2 touchPos;

    public LevelSelectionScreen(Main game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 480); // Adjust to your screen size
        backgroundTexture = new Texture("choose_level_background.png");
        backTexture = new Texture("back.png");
        chooseLevelTexture = new Texture("choose_level.png");
        easyTexture = new Texture("easy.png");
        mediumTexture = new Texture("medium.png");
        hardTexture = new Texture("hard.png");
        unmuteTexture = new Texture("unmute.png");
        muteTexture = new Texture("mute.png"); // Load the mute image

        backButtonRectangle = new Rectangle(35, 410, 50, 50);
        easyButtonRectangle = new Rectangle(355, 290, 120, 45);
        mediumButtonRectangle = new Rectangle(355, 235, 120, 45);
        hardButtonRectangle = new Rectangle(355, 180, 120, 45);
        unmuteButtonRectangle = new Rectangle(720, 420, 40, 40);
        touchPos = new Vector2();
    }

    @Override
    public void show() {
        SoundManager.getInstance().playMusic(); // Ensure music starts playing
    }

    @Override
    public void render(float delta) {
        handleInput(); // Check for button clicks

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        spriteBatch.draw(backTexture, 35, 410, 50, 50);
        spriteBatch.draw(chooseLevelTexture, 270, 370, 245, 75);

        // Draw mute/unmute button based on isMuted state
        Texture soundButtonTexture = isMuted ? muteTexture : unmuteTexture;
        spriteBatch.draw(soundButtonTexture, 720, 420, 45, 45);

        float buttonYStart = 290;
        float buttonSpacing = 20;

        spriteBatch.draw(easyTexture, 335, buttonYStart, 120, 45);
        spriteBatch.draw(mediumTexture, 335, buttonYStart - (35 + buttonSpacing), 120, 45);
        spriteBatch.draw(hardTexture, 335, buttonYStart - 2 * (35 + buttonSpacing), 120, 45);

        spriteBatch.end();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            isTouched = true;
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (backButtonRectangle.contains(touchPos.x, touchPos.y)) {
                Gdx.app.log("Input", "Back button clicked");
                game.setScreen(new HomeScreen(game));
            }
            if (mediumButtonRectangle.contains(touchPos.x, touchPos.y)) {
//                game.setScreen(new MediumLevelScreen(game));
            }
            if (easyButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new EasyLevelScreen(game));
            }
            if (hardButtonRectangle.contains(touchPos.x, touchPos.y)) {
//                game.setScreen(new HardLevelScreen(game));
            }
            if (unmuteButtonRectangle.contains(touchPos.x, touchPos.y)) {
                toggleMusic(); // Toggle music when the button is clicked
            }
        }
        if (!Gdx.input.isTouched()) {
            isTouched = false;
        }
    }

    private void toggleMusic() {
        if (isMuted) {
            SoundManager.getInstance().playMusic();
            isMuted = false;
        } else {
            SoundManager.getInstance().stopMusic();
            isMuted = true;
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
        backTexture.dispose();
        unmuteTexture.dispose();
        muteTexture.dispose();
    }
}
