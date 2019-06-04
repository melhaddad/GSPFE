package ma.ilem.inventorymanagement.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

public class UploadUtil {

    public static String upload(MultipartFile part, String repo, String extention) throws IOException {
        File fileSaveDir = new File(repo);

        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String imageName = timestamp.toString();

        imageName = new File(imageName).getName();
        imageName = imageName.replaceAll("[: \\-.]+", "");
        String image = imageName;
        imageName = repo + File.separator + imageName + "." + extention;

        //part.write(imageName);
        RenderedImage renderedImage = ImageIO.read(part.getInputStream());
        ImageIO.write(renderedImage, extention, new File(imageName));

        return image;
    }
}
