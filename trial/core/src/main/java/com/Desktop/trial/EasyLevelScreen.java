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
    private Texture redBirdTexture, yellowBirdTexture, purpleBirdTexture;
    private Texture woodBlockTexture, glassBlockTexture, steelBlockTexture;
    private Texture pigTexture, catapultTexture, resumeIconTexture;
    private Texture redBirdCardTexture, yellowBirdCardTexture, purpleBirdCardTexture;
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
        redBirdTexture = new Texture("red_bird.png");
        yellowBirdTexture = new Texture("yellow_bird.png");
        purpleBirdTexture = new Texture("purple_bird.png");
        woodBlockTexture = new Texture("wood_block.png");
        glassBlockTexture = new Texture("glass_block.png");
        steelBlockTexture = new Texture("steel_block.png");
        pigTexture = new Texture("pig.png");
        catapultTexture = new Texture("catapult.png");
        resumeIconTexture = new Texture("resume_icon.png");
        redBirdCardTexture = new Texture("red_bird_card.png");
        yellowBirdCardTexture = new Texture("yellow_bird_card.png");
        purpleBirdCardTexture = new Texture("purple_bird_card.png");

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 480);
        resumeButtonRectangle = new Rectangle(10, 410, 50, 50);
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
            if (resumeButtonRectangle.contains(touchPos.x, touchPos.y)) {
                System.out.println("Resume button clicked! Transitioning back.");
                // Logic to resume or go to a different screen
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

        // Draw background
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        // Draw bird cards in top right corner
        spriteBatch.draw(redBirdCardTexture, 630, 410, 45, 60);
        spriteBatch.draw(yellowBirdCardTexture, 690, 410, 45, 60);
        spriteBatch.draw(purpleBirdCardTexture, 750, 410, 45, 60);

        // Hardcoded fonts
        game.getFont().draw(spriteBatch, "Score: 100", 70, 445);
        game.getFont().draw(spriteBatch, "2", 650, 423);
        game.getFont().draw(spriteBatch, "1", 710, 423);
        game.getFont().draw(spriteBatch, "4", 770, 423);

        spriteBatch.draw(catapultTexture, 110, 75, 60, 60);
        spriteBatch.draw(yellowBirdTexture, 150, 130, 30, 30);

        // Structure Drawing
        drawStructure();

        spriteBatch.draw(resumeIconTexture, 10, 410, 50, 50);

        spriteBatch.end();
    }

    private void drawStructure() {
        int blockWidth = 60, blockHeight = 40;

        spriteBatch.draw(woodBlockTexture, 480, 75, blockWidth, blockHeight);
        spriteBatch.draw(woodBlockTexture, 570, 75, blockWidth, blockHeight);

        // Second Layer: Two vertical wood blocks on the sides
        spriteBatch.draw(woodBlockTexture, 480, 85, blockWidth, blockHeight * 2); // Double height for vertical blocks
        spriteBatch.draw(woodBlockTexture, 570, 85, blockWidth, blockHeight * 2);

        // Third Layer: One horizontal wood block
        spriteBatch.draw(woodBlockTexture, 480, 145, blockWidth, blockHeight);

        // Fourth Layer: Two vertical glass blocks in the center
        spriteBatch.draw(woodBlockTexture, 505, 200, blockWidth / 2, blockHeight * 2);
        spriteBatch.draw(woodBlockTexture, 570, 200, blockWidth / 2, blockHeight * 2);

        // Fifth Layer: One horizontal wood block on top
        spriteBatch.draw(woodBlockTexture, 570, 145, blockWidth, blockHeight);

        // Draw the pig in the middle of the top layer
        spriteBatch.draw(pigTexture, 535, 200, 40, 40);

        spriteBatch.draw(glassBlockTexture, 525, 175, blockWidth, blockHeight);

        spriteBatch.draw(glassBlockTexture, 525, 235, blockWidth, blockHeight);





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
        redBirdTexture.dispose();
        yellowBirdTexture.dispose();
        purpleBirdTexture.dispose();
        woodBlockTexture.dispose();
        glassBlockTexture.dispose();
        steelBlockTexture.dispose();
        pigTexture.dispose();
        catapultTexture.dispose();
        resumeIconTexture.dispose();
        redBirdCardTexture.dispose();
        yellowBirdCardTexture.dispose();
        purpleBirdCardTexture.dispose();
        spriteBatch.dispose();
    }
}