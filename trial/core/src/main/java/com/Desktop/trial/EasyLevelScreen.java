package com.Desktop.trial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.LinkedList;
import java.util.Queue;

public class EasyLevelScreen implements Screen {
    private final Main game;
    private Texture backgroundTexture, catapultTexture;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body catapult, birdBody;
    private Queue<Bird> birdsQueue;
    private Bird currentBird;

    private boolean isDragging = false;
    private Vector2 dragStart = new Vector2();

    public EasyLevelScreen(Main game) {
        this.game = game;
        create();
    }

    private void create() {
        // Background and HUD
        backgroundTexture = new Texture("easy_level_background.png");
        catapultTexture = new Texture("catapult.png");

        // Birds queue
        birdsQueue = new LinkedList<>();
        birdsQueue.add(new Bird("red_bird.png", "red_bird_card.png"));
        birdsQueue.add(new Bird("yellow_bird.png", "yellow_bird_card.png"));
        birdsQueue.add(new Bird("purple_bird.png", "purple_bird_card.png"));
        currentBird = birdsQueue.poll();

        // World and Physics
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        createCatapult();
        createStructure();

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 480);
    }

    private void createCatapult() {
        // Catapult body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(110 / 100f, 75 / 100f);

        catapult = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(30 / 100f, 30 / 100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        catapult.createFixture(fixtureDef);
        shape.dispose();
    }

    private void createStructure() {
        // Wood blocks and pig implementation (similar logic as catapult)
        // Each block and pig would have its Body and interact with birds
        // Omitted for brevity, similar to catapult creation.
    }

    private void shootBird(Vector2 velocity) {
        if (currentBird != null) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(catapult.getPosition().x + 0.5f, catapult.getPosition().y + 0.5f);

            birdBody = world.createBody(bodyDef);
            CircleShape shape = new CircleShape();
            shape.setRadius(0.3f);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1f;
            fixtureDef.restitution = 0.4f; // Bounce effect
            birdBody.createFixture(fixtureDef);
            shape.dispose();

            birdBody.setLinearVelocity(velocity);
            currentBird = birdsQueue.poll(); // Load the next bird
        }
    }

    private void input() {
        if (Gdx.input.isTouched()) {
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if (isDragging) {
                dragStart.set(touch);
            } else {
                isDragging = true;
                dragStart.set(touch);
            }
        } else if (isDragging) {
            isDragging = false;
            Vector2 velocity = new Vector2(dragStart).sub(catapult.getPosition()).scl(-5); // Adjust scaling
            shootBird(velocity);
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.SKY);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        spriteBatch.draw(catapultTexture, catapult.getPosition().x * 100 - 30, catapult.getPosition().y * 100 - 30, 60, 60);
        spriteBatch.end();

        debugRenderer.render(world, viewport.getCamera().combined); // Render physics bodies
    }

    @Override
    public void render(float delta) {
        input();
        world.step(1 / 60f, 6, 2); // Physics step
        draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        catapultTexture.dispose();
        spriteBatch.dispose();
        world.dispose();
        debugRenderer.dispose();
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
