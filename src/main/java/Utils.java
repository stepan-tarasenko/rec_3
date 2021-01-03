import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Utils {
    public static int[][] readPictureAsArray(String path) throws IOException, URISyntaxException {
        BufferedImage bImage = ImageIO.read(new File(Utils.class.getResource(path).toURI()));
        int[][] picture = new int[bImage.getHeight()][bImage.getWidth()];
        for (int i = 0; i < bImage.getHeight(); i++) {
            for (int j = 0; j < bImage.getWidth(); j++) {
                Color color = new Color(bImage.getRGB(j, i));
                picture[i][j] = color.getRed();
            }
        }
        return picture;
    }
}
