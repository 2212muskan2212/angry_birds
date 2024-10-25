package com.Desktop.trial;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class SoundManager {
    private static SoundManager instance;
    private Music backgroundMusic;

    private SoundManager() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("angry_birds_music.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void playMusic() {
        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public void stopMusic() {
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    public void dispose() {
        backgroundMusic.dispose();
    }
}
