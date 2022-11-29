package example.robotics.ev3.actuator.sonar;

import ev3dev.actuators.LCD;
import ev3dev.sensors.ev3.EV3ColorSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import ev3dev.sensors.mindsensors.NXTCamV5;
import ev3dev.utils.JarResource;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class SonarExample {

    public static Logger LOGGER = LoggerFactory.getLogger(SonarExample.class);

    public static GraphicsLCD lcd = LCD.getInstance();

    public static void main(final String[] args) {
        EV3UltrasonicSensor sensor = new EV3UltrasonicSensor(SensorPort.S1);
        SampleProvider sampleProvider = sensor.getDistanceMode();

        int i = 0;
        while (true) {

            float [] sample = new float[sampleProvider.sampleSize()];
            sampleProvider.fetchSample(sample, 0);
            float distanceValue = sample[0];

            System.out.println("Iteration: " + i + ", Distance: " + distanceValue);
            i++;
            Delay.msDelay(500);
        }
    }

    public static final String JAVA_DUKE_IMAGE_NAME = "java_logo.png";

    private static void showJavaLogo() {

        if(LOGGER.isDebugEnabled())
            LOGGER.debug("Showing Java logo on EV3 Brick");

        try {
            BufferedImage image  = JarResource.loadImage(JAVA_DUKE_IMAGE_NAME);
            lcd.drawImage(image, 35, 10, 0);
            lcd.refresh();
        }catch (IOException e){
            LOGGER.error(e.getLocalizedMessage());
        }
    }

}
