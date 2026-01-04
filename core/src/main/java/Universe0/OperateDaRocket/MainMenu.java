package Universe0.OperateDaRocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/***
 * This is a Screen
 * This screen is used in our game as a Scene which we can operate like a normal game code
 */
public class MainMenu implements Screen {
    Stage stage; // Stage for UI
    Table table; // Content Pane for items
    Skin skin; // The main skin which contains all styles for our UI
    Button start, collaboration; // Buttons

    @Override
    public void show() { } // when window is being shown

    // Constructor - contains our variable declaration and more
    public MainMenu() {

        table = new Table();
        table.setFillParent(true);

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("Skins/MainMenu/MainMenu.json"));

        start = new TextButton("Start", skin);
        collaboration = new TextButton("Collaborate", skin);

        Image image = new Image(skin.getDrawable("Logo"));

        table.add(image).width(256).height(256).align(Align.center).row();
        table.add(start).width(200).height(75).align(Align.center).row();
        table.add(collaboration).width(250).height(75).padTop(20).align(Align.center).row();

    }

    @Override
    public void render(float delta) { // render - used to show objects
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        ScreenUtils.clear(new Color(0x111111ff)); // 0x111111ff -> 111111ff color in hex

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height); // update viewport
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
