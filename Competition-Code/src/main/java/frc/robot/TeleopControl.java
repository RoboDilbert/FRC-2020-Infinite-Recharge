package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.*;
import frc.robot.subsystems.ColorWheel.*;
import frc.robot.subsystems.HangingMove.HangingMoveState;
//import frc.robot.subsystems.HangingMove.*;
import frc.robot.subsystems.Indexer.*;
import frc.robot.subsystems.Intake.*;
import frc.robot.subsystems.LiftSystem.LifterState;
//import frc.robot.subsystems.LiftSystem.*;
import frc.robot.subsystems.Shooter.*;
import frc.robot.subsystems.WallOfWheels.*;
import edu.wpi.first.wpilibj.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.core.PrettyPrinter;

import frc.robot.util.*;
import frc.robot.util.Constants.IntakeToggle;
import frc.robot.util.Pneumatics.CompressorState;
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
    private static Joystick ButtonLayout;

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
    private static boolean intakeDirectionFlag;
    private static boolean intakeFlipFlag = true;
    private static boolean toggleFlag;

    private static boolean autoIndex;
    private static boolean solenoidPosition; //false is up


    static Timer shootTimer = new Timer();
    //static TimerTask shootTask = new Helper();

    public static void init() {
        driver = new Joystick(Constants.DRIVER_CONTROLLER_ID);
        coDriver = new Joystick(Constants.CODRIVER_CONTROLLER_ID);
        ButtonLayout = new Joystick(Constants.BUTTON_LAYOUT_CONTROLLER_ID);
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        // add usb camera
         autoIndex = false;
        solenoidPosition = false;
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
        // if (driver.getRawButton(2)) {
        //     Drive.lineUpShot(driver.getX(), driver.getY(), driver.getZ() / 3, Constants.roboGyro);
        // } 

        // else if (driver.getRawButton(5)) {
        //     Drive.run(driver.getX() / 4, driver.getY() / 4, driver.getZ() / 6, Constants.roboGyro);
        // } 
        //else {
            Drive.run(driver.getX() / Constants.movementRestriction, driver.getY() / Constants.movementRestriction,
                    driver.getZ() / 3, Constants.roboGyro);
        //}

        // -------------------------------------------------------------------------------------------------------------------
        // Wall of Wheels & Intake Control
            if(driver.getRawButton(4) || driver.getRawButton(6)){
                toggleFlag = true;
            }
            else{
                toggleFlag = false;
            }

        
            if (driver.getRawButton(4) && solenoidPosition == true && intakeDirectionFlag == false) {
                Robot.currentIntakeState = IntakeToggle.REVERSE;
                intakeDirectionFlag = true;
            } else if (driver.getRawButton(4) && intakeDirectionFlag == true && toggleFlag == false || driver.getRawButton(6) && intakeDirectionFlag == true && toggleFlag == false){
                Robot.currentIntakeState = IntakeToggle.STOP;
                intakeDirectionFlag = false;
            } else if (driver.getRawButton(6) && solenoidPosition == true && intakeDirectionFlag == false) {
                Robot.currentIntakeState = IntakeToggle.FORWARD;
                intakeDirectionFlag = true;
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

        if(ButtonLayout.getRawButton(5)){
            Intake.dropIntake();
            solenoidPosition = true;
        }
        if(ButtonLayout.getRawButton(6)){
            Intake.liftIntake();
            solenoidPosition = false;
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

        // if(ButtonLayout.getRawButton(2)){
        //     ColorWheel.controlColorWheel(SearchValue.FORWARD);
        // } else if(ButtonLayout.getRawButton(1)){
        //     ColorWheel.controlColorWheel(SearchValue.REVERSE);
        // }else{
        //     ColorWheel.controlColorWheel(SearchValue.STOP);
        // }

        // if(coDriver.getRawButton(4)){
        //     ColorWheel.liftWheel();
        // }
        // if(coDriver.getRawButton(3)){
        //     ColorWheel.dropWheel();
        // }
        // ------------------------------------------------------------------------------------------------------
        // Indexer Control

        autoIndex = Indexer.Index();

        if(coDriver.getRawButton(4)){
            Indexer.currentBallCount++;
        }else if(coDriver.getRawButton(2)){
            Indexer.currentBallCount--;
        }

        // -------------------------------------------------------------------------------------------------------
        // Shooter Control

        if (driver.getRawButton(1)) {
            Pneumatics.controlCompressor(CompressorState.DISABLED);
            if(driver.getRawButton(4) != true  && driver.getRawButton(6) != true){
                Robot.currentIntakeState = IntakeToggle.STOP;
            }
            
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
            Pneumatics.controlCompressor(CompressorState.ENABLED);
        }

        // -------------------------------------------------------------------------------------------------------
        // Lifter Control
        if(coDriver.getPOV() == 0){
            LiftSystem.controlLifter(LifterState.FORWARD);
        }else if(coDriver.getPOV() == 45){
            LiftSystem.controlLifter(LifterState.FORWARD);
            HangingMove.controlMove(HangingMove.HangingMoveState.RIGHT);
        }else if(coDriver.getPOV() == 90){
            HangingMove.controlMove(HangingMove.HangingMoveState.RIGHT);
        }else if(coDriver.getPOV() == 135){
            LiftSystem.controlLifter(LifterState.REVERSE);
            HangingMove.controlMove(HangingMove.HangingMoveState.RIGHT);
        }else if(coDriver.getPOV() == 180){
            LiftSystem.controlLifter(LifterState.REVERSE);
        }else if(coDriver.getPOV() == 225){
            LiftSystem.controlLifter(LifterState.REVERSE);
            HangingMove.controlMove(HangingMove.HangingMoveState.LEFT);
        }else if(coDriver.getPOV() == 270){
            HangingMove.controlMove(HangingMove.HangingMoveState.LEFT);
        }else if(coDriver.getPOV() == 315){
            LiftSystem.controlLifter(LifterState.FORWARD);
            HangingMove.controlMove(HangingMove.HangingMoveState.LEFT);
        }else{
            HangingMove.controlMove(HangingMove.HangingMoveState.STOP);
            LiftSystem.controlLifter(LifterState.STOP);
        }
        //-------------------------------------------------------------------------------------------------------
        //Compressor Shut Off
        
        if(Timer.getMatchTime() < 30){
            Pneumatics.controlCompressor(CompressorState.DISABLED);
        }

        //-------------------------------------------------------------------------------------------------------
        //Debug Control
         Indexer.debugIndexer();
         //Shooter.debugShooter();
        SmartDashboard.putNumber("Match Time", Timer.getMatchTime());
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