package io.github.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music; // Import Music for background sound
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.game.AngryBirds;
import io.github.game.EasyLevel;
import io.github.game.LevelsScreen;

public class MainMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private TextButton levelsButton, playButton, exitButton;
    private Table table;
    private BitmapFont white;
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private Texture backMenu;
    private Sprite spriteBg;
    private Viewport viewport;
    private AngryBirds game;

    // Sound (Music) object for background music
    private Music backgroundMusic;

    public MainMenu(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        batch = new SpriteBatch();

        // Load background texture
        backMenu = new Texture("menubg.png");
        spriteBg = new Sprite(backMenu);
        spriteBg.setPosition(0, 0);
        spriteBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.input.setInputProcessor(stage);

        // Load TextureAtlas for buttons
        atlas = new TextureAtlas("ui/button.pack");
        white = new BitmapFont(Gdx.files.internal("font/w.fnt"), false);
        skin = new Skin(atlas);

        // Load and configure background music
//        Music s = Gdx.audio.newMusic(Gdx.files.internal("sound.mp3"));
//        s.setLooping(true);
//        s.setVolume(10);
//        s.play();
//        // Play the music

        // Create button style
        TextButton.TextButtonStyle ts = new TextButton.TextButtonStyle();
        ts.font = white; // Set the font for the button text

        // Set button textures from the TextureAtlas
        ts.up = new TextureRegionDrawable(atlas.findRegion("button-up"));
        ts.down = new TextureRegionDrawable(atlas.findRegion("button-down"));
        ts.pressedOffsetX = 1;
        ts.pressedOffsetY = -1;

        // Create buttons
        exitButton = new TextButton("EXIT", ts);
        exitButton.pad(10, 70, 10, 70);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        playButton = new TextButton("New Game", ts);
        playButton.pad(10, 70, 10, 70);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new EasyLevel(game));
            }
        });

        levelsButton = new TextButton("Levels", ts);
        levelsButton.pad(10, 70, 10, 70);
        levelsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelsScreen(game));
            }
        });

        // Set up the table layout
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.add(playButton).space(25);
        table.row();
        table.add(levelsButton).space(25);
        table.row();
        table.add(exitButton).space(25);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        spriteBg.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.setScreenSize(width, height);
    }

    @Override
    public void pause() {
        backgroundMusic.pause(); // Pause background music when the game is paused
    }

    @Override
    public void resume() {
        backgroundMusic.play();  // Resume background music when the game resumes
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        backMenu.dispose();
        stage.dispose();
        atlas.dispose();
        white.dispose();
        skin.dispose();
        backgroundMusic.dispose();  // Dispose of the background music resource
    }
}
