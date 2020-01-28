package frc.robot.Autonomous;

import com.playingwithfusion.TimeOfFlight.RangingMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.util.*;
import frc.robot.util.sensors.*;
import frc.robot.util.sensors.Gyro;

public class Routine{

    public static double XPower = 0;
    public static double YPower = 0;
    public static double ZPower = 0;
    public static double cameraX = 0;
    public static double angle = 0;
    public static double complimentAngle = 0;
    public static double feedForward = 0.03;

    //public static TOFSensor tofSensor = new TOFSensor();

    public static void run(){

        Constants.inPosition = false;

        Limelight.LimelightInitialize();

        //Get in Position
        while(Constants.inPosition == false){// TODO WHILE NOT SHOT
            cameraX = Limelight.tx.getDouble(0.0);
           
            if(TOFSensor.rightPP.getRange() >  0 || TOFSensor.leftPP.getRange() > 0){
                Constants.isSeeing = true;
            }
            else{
                Constants.isSeeing = false;
            }
            Constants.rightPPDistance = TOFSensor.rightPP.getRange();
            Constants.leftPPDistance = TOFSensor.leftPP.getRange();
            Constants.averagePPLength = (Constants.rightPPDistance + Constants.leftPPDistance)/2;


           // X Power
           //limelight locked on and X value of limelight
            if(cameraX < -1){
                XPower = Math.pow((Math.pow((0.18 * cameraX), 2)), 1/1.5);
                if(XPower > .215){
                    XPower = .15;
                }
            } else if(cameraX > 1){
                XPower = -Math.pow((Math.pow((0.18 * cameraX), 2)), 1/1.5);
                if(XPower < -.15){
                    XPower = -.15;
                }
            }
            else{
                XPower = 0;
            }


            //Y Power
            if(Constants.isSeeing == true){
                if(Constants.leftPPDistance < 700 && Constants.leftPPDistance > 0 || Constants.rightPPDistance < 700 && Constants.rightPPDistance > 0){
                        YPower = -0.1;
                }
                else if(Constants.averagePPLength > 750){
                    YPower = ((1.8*Math.pow((Constants.averagePPLength - 150) , 2)) /10000000) + feedForward;
                    if(YPower > 0.2){
                        YPower = 0.2;
                    }
                }
                if(TOFSensor.leftPP.getRange() < 750 && TOFSensor.leftPP.getRange() > 700 
                   && TOFSensor.rightPP.getRange() < 750 && TOFSensor.rightPP.getRange() > 700){
                    YPower = 0;
                }

            }else if(Constants.isSeeing == false){//If we don't see anything, drive straight
                YPower = 0.1;
                if(TOFSensor.leftPP.getRange() < 750 && TOFSensor.leftPP.getRange() > 700 
                   && TOFSensor.rightPP.getRange() < 750 && TOFSensor.rightPP.getRange() > 700){
                    YPower = 0;
                }
            }
            

            // if (Constants.averagePPLength < 1000){
            //     angle = Math.atan(Math.abs(YPower/XPower));
            //     complimentAngle = 90-angle;
            //     if(XPower < 0){

            //     }
            // }
        if(Constants.averagePPLength < (2508)){
            if(Constants.leftPPDistance == 0 || Constants.rightPPDistance == 0){
                ZPower = 0;
            }
             else{
                 ZPower = ((Constants.leftPPDistance - Constants.rightPPDistance)/750) + feedForward;

                 if(ZPower > 0.12){
                     ZPower = 0.12;
                 }
                 

                //  if(ZPower <= .07 && ZPower > 0){
                //     ZPower = .09;
                // }
                // else if(ZPower >= -.07 && ZPower < 0){
                //     ZPower = -.09;
                // }
             }
        }else{
            ZPower = 0;
        }
        
        if(Constants.averagePPLength < 4000 && Constants.averagePPLength > 2000){
            TOFSensor.rightPP.setRangingMode(RangingMode.Long, 25);
            TOFSensor.leftPP.setRangingMode(RangingMode.Long, 25);
        } else if(Constants.averagePPLength < 2000 && Constants.averagePPLength > 25){
            TOFSensor.rightPP.setRangingMode(RangingMode.Medium, 25);
            TOFSensor.leftPP.setRangingMode(RangingMode.Medium, 25);
        }else if(Constants.averagePPLength < 4000 && Constants.averagePPLength > 25){
            TOFSensor.rightPP.setRangingMode(RangingMode.Short, 25);
            TOFSensor.leftPP.setRangingMode(RangingMode.Short, 25);
        }
            

            Drive.run(-XPower, -YPower, ZPower, 0);

            if(TOFSensor.leftPP.getRange() > 700 && TOFSensor.leftPP.getRange() < 765
             && TOFSensor.rightPP.getRange() < 765 && TOFSensor.rightPP.getRange() > 700
             && cameraX > -1 && cameraX < 1){
                Constants.inPosition = true;
            }
            else{
                Constants.inPosition = false;
            }

            SmartDashboard.putBoolean("isSeeing", Constants.isSeeing);
            SmartDashboard.putNumber("LeftDistance", Constants.leftPPDistance);
            SmartDashboard.putNumber("RightDistance", Constants.rightPPDistance);
            SmartDashboard.putNumber("AverageDistance", Constants.averagePPLength);
            SmartDashboard.putBoolean("inPosition", Constants.inPosition);
            SmartDashboard.putNumber("YPower", YPower);
            SmartDashboard.putNumber("LimelightX", cameraX);
            SmartDashboard.putNumber("XPower", XPower);
            SmartDashboard.putNumber("ZPower", ZPower);
            SmartDashboard.updateValues();
        }

        //Cut Power
        Drive.run(0, 0, 0, 0);
        
        //Shoot

        //Drive to trench
    }
}