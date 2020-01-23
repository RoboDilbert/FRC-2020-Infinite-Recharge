package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.util.*;
import frc.robot.util.sensors.*;

public class Routine{

    public static double XPower = 0;
    public static double YPower = 0;
    public static double ZPower = 0;
    public static double cameraX = 0;

    //public static TOFSensor tofSensor = new TOFSensor();

    public static void run(){

        Limelight.LimelightInitialize();

        //TODO Make sure we can fix seeing the target

        //Get in Position
        while(Constants.inPosition == false){
            cameraX = Limelight.tx.getDouble(0.0);
           
            if(TOFSensor.rightPP.isRangeValid() || TOFSensor.leftPP.isRangeValid()){
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
            if(cameraX < -2){
                XPower = (Math.pow((0.025 * cameraX), 2));
            } else if(cameraX > 2){
                XPower = -(Math.pow((0.025 * cameraX), 2));
            }
            else{
                XPower = 0;
            }


            //Y Power
            if(Constants.isSeeing == true){
                YPower = (Constants.averagePPLength - (730))/3300;
            }else if(Constants.isSeeing == false){//If we don't see anything, drive straight
                YPower = 0.15;
        }


        if(Constants.averagePPLength < (1008)){
                ZPower = (Constants.leftPPDistance - Constants.rightPPDistance)/500;
            
        }else{
            ZPower = 0;
        }


            Drive.run(XPower, -YPower, -ZPower, 0);

            if(TOFSensor.leftPP.getRange() > 706 && TOFSensor.leftPP.getRange() < 806 && TOFSensor.rightPP.getRange() < 806 && TOFSensor.rightPP.getRange() > 706){
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