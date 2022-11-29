package example.robotics.ev3.actuator.opencv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageViewer extends JPanel {

    public static Logger LOGGER = LoggerFactory.getLogger(OpenCvExample.class);

    private BufferedImage image;
    private JFrame frame;

    public ImageViewer(BufferedImage img) {
        image = img;
        window(img, "test", 0, 0);
    }

    public void repaint(BufferedImage image) {
        this.image = image;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    //Show image on window
    public void window(BufferedImage img, String text, int x, int y) {
        frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(text);
        frame.setSize(img.getWidth(), img.getHeight() + 30);
        frame.setLocation(x, y);
        frame.setVisible(true);
    }


}
