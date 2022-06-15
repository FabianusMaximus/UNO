package UNO.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JLabel {
    private BufferedImage image;

    public ImagePanel(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            System.out.println("Des Bild gibts ned");
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }
}
