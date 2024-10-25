package com.Desktop.trial;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GlassBlock extends Block {
    public GlassBlock(Vector2 position, String type) {
        super(type.equals("square") ? new Texture("glass_square_block.png") : new Texture("glass_triangle_block.png"), position);
    }
}
