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
    private Bird redBird, yellowBird, purpleBird;
    private Pig pig;
    //private Texture woodBlockTexture, glassBlockTexture, steelBlockTexture,glassTriangleTexture, steelCircleTexture;
    private WoodBlock woodBlock1,woodBlock2,woodBlock3,woodBlock4,woodBlock5,woodBlock6,woodBlock7,woodBlock8,woodBlock9,woodBlock10,woodBlock11,woodBlock12,woodBlock13,woodBlock14;
    private GlassBlock glassBlock1,glassBlock2;
    private GlassBlock glassTriangleBlock1,glassTriangleBlock2,glassTriangleBlock3,glassTriangleBlock4;
    private SteelBlock steelBlock1,steelBlock2;
    private SteelBlock steelCircleBlock1,steelCircleBlock2;
    private Texture catapultTexture, resumeIconTexture;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Rectangle resumeButtonRectangle;
    private Vector2 touchPos;

    public HardLevelScreen(Main game) {
        this.game = game;
        create();
    }

    private void create() {
        redBird = new Bird("red_bird.png", "red_bird_card.png");
        yellowBird = new Bird("yellow_bird.png", "yellow_bird_card.png");
        purpleBird = new Bird("purple_bird.png", "purple_bird_card.png");

        backgroundTexture = new Texture("hard_level_background.png");
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
        woodBlock13 = new WoodBlock("wood_block.png");
        woodBlock14 = new WoodBlock("wood_block.png");
        glassBlock1 = new GlassBlock("glass_block.png");
        glassBlock2 = new GlassBlock("glass_block.png");
        glassTriangleBlock1 = new GlassBlock("glass_triangle_block.png");
        glassTriangleBlock2 = new GlassBlock("glass_triangle_block.png");
        glassTriangleBlock3 = new GlassBlock("glass_triangle_block.png");
        glassTriangleBlock4 = new GlassBlock("glass_triangle_block.png");
        steelBlock1 = new SteelBlock("steel_block.png");
        steelBlock2 = new SteelBlock("steel_block.png");
        steelCircleBlock1 = new SteelBlock("steel_circle_block.png");
        steelCircleBlock2 = new SteelBlock("steel_circle_block.png");




        //woodBlockTexture = new Texture("wood_block.png");
        //glassBlockTexture = new Texture("glass_block.png");
        //steelBlockTexture = new Texture("steel_block.png");

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
        game.getFont().draw(spriteBatch, "Score: 0", 70, 445);
        game.getFont().draw(spriteBatch, "2", 650, 423);
        game.getFont().draw(spriteBatch, "3", 710, 423);
        game.getFont().draw(spriteBatch, "1", 770, 423);

        spriteBatch.draw(catapultTexture, 105, 75, 60, 60);
        spriteBatch.draw(purpleBird.getBirdTexture(), 60, 70, 35, 35);

        // Structure Drawing
        drawStructure();

        spriteBatch.draw(resumeIconTexture, 10, 410, 50, 50);

        spriteBatch.end();
    }

    private void drawStructure() {
        int blockWidth = 60, blockHeight = 40;

        // First (Bottom) Layer: Wooden blocks
        spriteBatch.draw(woodBlock1.getBlockTexture(), 500, 75, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock2.getBlockTexture(), 560, 75, blockWidth, blockHeight);

        // Second Layer: Glass blocks on top of wood, supporting the pigs
        spriteBatch.draw(glassBlock1.getBlockTexture(), 500, 107, blockWidth, blockHeight);
        spriteBatch.draw(glassBlock2.getBlockTexture(), 560, 107, blockWidth, blockHeight);

        // Draw the first pig in the middle between two glass blocks
        spriteBatch.draw(pig.getPigTexture(), 510, 149, 45, 45);

        // Third Layer: Steel blocks on top of glass for more durability
        spriteBatch.draw(steelBlock1.getBlockTexture(), 500, 200, blockWidth, blockHeight);
        spriteBatch.draw(steelBlock2.getBlockTexture(), 560, 200, blockWidth, blockHeight);

        // Fourth Layer: Two horizontal wooden blocks on top of steel
        spriteBatch.draw(woodBlock3.getBlockTexture(), 494, 230, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock4.getBlockTexture(), 566, 230, blockWidth, blockHeight);

        // Fifth Layer: More pigs at the top
        spriteBatch.draw(pig.getPigTexture(), 513, 260, 40, 40);
        spriteBatch.draw(pig.getPigTexture(), 568, 260, 40, 40);

        spriteBatch.draw(woodBlock5.getBlockTexture(), 500, 249, blockWidth / 3, blockHeight * 2);
        spriteBatch.draw(woodBlock6.getBlockTexture(), 550, 220, blockWidth / 3, blockHeight * 3);
        spriteBatch.draw(woodBlock7.getBlockTexture(), 600, 249, blockWidth / 3, blockHeight * 2);
        //slim wood blocks
        spriteBatch.draw(woodBlock8.getBlockTexture(), 495, 130, blockWidth / 3, blockHeight * 2);
        spriteBatch.draw(woodBlock9.getBlockTexture(), 545, 130, blockWidth / 3, blockHeight * 2);
        spriteBatch.draw(woodBlock10.getBlockTexture(), 600, 130, blockWidth / 3, blockHeight * 2);

        spriteBatch.draw(woodBlock11.getBlockTexture(), 490, 310, 150, blockHeight);
        // Support columns on both sides (vertical wood)
        //spriteBatch.draw(woodBlockTexture, 460, 75, blockWidth / 2, 150); // Left column
        //spriteBatch.draw(woodBlockTexture, 640, 75, blockWidth / 2, 150); // Right column

        // Draw the first pig in the middle between two glass blocks
        spriteBatch.draw(pig.getPigTexture(), 560, 149, 45, 45);

        spriteBatch.draw(glassTriangleBlock1.getBlockTexture(), 578, 343, 40, 40);
        spriteBatch.draw(glassTriangleBlock2.getBlockTexture(), 510, 343, 40, 40);
        spriteBatch.draw(pig.getPigTexture(), 544, 343, 40, 40);
        spriteBatch.draw(woodBlock12.getBlockTexture(), 495, 375, 150, 20);

        //spriteBatch.draw(steelCircleTexture, 500, 390, 40, 40);
        //spriteBatch.draw(steelCircleTexture, 600, 390, 40, 40);

        spriteBatch.draw(woodBlock13.getBlockTexture(), 600, 75, 150, blockHeight);
        spriteBatch.draw(glassTriangleBlock3.getBlockTexture(), 620, 107, 40, 40);
        spriteBatch.draw(glassTriangleBlock4.getBlockTexture(), 690, 107, 40, 40);
        spriteBatch.draw(pig.getPigTexture(), 655, 105, 45, 45);
        spriteBatch.draw(woodBlock14.getBlockTexture(), 600, 140, 150, 20);

        spriteBatch.draw(steelCircleBlock1.getBlockTexture(), 620, 155, 40, 40);
        spriteBatch.draw(steelCircleBlock2.getBlockTexture(), 700, 155, 40, 40);
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
        woodBlock13.dispose();
        woodBlock14.dispose();
        glassBlock1.dispose();
        glassBlock2.dispose();
        glassTriangleBlock1.dispose();
        glassTriangleBlock2.dispose();
        glassTriangleBlock3.dispose();
        glassTriangleBlock4.dispose();
        steelBlock1.dispose();
        steelBlock2.dispose();
        steelCircleBlock1.dispose();
        steelCircleBlock2.dispose();
        pig.dispose();
        catapultTexture.dispose();
        resumeIconTexture.dispose();
        spriteBatch.dispose();
    }
}