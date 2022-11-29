package example.robotics.ev3.actuator;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.video.MarvinJavaCVAdapter;
import marvin.video.MarvinVideoInterface;
import marvin.video.MarvinVideoInterfaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class WebcamExample extends JPanel {

    public static Logger LOGGER = LoggerFactory.getLogger(WebcamExample.class);

    public static void main(final String[] args) throws MarvinVideoInterfaceException, InterruptedException {
        MarvinVideoInterface videoAdapter = new MarvinJavaCVAdapter();
        videoAdapter.connect(0);
        MarvinImage image = videoAdapter.getFrame();
        MarvinImageIO.saveImage(image, "selfie.jpg");
        System.exit(0);
    }

}
