package Universe0.OperateDaRocket;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

/**
 * The Main game class which we can use to change screens
 */
public class Main extends Game {

    @Override
    public void create() {

        // Get the current display mode (resolution, refresh rate, etc.)
        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();

        // Switch to fullscreen using that mode
        Gdx.graphics.setFullscreenMode(displayMode);

        // setScreen function lets us change the screen of the game
        // the parameter of this function is a screen (e.g. MainMenu).
        setScreen(new MainMenu(this));
    }
}
