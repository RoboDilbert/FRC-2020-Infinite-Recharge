package frc.robot.Autonomous;

import frc.robot.subsystems.*;
import frc.robot.util.*;
import frc.robot.util.sensors.*;

public class Routine{

    public static double XPower = 0;
    public static double YPower = 0;
    public static double ZPower = 0;
    public static double cameraX = 0;

    public TOFSensor tofSensor = new TOFSensor();

    public void run(){

        //TODO Make sure we can fix seeing the target

        //Get in Position
        while(Constants.inPosition == false){
            cameraX = Limelight.getHorizontalOffset();
            tofSensor.lockedOn(Constants.isSeeing);
            tofSensor.getPPLength(Constants.leftPPDistance, Constants.rightPPDistance, Constants.averagePPLength);
            //X Power
            //limelight locked on and X value of limelight
            if(cameraX < 0){
                XPower = (Math.pow((0.025 * cameraX), 2));
            } else if(cameraX > 0){
                XPower = -(Math.pow((0.025 * cameraX), 2));
            }
            else{
                XPower = 0;
            }
            //Y Power
            if(Constants.isSeeing == true){
                YPower = (Constants.averagePPLength - 30)/220;
            }else if(Constants.isSeeing == false){//If we don't see anything, drive straight
                YPower = 0.3;
        }
        if(Constants.averagePPLength < 40){
                ZPower = (Constants.leftPPDistance - Constants.rightPPDistance)/100;
            
        }else{
            ZPower = 0;
        }

            Drive.run(XPower, YPower, ZPower, 0);
            tofSensor.arePPEqual(Constants.inPosition);
        }

        //Cut Power
        Drive.run(0, 0, 0, 0);

        //Shoot

        //Drive to trench
    }
}