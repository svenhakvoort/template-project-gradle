package example.robotics.ev3.actuator;

import ev3dev.actuators.LCD;
import ev3dev.sensors.Button;
import ev3dev.sensors.EV3Key;
import ev3dev.utils.Brickman;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ButtonListenersExample {

    public static Logger LOGGER = LoggerFactory.getLogger(LCDShowJavaLogoExample.class);
    public static GraphicsLCD lcd = LCD.getInstance();

    public static void main(final String[] args){
        Brickman.disable();
        lcd.setAutoRefresh(false);
        lcd.setAutoRefreshPeriod(0);
        System.out.println("Written autoref");

        //lcd.setColor(Color.BLACK);
        lcd.setColor(0,0,0);
        lcd.drawRect(0,0, lcd.getWidth(), lcd.getHeight());
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
        System.out.println("Written rect");

        //lcd.setColor(Color.WHITE);
        lcd.setColor(255,255,255);
        lcd.drawString("Hello World", (lcd.getWidth()/ 2)-30, lcd.getHeight()/2, 0);
        System.out.println("Written string");
        lcd.refresh();
        System.out.println("Refreshed");

        Delay.msDelay(60_000);
    }
}