package frc.robot.util;

import frc.robot.util.*;

import com.fasterxml.jackson.core.StreamReadFeature;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.util.sensors.Limelight;



public class obstacleAvoidance{

    private static double limelightDistance;
    private static double ppAverageFT;
    private static double difference;

public static void init(){
    Limelight.LimelightInitialize();

}



public boolean detectObstacle(){
    Drive.averagePPLength = (Drive.rightPPDistance + Drive.leftPPDistance)/2;
    limelightDistance = ((72.19 / Math.tan(Math.toRadians(38 + Limelight.ty.getDouble(0.0))))-24/12);
     ppAverageFT = (Drive.averagePPLength / 305) + 2;
     difference = Math.abs(ppAverageFT - limelightDistance);
    if(difference <= 1.5){
        return true;
    }else{
        return false;
    }
}

public static void debugObstacle(){
    SmartDashboard.putNumber("Difference", difference);
}



}