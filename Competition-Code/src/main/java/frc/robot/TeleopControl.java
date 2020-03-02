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
import frc.robot.util.sensors.Limelight.LightMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMax.IdleMode;
// import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.DriverStation;

public class TeleopControl{

    public static Joystick driver;
    private static Joystick coDriver;
    private static Joystick ButtonLayout;

    private static String foundColor;
    private static int ColorCount;
    private static String gameData;
    private static boolean colorFlag;
    private static boolean intakeDirectionFlag;
    private static boolean intakeFlipFlag = true;
    private static boolean toggleFlag;
    private static int shooterClock = 0;

    private static boolean autoIndex;

    public static void init() {
        driver = new Joystick(Constants.DRIVER_CONTROLLER_ID);
        coDriver = new Joystick(Constants.CODRIVER_CONTROLLER_ID);
        ButtonLayout = new Joystick(Constants.BUTTON_LAYOUT_CONTROLLER_ID);
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        // add usb camera
         autoIndex = false;
         Limelight.LimelightInitialize();
    }

    public static void run() {
        autoIndex = false;

        // Driving Program w/ Gyro
        // -----------------------------------------------------------------------
        Constants.roboGyro = Gyro.updateGyroAngle();
        //Gyro.getGyroValues();
        if (driver.getRawButton(3)){
            Gyro.resetGyro();
        }
        if (driver.getRawButton(2)){
             Drive.lineUpShot();
        }
        else if (driver.getRawButton(4)){
            Drive.run(driver.getX() / 4, driver.getY() / 4, driver.getZ() / 6, Constants.roboGyro);
        }
        else{
            Drive.run(driver.getX() * Constants.movementRestriction, driver.getY() * Constants.movementRestriction,
                    driver.getZ() / 3, Constants.roboGyro);
            Limelight.setLedMode(LightMode.OFF);
            Drive.tInPosition = false;
            if(!driver.getRawButton(1)){
                Shooter.controlShooter(ShooterState.STOP);
                if(autoIndex = false){
                    Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                }
                Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
                Pneumatics.controlCompressor(CompressorState.ENABLED);
            }

        }




        // Test Auto Line Up 2
        if(driver.getRawButton(5)){
            Drive.autoLineUp(driver.getX() * Constants.movementRestriction, driver.getY() * Constants.movementRestriction, Constants.roboGyro);
        }



        

        // -------------------------------------------------------------------------------------------------------------------
        // Wall of Wheels & Intake Control

        // ButtonLayout 4 = Green
        // ButtonLayout 3 = Red
        if (Intake.getSolenoidState() == Intake.IntakeSolenoid.DOWN && ButtonLayout.getRawButton(3) && toggleFlag == false && Intake.getMotorState() != Intake.IntakeMotorState.REVERSE) {
            Robot.currentIntakeState = IntakeToggle.REVERSE;
        } else if (Intake.getSolenoidState() == Intake.IntakeSolenoid.DOWN && ButtonLayout.getRawButton(4) && toggleFlag == false && Intake.getMotorState() != Intake.IntakeMotorState.FORWARD) {
            Robot.currentIntakeState = IntakeToggle.FORWARD;
        } else if ((ButtonLayout.getRawButton(4) && toggleFlag == false && Intake.getMotorState() == Intake.IntakeMotorState.FORWARD) || 
                            (ButtonLayout.getRawButton(3) && toggleFlag == false && Intake.getMotorState() == Intake.IntakeMotorState.REVERSE)){
            Robot.currentIntakeState = IntakeToggle.STOP;
        }

        if(ButtonLayout.getRawButton(4) || ButtonLayout.getRawButton(3)){
            toggleFlag = true;
        }
        else{
            toggleFlag = false;
        }

        if(ButtonLayout.getRawButton(6)){ //White Button
            Intake.liftIntake();
            Robot.currentIntakeState = IntakeToggle.STOP;
        }
        if(ButtonLayout.getRawButton(5)){ //Black Button
            Intake.dropIntake();
            Robot.currentIntakeState = IntakeToggle.FORWARD;
        }

        if (Robot.currentIntakeState == IntakeToggle.FORWARD) {
            WallOfWheels.controlWall(WallState.FORWARD);
            Intake.controlIntake(IntakeMotorState.FORWARD);
        } else if (Robot.currentIntakeState == IntakeToggle.REVERSE) {
            WallOfWheels.controlWall(WallState.REVERSE);
            Intake.controlIntake(IntakeMotorState.REVERSE);
        } else if (Robot.currentIntakeState == IntakeToggle.STOP) {
            WallOfWheels.controlWall(WallState.STOP);
            Intake.controlIntake(IntakeMotorState.STOP);
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

        if(!driver.getRawButton(2)){
            autoIndex = Indexer.Index();
        }

        if(coDriver.getRawButton(4)){
            Indexer.currentBallCount++;
        }else if(coDriver.getRawButton(2)){
            Indexer.currentBallCount--;
        }

        // -------------------------------------------------------------------------------------------------------
        // Shooter Control
        if(!driver.getRawButton(2)){
            if(ButtonLayout.getRawButton(2) && !driver.getRawButton(1)){
                Shooter.controlShooter(ShooterState.FORWARD);
                if(autoIndex = false){
                    Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                }
                Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
                shooterClock++;
            }   
            if (driver.getRawButton(1) && !driver.getRawButton(2)){
                Pneumatics.controlCompressor(CompressorState.DISABLED);
                if(ButtonLayout.getRawButton(4) != true  && ButtonLayout.getRawButton(3) != true){
                    Robot.currentIntakeState = IntakeToggle.STOP;
                }
            
                if (Shooter.getShooterWheelSpeed() < 3300 || shooterClock < 25) {
                    Shooter.controlShooter(ShooterState.FORWARD);
                    if(autoIndex = false){
                        Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                    }
                    Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
                    shooterClock++;
                }
                else if (Shooter.getShooterWheelSpeed() > 3300) {
                    Shooter.controlShooter(ShooterState.FORWARD);
                    if (autoIndex = false) {
                        Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.FORWARD);
                    }
                    Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.FORWARD);
                    SmartDashboard.updateValues();
                }
                Indexer.indexerClear();
            }
            else{
                if(!ButtonLayout.getRawButton(2)){
                    Shooter.controlShooter(ShooterState.STOP);
                    if(autoIndex = false){
                    Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                    }
                    Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
                    Pneumatics.controlCompressor(CompressorState.ENABLED);
                    shooterClock = 0;
                }
            }
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
        // if(Timer.getMatchTime() < 30){
        //     Pneumatics.controlCompressor(CompressorState.DISABLED);
        //}
        //-------------------------------------------------------------------------------------------------------
        //Debug Control
        Indexer.debugIndexer();
        Drive.LineUpData();
        SmartDashboard.putNumber("Match Time", Timer.getMatchTime());
        SmartDashboard.updateValues();
    }
}