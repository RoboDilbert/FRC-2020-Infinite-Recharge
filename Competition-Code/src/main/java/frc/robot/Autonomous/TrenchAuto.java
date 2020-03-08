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

public class TrenchAuto{
   private static int trenchTimer;
   private static boolean trenchPosition;
   private static boolean flagGyro = true;
   private static boolean tAutoIndex;

    public static void init(){
        Indexer.currentBallCount = 3;
        trenchPosition = false;
        Limelight.LimelightInitialize();
        trenchTimer = 0;
        tAutoIndex = false;

    }
    
    public static void start(){
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
    

        if(trenchTimer < 175){
            Drive.run(0, -0.2, 0, Constants.roboGyro);
            trenchTimer++;
        }
       

        if(!Limelight.targetValid() && trenchTimer == 175){
            Drive.run(0, 0.3, 0.1, Constants.roboGyro);
            Limelight.setLedMode(Limelight.LightMode.ON);    
            Routine.inPosition = false;
            Routine.autoShootCount = 0;
        }
        else if (Limelight.targetValid()){
            Drive.lockOn(0, 0, Constants.roboGyro);
            trenchPosition = true;
            tAutoIndex = true;
            Intake.controlIntake(IntakeMotorState.STOP);
            Intake.liftIntake();
            WallOfWheels.controlWall(WallState.STOP);
        }
    }
    if(trenchPosition){

        Routine.run();
    }
    }
}