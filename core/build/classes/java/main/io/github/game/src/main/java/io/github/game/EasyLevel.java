package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music; // Import Music for sound
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EasyLevel implements Screen {
    private final AngryBirds game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Sprite spriteBg;
    private Viewport viewport;
    private Texture pauseTexture; // Texture for the pause image
    private boolean isPaused = false;

    // Define the position and size of the pause button
    private final int pauseButtonX = 20; // X position of pause button
    private final int pauseButtonY = Gdx.graphics.getHeight() - 70; // Y position of pause button
    private final int pauseButtonWidth = 50; // Width of the pause button
    private final int pauseButtonHeight = 50; // Height of the pause button

    // Sound (Music) object
    private Music sound;

    public EasyLevel(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        batch = new SpriteBatch();

        // Load background texture
        backgroundTexture = new Texture("walls.png");
        spriteBg = new Sprite(backgroundTexture);
        spriteBg.setPosition(0, 0);
        spriteBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Load the pause texture
        pauseTexture = new Texture("pause.png");

        // Load sound and set looping, volume, and play
//        Music s = Gdx.audio.newMusic(Gdx.files.internal("game.wav"));
//        s.setLooping(true);
//        s.setVolume(10);
//        s.play();
        // Play the sound


        // Set up the pause button with a listener
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Check if the click was inside the pause button area
                if (x >= pauseButtonX && x <= pauseButtonX + pauseButtonWidth &&
                    y >= pauseButtonY && y <= pauseButtonY + pauseButtonHeight) {
                    isPaused = !isPaused; // Toggle pause state
                }
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        spriteBg.draw(batch); // Draw background

        // Draw pause image button
        batch.draw(pauseTexture, pauseButtonX, pauseButtonY, pauseButtonWidth, pauseButtonHeight); // Draw pause image

        if (isPaused) {
            game.setScreen(new PauseScreen(game,this)); // Switch to the PauseScreen when paused
            return; // Prevent rendering further if paused
        }

        // Update game logic here (if any)
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        sound.pause();  // Pause the sound when the game is paused
    }

    @Override
    public void resume() {
        sound.play();   // Resume playing the sound when the game resumes
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        pauseTexture.dispose(); // Dispose of the pause texture
        sound.dispose();        // Dispose of the sound to free up resources
        stage.dispose();
    }
}
