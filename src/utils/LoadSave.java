package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static BufferedImage getSpriteAtlas(){
        BufferedImage image = null;
        InputStream inputStream = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");

        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
