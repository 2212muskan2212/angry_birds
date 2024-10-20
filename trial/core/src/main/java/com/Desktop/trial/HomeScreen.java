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

public class HomeScreen implements Screen {
    private final Main game;
    private Texture backgroundTexture;
    private Texture playButtonTexture;
    private Texture restoreTexture;
    private Texture titleTexture;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Rectangle playButtonRectangle;
    private Vector2 touchPos;

    public HomeScreen(Main game) {
        this.game = game;
        create();
    }

    private void create() {
        backgroundTexture = new Texture("home_background.png");
        playButtonTexture = new Texture("play_button.png");
        restoreTexture = new Texture("restore.png");
        titleTexture = new Texture("title.png");
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 480);
        playButtonRectangle = new Rectangle(340, 280, 120, 35);
        touchPos = new Vector2();
    }

    @Override
    public void show() {
        // This method is called when the screen becomes active
    }

    @Override
    public void render(float delta) {
        input();
        draw();
    }

    private void input() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (playButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new LevelSelectionScreen(game));
            }
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        spriteBatch.draw(titleTexture, 245, 370, 320, 80); // Draw the title
        spriteBatch.draw(playButtonTexture, 340, 285, 110, 35); // Draw the play button
        spriteBatch.draw(restoreTexture, 340, 235, 110, 35);

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        playButtonTexture.dispose();
        titleTexture.dispose();
        restoreTexture.dispose();
        spriteBatch.dispose();
    }
}
