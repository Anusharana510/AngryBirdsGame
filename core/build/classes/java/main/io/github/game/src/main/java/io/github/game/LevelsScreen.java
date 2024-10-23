package io.github.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

public class LevelsScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private TextButton easyButton, mediumButton, hardButton, backButton;
    private Table table;
    private BitmapFont white;
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Sprite spriteBg;
    private Viewport viewport;
    private AngryBirds game;

    public LevelsScreen(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        batch = new SpriteBatch();

        // Load background texture
        backgroundTexture = new Texture("levelsbg.png");
        spriteBg = new Sprite(backgroundTexture);
        spriteBg.setPosition(0, 0);
        spriteBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.input.setInputProcessor(stage);

        // Load TextureAtlas for buttons
        atlas = new TextureAtlas("ui/button.pack"); // Ensure you have this .pack file
        white = new BitmapFont(Gdx.files.internal("font/w.fnt"), false); // Using only the white font
        skin = new Skin(atlas);
//        Music ss = Gdx.audio.newMusic(Gdx.files.internal("sound.mp3"));
//        ss.setLooping(true);
//        ss.setVolume(10);
//        ss.play();

        // Create button style
        TextButton.TextButtonStyle ts = new TextButton.TextButtonStyle();
        ts.font = white; // Set the font for the button text

        // Set button textures from the TextureAtlas
        ts.up = new TextureRegionDrawable(atlas.findRegion("button-up")); // Normal state texture
        ts.down = new TextureRegionDrawable(atlas.findRegion("button-down")); // Pressed state texture
        ts.pressedOffsetX = 1;
        ts.pressedOffsetY = -1;

        // Create buttons
        easyButton = new TextButton("Easy", ts);
        easyButton.pad(10, 70, 10, 70);
        easyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Navigate to Easy Level
                ((Game) Gdx.app.getApplicationListener()).setScreen(new EasyLevel(game));
            }
        });

        mediumButton = new TextButton("Medium", ts);
        mediumButton.pad(10, 70, 10, 70);
        mediumButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Navigate to Medium Level
                ((Game) Gdx.app.getApplicationListener()).setScreen((Screen) new MediumLevel(game));
            }
        });

        hardButton = new TextButton("Hard", ts);
        hardButton.pad(10, 70, 10, 70);
        hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Navigate to Hard Level
                ((Game) Gdx.app.getApplicationListener()).setScreen((Screen) new HardLevel(game));
            }
        });

        backButton = new TextButton("Back", ts);
        backButton.pad(10, 70, 10, 70);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
            }
        });

        // Set up the table layout
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.add(easyButton).space(25);
        table.row();
        table.add(mediumButton).space(25);
        table.row();
        table.add(hardButton).space(25);
        table.row();
        table.add(backButton).space(25);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        spriteBg.draw(batch); // Draw background
        batch.end();

        stage.act(delta);
        stage.draw(); // Draw the stage (buttons)
    }

    @Override
    public void resize(int width, int height) {
        viewport.setScreenSize(width, height);
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
        stage.dispose();
        atlas.dispose();
        white.dispose();
        skin.dispose();
    }
}
