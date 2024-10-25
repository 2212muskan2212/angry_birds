package com.Desktop.trial;

import com.badlogic.gdx.graphics.Texture;

public class Bird {
    private Texture birdTexture;
    private Texture birdCardTexture;

    public Bird(String birdTexturePath, String birdCardTexturePath) {
        birdTexture = new Texture(birdTexturePath);
        birdCardTexture = new Texture(birdCardTexturePath);
    }

    public Texture getBirdTexture() {
        return birdTexture;
    }

    public Texture getBirdCardTexture() {
        return birdCardTexture;
    }

    public void dispose() {
        birdTexture.dispose();
        birdCardTexture.dispose();
    }
}
