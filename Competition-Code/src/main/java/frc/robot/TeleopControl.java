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

    double roboGyro;

    // public double yValue;
    // public double xValue;
    // public double zValue;
    // public float leftPower;
    // public float rightPower;
    // public double roboGyro; 
    
    public void init(){
        driver = new Joystick(Constants.DRIVER_CONTROLLER_ID);
        coDriver = new Joystick(Constants.CODRIVER_CONTROLLER_ID);
        //add usb camera
    }
    
    public void run(){

        Gyro.updateGyroAngle(roboGyro);
        Drive.run(driver.getX(), driver.getY(), driver.getZ(), roboGyro);
        // yValue = m_leftStick.getY();
        // xValue = m_leftStick.getX();
        // zValue = m_leftStick.getZ();
    
        // xValue = xValue;
        // yValue = yValue;
        // zValue = zValue/3;

        // if(xValue < 0.05 && xValue > -0.05){
        //     xValue = 0;
        // }
        // if(yValue < 0.05 && yValue > -0.05){
        //     yValue = 0;
        // }
        // if(zValue < 0.05 && zValue > -0.05){
        //     zValue = 0;
        // }
    
        // if(m_leftStick.getRawButton(10)){
        //   m_turretMotor.set(0.5);
        // }
        // else if(m_leftStick.getRawButton(9)){
        //   m_turretMotor.set(-0.5);
        // }
        // else{
        //   m_turretMotor.set(0);
        // }

        //roboGyro = ahrs.getAngle();

        // if(m_leftStick.getRawButton(1)){ //trigger pressed
        //     if(x > 0){
        //         m_myRobot.driveCartesian(-(Math.pow((0.025 * x), 2)), yValue, 0, -roboGyro);
        //     }
        //     else if(x < 0){
        //         m_myRobot.driveCartesian((Math.pow((0.025 * x), 2)), yValue, 0, -roboGyro);
        //     }
        //     else{
        //         m_myRobot.driveCartesian(0, 0, 0, 0);
        //     }
        // }
        // else{//Trigger not pressed = normal drive
            //m_myRobot.driveCartesian(xValue, -yValue, zValue, -roboGyro);
        //}
    }
}