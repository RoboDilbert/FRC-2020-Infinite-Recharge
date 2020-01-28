package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.subsystems.*;
import frc.robot.util.*;
import frc.robot.util.sensors.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.DriverStation;

public class TeleopControl{


    // AHRS ahrs;
    
    public static Joystick driver;
    private static Joystick coDriver;

    private static RevColor colorSensor;
    private static ColorWheel wheelColor;
    private static String foundColor;
    // public double yValue;
    // public double xValue;
    // public double zValue;
    // public float leftPower;
    // public float rightPower;
    // public double roboGyro;
    private static String gameData;

    public static void init() {
        driver = new Joystick(Constants.DRIVER_CONTROLLER_ID);
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        // coDriver = new Joystick(Constants.CODRIVER_CONTROLLER_ID);
        // add usb camera
        wheelColor = new ColorWheel();
        colorSensor = new RevColor();
    }

    public static void run() {

        // Driving Program w/ Gyro
        // -----------------------------------------------------------------------

        Constants.roboGyro = Gyro.updateGyroAngle();
        Gyro.getGyroValues();
        if (driver.getRawButton(3)) {
            Gyro.resetGyro();
        }
        if (driver.getRawButton(1)) {
            Drive.lineUpShot(driver.getX(), driver.getY(), driver.getZ() / 3, Constants.roboGyro);
        } else if (driver.getRawButton(2)) {
            Drive.driveWithoutTurn(driver.getX() / Constants.movementRestriction,
                    driver.getY() / Constants.movementRestriction, Constants.roboGyro);
        } else {
            Drive.run(driver.getX() / Constants.movementRestriction, driver.getY() / Constants.movementRestriction,
                    driver.getZ() / 3, Constants.roboGyro);
        }

        // -------------------------------------------------------------------------------------------------------------------

        // colorSensor.displayColor();
        
        if (coDriver.getRawButton(12) && foundColor != gameData) {
            foundColor = colorSensor.searchColor();
            //wheelColor.spinThatWheel();
        }

        SmartDashboard.updateValues();
    }
}