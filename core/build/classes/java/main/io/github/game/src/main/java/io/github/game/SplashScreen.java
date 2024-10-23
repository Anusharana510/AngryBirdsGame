package io.github.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class SplashScreen implements Screen {
    private AngryBirds game;
    private Texture splashTexture;
    private float splashTime;
    private static final float SPLASH_DURATION = 3f; // Duration to show the splash screen in seconds
    private SpriteBatch batch;

    public SplashScreen(AngryBirds game) {
        this.game = game;
        splashTexture = new Texture("splash.png"); // Replace with your splash screen image file
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        splashTime = 0; // Reset splash time
    }

    @Override
    public void render(float delta) {
        splashTime += delta; // Increment splash time
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

        // Draw the splash screen
        batch.begin();
        batch.draw(splashTexture, 0, 0, game.WORLD_WIDTH, game.WORLD_HEIGHT);
        batch.end();

        // Transition to MainMenu after SPLASH_DURATION
        if (splashTime > SPLASH_DURATION) {
            game.setScreen(new io.github.game.MainMenu(game)); // Transition to the main menu
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        splashTexture.dispose(); // Dispose of the splash texture
    }
}
