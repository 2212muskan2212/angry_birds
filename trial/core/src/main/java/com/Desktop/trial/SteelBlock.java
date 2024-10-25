package com.Desktop.trial;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class SteelBlock extends Block {
    public SteelBlock(Vector2 position, String type) {
        super(selectTexture(type), position);
    }

    private static Texture selectTexture(String type) {
        switch (type) {
            case "circle":
                return new Texture("steel_circle_block.png");
            case "figure":
                return new Texture("steel_figure_block.png");
            case "square":
                return new Texture("steel_square_block.png");
            case "triangle":
                return new Texture("steel_triangle_block.png");
            default:
                throw new IllegalArgumentException("Unknown steel block type");
        }
    }
}
