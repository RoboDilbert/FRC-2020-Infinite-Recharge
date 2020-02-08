package frc.robot.Autonomous;

import com.playingwithfusion.TimeOfFlight.RangingMode;
import com.playingwithfusion.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.util.*;
import frc.robot.util.sensors.*;
import frc.robot.util.sensors.Gyro;

public class Routine{

    //public static TOFSensor tofSensor = new TOFSensor();

    public static void run(){

        Constants.inPosition = false;

        Limelight.LimelightInitialize();

        //Get in Position
        while(Constants.inPosition == false){ //TODO WHILE NOT SHOT
            Constants.cameraX = Limelight.tx.getDouble(0.0);
           
            if(Drive.rightPP.getRange() >  0 || Drive.leftPP.getRange() > 0){
                Constants.isSeeing = true;
            }
            else{
                Constants.isSeeing = false;
            }
            Constants.rightPPDistance = Drive.rightPP.getRange();
            Constants.leftPPDistance = Drive.leftPP.getRange();
            Constants.averagePPLength = (Constants.rightPPDistance + Constants.leftPPDistance)/2;


           // X Power
           //limelight locked on and X value of limelight
            if(Constants.cameraX < -1){
                Constants.XPower = Math.pow((Math.pow((0.18 * Constants.cameraX), 2)), 1/1.5);
                if(Constants.XPower > .215){
                    Constants.XPower = .15;
                }
            } else if(Constants.cameraX > 1){
                Constants.XPower = -Math.pow((Math.pow((0.18 * Constants.cameraX), 2)), 1/1.5);
                if(Constants.XPower < -.15){
                    Constants.XPower = -.15;
                }
            }
            else{
                Constants.XPower = 0;
            }


            //Y Power
            if(Constants.isSeeing == true){
                if(Constants.leftPPDistance < 700 && Constants.leftPPDistance > 0 || Constants.rightPPDistance < 700 && Constants.rightPPDistance > 0){
                    Constants.YPower = -0.1;
                }
                else if(Constants.averagePPLength > 750){
                    Constants.YPower = ((1.8*Math.pow((Constants.averagePPLength - 150) , 2)) /10000000) + Constants.feedForward;
                    if(Constants.YPower > 0.2){
                        Constants.YPower = 0.2;
                    }
                }
                if(Drive.leftPP.getRange() < 750 && Drive.leftPP.getRange() > 700 
                   && Drive.rightPP.getRange() < 750 && Drive.rightPP.getRange() > 700){
                    Constants.YPower = 0;
                }

            }else if(Constants.isSeeing == false){//If we don't see anything, drive straight
                Constants.YPower = 0.1;
                if(Drive.leftPP.getRange() < 750 && Drive.leftPP.getRange() > 700 
                   && Drive.rightPP.getRange() < 750 && Drive.rightPP.getRange() > 700){
                    Constants.YPower = 0;
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
                Constants.ZPower = 0;
            }
             else{
                Constants.ZPower = ((Constants.leftPPDistance - Constants.rightPPDistance)/750) + Constants.feedForward;

                 if(Constants.ZPower > 0.12){
                    Constants.ZPower = 0.12;
                 }
                 

                //  if(ZPower <= .07 && ZPower > 0){
                //     ZPower = .09;
                // }
                // else if(ZPower >= -.07 && ZPower < 0){
                //     ZPower = -.09;
                // }
             }
        }else{
            Constants.ZPower = 0;
        }
        
        if(Constants.averagePPLength < 4000 && Constants.averagePPLength > 2000){
            Drive.rightPP.setRangingMode(RangingMode.Long, 25);
            Drive.leftPP.setRangingMode(RangingMode.Long, 25);
        } else if(Constants.averagePPLength < 2000 && Constants.averagePPLength > 25){
            Drive.rightPP.setRangingMode(RangingMode.Medium, 25);
            Drive.leftPP.setRangingMode(RangingMode.Medium, 25);
        }else if(Constants.averagePPLength < 4000 && Constants.averagePPLength > 25){
            Drive.rightPP.setRangingMode(RangingMode.Short, 25);
            Drive.leftPP.setRangingMode(RangingMode.Short, 25);
        }
            

            Drive.run(-Constants.XPower, -Constants.YPower, Constants.ZPower, 0);

            if(Drive.leftPP.getRange() > 700 && Drive.leftPP.getRange() < 765
             && Drive.rightPP.getRange() < 765 && Drive.rightPP.getRange() > 700
             && Constants.cameraX > -1 && Constants.cameraX < 1){
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
            SmartDashboard.putNumber("YPower", Constants.YPower);
            SmartDashboard.putNumber("LimelightX", Constants.cameraX);
            SmartDashboard.putNumber("XPower", Constants.XPower);
            SmartDashboard.putNumber("ZPower", Constants.ZPower);
            SmartDashboard.updateValues();
        }

        //Cut Power
        Drive.run(0, 0, 0, 0);
        
        //Shoot

        //Drive to trench
    }
}