package com.Desktop.trial;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class WoodBlock extends Block {
    public WoodBlock(Vector2 position) {
        super(new Texture("wood_block.png"), position);
    }
}
