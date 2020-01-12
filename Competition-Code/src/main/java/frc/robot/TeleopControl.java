package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.subsystems.*;
import frc.robot.util.*;
import frc.robot.util.sensors.*;

public class TeleopControl{


    // AHRS ahrs;
    
    private static Joystick driver;
    private static Joystick coDriver;

    private static double roboGyro;

    // public double yValue;
    // public double xValue;
    // public double zValue;
    // public float leftPower;
    // public float rightPower;
    // public double roboGyro; 
    
    public static void init(){
        driver = new Joystick(Constants.DRIVER_CONTROLLER_ID);
        coDriver = new Joystick(Constants.CODRIVER_CONTROLLER_ID);
        //add usb camera
    }
    
    public static void run(){

        Gyro.updateGyroAngle(roboGyro);
        Drive.run(driver.getX(), driver.getY(), driver.getZ()/3, roboGyro);

        if(driver.getRawButton(3)){
            Gyro.resetGyro();
        }
        if(driver.getRawButton(1)){
            Limelight.lineUpShot();
        }
    }
}