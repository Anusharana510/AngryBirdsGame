package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class PauseScreen implements Screen {
    private final AngryBirds game;
    private final Screen previousScreen;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private TextButton resumeButton, restartButton, mainMenuButton;
    private SpriteBatch batch;
    private Texture pauseBackground;
    private TextureAtlas buttonAtlas;

    public PauseScreen(AngryBirds game, Screen previousScreen) {
        this.game = game;
        this.previousScreen = previousScreen;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();

        // Load pause screen background texture
        pauseBackground = new Texture("pausebg.jpg");

        font = new BitmapFont();
        skin = new Skin();

        // Load button textures from atlas
        buttonAtlas = new TextureAtlas("ui/button.pack"); // Ensure you have this .pack file
        skin.addRegions(buttonAtlas);
//        Music ss = Gdx.audio.newMusic(Gdx.files.internal("sound.mp3"));
//        ss.setLooping(true);
//        ss.setVolume(10);
//        ss.play();

        // Set up button style using texture regions from the atlas
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = new TextureRegionDrawable(buttonAtlas.findRegion("button-up"));
        buttonStyle.down = new TextureRegionDrawable(buttonAtlas.findRegion("button-down"));
        buttonStyle.pressedOffsetX = 1;
        buttonStyle.pressedOffsetY = -1;

        // Create and set up buttons
        resumeButton = new TextButton("Resume", buttonStyle);
        resumeButton.pad(20);  // Increase padding to make the button larger
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (previousScreen instanceof EasyLevel) {
                    game.setScreen(new EasyLevel(game));
                } else if (previousScreen instanceof MediumLevel) {
                    game.setScreen(new MediumLevel(game));
                } else if (previousScreen instanceof HardLevel) {
                    game.setScreen(new HardLevel(game));
                }
            }
        });

        restartButton = new TextButton("Restart", buttonStyle);
        restartButton.pad(20);  // Increase padding to make the button larger
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (previousScreen instanceof EasyLevel) {
                    game.setScreen(new EasyLevel(game));
                } else if (previousScreen instanceof MediumLevel) {
                    game.setScreen(new MediumLevel(game));
                } else if (previousScreen instanceof HardLevel) {
                    game.setScreen(new HardLevel(game));
                }
            }
        });

        mainMenuButton = new TextButton("Main Menu", buttonStyle);
        mainMenuButton.pad(20);  // Increase padding to make the button larger
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }
        });

        // Set up table layout with padding and spacing for buttons
        Table table = new Table();
        table.setFillParent(true);
        table.add(resumeButton).pad(10).width(300).height(80).space(25);  // You can set width and height here
        table.row();
        table.add(restartButton).pad(10).width(300).height(80).space(25);  // Set button size
        table.row();
        table.add(mainMenuButton).pad(10).width(300).height(80).space(25);  // Set button size

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the pause background
        batch.begin();
        batch.draw(pauseBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Render the stage (buttons and UI)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        skin.dispose();
        batch.dispose();
        pauseBackground.dispose();
        buttonAtlas.dispose();
    }
}
