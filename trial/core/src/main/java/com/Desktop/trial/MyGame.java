package com.Desktop.trial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.Color;

public class MyGame extends ApplicationAdapter {
    private Stage stage;
    private Texture background;
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load images
        background = new Texture(Gdx.files.internal("background.png"));

        // Create a table to organize UI elements
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Create UI elements
        Label titleLabel = new Label("Login", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        TextField usernameField = new TextField("", new TextField.TextFieldStyle(new BitmapFont(), Color.WHITE, null, null, null));
        TextField passwordField = new TextField("", new TextField.TextFieldStyle(new BitmapFont(), Color.WHITE, null, null, null));

        // Create a button with a custom style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("submit_button.png"))); // Submit button texture
        TextButton submitButton = new TextButton("", buttonStyle);

        // Add UI elements to the table
        table.add(titleLabel).padBottom(20);
        table.row();
        table.add(usernameField).width(200).padBottom(10);
        table.row();
        table.add(passwordField).width(200).padBottom(10);
        table.row();
        table.add(submitButton).padTop(20);

        // Button click listener
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Username: " + usernameField.getText());
                System.out.println("Password: " + passwordField.getText());
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        batch.dispose();
    }
}
