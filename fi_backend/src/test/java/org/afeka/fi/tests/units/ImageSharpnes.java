package org.afeka.fi.tests.units;
import org.junit.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageSharpnes {


    @Test
    public void toBlackWhite () throws IOException {

        File file = new File("C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\fi_table_image.jpg");
        BufferedImage orginalImage = ImageIO.read(file);

        BufferedImage blackAndWhiteImg = new BufferedImage(
                orginalImage.getWidth(), orginalImage.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D graphics = blackAndWhiteImg.createGraphics();
        graphics.drawImage(orginalImage, 0, 0, null);

        ImageIO.write(blackAndWhiteImg, "png", new File("C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\fi_table_image_black_white.jpg"));
    }

}