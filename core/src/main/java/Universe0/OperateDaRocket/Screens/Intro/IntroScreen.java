package Universe0.OperateDaRocket.Screens.Intro;

import Universe0.OperateDaRocket.util.Screen.MyGameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class IntroScreen extends MyGameScreen {
    Stage stage;
    Table table;
    Skin uiskin;

    Label label;
    Button skipAnimation;

    String text = """
        7:05 PM 2156
        Dear Diary,
        \tI am Leven Morsen, this is the first diary entry I've ever written on a book.
        \tMy grandfather told me about the techs of 2050s. I liked them a lot. I like old techs.
        \tI don't like very much of automated work. Even though I work in a data center in London.
        \tNowadays Data centers have become very common and has a high demand.
        \tMe and My Wife do work to earn money for our family. We have only one child but
        \tIt this much effort is still not enough when including the Robots that take care
        \tof my child and some other ones.
        \tI will need to get money for my family as efficiently as I can.
        """;
    int charNum;

    public IntroScreen(Game game) {
        super(game);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);

        stage.addActor(table);

        uiskin = new Skin(Gdx.files.internal("Skins/MainMenu/MainMenu.json"));

        label = new Label("", uiskin, "TNR");
        label.setFontScale(0.75f);
        label.setAlignment(Align.left | Align.top);

        skipAnimation = new TextButton("Skip Animation", uiskin);
        skipAnimation.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                charNum = -1;
                label.setText(text.replaceAll("\t", "    "));
            }
        });
        skipAnimation.setPosition((Gdx.graphics.getWidth() - skipAnimation.getWidth()) / 2, table.getHeight() + 50);

        table.top().left();
        table.add(label).row();

        stage.addActor(skipAnimation);

        charNum = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        ScreenUtils.clear(new Color(0x111111ff)); // 0x111111ff -> 111111ff color in hex

        if (charNum >= 0)
            startTimer(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    private float timeElapsed = 0;

    public void startTimer(float delta) {
        timeElapsed += delta;
        float delay = 0.1f;
        char ch = '\0';
        if (charNum < text.length()) {
            ch = text.charAt(charNum);

            if (!Character.isLowerCase(ch) && Character.isAlphabetic(ch))
                delay = 0.125f;

            switch (ch) {
                case '.' -> delay = 0.6f;   // longer pause
                case ',' -> delay = 0.35f;  // medium pause
                case '\t' -> delay = 0.4f;  // tab pause
                case ' ' -> delay = 0.08f;  // short pause
                case '\n' -> delay = 0.75f; // longest pause
            }

            delay += MathUtils.random(-0.03f, 0.03f);

            delay *= 0.7f; // e.g., typingSpeed = 1.0f normal, 0.8f faster

        }

        if (timeElapsed >= delay) {
            timeElapsed = 0;
            System.out.println(charNum + " " + text.length());

            if (charNum >= text.length()) {
                charNum = -1;
            } else {
                label.setText(label.getText().toString() + (ch != '\t' ? ch : "    "));
                charNum++;

            }
        }
    }
}
