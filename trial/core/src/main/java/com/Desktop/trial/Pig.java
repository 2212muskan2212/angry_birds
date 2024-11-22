package com.Desktop.trial;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Pig {
    private Texture pigTexture;
    private Body body;
    private Fixture fixture;

    public Pig(World world, String texturePath, float x, float y) {
        // Load texture
        pigTexture = new Texture(texturePath);

        // Create Box2D body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        // Create shape and fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);  // Adjust size as needed

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;

        fixture = body.createFixture(fixtureDef);
        shape.dispose();
    }

    public Texture getPigTexture() {
        return pigTexture;
    }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        pigTexture.dispose();
        body.getWorld().destroyBody(body);
    }
}