//package com.Desktop.trial;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.utils.viewport.FitViewport;
//
//public class EasyLevelScreen implements Screen {
//    private final Main game;
//    private Texture backgroundTexture;
//    //private Texture woodBlockTexture, glassBlockTexture, steelBlockTexture;
//    private WoodBlock woodBlock1,woodBlock2,woodBlock3,woodBlock4,woodBlock5,woodBlock6,woodBlock7,woodBlock8;
//    private GlassBlock glassBlock1,glassBlock2;
//    private SteelBlock steelBlock;
//    private Texture catapultTexture, resumeIconTexture, wonTexture, lostTexture;
//    private Bird redBird, yellowBird, purpleBird;
//    private Pig pig;
//    private SpriteBatch spriteBatch;
//    private FitViewport viewport;
//    private Rectangle resumeButtonRectangle;
//    private Rectangle wonButtonRectangle, lostButtonRectangle;
//    private Vector2 touchPos;
//
//    public EasyLevelScreen(Main game) {
//        this.game = game;
//        create();
//    }
//
//    private void create() {
//        backgroundTexture = new Texture("easy_level_background.png");
//        redBird = new Bird("red_bird.png", "red_bird_card.png");
//        yellowBird = new Bird("yellow_bird.png", "yellow_bird_card.png");
//        purpleBird = new Bird("purple_bird.png", "purple_bird_card.png");
//
//        /*woodBlockTexture = new Texture("wood_block.png");
//        glassBlockTexture = new Texture("glass_block.png");
//        steelBlockTexture = new Texture("steel_block.png");*/
//        woodBlock1 = new WoodBlock("wood_block.png");
//        woodBlock2 = new WoodBlock("wood_block.png");
//        woodBlock3 = new WoodBlock("wood_block.png");
//        woodBlock4 = new WoodBlock("wood_block.png");
//        woodBlock5 = new WoodBlock("wood_block.png");
//        woodBlock6 = new WoodBlock("wood_block.png");
//        woodBlock7 = new WoodBlock("wood_block.png");
//        woodBlock8 = new WoodBlock("wood_block.png");
//        glassBlock1 = new GlassBlock("glass_block.png");
//        glassBlock2 = new GlassBlock("glass_block.png");
//
//        catapultTexture = new Texture("catapult.png");
//        resumeIconTexture = new Texture("resume_icon.png");
//        wonTexture = new Texture("level_won_button.png");
//        lostTexture = new Texture("level_lost_button.png");
//
//        pig = new Pig("pig.png");
//
//        spriteBatch = new SpriteBatch();
//        viewport = new FitViewport(800, 480);
//        resumeButtonRectangle = new Rectangle(10, 410, 50, 50);
//        wonButtonRectangle = new Rectangle(325, 435, 73, 25);
//        lostButtonRectangle = new Rectangle(410, 435, 73, 25);
//        touchPos = new Vector2();
//    }
//
//    @Override
//    public void show() {}
//
//    @Override
//    public void render(float delta) {
//        input();
//        draw();
//    }
//
//    private void input() {
//        if (Gdx.input.isTouched()) {
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
//            viewport.unproject(touchPos);
//            if (resumeButtonRectangle.contains(touchPos.x, touchPos.y)) {
//                System.out.println("Resume button clicked! Transitioning back.");
//                game.setScreen(new PauseScreen(game, this));
////                game.setScreen(new PauseScreen(game));
//            }
//            else if (wonButtonRectangle.contains(touchPos.x, touchPos.y)) {
//                game.setScreen(new LevelCompletedScreen(game));
//            }
//            else if (lostButtonRectangle.contains(touchPos.x, touchPos.y)) {
//                game.setScreen(new LevelLostScreen(game));
//            }
//        }
//    }
//
//    private void draw() {
//        ScreenUtils.clear(Color.SKY);
//        viewport.apply();
//        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
//        spriteBatch.begin();
//
//        float worldWidth = viewport.getWorldWidth();
//        float worldHeight = viewport.getWorldHeight();
//
//        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
//
//        spriteBatch.draw(redBird.getBirdCardTexture(), 630, 410, 45, 60);
//        spriteBatch.draw(yellowBird.getBirdCardTexture(), 690, 410, 45, 60);
//        spriteBatch.draw(purpleBird.getBirdCardTexture(), 750, 410, 45, 60);
//
//        game.getFont().draw(spriteBatch, "Score: 0", 70, 445);
//        game.getFont().draw(spriteBatch, "2", 650, 423);
//        game.getFont().draw(spriteBatch, "2", 710, 423);
//        game.getFont().draw(spriteBatch, "1", 770, 423);
//
//        spriteBatch.draw(catapultTexture, 110, 75, 60, 60);
//        spriteBatch.draw(redBird.getBirdTexture(), 150, 130, 30, 30);
//
//        drawStructure();
//        spriteBatch.draw(resumeIconTexture, 10, 410, 50, 50);
//        spriteBatch.draw(wonTexture, 325, 435, 73, 25);
//        spriteBatch.draw(lostTexture, 410, 435, 73, 25);
//
//        spriteBatch.end();
//    }
//
//    private void drawStructure() {
//        int blockWidth = 60, blockHeight = 40;
//
//        spriteBatch.draw(woodBlock1.getBlockTexture(), 480, 75, blockWidth, blockHeight);
//        spriteBatch.draw(woodBlock2.getBlockTexture(), 570, 75, blockWidth, blockHeight);
//
//        spriteBatch.draw(woodBlock3.getBlockTexture(), 480, 85, blockWidth, blockHeight * 2);
//        spriteBatch.draw(woodBlock4.getBlockTexture(), 570, 85, blockWidth, blockHeight * 2);
//
//        spriteBatch.draw(woodBlock5.getBlockTexture(), 480, 145, blockWidth, blockHeight);
//        spriteBatch.draw(woodBlock6.getBlockTexture(), 510, 200, blockWidth / 2, blockHeight * 2);
//        spriteBatch.draw(woodBlock7.getBlockTexture(), 567, 200, blockWidth / 2, blockHeight * 2);
//        spriteBatch.draw(woodBlock8.getBlockTexture(), 570, 145, blockWidth, blockHeight);
//
//        spriteBatch.draw(pig.getPigTexture(), 533, 212, 40, 40);
//
//        spriteBatch.draw(glassBlock1.getBlockTexture(), 525, 175, blockWidth, blockHeight);
//        spriteBatch.draw(glassBlock2.getBlockTexture(), 525, 265, blockWidth, blockHeight);
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height, true);
//    }
//
//    @Override
//    public void pause() {}
//
//    @Override
//    public void resume() {}
//
//    @Override
//    public void hide() {}
//
//    @Override
//    public void dispose() {
//        backgroundTexture.dispose();
//        redBird.dispose();
//        yellowBird.dispose();
//        purpleBird.dispose();
//        woodBlock1.dispose();
//        woodBlock2.dispose();
//        woodBlock3.dispose();
//        woodBlock4.dispose();
//        woodBlock5.dispose();
//        woodBlock6.dispose();
//        woodBlock7.dispose();
//        woodBlock8.dispose();
//        glassBlock1.dispose();
//        glassBlock2.dispose();
//        catapultTexture.dispose();
//        resumeIconTexture.dispose();
//        pig.dispose();
//        spriteBatch.dispose();
//    }
//}
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
    private WoodBlock woodBlock1, woodBlock2, woodBlock3, woodBlock4, woodBlock5, woodBlock6, woodBlock7, woodBlock8;
    private GlassBlock glassBlock1, glassBlock2;
    private Texture catapultTexture, resumeIconTexture, wonTexture, lostTexture;
    private Bird redBird, yellowBird, purpleBird;
    private Pig pig;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Rectangle resumeButtonRectangle;
    private Rectangle wonButtonRectangle, lostButtonRectangle;
    private Vector2 touchPos;

    private Vector2 birdPosition; // Current position of the red bird
    private boolean isDraggingBird; // Whether the bird is being dragged

    public EasyLevelScreen(Main game) {
        this.game = game;
        create();
    }

    private void create() {
        backgroundTexture = new Texture("easy_level_background.png");
        redBird = new Bird("red_bird.png", "red_bird_card.png");
        yellowBird = new Bird("yellow_bird.png", "yellow_bird_card.png");
        purpleBird = new Bird("purple_bird.png", "purple_bird_card.png");

        woodBlock1 = new WoodBlock("wood_block.png");
        woodBlock2 = new WoodBlock("wood_block.png");
        woodBlock3 = new WoodBlock("wood_block.png");
        woodBlock4 = new WoodBlock("wood_block.png");
        woodBlock5 = new WoodBlock("wood_block.png");
        woodBlock6 = new WoodBlock("wood_block.png");
        woodBlock7 = new WoodBlock("wood_block.png");
        woodBlock8 = new WoodBlock("wood_block.png");
        glassBlock1 = new GlassBlock("glass_block.png");
        glassBlock2 = new GlassBlock("glass_block.png");

        catapultTexture = new Texture("catapult.png");
        resumeIconTexture = new Texture("resume_icon.png");
        wonTexture = new Texture("level_won_button.png");
        lostTexture = new Texture("level_lost_button.png");

        pig = new Pig("pig.png");

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 480);
        resumeButtonRectangle = new Rectangle(10, 410, 50, 50);
        wonButtonRectangle = new Rectangle(325, 435, 73, 25);
        lostButtonRectangle = new Rectangle(410, 435, 73, 25);
        touchPos = new Vector2();

        birdPosition = new Vector2(150, 130); // Initial position of the red bird
        isDraggingBird = false; // Bird is not being dragged initially
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

            if (!isDraggingBird) {
                Rectangle birdBounds = new Rectangle(birdPosition.x, birdPosition.y, 30, 30); // Bird's bounding box
                if (birdBounds.contains(touchPos.x, touchPos.y)) {
                    isDraggingBird = true; // Start dragging the bird
                }
            }

            if (isDraggingBird) {
                birdPosition.set(touchPos.x - 15, touchPos.y - 15); // Update bird's position to follow touch
            }

            if (resumeButtonRectangle.contains(touchPos.x, touchPos.y)) {
                System.out.println("Resume button clicked! Transitioning back.");
                game.setScreen(new PauseScreen(game, this));
            } else if (wonButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new LevelCompletedScreen(game));
            } else if (lostButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new LevelLostScreen(game));
            }
        } else {
            isDraggingBird = false; // Stop dragging when touch ends
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
        spriteBatch.draw(redBird.getBirdTexture(), birdPosition.x, birdPosition.y, 30, 30); // Draw the bird at its position

        drawStructure();
        spriteBatch.draw(resumeIconTexture, 10, 410, 50, 50);
        spriteBatch.draw(wonTexture, 325, 435, 73, 25);
        spriteBatch.draw(lostTexture, 410, 435, 73, 25);

        spriteBatch.end();
    }

    private void drawStructure() {
        int blockWidth = 60, blockHeight = 40;

        spriteBatch.draw(woodBlock1.getBlockTexture(), 480, 75, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock2.getBlockTexture(), 570, 75, blockWidth, blockHeight);

        spriteBatch.draw(woodBlock3.getBlockTexture(), 480, 85, blockWidth, blockHeight * 2);
        spriteBatch.draw(woodBlock4.getBlockTexture(), 570, 85, blockWidth, blockHeight * 2);

        spriteBatch.draw(woodBlock5.getBlockTexture(), 480, 145, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock6.getBlockTexture(), 510, 200, blockWidth / 2, blockHeight * 2);
        spriteBatch.draw(woodBlock7.getBlockTexture(), 567, 200, blockWidth / 2, blockHeight * 2);
        spriteBatch.draw(woodBlock8.getBlockTexture(), 570, 145, blockWidth, blockHeight);

        spriteBatch.draw(pig.getPigTexture(), 533, 212, 40, 40);

        spriteBatch.draw(glassBlock1.getBlockTexture(), 525, 175, blockWidth, blockHeight);
        spriteBatch.draw(glassBlock2.getBlockTexture(), 525, 265, blockWidth, blockHeight);
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
        woodBlock1.dispose();
        woodBlock2.dispose();
        woodBlock3.dispose();
        woodBlock4.dispose();
        woodBlock5.dispose();
        woodBlock6.dispose();
        woodBlock7.dispose();
        woodBlock8.dispose();
        glassBlock1.dispose();
        glassBlock2.dispose();
        catapultTexture.dispose();
        resumeIconTexture.dispose();
        pig.dispose();
        spriteBatch.dispose();
    }
}
