package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.subsystems.*;
import frc.robot.subsystems.ColorWheel.SearchValue;
import frc.robot.subsystems.Intake.ballValues;
import frc.robot.util.*;
import frc.robot.util.sensors.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMax.IdleMode;
// import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.DriverStation;

public class TeleopControl{


    // AHRS ahrs;
    
    public static Joystick driver;
    private static Joystick coDriver;

    private static RevColor colorSensor;
    private static ColorWheel wheelColor;
    private static Intake ballIntake;
    private static String foundColor;
    // public double yValue;
    // public double xValue;
    // public double zValue;
    // public float leftPower;
    // public float rightPower;
    // public double roboGyro;
    private static int ColorCount;
    private static String gameData;
    private static boolean colorFlag;

    public static void init() {
        driver = new Joystick(Constants.DRIVER_CONTROLLER_ID);
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        coDriver = new Joystick(Constants.CODRIVER_CONTROLLER_ID);
        // add usb camera
        wheelColor = new ColorWheel();
        colorSensor = new RevColor();
        ballIntake = new Intake();
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

        // // -------------------------------------------------------------------------------------------------------------------

        //Wall of Wheels Testing
        if(driver.getRawButton(7)){
            WallOfWheels.PowerWall(WallOfWheels.WallDirections.FORWARD);
        } else if(driver.getRawButton(11)){
            WallOfWheels.PowerWall(WallOfWheels.WallDirections.REVERSE);
        }else if(driver.getRawButton(9)){
            WallOfWheels.PowerWall(WallOfWheels.WallDirections.STOP);
        }

        // //----------------------------------------------------------------------------------------------
        
        // //Color Wheel
        // if (coDriver.getRawButton(12) && foundColor != gameData) {
        //     foundColor = colorSensor.searchColor();
        //     wheelColor.WheelSearch(SearchValue.COLOR);
        // }
        // else if(coDriver.getRawButton(11) && ColorCount <= 6){
        //     if(Constants.initalColor == null){
        //         Constants.initalColor = colorSensor.searchColor();
        //         Constants.bufferColor = Constants.initalColor;
        //         colorFlag = true;
        //     }
        //     wheelColor.WheelSearch(SearchValue.FORWARD);
        //     Constants.bufferColor = Constants.nextColor;
        //     Constants.nextColor = colorSensor.searchColor();
        //     if(Constants.bufferColor == Constants.nextColor || Constants.bufferColor == null){
        //         colorFlag = true;
        //     }
        //     else{
        //         colorFlag = false;
        //     }
        //     if(Constants.initalColor == Constants.nextColor && colorFlag == false){
        //         ColorCount++;
        //     }
        // }
        // else{
        //     wheelColor.WheelSearch(SearchValue.STOP);
        // }

        // //--------------------------------------------------------------------------------------------------------------------

        // //Intake

        // if(driver.getRawButton(5)){
        //     ballIntake.intakeControl(ballValues.INTAKE);
        // }


        // //------------------------------------------------------------------------------------------------------
        // //indexer test
        if (driver.getRawButton(5)){
            Indexer.indexFeed.set(0.25);
        }
        else{
            Indexer.indexFeed.set(0.0);
        }
        
        if(driver.getRawButton(6)){
            Indexer.indexShoot.set(0.25);
        }
        else{
            Indexer.indexShoot.set(0.0);
        }
        // //-------------------------------------------------------------------------------------------------------
        // //shooter test
        // if(coDriver.getTrigger()){
        //     Shooter.ShooterForward();
        // }
        // else{
        //     Shooter.ShooterStop();
        // }


        SmartDashboard.updateValues();
    }
}