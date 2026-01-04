package Universe0.OperateDaRocket;

import com.badlogic.gdx.Game;

/**
 * The Main game class which we can use to change screens
 */
public class Main extends Game {

    @Override
    public void create() {
        // setScreen function lets us change the screen of the game
        // the parameter of this function is a screen (e.g. MainMenu).
        setScreen(new MainMenu());
    }
}
