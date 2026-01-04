package Universe0.OperateDaRocket.util;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontUtilities {
    public static BitmapFont ttf(FileHandle path, FreeTypeFontGenerator.FreeTypeFontParameter param) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(path);
        return generator.generateFont(param);
    }
}
