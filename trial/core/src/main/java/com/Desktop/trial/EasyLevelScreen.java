package com.Desktop.trial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class EasyLevelScreen implements Screen {
    private final Main game;
    private Texture backgroundTexture;
    private WoodBlock woodBlock1, woodBlock2, woodBlock3, woodBlock4, woodBlock5, woodBlock6, woodBlock7, woodBlock8;
    private GlassBlock glassBlock1, glassBlock2;
    private SteelBlock steelBlock;
    private Texture catapultTexture, resumeIconTexture, wonTexture, lostTexture;
    private Bird redBird, yellowBird, purpleBird;
    private Pig pig;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private FitViewport viewport;
    private Rectangle resumeButtonRectangle;
    private Rectangle wonButtonRectangle, lostButtonRectangle;
    private Vector2 touchPos;

    // Bird launching variables
    private Vector2 catapultAnchor;
    private Vector2 birdPosition;
    private Vector2 originalBirdPosition;
    private Vector2 launchVector;
    private boolean isDragging = false;
    private boolean isLaunched = false;
    private boolean birdHitStructure = false;
    private float maxDragDistance = 100f;
    private float launchPower = 20f;
    private Bird currentBird;

    // Bird dragging constraints
    private float minDragX;
    private float maxDragX;
    private float minDragY;
    private float maxDragY;

    // Structures for collision
    private Rectangle[] allBlockRectangles;

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
        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(800, 480);

        resumeButtonRectangle = new Rectangle(10, 410, 50, 50);
        wonButtonRectangle = new Rectangle(325, 435, 73, 25);
        lostButtonRectangle = new Rectangle(410, 435, 73, 25);
        touchPos = new Vector2();

        // Initialize bird launching
        catapultAnchor = new Vector2(150, 130);
        originalBirdPosition = new Vector2(catapultAnchor);
        birdPosition = new Vector2(catapultAnchor);
        currentBird = redBird;

        // Set up dragging constraints
        minDragX = catapultAnchor.x - maxDragDistance;
        maxDragX = catapultAnchor.x;
        minDragY = catapultAnchor.y;
        maxDragY = catapultAnchor.y + maxDragDistance;

        // Initialize block rectangles for collision
        initializeBlockRectangles();
    }

    private void initializeBlockRectangles() {
        int blockWidth = 60, blockHeight = 40;

        // Wood block rectangles
        Rectangle[] woodBlockRects = new Rectangle[] {
                new Rectangle(480, 75, blockWidth, blockHeight),
                new Rectangle(570, 75, blockWidth, blockHeight),
                new Rectangle(480, 85, blockWidth, blockHeight * 2),
                new Rectangle(570, 85, blockWidth, blockHeight * 2),
                new Rectangle(480, 145, blockWidth, blockHeight),
                new Rectangle(510, 200, blockWidth / 2, blockHeight * 2),
                new Rectangle(567, 200, blockWidth / 2, blockHeight * 2),
                new Rectangle(570, 145, blockWidth, blockHeight)
        };

        // Glass block rectangles
        Rectangle[] glassBlockRects = new Rectangle[] {
                new Rectangle(525, 175, blockWidth, blockHeight),
                new Rectangle(525, 265, blockWidth, blockHeight)
        };

        // Combine wood and glass block rectangles
        allBlockRectangles = new Rectangle[woodBlockRects.length + glassBlockRects.length];
        System.arraycopy(woodBlockRects, 0, allBlockRectangles, 0, woodBlockRects.length);
        System.arraycopy(glassBlockRects, 0, allBlockRectangles, woodBlockRects.length, glassBlockRects.length);
    }

    @Override
    public void render(float delta) {
        input();
        updateBirdPhysics(delta);
        draw();
    }

    private void input() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            // Existing button checks
            if (resumeButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new PauseScreen(game, this));
                return;
            } else if (wonButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new LevelCompletedScreen(game));
                return;
            } else if (lostButtonRectangle.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new LevelLostScreen(game));
                return;
            }

            // Bird dragging logic
            if (!isLaunched) {
                if (isBirdDraggable(touchPos)) {
                    if (!isDragging) {
                        isDragging = true;
                    }

                    // Constrain dragging to a specific area
                    float constrainedX = Math.max(minDragX, Math.min(touchPos.x, maxDragX));
                    float constrainedY = Math.max(minDragY, Math.min(touchPos.y, maxDragY));

                    // Update bird position during drag
                    birdPosition.set(constrainedX, constrainedY);
                }
            }
        } else if (isDragging) {
            // Launch bird when touch is released
            launchBird();
            isDragging = false;
            isLaunched = true;
        }
    }

    private boolean isBirdDraggable(Vector2 touchPos) {
        // Check if touch is near the catapult and within dragging constraints
        return touchPos.dst(catapultAnchor) < 50 &&
                touchPos.x <= catapultAnchor.x &&
                touchPos.y >= catapultAnchor.y &&
                touchPos.y <= catapultAnchor.y + maxDragDistance;
    }

    private void launchBird() {
        // Reset bird hit structure flag
        birdHitStructure = false;

        // Calculate launch vector based on drag
        // Ensure forward and upward launch
        launchVector = new Vector2(
                Math.abs(catapultAnchor.x - birdPosition.x) * launchPower,
                (birdPosition.y - catapultAnchor.y) * launchPower
        );
    }

    private void updateBirdPhysics(float delta) {
        if (isLaunched && !birdHitStructure) {
            // Simple physics simulation
            birdPosition.x += launchVector.x * delta;
            birdPosition.y += launchVector.y * delta;

            // Apply gravity
            launchVector.y -= 9.8f * delta;

            // Check for collision with structures
            if (checkStructureCollision()) {
                birdHitStructure = true;
                isLaunched = false;
                launchVector.setZero();
            }

            // Optional: Stop bird if it goes off-screen
            if (birdPosition.x > viewport.getWorldWidth() ||
                    birdPosition.y > viewport.getWorldHeight() ||
                    birdPosition.y < 0) {
                birdHitStructure = true;
                isLaunched = false;
                launchVector.setZero();
            }
        }
    }

    private boolean checkStructureCollision() {
        Rectangle birdRect = new Rectangle(
                birdPosition.x,
                birdPosition.y,
                30,  // Bird width
                30   // Bird height
        );

        for (Rectangle blockRect : allBlockRectangles) {
            if (birdRect.overlaps(blockRect)) {
                return true;
            }
        }
        return false;
    }

    private void draw() {
        ScreenUtils.clear(Color.SKY);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw bird cards and counts
        spriteBatch.draw(redBird.getBirdCardTexture(), 630, 410, 45, 60);
        spriteBatch.draw(yellowBird.getBirdCardTexture(), 690, 410, 45, 60);
        spriteBatch.draw(purpleBird.getBirdCardTexture(), 750, 410, 45, 60);

        game.getFont().draw(spriteBatch, "Score: 0", 70, 445);
        game.getFont().draw(spriteBatch, "2", 650, 423);
        game.getFont().draw(spriteBatch, "2", 710, 423);
        game.getFont().draw(spriteBatch, "1", 770, 423);

        // Draw catapult
        spriteBatch.draw(catapultTexture, 110, 75, 60, 60);

        // Draw bird
        spriteBatch.draw(
                currentBird.getBirdTexture(),
                birdPosition.x,
                birdPosition.y,
                30,
                30
        );

        // Draw structures
        drawStructure();

        // Draw UI elements
        spriteBatch.draw(resumeIconTexture, 10, 410, 50, 50);
        spriteBatch.draw(wonTexture, 325, 435, 73, 25);
        spriteBatch.draw(lostTexture, 410, 435, 73, 25);

        spriteBatch.end();

        // Optional: Draw slingshot stretch visualization
        if (isDragging) {
            drawSlingshotStretch();
        }
    }

    private void drawSlingshotStretch() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(catapultAnchor.x, catapultAnchor.y, birdPosition.x, birdPosition.y);
        shapeRenderer.end();
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
    public void show() {}

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
        shapeRenderer.dispose();
    }
}