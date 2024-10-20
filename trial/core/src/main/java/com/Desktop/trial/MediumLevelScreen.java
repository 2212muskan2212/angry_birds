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

public class MediumLevelScreen implements Screen {
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

    public MediumLevelScreen(Main game) {
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
        game.getFont().draw(spriteBatch, "Score: 338", 70, 445);
        game.getFont().draw(spriteBatch, "1", 650, 423);
        game.getFont().draw(spriteBatch, "1", 710, 423);
        game.getFont().draw(spriteBatch, "1", 770, 423);

        spriteBatch.draw(catapultTexture, 110, 75, 60, 60);
        spriteBatch.draw(redBirdTexture, 50, 75, 30, 30);

        // Structure Drawing
        drawStructure();

        spriteBatch.draw(resumeIconTexture, 10, 410, 50, 50);

        spriteBatch.end();
    }

    private void drawStructure() {
        int blockWidth = 60, blockHeight = 40;

        // First (Bottom) Layer
        spriteBatch.draw(woodBlockTexture, 480, 75, blockWidth, blockHeight);
        spriteBatch.draw(woodBlockTexture, 530, 75, blockWidth, blockHeight);
        spriteBatch.draw(woodBlockTexture, 580, 75, blockWidth, blockHeight);
        spriteBatch.draw(woodBlockTexture, 630, 75, blockWidth, blockHeight);

        // Second Layer
        spriteBatch.draw(woodBlockTexture, 480, 105, blockWidth, blockHeight);
        spriteBatch.draw(glassBlockTexture, 530, 105, blockWidth, blockHeight);
        spriteBatch.draw(glassBlockTexture, 585, 105, blockWidth, blockHeight);
        spriteBatch.draw(woodBlockTexture, 630, 105, blockWidth, blockHeight);

        // Third Layer: One horizontal wood block in the center
        spriteBatch.draw(steelBlockTexture, 490,140, blockWidth, blockHeight);
        spriteBatch.draw(steelBlockTexture, 550,140, blockWidth, blockHeight);
        spriteBatch.draw(steelBlockTexture, 610,140, blockWidth, blockHeight);

        // Fourth Layer
        spriteBatch.draw(glassBlockTexture, 490, 180, blockWidth, blockHeight);
        spriteBatch.draw(glassBlockTexture, 615, 180, blockWidth, blockHeight);

        // Top Layer
        spriteBatch.draw(woodBlockTexture, 490, 210, blockWidth, blockHeight);
        spriteBatch.draw(woodBlockTexture, 610, 210, blockWidth, blockHeight);

        // Draw pigs: Three pigs placed symmetrically
        spriteBatch.draw(pigTexture, 561,175, 40, 40); // Center pig on top
        spriteBatch.draw(pigTexture, 500,241, 40, 40);
        spriteBatch.draw(pigTexture, 620,241, 40, 40);
    }

    private void input() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            if (resumeButtonRectangle.contains(touchPos.x, touchPos.y)) {
                System.out.println("Resume button clicked! Transitioning to PauseScreen.");
                game.setScreen(new PauseScreen(game)); // Transition to the PauseScreen
            }
        }
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
