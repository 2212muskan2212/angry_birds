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

public class HardLevelScreen implements Screen {
    private final Main game;
    private Texture backgroundTexture;
    private Texture redBirdTexture, yellowBirdTexture, purpleBirdTexture;
    private Texture woodBlockTexture, glassBlockTexture, steelBlockTexture,glassTriangleTexture, stoneCircleTexture;
    private Texture pigTexture, catapultTexture, resumeIconTexture;
    private Texture redBirdCardTexture, yellowBirdCardTexture, purpleBirdCardTexture;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Rectangle resumeButtonRectangle;
    private Vector2 touchPos;

    public HardLevelScreen(Main game) {
        this.game = game;
        create();
    }

    private void create() {
        backgroundTexture = new Texture("hard_level_background.png");
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
        glassTriangleTexture = new Texture("glass_triangle_block.png");
        stoneCircleTexture = new Texture("stone_circle_block.png");

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
                game.setScreen(new PauseScreen(game, this));
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
        game.getFont().draw(spriteBatch, "Score: 0", 70, 445);
        game.getFont().draw(spriteBatch, "2", 650, 423);
        game.getFont().draw(spriteBatch, "3", 710, 423);
        game.getFont().draw(spriteBatch, "1", 770, 423);

        spriteBatch.draw(catapultTexture, 105, 75, 60, 60);
        spriteBatch.draw(purpleBirdTexture, 60, 70, 35, 35);

        // Structure Drawing
        drawStructure();

        spriteBatch.draw(resumeIconTexture, 10, 410, 50, 50);

        spriteBatch.end();
    }

    private void drawStructure() {
        int blockWidth = 60, blockHeight = 40;

        // First (Bottom) Layer: Wooden blocks
        spriteBatch.draw(woodBlockTexture, 500, 75, blockWidth, blockHeight);
        spriteBatch.draw(woodBlockTexture, 560, 75, blockWidth, blockHeight);

        // Second Layer: Glass blocks on top of wood, supporting the pigs
        spriteBatch.draw(glassBlockTexture, 500, 107, blockWidth, blockHeight);
        spriteBatch.draw(glassBlockTexture, 560, 107, blockWidth, blockHeight);

        // Draw the first pig in the middle between two glass blocks
        spriteBatch.draw(pigTexture, 510, 149, 45, 45);

        // Third Layer: Steel blocks on top of glass for more durability
        spriteBatch.draw(steelBlockTexture, 500, 200, blockWidth, blockHeight);
        spriteBatch.draw(steelBlockTexture, 560, 200, blockWidth, blockHeight);

        // Fourth Layer: Two horizontal wooden blocks on top of steel
        spriteBatch.draw(woodBlockTexture, 494, 230, blockWidth, blockHeight);
        spriteBatch.draw(woodBlockTexture, 566, 230, blockWidth, blockHeight);

        // Fifth Layer: More pigs at the top
        spriteBatch.draw(pigTexture, 513, 260, 40, 40);
        spriteBatch.draw(pigTexture, 568, 260, 40, 40);

        spriteBatch.draw(woodBlockTexture, 500, 249, blockWidth / 3, blockHeight * 2);
        spriteBatch.draw(woodBlockTexture, 550, 220, blockWidth / 3, blockHeight * 3);
        spriteBatch.draw(woodBlockTexture, 600, 249, blockWidth / 3, blockHeight * 2);
        //slim wood blocks
        spriteBatch.draw(woodBlockTexture, 495, 130, blockWidth / 3, blockHeight * 2);
        spriteBatch.draw(woodBlockTexture, 545, 130, blockWidth / 3, blockHeight * 2);
        spriteBatch.draw(woodBlockTexture, 600, 130, blockWidth / 3, blockHeight * 2);

        spriteBatch.draw(woodBlockTexture, 490, 310, 150, blockHeight);
        // Support columns on both sides (vertical wood)
        //spriteBatch.draw(woodBlockTexture, 460, 75, blockWidth / 2, 150); // Left column
        //spriteBatch.draw(woodBlockTexture, 640, 75, blockWidth / 2, 150); // Right column

        // Draw the first pig in the middle between two glass blocks
        spriteBatch.draw(pigTexture, 560, 149, 45, 45);

        spriteBatch.draw(glassTriangleTexture, 578, 343, 40, 40);
        spriteBatch.draw(glassTriangleTexture, 510, 343, 40, 40);
        spriteBatch.draw(pigTexture, 544, 343, 40, 40);
        spriteBatch.draw(woodBlockTexture, 495, 375, 150, 20);

        //spriteBatch.draw(stoneCircleTexture, 500, 390, 40, 40);
        //spriteBatch.draw(stoneCircleTexture, 600, 390, 40, 40);

        spriteBatch.draw(woodBlockTexture, 600, 75, 150, blockHeight);
        spriteBatch.draw(glassTriangleTexture, 620, 107, 40, 40);
        spriteBatch.draw(glassTriangleTexture, 690, 107, 40, 40);
        spriteBatch.draw(pigTexture, 655, 105, 45, 45);
        spriteBatch.draw(woodBlockTexture, 600, 140, 150, 20);

        spriteBatch.draw(stoneCircleTexture, 620, 155, 40, 40);
        spriteBatch.draw(stoneCircleTexture, 700, 155, 40, 40);
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