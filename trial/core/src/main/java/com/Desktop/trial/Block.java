package com.Desktop.trial;

import com.badlogic.gdx.graphics.Texture;

public class Block {
    private Texture blockTexture;

    public Block(String texturePath) {
        blockTexture = new Texture(texturePath);
    }

    public Texture getBlockTexture() {
        return blockTexture;
    }

    public void dispose() {
        blockTexture.dispose();
    }
}