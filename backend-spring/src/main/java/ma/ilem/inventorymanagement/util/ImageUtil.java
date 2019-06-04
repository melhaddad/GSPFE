package ma.ilem.inventorymanagement.util;

import ma.ilem.inventorymanagement.exception.ImageInvalidFormatException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    public static void resize(String sourcePath, String savePath, String extention, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(sourcePath));
        try {
            BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();
            ImageIO.write(resizedImage, extention, new File(savePath));
        } catch (IllegalArgumentException ex) {
            throw new ImageInvalidFormatException("Le type de la pièce jointe n'est pas supporter ( types supporter: jpg ).");
        }
    }
}
