package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.*;
import frc.robot.subsystems.ColorWheel.*;
//import frc.robot.subsystems.HangingMove.*;
import frc.robot.subsystems.Indexer.*;
import frc.robot.subsystems.Intake.*;
import frc.robot.subsystems.LiftSystem.LifterState;
//import frc.robot.subsystems.LiftSystem.*;
import frc.robot.subsystems.Shooter.*;
import frc.robot.subsystems.WallOfWheels.*;
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.core.PrettyPrinter;

import frc.robot.util.*;
import frc.robot.util.Constants.IntakeToggle;
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

    private static boolean autoIndex;
    private static int solenoidPosition = 1;

    static Timer shootTimer = new Timer();
    //static TimerTask shootTask = new Helper();

    public static void init() {
        driver = new Joystick(Constants.DRIVER_CONTROLLER_ID);
        coDriver = new Joystick(Constants.CODRIVER_CONTROLLER_ID);
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        // add usb camera
         autoIndex = false;

    }

    public static void run() {
        autoIndex = false;

        // Driving Program w/ Gyro
        // -----------------------------------------------------------------------
        Constants.roboGyro = Gyro.updateGyroAngle();
        Gyro.getGyroValues();
        if (driver.getRawButton(3)) {
            Gyro.resetGyro();
        }
        if (coDriver.getRawButton(9)) {
            Drive.lineUpShot(driver.getX(), driver.getY(), driver.getZ() / 3, Constants.roboGyro);
        } else if (coDriver.getRawButton(10)) {
            Drive.driveWithoutTurn(driver.getX() / Constants.movementRestriction,
                    driver.getY() / Constants.movementRestriction, Constants.roboGyro);
        } else {
            Drive.run(driver.getX() / Constants.movementRestriction, driver.getY() / Constants.movementRestriction,
                    driver.getZ() / 3, Constants.roboGyro);
        }

        // -------------------------------------------------------------------------------------------------------------------
        // Wall of Wheels & Intake Control
        
            if (driver.getRawButton(7)) {
                Robot.currentIntakeState = IntakeToggle.FORWARD;
            } else if (driver.getRawButton(9)) {
                Robot.currentIntakeState = IntakeToggle.STOP;
            } else if (driver.getRawButton(11)) {
                Robot.currentIntakeState = IntakeToggle.REVERSE;
            }

        if (Robot.currentIntakeState == IntakeToggle.FORWARD) {
            WallOfWheels.controlWall(WallState.FORWARD);
            Intake.controlIntake(IntakeState.INTAKE);
        } else if (Robot.currentIntakeState == IntakeToggle.REVERSE) {
            WallOfWheels.controlWall(WallState.REVERSE);
            Intake.controlIntake(IntakeState.REVERSE);
        } else if (Robot.currentIntakeState == IntakeToggle.STOP) {
            WallOfWheels.controlWall(WallState.STOP);
            Intake.controlIntake(IntakeState.STOP);
        }

        if(driver.getRawButton(12)){
            Intake.dropIntake();
            solenoidPosition = 0;
        }
        if(driver.getRawButton(10)){
            Intake.liftIntake();
            solenoidPosition = 1;
        }

        if(coDriver.getRawButton(7)){
            Indexer.controlIndexer(Indexer.SelectIndexer.FEEDER, IndexerState.FORWARD);
        }
        else{
            Indexer.controlIndexer(Indexer.SelectIndexer.FEEDER, IndexerState.STOP);
        }

        // ----------------------------------------------------------------------------------------------
        // // Color Wheel Control
        // if (coDriver.getRawButton(12) && foundColor != gameData) {
        // foundColor = colorSensor.searchColor();
        // ColorWheel.WheelSearch(SearchValue.COLOR);
        // }
        // else if(coDriver.getRawButton(11) && ColorCount <= 6){
        // if(Constants.initalColor == null){
        // Constants.initalColor = colorSensor.searchColor();
        // Constants.bufferColor = Constants.initalColor;
        // colorFlag = true;
        // }
        // ColorWheel.WheelSearch(SearchValue.FORWARD);
        // Constants.bufferColor = Constants.nextColor;
        // Constants.nextColor = colorSensor.searchColor();
        // if(Constants.bufferColor == Constants.nextColor || Constants.bufferColor ==
        // null){
        // colorFlag = true;
        // }
        // else{
        // colorFlag = false;
        // }
        // if(Constants.initalColor == Constants.nextColor && colorFlag == false){
        // ColorCount++;
        // }
        // }
        // else{
        // ColorWheel.WheelSearch(SearchValue.STOP);
        // }

        // if(driver.getRawButton(8)){
        //     ColorWheel.controlColorWheel(SearchValue.FORWARD);
        // }
        // else{
        //     ColorWheel.controlColorWheel(SearchValue.STOP);
        // }

        if(coDriver.getRawButton(4)){
            ColorWheel.liftWheel();
        }
        if(coDriver.getRawButton(3)){
            ColorWheel.dropWheel();
        }
        // ------------------------------------------------------------------------------------------------------
        // Indexer Control

        //autoIndex = Indexer.Index();

        // -------------------------------------------------------------------------------------------------------
        // Shooter Control

        if (driver.getRawButton(1)) {
            // if(driver.getRawButton(7) != false  && driver.getRawButton(11) != true){
            //     Robot.currentIntakeState = IntakeToggle.STOP;
            // }
            if (Shooter.getShooterWheelSpeed() < 3300) {
                Shooter.controlShooter(ShooterState.FORWARD);
                if(autoIndex = false){
                    Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                }
                Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
            } else if (Shooter.getShooterWheelSpeed() > 3300) {
                Shooter.controlShooter(ShooterState.FORWARD);
                if (autoIndex = false) {
                    Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.FORWARD);
                }
                //if (Shooter.getShooterWheelSpeed() > 3500) {
                    Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.FORWARD);
                   // Constants.shootFlag = false;
                    SmartDashboard.updateValues();
                    //shootTimer.purge();
                 //else {
                    // if (Constants.shootFlag == false) {
                    //     //shootTimer.schedule(shootTask, 1000);
                    // }
                //}
            }
            Indexer.indexerClear();
        }
        else{
            Shooter.controlShooter(ShooterState.STOP);
            if(autoIndex = false){
                Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
            }
            Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
        }

        // -------------------------------------------------------------------------------------------------------
        // Lifter Control
        if(coDriver.getRawButton(1)){
            LiftSystem.controlLifter(LifterState.FORWARD);
        }else if(coDriver.getRawButton(2)){
            LiftSystem.controlLifter(LifterState.REVERSE);
        }else{
            LiftSystem.controlLifter(LifterState.STOP);
        }

        //--------------------------------------------------------------------------------------------------------
        //Hook Control
        
        //--------------------------------------------------------------------------------------------------------
        //Debug Control
        Indexer.debugIndexer();
        Shooter.debugShooter();
        SmartDashboard.putBoolean("TimerFlag", Constants.shootFlag);
        SmartDashboard.updateValues();
    }
}

// class Helper extends TimerTask{

//     public void run() 
//     { 
//         Constants.shootFlag = true;

//         SmartDashboard.updateValues();
//     } 
// }