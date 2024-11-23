package com.Desktop.trial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class EasyLevelScreen implements Screen, ContactListener {
    private final Main game;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Texture backgroundTexture;
    private WoodBlock woodBlock1, woodBlock2, woodBlock3, woodBlock4, woodBlock5, woodBlock6, woodBlock7, woodBlock8;
    private GlassBlock glassBlock1, glassBlock2;
    private Texture catapultTexture, resumeIconTexture, wonTexture, lostTexture;
    private Bird redBird, yellowBird, purpleBird;
    private Pig pig;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private FitViewport viewport;
    private Rectangle resumeButtonRectangle;
    private Rectangle wonButtonRectangle, lostButtonRectangle;
    private Vector2 touchPos;
    private Array<Body> bodiesToRemove;
    private Array<Bird> availableBirds;
    private int redBirdsLeft = 2;
    private int yellowBirdsLeft = 2;
    private int purpleBirdsLeft = 1;
    private float outOfBoundsX = 1000f; // Distance beyond which bird is considered out of bounds
    private boolean isNextBirdReady = false;

    // Bird launching variables
    private Vector2 catapultAnchor;
    private boolean isDragging = false;
    private boolean isLaunched = false;
    private float maxDragDistance = 100f;
    private Bird currentBird;
    private boolean birdStopped = false;

    // Bird dragging constraints
    private float minDragX;
    private float maxDragX;
    private float minDragY;
    private float maxDragY;

    public EasyLevelScreen(Main game) {
        this.game = game;
        create();
    }

    private void create() {
        // Initialize Box2D World
        world = new World(new Vector2(0, -0.98f), true);
        world.setContactListener(this);
        bodiesToRemove = new Array<>();
        debugRenderer = new Box2DDebugRenderer();

        backgroundTexture = new Texture("easy_level_background.png");
        catapultTexture = new Texture("catapult.png");
        resumeIconTexture = new Texture("resume_icon.png");
        wonTexture = new Texture("level_won_button.png");
        lostTexture = new Texture("level_lost_button.png");

        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(800, 480);

        // Create birds, blocks, and pig with physics
        createGameElements();

        resumeButtonRectangle = new Rectangle(10, 410, 50, 50);
        wonButtonRectangle = new Rectangle(325, 435, 73, 25);
        lostButtonRectangle = new Rectangle(410, 435, 73, 25);
        touchPos = new Vector2();

        // Initialize bird launching
        catapultAnchor = new Vector2(150, 130);
        currentBird = redBird;

        // Set up dragging constraints
        minDragX = catapultAnchor.x - maxDragDistance;
        maxDragX = catapultAnchor.x;
        minDragY = catapultAnchor.y;
        maxDragY = catapultAnchor.y + maxDragDistance;

        availableBirds = new Array<>();
        initializeBirds();
    }

    private void initializeBirds() {
        // Clear existing birds
        availableBirds.clear();

        // Add birds in the order they should be used
        // 2 red birds
        for (int i = 0; i < 2; i++) {
            Bird redBird = new Bird(world, "red_bird.png", "red_bird_card.png", 150f, 130f);
            availableBirds.add(redBird);
        }
        // 2 yellow birds
        for (int i = 0; i < 2; i++) {
            Bird yellowBird = new Bird(world, "yellow_bird.png", "yellow_bird_card.png", 150f, 130f);
            availableBirds.add(yellowBird);
        }
        // 1 purple bird
        Bird purpleBird = new Bird(world, "purple_bird.png", "purple_bird_card.png", 150f, 130f);
        availableBirds.add(purpleBird);

        // Set the first bird as current
        if (availableBirds.size > 0) {
            currentBird = availableBirds.first();
        }
    }

    private void checkBirdOutOfBounds() {
        if (currentBird != null && isLaunched) {
            Vector2 birdPos = currentBird.getBody().getPosition();

            // Check if bird is out of bounds or has nearly stopped
            if (birdPos.x > outOfBoundsX || birdPos.x < -100 ||
                    (birdStopped && Math.abs(currentBird.getBody().getLinearVelocity().x) < 0.1f)) {

                // Remove the current bird from available birds
                availableBirds.removeValue(currentBird, true);

                // Update bird counts
                if (currentBird.getBirdTexture().toString().contains("red")) {
                    redBirdsLeft--;
                } else if (currentBird.getBirdTexture().toString().contains("yellow")) {
                    yellowBirdsLeft--;
                } else if (currentBird.getBirdTexture().toString().contains("purple")) {
                    purpleBirdsLeft--;
                }

                // Clean up the current bird
                world.destroyBody(currentBird.getBody());

                // Prepare for next bird
                prepareNextBird();
            }
        }
    }

    private void prepareNextBird() {
        if (availableBirds.size > 0) {
            // Get the next bird
            currentBird = availableBirds.first();
            // Reset bird position and state
            currentBird.getBody().setTransform(150f, 130f, 0);
            currentBird.getBody().setLinearVelocity(0, 0);
            currentBird.getBody().setAngularVelocity(0);
            currentBird.getBody().setType(BodyDef.BodyType.KinematicBody);

            // Reset launch states
            isLaunched = false;
            isDragging = false;
            birdStopped = false;
        } else {
            // No more birds available, check if level is completed
            checkLevelCompletion();
        }
    }

    private void checkLevelCompletion() {
        if (availableBirds.size == 0) {
            // Check if pig is destroyed or other victory conditions
            // For now, we'll just transition to the completed screen
            game.setScreen(new LevelCompletedScreen(game));
        }
    }

    private void drawTrajectory(Vector2 start, Vector2 velocity, float timeStep, int maxSteps) {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BROWN); // Adjust the color to match your design.

        Vector2 gravity = world.getGravity(); // Gravity from the physics world.
        Vector2 currentPoint = new Vector2(start);

        for (int i = 0; i < maxSteps; i++) {
            // Calculate the position of the bird at each step
            float t = i * timeStep;
            float x = start.x + velocity.x * t;
            float y = start.y + velocity.y * t + 0.5f * gravity.y * t * t;

            // Stop drawing if the point is below ground level
            if (y < 0) break;

            currentPoint.set(x, y);
            shapeRenderer.circle(currentPoint.x, currentPoint.y, 2f); // Draw a small circle
        }

        shapeRenderer.end();
    }

    private void createGameElements() {
        // Birds
        redBird = new Bird(world, "red_bird.png", "red_bird_card.png", 150f, 130f);
        yellowBird = new Bird(world, "yellow_bird.png", "yellow_bird_card.png", 200f, 130f);
        purpleBird = new Bird(world, "purple_bird.png", "purple_bird_card.png", 250f, 130f);

        // Wood Blocks (maintaining original X, Y coordinates)
        woodBlock1 = new WoodBlock(world, "wood_block.png", 480f, 75f);
        woodBlock2 = new WoodBlock(world, "wood_block.png", 570f, 75f);
        woodBlock3 = new WoodBlock(world, "wood_block.png", 480f, 85f);
        woodBlock4 = new WoodBlock(world, "wood_block.png", 570f, 85f);
        woodBlock5 = new WoodBlock(world, "wood_block.png", 480f, 145f);
        woodBlock6 = new WoodBlock(world, "wood_block.png", 510f, 200f);
        woodBlock7 = new WoodBlock(world, "wood_block.png", 567f, 200f);
        woodBlock8 = new WoodBlock(world, "wood_block.png", 570f, 145f);

        // Glass Blocks
        glassBlock1 = new GlassBlock(world, "glass_block.png", 525f, 175f);
        glassBlock2 = new GlassBlock(world, "glass_block.png", 525f, 265f);

        // Pig
        pig = new Pig(world, "pig.png", 533f, 212f);
    }

    @Override
    public void render(float delta) {
        world.step(delta, 6, 2);
        checkBirdOutOfBounds();
        // Handle bird launch input and draw the scene
        input();
        draw();

        if (isDragging && currentBird != null) {
            Vector2 birdPos = currentBird.getBody().getPosition();
            Vector2 pullVector = new Vector2(birdPos.x - catapultAnchor.x, birdPos.y - catapultAnchor.y);
            Vector2 launchVelocity = pullVector.nor().scl(-Math.min(pullVector.len() * 50.0f, 1000f)); // Match launch calculation

            drawTrajectory(birdPos, launchVelocity, 0.1f, 100); // Adjust timeStep and maxSteps for accuracy
        }
    }

    @Override
    public void beginContact(Contact contact) {
//        Body bodyA = contact.getFixtureA().getBody();
//        Body bodyB = contact.getFixtureB().getBody();
//
//        if (currentBird != null && (bodyA == currentBird.getBody() || bodyB == currentBird.getBody())) {
//            Body otherBody = (bodyA == currentBird.getBody()) ? bodyB : bodyA;

//            if (isWoodBlock(otherBody)) {
//                bodiesToRemove.add(otherBody);
//                // Don't force velocity change, let physics handle it naturally
//            }
//            else if (isGlassBlock(otherBody)) {
//                // Let the bird bounce naturally
//                currentBird.getBody().applyLinearImpulse(
//                        new Vector2(-0.5f, 0.5f),
//                        currentBird.getBody().getWorldCenter(),
//                        true
//                );
//            }
//        }
    }
    private boolean isWoodBlock(Body body) {
        return (woodBlock1 != null && body == woodBlock1.getBody()) ||
                (woodBlock2 != null && body == woodBlock2.getBody()) ||
                (woodBlock3 != null && body == woodBlock3.getBody()) ||
                (woodBlock4 != null && body == woodBlock4.getBody()) ||
                (woodBlock5 != null && body == woodBlock5.getBody()) ||
                (woodBlock6 != null && body == woodBlock6.getBody()) ||
                (woodBlock7 != null && body == woodBlock7.getBody()) ||
                (woodBlock8 != null && body == woodBlock8.getBody());
    }

    private boolean isGlassBlock(Body body) {
        return (glassBlock1 != null && body == glassBlock1.getBody()) ||
                (glassBlock2 != null && body == glassBlock2.getBody());
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}


    private void input() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (!isLaunched) {
                if (isBirdDraggable(touchPos)) {
                    if (!isDragging) {
                        isDragging = true;
                        currentBird.getBody().setType(BodyDef.BodyType.DynamicBody);
                    }

                    Vector2 dragVector = new Vector2(
                            touchPos.x - catapultAnchor.x,
                            touchPos.y - catapultAnchor.y
                    );
                    float dragDistance = dragVector.len();
                    if (dragDistance > maxDragDistance) {
                        dragVector.nor().scl(maxDragDistance);
                    }

                    float newX = catapultAnchor.x + dragVector.x;
                    float newY = catapultAnchor.y + dragVector.y;
                    currentBird.getBody().setTransform(newX, newY, 0);
                }
            }
        } else if (isDragging) {
            launchBird();
            isDragging = false;
        }
    }
    private void prepareBirdForLaunch(Bird bird) {
        bird.getBody().setType(BodyDef.BodyType.DynamicBody);
        bird.getBody().setLinearVelocity(0, 0);
        bird.getBody().setType(BodyDef.BodyType.DynamicBody);
    }

    private boolean isBirdDraggable(Vector2 touchPos) {
        // Check if touch is within a reasonable radius of the bird's current position
        if (!isLaunched) {
            float touchRadius = 50f; // Adjust this value as needed
            Vector2 birdPos = currentBird.getBody().getPosition();
            return touchPos.dst(birdPos) < touchRadius;
        }
        return false;
    }
    private void launchBird() {
        Vector2 birdPos = currentBird.getBody().getPosition();

        // Calculate the pull vector and launch the bird
        Vector2 pullVector = new Vector2(birdPos.x - catapultAnchor.x, birdPos.y - catapultAnchor.y);
        Vector2 launchVector = pullVector.nor().scl(-Math.min(pullVector.len() * 50.0f, 1000f)); // Cap the launch power

        currentBird.getBody().applyLinearImpulse(launchVector, currentBird.getBody().getWorldCenter(), true);

        // Ensure bird continues indefinitely
        currentBird.getBody().setLinearDamping(0); // No friction
        currentBird.getBody().setGravityScale(0); // No gravity
        isLaunched = true;
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
        game.getFont().draw(spriteBatch, String.valueOf(redBirdsLeft), 650, 423);
        game.getFont().draw(spriteBatch, String.valueOf(yellowBirdsLeft), 710, 423);
        game.getFont().draw(spriteBatch, String.valueOf(purpleBirdsLeft), 770, 423);

        // Draw catapult
        spriteBatch.draw(catapultTexture, 110, 75, 60, 60);

        // Draw current bird
        Vector2 birdPos = currentBird.getBody().getPosition();
        spriteBatch.draw(
                currentBird.getBirdTexture(),
                birdPos.x - 0.5f,
                birdPos.y - 0.5f,
                30f,
                30f
        );

        // Draw structures
        drawStructure();

        // Draw UI elements
        spriteBatch.draw(resumeIconTexture, 10, 410, 50, 50);
        spriteBatch.draw(wonTexture, 325, 435, 73, 25);
        spriteBatch.draw(lostTexture, 410, 435, 73, 25);

        spriteBatch.end();

        // Optional: Render Box2D debug lines
        debugRenderer.render(world, viewport.getCamera().combined);
    }

    private void drawStructure() {
        int blockWidth = 60, blockHeight = 40;

        Vector2 woodBlock1Pos = woodBlock1.getBody().getPosition();
        Vector2 woodBlock2Pos = woodBlock2.getBody().getPosition();
        Vector2 woodBlock3Pos = woodBlock3.getBody().getPosition();
        Vector2 woodBlock4Pos = woodBlock4.getBody().getPosition();
        Vector2 woodBlock5Pos = woodBlock5.getBody().getPosition();
        Vector2 woodBlock6Pos = woodBlock6.getBody().getPosition();
        Vector2 woodBlock7Pos = woodBlock7.getBody().getPosition();
        Vector2 woodBlock8Pos = woodBlock8.getBody().getPosition();
        Vector2 pigPos = pig.getBody().getPosition();
        Vector2 glassBlock1Pos = glassBlock1.getBody().getPosition();
        Vector2 glassBlock2Pos = glassBlock2.getBody().getPosition();

        spriteBatch.draw(woodBlock1.getBlockTexture(), woodBlock1Pos.x - 0.5f, woodBlock1Pos.y - 0.5f, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock2.getBlockTexture(), woodBlock2Pos.x - 0.5f, woodBlock2Pos.y - 0.5f, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock3.getBlockTexture(), woodBlock3Pos.x - 0.5f, woodBlock3Pos.y - 0.5f, blockWidth, blockHeight * 2);
        spriteBatch.draw(woodBlock4.getBlockTexture(), woodBlock4Pos.x - 0.5f, woodBlock4Pos.y - 0.5f, blockWidth, blockHeight * 2);
        spriteBatch.draw(woodBlock5.getBlockTexture(), woodBlock5Pos.x - 0.5f, woodBlock5Pos.y - 0.5f, blockWidth, blockHeight);
        spriteBatch.draw(woodBlock6.getBlockTexture(), woodBlock6Pos.x - 0.5f, woodBlock6Pos.y - 0.5f, blockWidth / 2, blockHeight * 2);
        spriteBatch.draw(woodBlock7.getBlockTexture(), woodBlock7Pos.x - 0.5f, woodBlock7Pos.y - 0.5f, blockWidth / 2, blockHeight * 2);
        spriteBatch.draw(woodBlock8.getBlockTexture(), woodBlock8Pos.x - 0.5f, woodBlock8Pos.y - 0.5f, blockWidth, blockHeight);

        spriteBatch.draw(pig.getPigTexture(), pigPos.x - 0.5f, pigPos.y - 0.5f, 40, 40);

        spriteBatch.draw(glassBlock1.getBlockTexture(), glassBlock1Pos.x - 0.5f, glassBlock1Pos.y - 0.5f, blockWidth, blockHeight);
        spriteBatch.draw(glassBlock2.getBlockTexture(), glassBlock2Pos.x - 0.5f, glassBlock2Pos.y - 0.5f, blockWidth, blockHeight);
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
        world.dispose();
        debugRenderer.dispose();
        shapeRenderer.dispose();
        for (Bird bird : availableBirds) {
            bird.dispose();
        }
    }
}