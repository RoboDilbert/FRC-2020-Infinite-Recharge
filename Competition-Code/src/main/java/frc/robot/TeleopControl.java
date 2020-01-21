package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.subsystems.*;
import frc.robot.util.*;
import frc.robot.util.sensors.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopControl{


    // AHRS ahrs;
    
    public static Joystick driver;
    private static Joystick coDriver;

   // private static RevColor colorSensor;

    // public double yValue;
    // public double xValue;
    // public double zValue;
    // public float leftPower;
    // public float rightPower;
    // public double roboGyro; 
    
    public static void init(){
        driver = new Joystick(Constants.DRIVER_CONTROLLER_ID);
        //coDriver = new Joystick(Constants.CODRIVER_CONTROLLER_ID);
        //add usb camera
        //colorSensor = new RevColor();
    }
    
    public static void run(){


        // Driving Program w/ Gyro -----------------------------------------------------------------------

        Constants.roboGyro = Gyro.updateGyroAngle();
        Gyro.getGyroValues();
        if(driver.getRawButton(3)){
            Gyro.resetGyro();
        }
        if(driver.getRawButton(1)){
            Drive.lineUpShot(driver.getX(), driver.getY(), driver.getZ()/3, Constants.roboGyro);
        }
       else if(driver.getRawButton(2)){
            Drive.driveWithoutTurn(driver.getX()/Constants.movementRestriction, driver.getY()/Constants.movementRestriction, Constants.roboGyro);
        }
        else{
            Drive.run(driver.getX()/Constants.movementRestriction, driver.getY()/Constants.movementRestriction, driver.getZ()/3, Constants.roboGyro);
        }

        //-------------------------------------------------------------------------------------------------------------------

        //colorSensor.displayColor();
        SmartDashboard.updateValues();
    }
}