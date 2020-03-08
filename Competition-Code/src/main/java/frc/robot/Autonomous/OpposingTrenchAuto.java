package frc.robot.Autonomous;

import com.playingwithfusion.TimeOfFlight.RangingMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.*;
import frc.robot.util.*;
import frc.robot.util.Constants.IntakeToggle;
import frc.robot.util.Pneumatics.CompressorState;
import frc.robot.util.sensors.*;
import frc.robot.util.sensors.Limelight.LightMode;
import frc.robot.subsystems.Indexer.*;
import frc.robot.subsystems.Intake.IntakeMotorState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.subsystems.Shooter.*;
import frc.robot.subsystems.WallOfWheels.WallState;

public class OpposingTrenchAuto{

    private static int trenchTimer;
    private static int backupTimer;
    private static boolean turnFlag;
    private static boolean readyToStrafe;
    private static boolean trenchPosition;
    private static boolean flagGyro = true;
    private static boolean tAutoIndex;

    public static void init(){
        Indexer.currentBallCount = 3;
        trenchPosition = false;
        Limelight.LimelightInitialize();
        trenchTimer = 0;
        backupTimer = 0;
        turnFlag = false;
        readyToStrafe = false;
        tAutoIndex = false;
        Intake.liftIntake();
        

    }
    
    public static void run(){


        if(flagGyro == true){
            Gyro.resetGyro();
            flagGyro = false;
        }
        
        Constants.roboGyro = Gyro.updateGyroAngle();
        if(trenchPosition == false){
            tAutoIndex = false;
            Intake.dropIntake();
            Intake.controlIntake(IntakeMotorState.FORWARD);
            WallOfWheels.controlWall(WallState.FORWARD);
            if(!tAutoIndex){
                Indexer.Index();
            }
            //Drive into trench
            if(trenchTimer < 175){
                Drive.run(0, -0.2, 0, Constants.roboGyro);
                trenchTimer++;
            }

            //Drive out of trench & lift intake
            if(trenchTimer > 175 && backupTimer < 175){
                Drive.run(0, -0.2, 0, Constants.roboGyro);
                Intake.liftIntake();
                Intake.controlIntake(IntakeMotorState.STOP);
                WallOfWheels.controlWall(WallState.STOP);
                backupTimer++;
            }
            if(backupTimer > 175 && trenchTimer > 175 && turnFlag == false){
                if(Constants.roboGyro < 180){
                    Drive.run(0, 0, 0.2, Constants.roboGyro);
                } else{
                    turnFlag = true;
                    readyToStrafe = true;
                }
            }
            if(readyToStrafe = true){
                if(Limelight.targetValid() == false){
                    Drive.run(0.2, 0, 0, Constants.roboGyro);
                }else{
                    Drive.run(0, 0, 0, 0);
                    readyToStrafe = false;
                }
            }
            if(Limelight.tx.getDouble(0.0) > 1 || Limelight.tx.getDouble(0.0) < -1){
                Drive.lockOn(0, 0, Constants.roboGyro);
                trenchPosition = true;
                tAutoIndex = true;
            }
        }
        //Run Routine
        if(trenchPosition){
            Routine.init();
            Routine.run();
        }
    }
}