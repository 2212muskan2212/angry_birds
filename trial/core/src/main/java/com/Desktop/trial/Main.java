package com.Desktop.trial;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        // Create the configuration for the application
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Donate Hub");
        config.setWindowedMode(800, 600); // Set your desired width and height

        // Start the application with the MyGame instance
        new Lwjgl3Application(new MyGame(), config);
    }
}
