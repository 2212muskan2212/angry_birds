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
    //private Texture woodBlockTexture, glassBlockTexture, steelBlockTexture, glassTriangleTexture, steelCircleTexture;
    private WoodBlock woodBlock1,woodBlock2,woodBlock3,woodBlock4,woodBlock5,woodBlock6,woodBlock7,woodBlock8,woodBlock9,woodBlock10,woodBlock11,woodBlock12;
    private GlassBlock glassBlock1,glassBlock2,glassBlock3,glassBlock4;
    private GlassBlock glassTriangleBlock1,glassTriangleBlock2;
    private SteelBlock steelBlock1,steelBlock2,steelBlock3;
    private SteelBlock steelCircleBlock1,steelCircleBlock2;

    private Texture catapultTexture, resumeIconTexture;
    private Bird redBird, yellowBird, purpleBird;
    private Pig pig;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Rectangle resumeButtonRectangle;
    private Vector2 touchPos;

    public MediumLevelScreen(Main game) {
        this.game = game;
        create();
    }

    private void create() {
        redBird = new Bird("red_bird.png", "red_bird_card.png");
        yellowBird = new Bird("yellow_bird.png", "yellow_bird_card.png");
        purpleBird = new Bird("purple_bird.png", "purple_bird_card.png");

        backgroundTexture = new Texture("medium_level_background.png");
        woodBlock1 = new WoodBlock("wood_block.png");
        woodBlock2 = new WoodBlock("wood_block.png");
        woodBlock3 = new WoodBlock("wood_block.png");
        woodBlock4 = new WoodBlock("wood_block.png");
        woodBlock5 = new WoodBlock("wood_block.png");
        woodBlock6 = new WoodBlock("wood_block.png");
        woodBlock7 = new WoodBlock("wood_block.png");
        woodBlock8 = new WoodBlock("wood_block.png");
        woodBlock9 = new WoodBlock("wood_block.png");
        woodBlock10 = new WoodBlock("wood_block.png");
        woodBlock11 = new WoodBlock("wood_block.png");
        woodBlock12 = new WoodBlock("wood_block.png");
        glassBlock1 = new GlassBlock("glass_block.png");
        glassBlock2 = new GlassBlock("glass_block.png");
        glassBlock3 = new GlassBlock("glass_block.png");
        glassBlock4 = new GlassBlock("glass_block.png");
        glassTriangleBlock1 = new GlassBlock("glass_triangle_block.png");
        glassTriangleBlock2 = new GlassBlock("glass_triangle_block.png");
        steelBlock1 = new SteelBlock("steel_block.png");
        steelBlock2 = new SteelBlock("steel_block.png");
        steelBlock3 = new SteelBlock("steel_block.png");
        steelCircleBlock1 = new SteelBlock("steel_circle_block.png");
        steelCircleBlock2 = new SteelBlock("steel_circle_block.png");

        pig = new Pig("pig.png");

        catapultTexture = new Texture("catapult.png");
        resumeIconTexture = new Texture("resume_icon.png");
        //glassTriangleTexture = new Texture("glass_triangle_block.png");
        //steelCircleTexture = new Texture("steel_circle_block.png");

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
//                System.out.println("Resume button clicked! Transitioning back.");
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

        // Draw background
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        // Draw bird cards in top right corner
        spriteBatch.draw(redBird.getBirdCardTexture(), 630, 410, 45, 60);
        spriteBatch.draw(yellowBird.getBirdCardTexture(), 690, 410, 45, 60);
        spriteBatch.draw(purpleBird.getBirdCardTexture(), 750, 410, 45, 60);

        // Hardcoded fonts
        game.getFont().draw(spriteBatch, "Score: 338", 70, 445);
        game.getFont().draw(spriteBatch, "1", 650, 423);
        game.getFont().draw(spriteBatch, "1", 710, 423);
        game.getFont().draw(spriteBatch, "1", 770, 423);

        spriteBatch.draw(catapultTexture, 110, 75, 60, 60);
        spriteBatch.draw(redBird.getBirdTexture(), 50, 75, 30, 30);

        // Structure Drawing
        drawStructure();

        spriteBatch.draw(resumeIconTexture, 10, 410, 50, 50);

        spriteBatch.end();
    }

    private void drawStructure() {
        int blockWidth = 60, blockHeight = 40;

        // First (Bottom) Layer
        spriteBatch.draw(woodBlock1.getBlockTexture(), 480, 75, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock2.getBlockTexture(), 530, 75, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock3.getBlockTexture(), 580, 75, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock4.getBlockTexture(), 630, 75, blockWidth, blockHeight);

        // Second Layer
        spriteBatch.draw(woodBlock5.getBlockTexture(), 480, 105, blockWidth, blockHeight);
        spriteBatch.draw(glassBlock1.getBlockTexture(), 530, 105, blockWidth, blockHeight);
        spriteBatch.draw(glassBlock2.getBlockTexture(), 585, 105, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock6.getBlockTexture(), 630, 105, blockWidth, blockHeight);

        // Third Layer: One horizontal wood block in the center
        spriteBatch.draw(steelBlock1.getBlockTexture(), 490,140, blockWidth, blockHeight);
        spriteBatch.draw(steelBlock2.getBlockTexture(), 550,140, blockWidth, blockHeight);
        spriteBatch.draw(steelBlock3.getBlockTexture(), 610,140, blockWidth, blockHeight);

        // Fourth Layer
        spriteBatch.draw(glassBlock3.getBlockTexture(), 490, 180, blockWidth, blockHeight);
        spriteBatch.draw(glassBlock4.getBlockTexture(), 615, 180, blockWidth, blockHeight);

        // Top Layer
        spriteBatch.draw(woodBlock7.getBlockTexture(), 490, 210, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock8.getBlockTexture(), 610, 210, blockWidth, blockHeight);

        // Draw pigs: Three pigs placed symmetrically
        spriteBatch.draw(pig.getPigTexture(), 564,210, 36, 36);
        spriteBatch.draw(pig.getPigTexture(), 500,241, 36, 36);
        spriteBatch.draw(pig.getPigTexture(), 620,241, 36, 36);

        spriteBatch.draw(woodBlock9.getBlockTexture(), 495, 230, blockWidth / 5, blockHeight * 2);
        spriteBatch.draw(woodBlock10.getBlockTexture(), 530, 230, blockWidth / 5, blockHeight * 2);

        spriteBatch.draw(woodBlock11.getBlockTexture(), 613, 230, blockWidth / 5, blockHeight * 2);
        spriteBatch.draw(woodBlock12.getBlockTexture(), 650, 230, blockWidth / 5, blockHeight * 2);

        spriteBatch.draw(glassTriangleBlock1.getBlockTexture(), 495, 297, 50, 50);
        spriteBatch.draw(glassTriangleBlock2.getBlockTexture(), 615, 297, 50, 50);

        spriteBatch.draw(steelCircleBlock1.getBlockTexture(), 545, 177, 40, 40);
        spriteBatch.draw(steelCircleBlock2.getBlockTexture(), 580, 177, 40, 40);
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
        redBird.getBirdTexture().dispose();
        yellowBird.getBirdTexture().dispose();
        purpleBird.getBirdTexture().dispose();
        redBird.getBirdCardTexture().dispose();
        yellowBird.getBirdCardTexture().dispose();
        purpleBird.getBirdCardTexture().dispose();
        woodBlock1.dispose();
        woodBlock2.dispose();
        woodBlock3.dispose();
        woodBlock4.dispose();
        woodBlock5.dispose();
        woodBlock6.dispose();
        woodBlock7.dispose();
        woodBlock8.dispose();
        woodBlock9.dispose();
        woodBlock10.dispose();
        woodBlock11.dispose();
        woodBlock12.dispose();
        glassBlock1.dispose();
        glassBlock2.dispose();
        glassBlock3.dispose();
        glassBlock4.dispose();
        glassTriangleBlock1.dispose();
        glassTriangleBlock2.dispose();
        steelBlock1.dispose();
        steelBlock2.dispose();
        steelBlock3.dispose();
        steelCircleBlock1.dispose();
        steelCircleBlock2.dispose();
        pig.dispose();
        catapultTexture.dispose();
        resumeIconTexture.dispose();
        spriteBatch.dispose();
    }
}