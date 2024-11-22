package com.Desktop.trial;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class GlassBlock extends Block {
    public GlassBlock(World world, String texturePath, float x, float y) {
        super(world, texturePath, x, y, BodyDef.BodyType.StaticBody);
        // Glass-specific properties can be added here
        fixture.setDensity(0.5f);
    }
}