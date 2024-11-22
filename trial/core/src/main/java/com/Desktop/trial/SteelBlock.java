package com.Desktop.trial;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class SteelBlock extends Block {
    public SteelBlock(World world, String texturePath, float x, float y) {
        super(world, texturePath, x, y, BodyDef.BodyType.StaticBody);
        // Steel-specific properties can be added here
        fixture.setDensity(2.0f);
    }
}