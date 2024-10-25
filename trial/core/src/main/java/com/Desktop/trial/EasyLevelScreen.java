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

public class EasyLevelScreen implements Screen {
    private final Main game;
    private Texture backgroundTexture;
    private Texture woodBlockTexture, glassBlockTexture, steelBlockTexture;
    private Texture catapultTexture, resumeIconTexture;
    private Bird redBird, yellowBird, purpleBird;
    private Pig pig;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Rectangle resumeButtonRectangle;
    private Vector2 touchPos;

    public EasyLevelScreen(Main game) {
        this.game = game;
        create();
    }

    private void create() {
        backgroundTexture = new Texture("easy_level_background.png");
        redBird = new Bird("red_bird.png", "red_bird_card.png");
        yellowBird = new Bird("yellow_bird.png", "yellow_bird_card.png");
        purpleBird = new Bird("purple_bird.png", "purple_bird_card.png");

        woodBlockTexture = new Texture("wood_block.png");
        glassBlockTexture = new Texture("glass_block.png");
        steelBlockTexture = new Texture("steel_block.png");
        catapultTexture = new Texture("catapult.png");
        resumeIconTexture = new Texture("resume_icon.png");

        pig = new Pig("pig.png");

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 480);
        resumeButtonRectangle = new Rectangle(10, 410, 50, 50);
        touchPos = new Vector2();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        input();
        draw();
    }

    private void input() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            if (resumeButtonRectangle.contains(touchPos.x, touchPos.y)) {
                System.out.println("Resume button clicked! Transitioning back.");
                game.setScreen(new PauseScreen(game, this));
            }
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.SKY);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        spriteBatch.draw(redBird.getBirdCardTexture(), 630, 410, 45, 60);
        spriteBatch.draw(yellowBird.getBirdCardTexture(), 690, 410, 45, 60);
        spriteBatch.draw(purpleBird.getBirdCardTexture(), 750, 410, 45, 60);

        game.getFont().draw(spriteBatch, "Score: 0", 70, 445);
        game.getFont().draw(spriteBatch, "2", 650, 423);
        game.getFont().draw(spriteBatch, "2", 710, 423);
        game.getFont().draw(spriteBatch, "1", 770, 423);

        spriteBatch.draw(catapultTexture, 110, 75, 60, 60);
        spriteBatch.draw(redBird.getBirdTexture(), 150, 130, 30, 30);

        drawStructure();
        spriteBatch.draw(resumeIconTexture, 10, 410, 50, 50);

        spriteBatch.end();
    }

    private void drawStructure() {
        int blockWidth = 60, blockHeight = 40;

        spriteBatch.draw(woodBlockTexture, 480, 75, blockWidth, blockHeight);
        spriteBatch.draw(woodBlockTexture, 570, 75, blockWidth, blockHeight);

        spriteBatch.draw(woodBlockTexture, 480, 85, blockWidth, blockHeight * 2);
        spriteBatch.draw(woodBlockTexture, 570, 85, blockWidth, blockHeight * 2);

        spriteBatch.draw(woodBlockTexture, 480, 145, blockWidth, blockHeight);
        spriteBatch.draw(woodBlockTexture, 510, 200, blockWidth / 2, blockHeight * 2);
        spriteBatch.draw(woodBlockTexture, 567, 200, blockWidth / 2, blockHeight * 2);
        spriteBatch.draw(woodBlockTexture, 570, 145, blockWidth, blockHeight);

        spriteBatch.draw(pig.getPigTexture(), 533, 212, 40, 40);

        spriteBatch.draw(glassBlockTexture, 525, 175, blockWidth, blockHeight);
        spriteBatch.draw(glassBlockTexture, 525, 265, blockWidth, blockHeight);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        redBird.dispose();
        yellowBird.dispose();
        purpleBird.dispose();
        woodBlockTexture.dispose();
        glassBlockTexture.dispose();
        steelBlockTexture.dispose();
        catapultTexture.dispose();
        resumeIconTexture.dispose();
        pig.dispose();
        spriteBatch.dispose();
    }
}
