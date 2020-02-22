package frc.robot.Autonomous;

import com.playingwithfusion.TimeOfFlight.RangingMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.util.*;
import frc.robot.util.Pneumatics.CompressorState;
import frc.robot.util.sensors.*;
import frc.robot.util.sensors.Limelight.LightMode;
import frc.robot.subsystems.Indexer.*;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Shooter.*;

public class Routine{
    //test
    //public static TOFSensor tofSensor = new TOFSensor();
    private static double XPower;
    private static double YPower;
    private static double ZPower ;
    private static double cameraX;
    private static double angle;
    private static double complimentAngle;
    private static double feedForward;
    private static boolean notShot;
    private static boolean inPosition;
    private static boolean isSeeing;
    private static int autoShootCount = 0;

    public static void init(){
        Limelight.LimelightInitialize();
        XPower = 0;
        YPower = 0;
        ZPower = 0;
        cameraX = 0;
        angle = 0;
        complimentAngle = 0;
        feedForward = 0.03;
        notShot = false;
        inPosition = false;
        isSeeing = false;
        autoShootCount = 0;
    }

    public static void run(){
        
        //Get in Position
        if(inPosition == false){ 
            cameraX = Limelight.tx.getDouble(0.0);
            Limelight.setLedMode(LightMode.ON);
            debugAuto();
            if(Drive.rightPP.getRange() >  0 || Drive.leftPP.getRange() > 0){
                isSeeing = true;
            }
            else{
                isSeeing = false;
            }
            Drive.rightPPDistance = Drive.rightPP.getRange();
            Drive.leftPPDistance = Drive.leftPP.getRange();
            Drive.averagePPLength = (Drive.rightPPDistance + Drive.leftPPDistance)/2;

           // X Power
           //limelight locked on and X value of limelight
            if(cameraX < -1){
                XPower = Math.pow((Math.pow((0.18 * cameraX), 2)), 1/1.5);
                if(XPower > .3){//.2
                    XPower = .3;
                }
            }
            else if(cameraX > 1){
                XPower = -Math.pow((Math.pow((0.18 * cameraX), 2)), 1/1.5);
                if(XPower < -.3){//-.2
                    XPower = -.3;
                }
            }
            else{
                XPower = 0;
            }


            //Y Power
            if(isSeeing == true){
                if(Drive.leftPPDistance < 1200 && Drive.leftPPDistance > 0 || Drive.rightPPDistance < 700 && Drive.rightPPDistance > 0){
                    YPower = -0.15;//.1
                }
                else if(Drive.averagePPLength > 1200){
                    YPower = ((1.8*Math.pow((Drive.averagePPLength - 150) , 2)) /10000000) + feedForward;
                    if(YPower > 0.3){//.2
                        YPower = 0.3;
                    }
                }
                if(Drive.leftPP.getRange() < 1300 && Drive.leftPP.getRange() > 1200 &&
                                Drive.rightPP.getRange() < 1300 && Drive.rightPP.getRange() > 1200){
                    YPower = 0;
                }
            }
            else if(isSeeing == false){//If we don't see anything, drive straight
                YPower = 0.15;//.1
            }
            
            //ZPower
            if(Drive.averagePPLength < 2508){
                if(Drive.leftPPDistance == 0 || Drive.rightPPDistance == 0){
                    ZPower = 0;
                }
                else{
                    ZPower = ((Drive.leftPPDistance - Drive.rightPPDistance) / 750) + feedForward;
                    if(ZPower > 0.2){//.12
                        ZPower = 0.2;
                    }
                    else if(ZPower < -0.2){
                        ZPower = -0.2;
                    }
                }
            }
            else{
                ZPower = 0;
            }
        
            if(Drive.averagePPLength < 4000 && Drive.averagePPLength > 2000){
                Drive.rightPP.setRangingMode(RangingMode.Long, 25);
                Drive.leftPP.setRangingMode(RangingMode.Long, 25);
            }
            else if(Drive.averagePPLength < 2000 && Drive.averagePPLength > 25){
                Drive.rightPP.setRangingMode(RangingMode.Medium, 25);
                Drive.leftPP.setRangingMode(RangingMode.Medium, 25);
            }
            else if(Drive.averagePPLength < 4000 && Drive.averagePPLength > 25){
                Drive.rightPP.setRangingMode(RangingMode.Short, 25);
                Drive.leftPP.setRangingMode(RangingMode.Short, 25);
            }
            
            Drive.run(-XPower, -YPower, ZPower, 0);

            if(Drive.leftPP.getRange() > 1200 && Drive.leftPP.getRange() < 1300
            && Drive.rightPP.getRange() < 1300 && Drive.rightPP.getRange() > 1200
            && cameraX > -1 && cameraX < 1){
                inPosition = true;
            }
        }
        if(inPosition == true){
            Pneumatics.controlCompressor(CompressorState.DISABLED);
            Drive.run(0, 0, 0, 0);
            Limelight.setLedMode(LightMode.OFF);
            //Shoot
            if(autoShootCount < 175){
                if (Shooter.getShooterWheelSpeed() < 3600) {            
                    Shooter.controlShooter(ShooterState.FORWARD);
                    Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                    Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
                }
                else if (Shooter.getShooterWheelSpeed() > 3600) {
                    Shooter.controlShooter(ShooterState.FORWARD);
                    Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.FORWARD);
                    Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.FORWARD);
                }
                autoShootCount++;
                Indexer.indexerClear();
            }
            else{
                Shooter.controlShooter(ShooterState.STOP);
                Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
                }
        }
        debugAuto();
    }

    public static void debugAuto(){
        SmartDashboard.putBoolean("isSeeing", isSeeing);
        SmartDashboard.putBoolean("inPosition", inPosition);
        SmartDashboard.putNumber("LeftDistance", Drive.leftPPDistance);
        SmartDashboard.putNumber("RightDistance", Drive.rightPPDistance);
        SmartDashboard.putNumber("AverageDistance", Drive.averagePPLength);
        SmartDashboard.putNumber("YPower", YPower);
        SmartDashboard.putNumber("LimelightX", cameraX);
        SmartDashboard.putNumber("XPower", XPower);
        SmartDashboard.putNumber("ZPower", ZPower);
        SmartDashboard.putNumber("AutoShootCount", autoShootCount);
        SmartDashboard.updateValues();
    }
}