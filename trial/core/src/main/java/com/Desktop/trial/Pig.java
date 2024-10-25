package com.Desktop.trial;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class Pig implements Disposable {
    private Texture pigTexture;

    public Pig(String texturePath) {
        pigTexture = new Texture(texturePath);
    }

    public Texture getPigTexture() {
        return pigTexture;
    }

    @Override
    public void dispose() {
        pigTexture.dispose();
    }
}
