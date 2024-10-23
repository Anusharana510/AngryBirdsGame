package io.github.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music; // Import Music for background sound
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class AngryBirds extends Game {
    public final int WORLD_WIDTH = 1200;  // Background width
    public final int WORLD_HEIGHT = 560;  // Background height

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture backgroundTexture;

    // Sound (Music) object for background music
    private Music backgroundMusic;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // Load the background texture
        backgroundTexture = new Texture("splash.png");

        // Set up the camera and viewport with the background dimensions
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);

        // Load and configure background music
        Music s = Gdx.audio.newMusic(Gdx.files.internal("sound.mp3"));
        s.setLooping(true);
        s.setVolume(10);
        s.play();
        // Play the background music

        // Start with the SplashScreen
        setScreen(new SplashScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        super.render(); // Call the render method of the Game class
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose(); // Dispose the background texture when done

        // Dispose the background music when done
        backgroundMusic.dispose();

        if (getScreen() != null) {
            getScreen().dispose(); // Dispose of the current screen if it exists
        }
    }
}
