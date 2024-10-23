package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MediumLevel implements Screen {
    private final AngryBirds game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Sprite spriteBg;
    private Viewport viewport;
    private Texture pauseTexture; // Texture for the pause image
    private boolean isPaused = false;

    public MediumLevel(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        batch = new SpriteBatch();

        // Load background texture
        backgroundTexture = new Texture("mediumbg.jpg");
        spriteBg = new Sprite(backgroundTexture);
        spriteBg.setPosition(0, 0);
        spriteBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Load the pause texture
        pauseTexture = new Texture("pause.png");

        Gdx.input.setInputProcessor(stage);

        // Set up the pause button with a listener
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Assume pause button coordinates
                if (x >= 50 && x <= 150 && y >= Gdx.graphics.getHeight() - 50 && y <= Gdx.graphics.getHeight()) {
                    // Pass both game and the current screen (MediumLevel.this) to PauseScreen
                    game.setScreen(new PauseScreen(game, MediumLevel.this));
                }
            }
        });
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        spriteBg.draw(batch); // Draw background
        batch.draw(pauseTexture, 50, Gdx.graphics.getHeight() - 50, 100, 50); // Draw pause image
        batch.end();

        if (!isPaused) {
            // Update game logic here (if any)
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        pauseTexture.dispose(); // Dispose of the pause texture
        stage.dispose();
    }
}
