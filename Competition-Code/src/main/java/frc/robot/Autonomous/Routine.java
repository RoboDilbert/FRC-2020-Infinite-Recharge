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
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.subsystems.Shooter.*;

public class Routine {
    // test
    // public static TOFSensor tofSensor = new TOFSensor();
    private static double XPower;
    private static double YPower;
    private static double ZPower;
    private static double cameraX;
    private static double cameraY;
    private static double cameraS;
    private static double initialSkew;
    private static boolean initialSkewFlag;
    private static double testSkew;
    public static double limelightDistance;
    private static double angle;
    private static double complimentAngle;
    private static double feedForward;
    private static boolean notShot;
    public static boolean inPosition;
    private static boolean isSeeing;
    private static boolean sleepTick;
    public static int autoShootCount = 0;
    private static int shooterClock = 0;
    private static int strafeClock = 0;

    public static void init() {
        Limelight.LimelightInitialize();
        XPower = 0;
        YPower = 0;
        ZPower = 0;
        cameraX = 0;
        cameraY = 0;
        cameraS = 0;
        limelightDistance = 0;
        angle = 0;
        complimentAngle = 0;
        feedForward = 0.03;
        notShot = false;
        inPosition = false;
        isSeeing = false;
        sleepTick = false;
        autoShootCount = 0;
        initialSkew = 0;
        testSkew = 1;
        initialSkewFlag = true;
    }

