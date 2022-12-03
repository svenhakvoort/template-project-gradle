package example.robotics.ev3.driving;

import ev3dev.actuators.LCD;
import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.hardware.EV3DevDistro;
import ev3dev.hardware.EV3DevDistros;
import ev3dev.sensors.EV3Key;
import ev3dev.sensors.ev3.EV3GyroSensor;
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import ev3dev.utils.Brickman;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AvoidObstaclesDriving {

    public static final EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.B);
    public static final EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.C);
    public static final EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
    public static final EV3TouchSensor touchSensor2 = new EV3TouchSensor(SensorPort.S3);
    public static final EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S4);
    public static final EV3Key startKey = new EV3Key(EV3Key.BUTTON_ENTER);
    public static final EV3Key stopKey = new EV3Key(EV3Key.BUTTON_ESCAPE);
    public static Logger LOGGER = LoggerFactory.getLogger(AvoidObstaclesDriving.class);

    public static void main(String[] args) throws InterruptedException {
        Brickman.disable();

        SampleProvider sampleProvider = ultrasonicSensor.getDistanceMode();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        Runnable runnable = () -> {
            if (touchSensor.isPressed() || touchSensor2.isPressed()) {
                motorLeft.setSpeed((int) motorLeft.getMaxSpeed() / 2);
                motorRight.setSpeed((int) motorRight.getMaxSpeed() / 2);

                motorLeft.backward();
                motorRight.backward();

                float distanceValue = 0;

                while (distanceValue < 20) {
                    float[] sample = new float[sampleProvider.sampleSize()];
                    sampleProvider.fetchSample(sample, 0);
                    distanceValue = sample[0];
                }

                motorLeft.stop();
                motorRight.stop();
            } else {
                float[] sample = new float[sampleProvider.sampleSize()];
                sampleProvider.fetchSample(sample, 0);
                float distanceValue = sample[0];

                System.out.println(distanceValue);
                if (distanceValue > 50) {
                    motorLeft.setSpeed((int) motorLeft.getMaxSpeed());
                    motorRight.setSpeed((int) motorRight.getMaxSpeed());

                    motorLeft.forward();
                    motorRight.forward();
                } else if (distanceValue > 30) {
                    motorLeft.setSpeed((int) motorLeft.getMaxSpeed() / 2);
                    motorRight.setSpeed((int) motorRight.getMaxSpeed() / 2);

                    motorLeft.forward();
                    motorRight.forward();
                } else if (distanceValue < 30 && distanceValue > 10) {
                    motorLeft.setSpeed((int) motorLeft.getMaxSpeed() / 2);
                    motorLeft.forward();

                    motorRight.setSpeed((int) motorRight.getMaxSpeed() / 2);
                    motorRight.backward();
                } else {
                    motorLeft.backward();
                    motorRight.backward();
                }
            }
        };


        startKey.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(Key k) {
                scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 10, TimeUnit.MILLISECONDS);
            }

            @Override
            public void keyReleased(Key k) {

            }
        });

        LOGGER.info("WAITING FOR ESC OR START PRESS");
        stopKey.waitForPressAndRelease();
        LOGGER.info("SHUTTING DOWN");

        scheduledExecutorService.shutdown();

        motorRight.stop();
        motorLeft.stop();

        scheduledExecutorService.awaitTermination(1, TimeUnit.MINUTES);

        motorRight.stop();
        motorLeft.stop();
        System.exit(0);
    }

}