    public static void run() {
        // if(sleepTick == false)
        // {
        //     try {
        //         Thread.sleep(5000);
        //     } catch (InterruptedException e) {
            
        //     }
        //     sleepTick = true;
        // }
        // if(initialSkewFlag == true){
        //     initialSkew = Limelight.ts.getDouble(1);
        //     initialSkewFlag = false;
        // }

        //Get in Position
        if(limelightDistance < 64 && limelightDistance > 59
            && cameraX > -1 && cameraX < 1 && cameraX != 0 //&&
            /*((cameraS > -1.3 && cameraS < 0) || (cameraS < -86 && cameraS > -90))*/){
                inPosition = true;
            }

        if(inPosition == false){ 
            cameraX = Limelight.tx.getDouble(0.0);
            cameraY = Limelight.ty.getDouble(0.0);
            cameraS = Limelight.ts.getDouble(1);
            limelightDistance = (72.19 / Math.tan(Math.toRadians(38 + Limelight.ty.getDouble(0.0))))-24;
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
        //    if(testSkew == 1){
        //        XPower = 0.1;
        //    }
           
            if(cameraX < -1){
                XPower = (Math.pow((Math.pow((0.18 * cameraX), 2)), 1/1.5))/1.25;
                if(XPower > .2){//.24
                    XPower = .2;
                }
            }
            else if(cameraX > 1){
                XPower = (-Math.pow((Math.pow((0.18 * cameraX), 2)), 1/1.5))/1.25;
                if(XPower < -.2){//-.24
                    XPower = -.2;
                }
            }
            else{
                XPower = 0;
            }
        

            //Y Power
            // if(isSeeing == true){
            //     if(Drive.leftPPDistance < 1000 && Drive.leftPPDistance > 0 || Drive.rightPPDistance < 1000 && Drive.rightPPDistance > 0){
            //         YPower = -0.135;//.1
            //     }
            //     else if(Drive.averagePPLength > 1000){
            //         YPower = ((1.8*Math.pow((Drive.averagePPLength - 150) , 2)) /10000000) + feedForward;
            //         if(YPower > 0.27){//.2
            //             YPower = 0.27;
            //         }
            //     }
            //     if(Drive.leftPP.getRange() < 1000 && Drive.leftPP.getRange() > 900 &&
            //                     Drive.rightPP.getRange() < 1000 && Drive.rightPP.getRange() > 900){
            //         YPower = 0;
            //     }
            // }
            // else if(isSeeing == false){//If we don't see anything, drive straight
            //     YPower = 0.15;//.1
            // }

            // YPower2
    
            
            if(limelightDistance < 60 && limelightDistance  > 0){
                    YPower = -0.115;//.1
                }
            else if(limelightDistance > 64){
                YPower = limelightDistance/600 + feedForward;
                if(YPower > 0.24){//.2
                    YPower = 0.24;
                }
            }
            if(limelightDistance < 65 && limelightDistance  > 58){
                YPower = 0.75 * YPower;//.1
            }
            else if(cameraY == 0){//If we don't see anything, drive straight
                YPower = 0.15;//.1
            }
            // //ZPower
            // if(Drive.averagePPLength < 2508){
            //     if(Drive.leftPPDistance == 0 || Drive.rightPPDistance == 0){
            //         ZPower = 0;
            //     }
            //     else{
            //         ZPower = ((Drive.leftPPDistance - Drive.rightPPDistance) / 750) + feedForward;
            //         if(ZPower > 0.2){//.12
            //             ZPower = 0.2;
            //         }
            //         else if(ZPower < -0.2){
            //             ZPower = -0.2;
            //         }
            //     }
            // }
            // else{
            //     ZPower = 0;
            // }
            cameraX = Limelight.tx.getDouble(0.0);
            cameraY = Limelight.ty.getDouble(0.0);
            cameraS = Limelight.ts.getDouble(1);
            SmartDashboard.updateValues();
            //ZPower
            if(limelightDistance < 64 && limelightDistance > 59
            && cameraX > -1 && cameraX < 1 && cameraX != 0 &&
            ((cameraS > -1.3 && cameraS < 0) || (cameraS < -86 && cameraS > -90))){
                inPosition = true;
            }

            if(limelightDistance < 185 && !inPosition){
                if(limelightDistance == 0){
                    ZPower = 0;
                }
                else{
                    if(cameraS < 0 && cameraS > -45 && cameraX < 20 && cameraX > -20){
                        ZPower = Math.abs(cameraS/120) + 2*feedForward;
                       
                        if((limelightDistance < 64 && limelightDistance > 59
                            && cameraX > -1 && cameraX < 1 && cameraX != 0 &&(cameraS > -10 && cameraS < 0))){
                            //ZPower = Math.abs((cameraS - 4)/120) + feedForward;
                            ZPower = 0.1 * Math.pow(cameraX, 1/2);
                        }
                         if(ZPower > 0.2){
                            ZPower = 0.2;
                        }
                    }else if(cameraS < -45 && cameraS > -90 && cameraX < 20 && cameraX > -20){
                        ZPower = (cameraS)/225 - feedForward;
                        
                        if (limelightDistance < 64 && limelightDistance > 59
                            && cameraX > -1 && cameraX < 1 && cameraX != 0 &&((cameraS > -85 && cameraS < -90))){
                                //ZPower = (-(cameraS + 90)/160) - feedForward;
                                ZPower = -(0.1 * Math.pow(cameraX, 1/2));
                        }
                        if(ZPower < -0.2){
                            ZPower = -0.2;
                        }
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
            
            // if(limelightDistance < 69 && limelightDistance > 64
            // && cameraX > -2 && cameraX < 2 && cameraX != 0 &&
            // ((cameraS > -5 && cameraS < 0) || (cameraS < -85 && cameraS > -90))){
            //     Drive.run(-XPower * 0.75, -YPower * 0.75, ZPower * 0.75, 0);
            // }

            Drive.run(-XPower, -YPower, ZPower, 0);
            //testSkew = Limelight.ts.getDouble(1);

            // if(limelightDistance < 64 && limelightDistance > 59
            // && cameraX > -1 && cameraX < 1 && cameraX != 0 &&
            // ((cameraS > -1 && cameraS < 0) || (cameraS < -85 && cameraS > -90))){
            //     inPosition = true;
            // }
            // else if(limelightDistance < 64 && limelightDistance > 59
            // && cameraX > -1 && cameraX < 1 && cameraX != 0 &&
            // ((cameraS > -10 && cameraS < -1))){
            //     Drive.run(0,0,0.1,0); 
            // }
        }
        if(inPosition == true){
            Pneumatics.controlCompressor(CompressorState.DISABLED);
            Drive.run(0, 0, 0, 0);
            Limelight.setLedMode(LightMode.OFF);
            //Shoot
            if(autoShootCount < 250){
                if (Shooter.getShooterWheelSpeed() < 3000 || shooterClock < 25) {            
                    Shooter.controlShooter(ShooterState.AUTO);
                    Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                    Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
                    shooterClock++;
                }
                else if (Shooter.getShooterWheelSpeed() > 3000) {
                    Shooter.controlShooter(ShooterState.AUTO);
                    Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.FORWARD);
                    Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.FORWARD);
                }
                // else{
                //     Shooter.controlShooter(ShooterState.STOP);
                //     Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                //     Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
                // }
                autoShootCount++;
                Indexer.indexerClear();
            }else{
                Shooter.controlShooter(ShooterState.STOP);
                Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
        
                if(strafeClock < 50){
                   // Drive.run(-0.5, 0, 0, 0);
                    strafeClock++;
                }else{
                    Drive.run(0, 0, 0, 0);
                }
            }
        }      
        debugAuto();
    }

    public static void debugAuto(){
        SmartDashboard.putBoolean("isSeeing", isSeeing);
        SmartDashboard.putBoolean("inPosition", inPosition);
        // SmartDashboard.putNumber("LeftDistance", Drive.leftPPDistance);
        // SmartDashboard.putNumber("RightDistance", Drive.rightPPDistance);
        // SmartDashboard.putNumber("AverageDistance", Drive.averagePPLength);
        SmartDashboard.putNumber("YPower", YPower);
        SmartDashboard.putNumber("LimelightX", cameraX);
        SmartDashboard.putNumber("Skew", cameraS);
        SmartDashboard.putNumber("XPower", XPower);
        SmartDashboard.putNumber("ZPower", ZPower);
        SmartDashboard.putNumber("Limelight Distance", limelightDistance);
        SmartDashboard.putNumber("Limelight Feet", limelightDistance/12);
        // SmartDashboard.putNumber("AutoShootCount", autoShootCount);
        SmartDashboard.updateValues();
    }
}